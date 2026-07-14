package org.socymet.org.socymet.reportes

import grails.gorm.transactions.Transactional
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.springframework.security.access.annotation.Secured

/**
 * Reporte "Lotes y Compósitos": lista los lotes de complejo EN o SIN compósito en un rango de
 * fechas (opcionalmente por empresa y, cuando estado = EN COMPOSITO, por un compósito específico).
 * Solo se consideran lotes CON análisis de laboratorio (ControlCalidadComplejo); las columnas
 * (leyes Promexbol, PNS, finos, valor, líquido) las calcula CompositoCalculoService (mismo motor
 * del formulario de conformación). `create` = vista previa, `exportarExcel` = descarga XLSX.
 */
@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
class ReporteLotesCompositosController {

    def reporteXlsxBuilderService
    def compositoCalculoService

    def index() { redirect(action: "create", params: params) }

    /** Vista previa: si vienen filtros (datepickerUI), ejecuta la consulta y arma las filas. */
    def create() {
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def composito = params.compositoId ? ReporteCompositoDeLotes.get(params.long('compositoId')) : null
        def estado = params.estado
        Date fi = fechaDe('fechaInicial')
        Date ff = fechaDe('fechaFinal', true)   // hasta 23:59:59
        def modelo = [estado: estado, empresa: empresa, composito: composito,
                      fechaInicial: fi ?: new Date(), fechaFinal: ff ?: new Date()]
        if (estado && fi && ff) {
            modelo.resumen = consultar(estado, fi, ff, empresa, composito)
        }
        modelo
    }

    def exportarExcel() {
        Date fi = fecha(params.fi), ff = fechaFin(params.ff)
        if (!params.estado || !fi || !ff) { flash.message = "Seleccione estado y rango de fechas antes de exportar."; redirect(action: "create", params: params); return }
        def empresa = params.empresaId ? Empresa.get(params.long('empresaId')) : null
        def composito = params.compositoId ? ReporteCompositoDeLotes.get(params.long('compositoId')) : null
        def r = consultar(params.estado, fi, ff, empresa, composito)
        def dfmt = new java.text.SimpleDateFormat('dd/MM/yyyy')

        // Mismas columnas que las tablas del formulario de compósitos + Compósito.
        def columnas = [
            [titulo: 'Lote',          ancho: 16, tipo: 'texto'],
            [titulo: 'Empresa',       ancho: 26, tipo: 'texto'],
            [titulo: 'Fec. Rec.',     ancho: 12, tipo: 'fecha'],
            [titulo: 'P. Bruto [Kg]', ancho: 13, tipo: 'numero', total: 'suma'],
            [titulo: 'Humedad [%]',   ancho: 11, tipo: 'numero'],
            [titulo: 'PNS [Kg]',      ancho: 13, tipo: 'numero', total: 'suma'],
            [titulo: 'Ley Zn [%]',    ancho: 10, tipo: 'numero'],
            [titulo: 'Ley Pb [%]',    ancho: 10, tipo: 'numero'],
            [titulo: 'Ley Ag [DM]',   ancho: 10, tipo: 'numero'],
            [titulo: 'Estado',        ancho: 13, tipo: 'texto'],
            [titulo: 'V. Neto [Bs]',  ancho: 14, tipo: 'numero', total: 'suma'],
            [titulo: 'Líquido [Bs]',  ancho: 14, tipo: 'numero', total: 'suma'],
            [titulo: 'Compósito',     ancho: 16, tipo: 'texto'],
        ]
        def filas = r.lotes.collect { l ->
            [ l.lote, l.nombreEmpresa, l.fechaDeRecepcion, l.pesoBruto, l.humedad, l.pns,
              l.leyZinc, l.leyPlomo, l.leyPlata, (l.liquidado ? 'LIQUIDADO' : 'NO LIQ.'),
              (l.liquidado ? l.valorNeto : 0), (l.liquidado ? l.liquidoPagable : 0), l.nombreComposito ]
        }
        // Promedios ponderados: Humedad (col 4), Ley Zn (6), Ley Pb (7), Ley Ag (8).
        def promedios = [[ etiqueta: 'Promedios ponderados', etiquetaHasta: 3,
                           valores: [4: r.humedadPromedio, 6: r.leyPromedioZinc,
                                     7: r.leyPromedioPlomo, 8: r.leyPromedioPlata] ]]

        byte[] xlsx = reporteXlsxBuilderService.construir([
            nombreHoja: 'Lotes y Compósitos',
            titulo: 'REPORTE DE LOTES Y COMPÓSITOS',
            subtitulos: ["Estado: ${params.estado}${composito ? ' — Compósito: ' + composito.sigla : ''}",
                         (empresa ? "Empresa: ${empresa}" : "Empresa: Todas"),
                         "Periodo: ${dfmt.format(fi)} al ${dfmt.format(ff)}",
                         "Solo se incluyen lotes con análisis de laboratorio registrado."],
            columnas: columnas, filas: filas, filasResumen: promedios
        ])
        response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        response.setHeader('Content-Disposition', 'attachment; filename="reporte_lotes_compositos.xlsx"')
        response.outputStream << xlsx
        response.outputStream.flush()
    }

    /**
     * Consulta: recepciones EN ('nombreComposito' != '-'; o = sigla si se filtra un compósito) o SIN
     * ('= -') compósito, en el rango/empresa. Enriquece con CompositoCalculoService (solo quedan las
     * que tienen análisis de laboratorio) y devuelve el resumen (lotes + totales + ponderados).
     */
    private Map consultar(String estado, Date fi, Date ff, Empresa empresa, ReporteCompositoDeLotes composito) {
        def recs = RecepcionDeComplejo.createCriteria().list {
            if (fi && ff) between('fechaDeRecepcion', fi, ff)
            if (empresa) eq('empresa', empresa)
            if (estado == 'EN COMPOSITO') {
                if (composito) eq('nombreComposito', composito.sigla) else ne('nombreComposito', '-')
            } else {
                eq('nombreComposito', '-')
            }
            order('fechaDeRecepcion', 'asc')
        }
        compositoCalculoService.calcularPorRecepciones(recs.collect { it.id })
    }

    /** Parsea las partes _day/_month/_year del datepickerUI a Date (fin=true → 23:59:59). */
    private Date fechaDe(String campo, boolean fin = false) {
        if (!params["${campo}_year"]) return null
        def base = "${params[campo + '_year']}-${params[campo + '_month']}-${params[campo + '_day']}"
        fin ? new java.text.SimpleDateFormat('yyyy-M-d HH:mm:ss').parse("$base 23:59:59")
            : new java.text.SimpleDateFormat('yyyy-M-d').parse(base)
    }

    private static Date fecha(v) {
        if (!v) return null
        try { return new java.text.SimpleDateFormat('yyyy-MM-dd').parse(v.toString()) } catch (ignored) { return null }
    }
    private static Date fechaFin(v) {
        if (!v) return null
        try { return new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss').parse(v.toString() + ' 23:59:59') } catch (ignored) { return null }
    }
}
