package org.socymet.org.socymet.reportes

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.util.Units
import org.apache.poi.xssf.usermodel.XSSFWorkbook

/**
 * Constructor reutilizable de reportes XLSX (Apache POI) para la categoría REPORTES.
 * Encapsula el formato común: logo de empresa arriba-izquierda, título, subtítulos (filtros/periodo),
 * tabla con cabecera con estilo, filas tipadas (texto/número/fecha) y fila de totales.
 *
 * Uso: ver construir(Map). Pensado para que cada reporte solo describa columnas + filas.
 */
class ReporteXlsxBuilderService {

    static transactional = false

    // Resolución del logo (classpath src/main/resources, y respaldos).
    def grailsApplication
    def assetResourceLocator

    /**
     * Construye un reporte tabular estándar y devuelve sus bytes XLSX.
     *
     * config:
     *   nombreHoja     : String (nombre de la pestaña)
     *   titulo         : String (título principal)
     *   subtitulos     : List<String> (líneas bajo el título: cliente, periodo, filtros…)
     *   columnas       : List<Map> con [titulo, ancho (caracteres), tipo:'texto'|'numero'|'fecha',
     *                                    total: null|'suma'|'ultimo']
     *   filas          : List<List> valores crudos por columna (mismo orden que columnas)
     *   etiquetaTotales: String (opcional, por defecto 'Totales')
     */
    byte[] construir(Map config) {
        def wb = new XSSFWorkbook()
        agregarHoja(wb, config)
        return escribir(wb)
    }

    /**
     * Construye un libro XLSX con varias hojas (mismo formato que construir, una hoja por entrada).
     * @param hojas List<Map> con la misma config que construir(Map) por hoja.
     */
    byte[] construirLibro(List<Map> hojas) {
        def wb = new XSSFWorkbook()
        (hojas ?: []).each { agregarHoja(wb, it) }
        return escribir(wb)
    }

    /** Escribe el workbook a bytes y lo cierra. */
    private static byte[] escribir(XSSFWorkbook wb) {
        def baos = new ByteArrayOutputStream()
        wb.write(baos)
        wb.close()
        baos.toByteArray()
    }

    /** Agrega una hoja tabular estándar al workbook (ver construir(Map) para la config). */
    private void agregarHoja(XSSFWorkbook wb, Map config) {
        List columnas = config.columnas ?: []
        List filas = config.filas ?: []
        int n = columnas.size()

        def sheet = wb.createSheet(config.nombreHoja ?: 'Reporte')

        // ── Estilos ───────────────────────────────────────────────────────────
        def estTitulo = estilo(wb, [bold: true, size: 16, align: HorizontalAlignment.CENTER])
        def estSub    = estilo(wb, [bold: true, size: 10])
        def estHead   = estilo(wb, [bold: true, blanco: true, fondoTeal: true, align: HorizontalAlignment.CENTER, borde: true])
        def estTexto  = estilo(wb, [borde: true])
        def estNum    = estilo(wb, [borde: true, align: HorizontalAlignment.RIGHT, formato: '#,##0.00'])
        def estFecha  = estilo(wb, [borde: true, align: HorizontalAlignment.CENTER, formato: 'dd/mm/yyyy'])
        def estTotNum = estilo(wb, [bold: true, borde: true, align: HorizontalAlignment.RIGHT, formato: '#,##0.00'])
        def estTotTxt = estilo(wb, [bold: true, borde: true, align: HorizontalAlignment.RIGHT])

        // ── Anchos de columna (antes del logo: su tamaño se calcula con estos anchos) ──
        columnas.eachWithIndex { c, i -> sheet.setColumnWidth(i, ((c.ancho ?: 16) as int) * 256) }

        // ── Logo (esquina superior izquierda) ─────────────────────────────────
        try {
            byte[] img = leerLogo()
            if (img) {
                (0..4).each { if (sheet.getRow(it) == null) sheet.createRow(it) }
                int idx = wb.addPicture(img, Workbook.PICTURE_TYPE_PNG)
                colocarLogo(sheet, sheet.createDrawingPatriarch(), idx, 266, 62)
            } else {
                log.warn("ReporteXlsxBuilder: no se encontró el logo logo_empresa.png")
            }
        } catch (e) { log.error("ReporteXlsxBuilder: error cargando el logo", e) }

        int ultimaCol = Math.max(0, n - 1)
        int fila = 5

        // ── Título ─────────────────────────────────────────────────────────────
        celda(sheet, fila, 0, config.titulo ?: '', estTitulo)
        if (n > 1) sheet.addMergedRegion(new CellRangeAddress(fila, fila, 0, ultimaCol))
        fila++

        // ── Subtítulos (cliente, periodo, filtros) ──────────────────────────────
        (config.subtitulos ?: []).each { st ->
            celda(sheet, fila, 0, st?.toString() ?: '', estSub)
            if (n > 1) sheet.addMergedRegion(new CellRangeAddress(fila, fila, 0, ultimaCol))
            fila++
        }
        fila++   // fila en blanco

        // ── Banda de grupos (opcional): fila de encabezados fusionados sobre la cabecera ──
        // config.grupos = List de [titulo, desde, hasta] (índices de columna 0-based, inclusive).
        if (config.grupos) {
            def rGrupo = sheet.createRow(fila)
            (0..<n).each { rGrupo.createCell(it).setCellStyle(estHead) }  // bordes/relleno en toda la fila
            config.grupos.each { g ->
                int desde = (g.desde ?: 0) as int, hasta = (g.hasta ?: desde) as int
                def cell = rGrupo.getCell(desde) ?: rGrupo.createCell(desde)
                cell.setCellValue(g.titulo ?: ''); cell.setCellStyle(estHead)
                if (hasta > desde) sheet.addMergedRegion(new CellRangeAddress(fila, fila, desde, hasta))
            }
            fila++
        }

        // ── Cabecera de la tabla ────────────────────────────────────────────────
        def rHead = sheet.createRow(fila)
        columnas.eachWithIndex { c, i -> def cell = rHead.createCell(i); cell.setCellValue(c.titulo ?: ''); cell.setCellStyle(estHead) }
        fila++

        // ── Filas de datos ──────────────────────────────────────────────────────
        filas.each { valores ->
            def r = sheet.createRow(fila)
            columnas.eachWithIndex { c, i ->
                def cell = r.createCell(i)
                def v = (i < valores.size()) ? valores[i] : null
                switch (c.tipo) {
                    case 'numero': cell.setCellValue(num(v)); cell.setCellStyle(estNum); break
                    case 'fecha':  if (v instanceof Date) cell.setCellValue((Date) v); cell.setCellStyle(estFecha); break
                    default:       cell.setCellValue(v != null ? v.toString() : ''); cell.setCellStyle(estTexto)
                }
            }
            fila++
        }

        // ── Fila de totales (si alguna columna lo pide) ─────────────────────────
        int primeraTotal = columnas.findIndexOf { it.total }
        if (primeraTotal >= 0) {
            def rTot = sheet.createRow(fila)
            // Etiqueta a la izquierda, fusionando las columnas previas a la primera con total
            def cLbl = rTot.createCell(0); cLbl.setCellValue(config.etiquetaTotales ?: 'Totales'); cLbl.setCellStyle(estTotTxt)
            if (primeraTotal > 1) {
                sheet.addMergedRegion(new CellRangeAddress(fila, fila, 0, primeraTotal - 1))
                (1..<primeraTotal).each { rTot.createCell(it).setCellStyle(estTotTxt) }
            } else if (primeraTotal == 1) {
                rTot.createCell(0).setCellStyle(estTotTxt)
            }
            (primeraTotal..<n).each { ci ->
                def col = columnas[ci]
                def cell = rTot.createCell(ci)
                if (col.total == 'suma') { cell.setCellValue(sumaColumna(filas, ci, false)); cell.setCellStyle(estTotNum) }
                else if (col.total == 'sumaPositivos') { cell.setCellValue(sumaColumna(filas, ci, true)); cell.setCellStyle(estTotNum) }
                else if (col.total == 'ultimo') { cell.setCellValue(filas ? num(filas[-1][ci]) : 0.0d); cell.setCellStyle(estTotNum) }
                else { cell.setCellStyle(col.tipo == 'numero' ? estTotNum : estTotTxt) }
            }
            fila++   // avanzar para que las filas de resumen no colisionen con la de totales
        }

        // ── Filas de resumen adicionales (p. ej. promedios ponderados) ──────────
        // Cada entrada: [etiqueta:String, etiquetaHasta:int (fusiona cols 0..hasta), valores:Map<Integer,Number>]
        (config.filasResumen ?: []).each { res ->
            def rr = sheet.createRow(fila)
            def cLbl = rr.createCell(0); cLbl.setCellValue(res.etiqueta ?: ''); cLbl.setCellStyle(estTotTxt)
            int hasta = (res.etiquetaHasta ?: 0) as int
            if (hasta > 0) {
                sheet.addMergedRegion(new CellRangeAddress(fila, fila, 0, hasta))
                (1..hasta).each { rr.createCell(it).setCellStyle(estTotTxt) }
            }
            (res.valores ?: [:]).each { ci, val ->
                def cell = rr.createCell(ci as int); cell.setCellValue(num(val)); cell.setCellStyle(estTotNum)
            }
            fila++
        }
    }

    // ── Helpers ────────────────────────────────────────────────────────────────

    private static void celda(Sheet sheet, int fila, int col, def valor, CellStyle estilo) {
        def r = sheet.getRow(fila) ?: sheet.createRow(fila)
        def c = r.createCell(col)
        c.setCellValue(valor?.toString() ?: '')
        c.setCellStyle(estilo)
    }

    /** Crea un CellStyle a partir de un mapa de opciones (bold, size, align, formato, borde, blanco, fondoTeal). */
    private CellStyle estilo(XSSFWorkbook wb, Map o) {
        def s = wb.createCellStyle()
        if (o.bold || o.blanco || o.size) {
            def f = wb.createFont()
            if (o.bold) f.bold = true
            if (o.blanco) f.color = IndexedColors.WHITE.index
            if (o.size) f.fontHeightInPoints = (short) o.size
            s.font = f
        }
        if (o.align) s.alignment = (HorizontalAlignment) o.align
        if (o.formato) s.dataFormat = wb.createDataFormat().getFormat((String) o.formato)
        if (o.fondoTeal) { s.fillForegroundColor = IndexedColors.TEAL.index; s.fillPattern = FillPatternType.SOLID_FOREGROUND }
        if (o.borde) {
            s.borderTop = BorderStyle.THIN; s.borderBottom = BorderStyle.THIN
            s.borderLeft = BorderStyle.THIN; s.borderRight = BorderStyle.THIN
        }
        s
    }

    private static double num(def v) {
        if (v == null) return 0.0d
        if (v instanceof Number) return ((Number) v).doubleValue()
        try { return new BigDecimal(v.toString()).doubleValue() } catch (ignored) { return 0.0d }
    }

    private static double sumaColumna(List filas, int ci, boolean soloPositivos) {
        double t = 0.0d
        filas.each { fila ->
            if (ci < fila.size()) {
                double v = num(fila[ci])
                if (!soloPositivos || v >= 0) t += v   // soloPositivos: ignora valores < 0
            }
        }
        t
    }

    /**
     * Coloca la imagen anclada en A1 con tamaño fijo (anchoPx x altoPx), calculando el ancla de
     * destino con los anchos/altos REALES de columnas y filas. No usa Picture.resize() (que en esta
     * versión de POI puede dejar la imagen en tamaño 0 → invisible).
     */
    private void colocarLogo(Sheet sheet, Drawing dibujo, int picIdx, int anchoPx, int altoPx) {
        def anchor = sheet.workbook.creationHelper.createClientAnchor()
        anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE)
        anchor.setCol1(0); anchor.setRow1(0); anchor.setDx1(0); anchor.setDy1(0)
        int restanteW = anchoPx, col = 0
        while (true) {
            int wCol = Math.max(1, (int) sheet.getColumnWidthInPixels(col))
            if (restanteW <= wCol) { anchor.setCol2(col); anchor.setDx2(Units.pixelToEMU(restanteW)); break }
            restanteW -= wCol; col++
        }
        int restanteH = altoPx, row = 0
        while (true) {
            def r = sheet.getRow(row) ?: sheet.createRow(row)
            int hRow = Math.max(1, (int) Math.round((r.heightInPoints ?: sheet.defaultRowHeightInPoints) * 96f / 72f))
            if (restanteH <= hRow) { anchor.setRow2(row); anchor.setDy2(Units.pixelToEMU(restanteH)); break }
            restanteH -= hRow; row++
        }
        dibujo.createPicture(anchor, picIdx)
    }

    /** Lee los bytes del logo probando varias fuentes (dev y WAR). */
    private byte[] leerLogo() {
        for (String ruta : ['classpath:reportes/logo_empresa.png', 'classpath:logo_empresa.png']) {
            try {
                def res = grailsApplication?.mainContext?.getResource(ruta)
                if (res?.exists()) return res.inputStream.bytes
            } catch (ignored) { }
        }
        for (String ruta : ['reportes/logo_empresa.png', 'logo_empresa.png', 'assets/logo_empresa.png', 'META-INF/assets/logo_empresa.png']) {
            def is = this.class.classLoader.getResourceAsStream(ruta)
            if (is) { try { return is.bytes } finally { is.close() } }
        }
        try {
            def r = assetResourceLocator?.findAssetForURI('logo_empresa.png')
            if (r?.exists()) return r.inputStream.bytes
        } catch (ignored) { }
        def f = new File('grails-app/assets/images/logo_empresa.png')
        if (f.exists()) return f.bytes
        return null
    }
}
