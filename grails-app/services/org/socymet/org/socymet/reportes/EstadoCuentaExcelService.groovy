package org.socymet.org.socymet.reportes

/**
 * Genera el reporte "Estado de Cuenta de Cliente" en XLSX. Solo define columnas y filas;
 * el formato (logo, estilos, encabezado, totales) lo aporta ReporteXlsxBuilder.
 */
class EstadoCuentaExcelService {

    static transactional = false

    def reporteXlsxBuilderService

    /**
     * Construye el libro XLSX y devuelve sus bytes.
     * @param comprobantePorId  mapa id(EstadoDeCuenta) → texto de comprobante (p. ej. "12/26")
     */
    byte[] generar(cliente, Date fechaInicial, Date fechaFinal, List movimientos, Map comprobantePorId) {
        def fmt = new java.text.SimpleDateFormat('dd/MM/yyyy')

        def columnas = [
            [titulo: 'Fecha',          ancho: 14, tipo: 'fecha'],
            [titulo: 'N° Comprobante', ancho: 16, tipo: 'texto'],
            [titulo: 'Detalle',        ancho: 48, tipo: 'texto'],
            [titulo: 'Debe [Bs]',      ancho: 16, tipo: 'numero', total: 'suma'],
            [titulo: 'Haber [Bs]',     ancho: 16, tipo: 'numero', total: 'suma'],
            [titulo: 'Saldo [Bs]',     ancho: 16, tipo: 'numero', total: 'ultimo'],
        ]

        def filas = movimientos.collect { ec ->
            [ ec.fecha,
              (comprobantePorId[ec.id] ?: (ec.numeroComprobante?.toString() ?: '')),
              ec.detalle ?: '',
              ec.debe ?: 0, ec.haber ?: 0, ec.saldo ?: 0 ]
        }

        reporteXlsxBuilderService.construir([
            nombreHoja: 'Estado de Cuenta',
            titulo: 'ESTADO DE CUENTA DE CLIENTE',
            subtitulos: [
                "Cliente: ${cliente?.nombre ?: ''}",
                "Periodo: ${fechaInicial ? fmt.format(fechaInicial) : ''} al ${fechaFinal ? fmt.format(fechaFinal) : ''}"
            ],
            columnas: columnas,
            filas: filas
        ])
    }
}
