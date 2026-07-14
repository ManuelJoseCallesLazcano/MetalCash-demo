package org.socymet.org.socymet.reportes

/**
 * Genera el reporte "Estado de Cuenta de Transporte" (por Automóvil) en XLSX.
 * Solo define columnas y filas; el formato (logo, estilos, encabezado, totales)
 * lo aporta ReporteXlsxBuilder. Espejo de EstadoCuentaExcelService (cliente).
 */
class EstadoCuentaTransporteExcelService {

    static transactional = false

    def reporteXlsxBuilderService

    /**
     * Construye el libro XLSX y devuelve sus bytes.
     * @param comprobantePorId  mapa id(EstadoCuentaTransporte) → comprobante del origen ("12/26")
     */
    byte[] generar(automovil, Date fechaInicial, Date fechaFinal, List movimientos, Map comprobantePorId) {
        reporteXlsxBuilderService.construir(hojaConfig(automovil, fechaInicial, fechaFinal, movimientos, comprobantePorId))
    }

    /**
     * Devuelve la config de hoja (columnas + filas) del estado de cuenta, para reutilizarla
     * como una pestaña dentro de un libro multi-hoja (p. ej. el Reporte de Pago de Transporte).
     */
    Map hojaConfig(automovil, Date fechaInicial, Date fechaFinal, List movimientos, Map comprobantePorId) {
        def fmt = new java.text.SimpleDateFormat('dd/MM/yyyy')

        def columnas = [
            [titulo: 'Fecha',                 ancho: 14, tipo: 'fecha'],
            [titulo: 'N° Comprobante',        ancho: 16, tipo: 'texto'],
            [titulo: 'Detalle',               ancho: 48, tipo: 'texto'],
            [titulo: 'Ingreso [Bs]',          ancho: 16, tipo: 'numero', total: 'suma'],
            [titulo: 'Egreso [Bs]',           ancho: 16, tipo: 'numero', total: 'suma'],
            [titulo: 'Saldo Disponible [Bs]', ancho: 20, tipo: 'numero', total: 'ultimo'],
        ]

        def filas = movimientos.collect { ec ->
            [ ec.fecha,
              (comprobantePorId[ec.id] ?: ''),
              ec.descripcion ?: '',
              ec.ingreso ?: 0, ec.egreso ?: 0, ec.saldo ?: 0 ]
        }

        [
            nombreHoja: 'Estado Cuenta Transporte',
            titulo: 'ESTADO DE CUENTA DE TRANSPORTE',
            subtitulos: [
                "Automóvil: ${automovil?.placa ?: ''}",
                "Periodo: ${fechaInicial ? fmt.format(fechaInicial) : ''} al ${fechaFinal ? fmt.format(fechaFinal) : ''}"
            ],
            columnas: columnas,
            filas: filas
        ]
    }
}
