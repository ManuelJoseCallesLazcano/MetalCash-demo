package org.socymet.org.socymet.reportes
import grails.gorm.transactions.Transactional

import grails.converters.JSON
import org.socymet.calidad.ControlCalidadComplejo
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecUser
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA'])
@Transactional
class ReporteCompositoDeLotesController {
    def reporteXlsxBuilderService
    def compositoCalculoService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", reabrir: "POST", anular: "POST"]

    transient springSecurityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = params.sort ?: "id"
        params.order = params.order ?: "desc"

        // Buscador: N° de compósito, nombre (sigla), nombre de comprador o nombre de ingenio
        def q = params.q?.trim()
        def numero = (q && q.isInteger()) ? (q as Integer) : null

        def results = ReporteCompositoDeLotes.createCriteria().list(
                max: params.max, offset: params.offset ?: 0,
                sort: params.sort, order: params.order) {
            if (q) {
                createAlias('comprador', 'c', org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
                createAlias('ingenio', 'ing', org.hibernate.sql.JoinType.LEFT_OUTER_JOIN)
                or {
                    ilike('sigla', "%${q}%")
                    ilike('c.nombreComprador', "%${q}%")
                    ilike('ing.nombreIngenio', "%${q}%")
                    if (numero != null) eq('numeroComposito', numero)
                }
            }
        }
        [reporteCompositoDeLotesInstanceList: results, reporteCompositoDeLotesInstanceTotal: results.totalCount, q: q]
    }

    def create() {
        def u = springSecurityService.getCurrentUser() as SecUser
        def instance = new ReporteCompositoDeLotes(params)
        instance.deposito = u.deposito
        // elaboradoPor y fechaDeElaboracion se asignan en el dominio (beforeValidate).
        if (!instance.estadoDelComposito) instance.estadoDelComposito = 'PROVISIONAL'
        if (!instance.destino)            instance.destino = 'VENTA'
        if (!instance.ordenarElemento)    instance.ordenarElemento = 'ZINC'
        [reporteCompositoDeLotesInstance: instance]
    }

    def save() {
        def instance = new ReporteCompositoDeLotes(params)
        def u = springSecurityService.getCurrentUser() as SecUser
        if (instance.deposito == null) instance.deposito = u.deposito

        def ids = parseIds(params.recepcionIds)
        if (!ids) {
            flash.message = 'Seleccione al menos un lote para el compósito.'
            render(view: "create", model: [reporteCompositoDeLotesInstance: instance])
            return
        }

        // Recálculo AUTORITATIVO en backend (fuente única); no se confía en los totales del cliente.
        def resumen = compositoCalculoService.armarComposito(instance, ids)
        if (!instance.save(flush: true)) {
            render(view: "create", model: [reporteCompositoDeLotesInstance: instance])
            return
        }
        compositoCalculoService.poblarHijos(instance, resumen)
        compositoCalculoService.reservar(instance, ids)   // D6: reservar aun en PROVISIONAL

        flash.message = message(code: 'default.created.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'Compósito'), instance.sigla])
        redirect(action: "show", id: instance.id)
    }

    /** recepcionIds coma-separados o repetidos → List<Long> único. */
    private static List<Long> parseIds(v) {
        (v?.toString()?.split(',') ?: []).collect { it?.toString()?.trim() }.findAll { it }.collect { it.toLong() }.unique()
    }

    def show(Long id) {
        def reporteCompositoDeLotesInstance = ReporteCompositoDeLotes.get(id)
        if (!reporteCompositoDeLotesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
            return
        }

        def detalle = CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(reporteCompositoDeLotesInstance, [sort: 'lote'])
        [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance,
         detalle: detalle,
         humedadPromedio: humedadPonderada(detalle)]
    }

    /** Humedad ponderada del conjunto desde el detalle: (ΣPNH − ΣPNS)/ΣPNH·100, PNH = PNS/(1−H/100). */
    private static BigDecimal humedadPonderada(detalle) {
        def rm = java.math.RoundingMode.HALF_UP
        BigDecimal sPNS = 0.0G, sPNH = 0.0G
        (detalle ?: []).each { d ->
            BigDecimal pns = (d.kilosNetosSecos ?: 0.0G)
            BigDecimal h = (d.porcentajeHumedad ?: 0.0G)
            BigDecimal factor = 1.0G - h.divide(100.0G, 12, rm)
            sPNS += pns
            sPNH += (factor <= 0.0G) ? pns : pns.divide(factor, 12, rm)
        }
        (sPNH == 0.0G) ? 0.0G : (sPNH - sPNS).divide(sPNH, 12, rm) * 100.0G
    }

    def edit(Long id) {
        def reporteCompositoDeLotesInstance = ReporteCompositoDeLotes.get(id)
        if (!reporteCompositoDeLotesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
            return
        }

        // Un compósito DEFINITIVO o ANULADO es inmutable (D7): no se puede editar.
        if(reporteCompositoDeLotesInstance.estadoDelComposito.equals("DEFINITIVO") || reporteCompositoDeLotesInstance.anulado){
            flash.message = 'Un compósito DEFINITIVO o anulado no se puede modificar.'
            redirect(action: "list")
            return
        }

        def sdf = new java.text.SimpleDateFormat("dd/MM/yyyy")
        def preseleccion = CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(reporteCompositoDeLotesInstance).collect { d ->
            [ recepcionId    : d.recepcionId as Long,
              lote           : d.lote,
              nombreEmpresa  : d.nombreEmpresa,
              fechaDeRecepcion: d.fechaDeRecepcion ? sdf.format(d.fechaDeRecepcion) : '',
              pesoBruto      : d.pesoBruto,
              porcentajeHumedad: d.porcentajeHumedad,
              leyZinc        : d.porcentajeZincFinal,
              leyPlomo       : d.porcentajePlomoFinal,
              leyPlata       : d.porcentajePlataFinal,
              kilosNetosSecos: d.kilosNetosSecos,
              kilosFinosZinc : d.kilosFinosZinc,
              kilosFinosPlomo: d.kilosFinosPlomo,
              kilosFinosPlata: d.kilosFinosPlata,
              liquidado      : (d.liquidacionId != null && d.liquidacionId != 0),
              valorNeto      : d.valorNetoMineralEnBolivianos,
              liquidoPagable : d.liquidoPagable ]
        }

        [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance,
         preseleccionJson: (preseleccion as JSON).toString()]
    }

    def update(Long id, Long version) {
        def reporteCompositoDeLotesInstance = ReporteCompositoDeLotes.get(id)
        if (!reporteCompositoDeLotesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reporteCompositoDeLotesInstance.version > version) {
                reporteCompositoDeLotesInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes')] as Object[],
                        "Another user has updated this ReporteCompositoDeLotes while you were editing")
                render(view: "edit", model: [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance])
                return
            }
        }

        // DEFINITIVO o ANULADO es inmutable (D7)
        if (reporteCompositoDeLotesInstance.estadoDelComposito == 'DEFINITIVO' || reporteCompositoDeLotesInstance.anulado) {
            flash.message = 'Un compósito DEFINITIVO o anulado no se puede modificar.'
            redirect(action: "list")
            return
        }

        def ids = parseIds(params.recepcionIds)
        if (!ids) {
            flash.message = 'Seleccione al menos un lote para el compósito.'
            render(view: "edit", model: [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance])
            return
        }

        // Liberar la selección anterior y limpiar los hijos antes de re-armar.
        def oldIds = CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(reporteCompositoDeLotesInstance).collect { it.recepcionId as Long }
        compositoCalculoService.liberar(oldIds, reporteCompositoDeLotesInstance.sigla)
        CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(reporteCompositoDeLotesInstance)*.delete()
        CompositoLotesParticipacion.findAllByReporteCompositoDeLotes(reporteCompositoDeLotesInstance)*.delete()

        reporteCompositoDeLotesInstance.properties = params
        def resumen = compositoCalculoService.armarComposito(reporteCompositoDeLotesInstance, ids)
        if (!reporteCompositoDeLotesInstance.save(flush: true)) {
            render(view: "edit", model: [reporteCompositoDeLotesInstance: reporteCompositoDeLotesInstance])
            return
        }
        compositoCalculoService.poblarHijos(reporteCompositoDeLotesInstance, resumen)
        compositoCalculoService.reservar(reporteCompositoDeLotesInstance, ids)

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'Compósito'), reporteCompositoDeLotesInstance.sigla])
        redirect(action: "show", id: reporteCompositoDeLotesInstance.id)
    }

    def delete(Long id) {
        def reporteCompositoDeLotesInstance = ReporteCompositoDeLotes.get(id)
        if (!reporteCompositoDeLotesInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
            return
        }

        try {
            reporteCompositoDeLotesInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reporteCompositoDeLotes.label', default: 'ReporteCompositoDeLotes'), id])
            redirect(action: "show", id: id)
        }
    }

    /** Reabrir un compósito DEFINITIVO → PROVISIONAL para poder editarlo (D7). Los lotes siguen reservados. */
    @Secured(['ROLE_ADMIN'])
    def reabrir(Long id) {
        def instance = ReporteCompositoDeLotes.get(id)
        if (!instance) { redirect(action: "list"); return }
        if (instance.anulado) {
            flash.message = 'No se puede reabrir un compósito anulado.'
        } else if (instance.estadoDelComposito != 'DEFINITIVO') {
            flash.message = 'Solo se reabren compósitos DEFINITIVOS.'
        } else {
            instance.estadoDelComposito = 'PROVISIONAL'
            instance.save(flush: true)
            flash.message = "Compósito ${instance.sigla} reabierto (PROVISIONAL)."
        }
        redirect(action: "show", id: id)
    }

    /** Anular un compósito (baja lógica, D7): libera los lotes reservados y conserva el registro. */
    @Secured(['ROLE_ADMIN'])
    def anular(Long id) {
        def instance = ReporteCompositoDeLotes.get(id)
        if (!instance) { redirect(action: "list"); return }
        if (instance.anulado) {
            flash.message = 'El compósito ya está anulado.'
            redirect(action: "show", id: id); return
        }
        def ids = CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(instance).collect { it.recepcionId as Long }
        compositoCalculoService.liberar(ids, instance.sigla)   // libera lotes (HQL)
        instance.anulado = true
        instance.save(flush: true)
        flash.message = "Compósito ${instance.sigla} anulado; ${ids.size()} lote(s) liberado(s)."
        redirect(action: "show", id: id)
    }

    /** Búsqueda asíncrona de compósitos para Select2 (por sigla o N°); solo no anulados. */
    @Secured(['ROLE_ADMIN','ROLE_LIQUIDACION','ROLE_CAJA','ROLE_REPORTES'])
    def compositosBusquedaJSON() {
        def term = params.q?.trim()
        def results = ReporteCompositoDeLotes.createCriteria().list(max: 20) {
            eq('anulado', false)
            if (term) {
                or {
                    ilike('sigla', "%${term}%")
                    if (term.isInteger()) eq('numeroComposito', term as Integer)
                }
            }
            order('sigla', 'asc')
        }.collect { [id: it.id, text: it.sigla] }
        render([results: results] as JSON)
    }

    /**
     * F3 — Lotes disponibles para conformar un compósito. Universo (§5.1): recepciones de complejo
     * CON análisis de laboratorio (ControlCalidadComplejo) y NO reservadas (nombreComposito = "-").
     * Filtros (§5.2): empresa, rangos de ley Zn/Pb/Ag (Promexbol, D4; individual/conjunta/combinada),
     * búsqueda por lote, rango de fechas. Orden (§5.3) asc/desc por ley de cualquier elemento, fecha
     * o lote. Paginación por max/offset. Enriquecido (PNS/finos/valor) por CompositoCalculoService (F2).
     */
    def lotesDisponiblesJSON() {
        Empresa empresa = params.empresaId && params.empresaId != "null" ? Empresa.get(params.empresaId.toLong()) : null
        BigDecimal minZn = num(params.leyMinZinc),  maxZn = num(params.leyMaxZinc)
        BigDecimal minPb = num(params.leyMinPlomo), maxPb = num(params.leyMaxPlomo)
        BigDecimal minAg = num(params.leyMinPlata), maxAg = num(params.leyMaxPlata)
        Date fi = fecha(params.fechaInicial), ff = fecha(params.fechaFinal)
        String loteQ = params.lote?.trim()
        String ordenarPor = (params.ordenarPor ?: 'LOTE').toString().toUpperCase()
        String orden = (params.orden ?: 'desc').toString().toLowerCase() == 'asc' ? 'asc' : 'desc'
        int max = Math.min((params.max ?: '500').toInteger(), 1000)
        int offset = (params.offset ?: '0').toInteger()

        def controles = ControlCalidadComplejo.createCriteria().list(max: max, offset: offset) {
            createAlias('recepcionDeComplejo', 'r')
            eq('r.nombreComposito', '-')
            eq('r.estadoAnalisis', 'CON ANALISIS')
            if (empresa) eq('r.empresa', empresa)
            if (fi && ff) { ge('r.fechaDeRecepcion', fi); lt('r.fechaDeRecepcion', ff + 1) }
            if (loteQ) ilike('r.codigoLote', "%${loteQ}%")
            if (minZn != null) ge('porcentajeZincPromexbol', minZn)
            if (maxZn != null) le('porcentajeZincPromexbol', maxZn)
            if (minPb != null) ge('porcentajePlomoPromexbol', minPb)
            if (maxPb != null) le('porcentajePlomoPromexbol', maxPb)
            if (minAg != null) ge('porcentajePlataPromexbol', minAg)
            if (maxAg != null) le('porcentajePlataPromexbol', maxAg)
            switch (ordenarPor) {
                case 'ZINC':  order('porcentajeZincPromexbol', orden);  break
                case 'PLOMO': order('porcentajePlomoPromexbol', orden); break
                case 'PLATA': order('porcentajePlataPromexbol', orden); break
                case 'FECHA': order('r.fechaDeRecepcion', orden);       break
                default:      order('r.codigoLote', orden)
            }
        }

        def recepcionIds = controles.collect { it.recepcionDeComplejo.id }

        // Enriquecimiento (PNS/finos/valor) reusando el motor puro; conserva el orden de recepcionIds.
        def resumen = compositoCalculoService.calcularPorRecepciones(recepcionIds)
        def sdf = new java.text.SimpleDateFormat("dd/MM/yyyy")

        def filas = resumen.lotes.collect { l ->
            [
                recepcionId       : l.recepcionId,
                liquidacionId     : l.liquidacionId,
                lote              : l.lote,
                fechaDeRecepcion  : l.fechaDeRecepcion ? sdf.format(l.fechaDeRecepcion) : '',
                nombreEmpresa     : l.nombreEmpresa,
                departamento      : l.departamento,
                municipio         : l.municipio,
                proveedor         : l.proveedor,
                pesoBruto         : l.pesoBruto,
                porcentajeHumedad : l.humedad,
                kilosNetosSecos   : l.pns,
                leyZinc           : l.leyZinc,
                leyPlomo          : l.leyPlomo,
                leyPlata          : l.leyPlata,
                kilosFinosZinc    : l.kilosFinosZinc,
                kilosFinosPlomo   : l.kilosFinosPlomo,
                kilosFinosPlata   : l.kilosFinosPlata,
                liquidado         : l.liquidado,
                valorNeto         : l.valorNeto,
                liquidoPagable    : l.liquidoPagable
            ]
        }

        render([lotes: filas, total: filas.size()] as JSON)
    }

    /**
     * F3 — Resumen autoritativo (totales, ponderados, participación) de una selección de lotes.
     * Recibe recepcionIds (coma-separados o repetidos) y delega en CompositoCalculoService (F2).
     */
    def resumenSeleccionJSON() {
        def ids = (params.list('recepcionIds') ?: (params.recepcionIds?.toString()?.split(',') ?: []))
                .collect { it?.toString()?.trim() }.findAll { it }.collect { it.toLong() }
        def r = compositoCalculoService.calcularPorRecepciones(ids)
        r.remove('lotes')   // el resumen no necesita el detalle por lote
        render(r as JSON)
    }

    /** Parseo seguro de BigDecimal desde params (null si vacío/ inválido). */
    private static BigDecimal num(v) {
        if (v == null) return null
        String s = v.toString().trim()
        if (s == '') return null
        try { return new BigDecimal(s) } catch (ignored) { return null }
    }

    /** Parseo seguro de fecha dd/MM/yyyy (null si vacío/ inválido). */
    private static Date fecha(v) {
        if (v == null) return null
        String s = v.toString().trim()
        if (s == '') return null
        try { return new java.text.SimpleDateFormat("dd/MM/yyyy").parse(s) } catch (ignored) { return null }
    }

    /** F6 — Exporta el compósito a XLSX (2 hojas: Lotes + Participación) usando el builder compartido. */
    def exportarExcel(Long id) {
        def c = ReporteCompositoDeLotes.get(id)
        if (!c) { flash.message = 'Compósito no encontrado.'; redirect(action: "list"); return }
        def detalle = CompositoDeLotesDetalle.findAllByReporteCompositoDeLotes(c, [sort: 'lote'])
        def participacion = CompositoLotesParticipacion.findAllByReporteCompositoDeLotes(c)
        def fmt = new java.text.SimpleDateFormat('dd/MM/yyyy')

        def columnasLotes = [
            [titulo: 'Lote',          ancho: 16, tipo: 'texto'],
            [titulo: 'Empresa',       ancho: 24, tipo: 'texto'],
            [titulo: 'Proveedor',     ancho: 22, tipo: 'texto'],
            [titulo: 'Municipio',     ancho: 16, tipo: 'texto'],
            [titulo: 'Fec. Rec.',     ancho: 12, tipo: 'fecha'],
            [titulo: 'P. Bruto [Kg]',   ancho: 13, tipo: 'numero', total: 'suma'],
            [titulo: 'Humedad [%]',     ancho: 11, tipo: 'numero'],
            [titulo: 'PNS [Kg]',        ancho: 13, tipo: 'numero', total: 'suma'],
            [titulo: 'Ley Zn [%]',      ancho: 10, tipo: 'numero'],
            [titulo: 'Ley Pb [%]',      ancho: 10, tipo: 'numero'],
            [titulo: 'Ley Ag [DM]',     ancho: 10, tipo: 'numero'],
            [titulo: 'K.F. Zn [Kg]',    ancho: 12, tipo: 'numero', total: 'suma'],
            [titulo: 'K.F. Pb [Kg]',    ancho: 12, tipo: 'numero', total: 'suma'],
            [titulo: 'K.F. Ag [Kg]',    ancho: 12, tipo: 'numero', total: 'suma'],
            [titulo: 'V. Neto [Bs]',    ancho: 14, tipo: 'numero', total: 'suma'],
            [titulo: 'Líq. Pagable [Bs]', ancho: 16, tipo: 'numero', total: 'suma'],
        ]
        def filasLotes = detalle.collect { d ->
            def liquidado = (d.liquidacionId != null && d.liquidacionId != 0)
            [ d.lote, d.nombreEmpresa, d.proveedor, d.municipio, d.fechaDeRecepcion,
              d.pesoBruto, d.porcentajeHumedad, d.kilosNetosSecos,
              d.porcentajeZincFinal, d.porcentajePlomoFinal, d.porcentajePlataFinal,
              d.kilosFinosZinc, d.kilosFinosPlomo, d.kilosFinosPlata,
              liquidado ? d.valorNetoMineralEnBolivianos : 0, liquidado ? d.liquidoPagable : 0 ]
        }

        def columnasPart = [
            [titulo: 'Empresa',        ancho: 26, tipo: 'texto'],
            [titulo: 'Departamento',   ancho: 18, tipo: 'texto'],
            [titulo: 'Municipio',      ancho: 18, tipo: 'texto'],
            [titulo: 'PNS [Kg]',       ancho: 14, tipo: 'numero', total: 'suma'],
            [titulo: 'Particip. [%]',  ancho: 12, tipo: 'numero'],
        ]
        def filasPart = participacion.collect { p -> [p.nombreEmpresa, p.departamento, p.municipio, p.kilosNetosSecos, p.porcentajeParticipacion] }

        def subt = ["Sigla: ${c.sigla}    N°: ${c.numeroComposito ?: '-'}",
                    "Destino: ${c.destino} (${c.nombreDestino})",
                    "Estado: ${c.estadoDelComposito}${c.anulado ? ' — ANULADO' : ''}    Elaborado por: ${c.elaboradoPor}    Fecha: ${c.fechaDeElaboracion ? fmt.format(c.fechaDeElaboracion) : '-'}"]

        // Fila de promedios ponderados: Humedad (col 6), Ley Zn (8), Ley Pb (9), Ley Ag (10).
        def promedios = [[ etiqueta: 'Promedios ponderados', etiquetaHasta: 4,
                           valores: [6: humedadPonderada(detalle), 8: c.leyPromedioZinc,
                                     9: c.leyPromedioPlomo, 10: c.leyPromedioPlata] ]]

        byte[] xlsx = reporteXlsxBuilderService.construirLibro([
            [nombreHoja: 'Lotes', titulo: 'COMPÓSITO DE LOTES', subtitulos: subt, columnas: columnasLotes, filas: filasLotes, filasResumen: promedios],
            [nombreHoja: 'Participación', titulo: 'PARTICIPACIÓN POR EMPRESA', subtitulos: subt, columnas: columnasPart, filas: filasPart]
        ])
        response.setContentType('application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        response.setHeader('Content-Disposition', "attachment; filename=\"composito_${c.sigla.replace(' ', '_')}.xlsx\"")
        response.outputStream << xlsx
        response.outputStream.flush()
    }
}
