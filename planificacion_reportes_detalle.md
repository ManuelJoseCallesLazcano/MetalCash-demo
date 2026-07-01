# Plan — Actualización, rediseño y mejora de los reportes (categoría REPORTES)

Continúa el PoC de **ReporteEstadoCuentaCliente** (Apache POI / XLSX + logo), que queda como **plantilla de referencia**.

## 0. Decisiones confirmadas (2026-06-29)
1. **Alcance:** solo los reportes **activos** del menú. Los comentados (Lotes y Análisis, Detalle de Compras, Canje Tornaguías, gráficos) quedan fuera.
2. **Excluir** "Lotes y Compósitos" (depende de compósitos, no es foco).
3. **UX:** vista previa en pantalla + exportar (como Estado de Cuenta), no descarga directa.
4. **Formato:** **solo XLSX** (Apache POI). Sin PDF → se quitan los botones de PDF.

### Reportes en alcance (7)
| # | Menú | Controller | Hoy |
|---|------|-----------|-----|
| 1 | Lotes Recepcionados | `reporteLotesRecepcionados` | JXL (.xls), vista legacy |
| 2 | Lotes Liquidados | `reporteLotesLiquidados` | JXL, legacy |
| 3 | Planilla Liquidación | `planillaDeLiquidacion` | JXL, legacy |
| 4 | Libro RM Compras | `libroRegaliasMineras` | JXL, legacy |
| 5 | Anticipos c/Entrega | `reporteAnticipos` | JXL, legacy |
| 6 | Pago de Transporte | `reportePagoDeTransporte` | JXL, legacy |enciones | `reporteRetenciones` | JXL, legacy |

(Estado de Cuenta Cliente ya migrado; vive en ANTICIPOS.)

## 1. Estado actual (diagnóstico)
- Los 7 exportan con **JXL** (`WritableWorkbook` → `.xls` BIFF8), formato antiguo que da el aviso "el formato no coincide con la extensión".
- Vistas de filtros **legacy**: `chosen`, `jquery-ui-1.10`, scaffolding (`fieldcontain`, nav de scaffold), datepicker triple-param (`fecha*_day/_month/_year`).
- Filtran por **empresa, depósito y rango de fechas** (algunos por gestión/cliente).
- Otros **32 reportes** del sistema (Oro, Estaño, Plata, Cobre, Antimonio, Acopiadoras, M02…) siguen en JXL y **están fuera de alcance** → la dependencia `jxl` NO se elimina aún.

## 2. Arquitectura objetivo (estandarización)
**2.1 Infraestructura POI compartida (lo nuevo más importante).** Extraer del `EstadoCuentaExcelService` un componente reutilizable, p. ej. **`ReporteXlsxBuilder`** (service o clase de utilidad) con:
- `crearLibro()` / estilos reutilizables: título, subtítulo, encabezado (teal + bordes), número `#,##0.00`, fecha, total (negrita).
- `colocarLogo(sheet, drawing, picIdx, anchoPx, altoPx)` — anclaje de dos celdas con tamaño calculado (NO `resize()`, que dejaba la imagen en 0).
- `leerLogo()` — desde `classpath:reportes/logo_empresa.png` (ya en `src/main/resources`).
- `encabezado(sheet, titulo, subtitulos[])` — logo + título + filtros aplicados + periodo.
- `tabla(sheet, filaInicio, columnas[], filas[], formatos[])` — escribe cabecera y datos con estilos.
- `fila de totales` configurable.
- (Opcional) usar `SXSSFWorkbook` si algún reporte maneja miles de filas.

**2.2 Plantilla de vista (filtros + preview).** Un patrón común de `create.gsp` por reporte:
- Filtros AdminLTE/Bootstrap: **Select2 async** para cliente/empresa (endpoints existentes), **`g:datepickerUI`** para rango de fechas (es/`dd/mm/yy`), `g:select` para depósito/gestión.
- **Tabla de vista previa** (AdminLTE) con los resultados filtrados + totales.
- Botón **Exportar a Excel** (`g:link` a `exportarExcel` con los filtros actuales). Sin botón PDF.

**2.3 Controllers (patrón común por reporte).**
- `create()`: parsea filtros → consulta datos → devuelve lista + metadatos para la **vista previa**.
- `exportarExcel()`: re-consulta con los mismos filtros y arma el XLSX con `ReporteXlsxBuilder` → descarga (`Content-Disposition: attachment`, content-type OOXML).
- Se **retira el `crearReporte` JXL** de cada reporte migrado (queda el flujo POI).

## 3. Definición por reporte (a afinar al implementar)
Para cada uno: **filtros**, **columnas** y **totales** se toman del `crearReporte` JXL actual (fuente de verdad de la lógica), reexpresados en el nuevo formato.
1. **Lotes Recepcionados** — filtros: empresa/depósito/fechas; columnas: lote, fecha, cliente, empresa, tipo material, peso bruto…; totales: peso.
2. **Lotes Liquidados** — filtros: empresa/fechas/gestión; columnas: N° liq., lote, cliente, KNS, valor bruto, líquido pagable…; totales: KNS, valores.
3. **Planilla Liquidación** — consolidado de liquidaciones para pago; columnas por elemento + líquido; totales.
4. **Libro RM Compras** — regalías mineras de las compras (cumplimiento); columnas fiscales; totales por elemento.
5. **Anticipos c/Entrega** — anticipos otorgados/saldos; columnas: comprobante, cliente, importe, pagado, por pagar; totales.
6. **Pago de Transporte** — pagos por transporte; columnas: lote/chofer/importe; totales.
7. **Retenciones** — retenciones aplicadas; columnas: tipo, base, monto; totales.

## 4. Secuenciación (fases)
- **F1 — Infraestructura compartida:** crear `ReporteXlsxBuilder` refactorizando lo del PoC; dejar `EstadoCuentaExcelService` usándolo (validación de que la plantilla sirve). Plantilla `create.gsp` base reutilizable.
- **F2 — Reportes "de lotes":** Lotes Recepcionados y Lotes Liquidados (estructura tabla simple) — valida el patrón completo.
- **F3 — Financieros:** Anticipos c/Entrega, Pago de Transporte, Retenciones.
- **F4 — Fiscales/consolidados:** Planilla Liquidación y Libro RM Compras (más columnas/totales).
- **F5 — Verificación:** checklist por reporte (filtros, preview, XLSX con logo/estilos/totales, números vs JXL anterior).

Cada fase: compila + el usuario levanta la app y valida.

## 5. Riesgos y consideraciones
- **JXL permanece** para los 32 reportes fuera de alcance → no quitar la dependencia; POI y JXL conviven (ya verificado).
- **log4j/commons-io** ya resueltos para POI (`ext['log4j2.version']='2.17.1'`, `commons-io:2.16.1`).
- **`Picture.resize()` NO usar** (deja la imagen en tamaño 0); usar el anclaje explícito de `colocarLogo`.
- **Logo** en `src/main/resources/reportes/logo_empresa.png` (classpath, dev y WAR). Si cambia, actualizar también `assets/` (web).
- **Volúmenes**: si Lotes/Planilla devuelven miles de filas, considerar `SXSSFWorkbook` (streaming) y/o paginación en la vista previa.
- **Consistencia de filtros**: unificar el manejo del datepicker (triple-param) y los Select2 async en la plantilla.

## 6. Pendientes / a confirmar al implementar
- Columnas y totales exactos de cada reporte (se extraen del JXL actual).
- ¿La vista previa pagina o muestra todo? (depende del volumen por reporte).
- ¿Retirar del menú definitivamente los comentados, o dejarlos para fases futuras?
- Branding del encabezado (datos de empresa/título fijos) — definir textos.
