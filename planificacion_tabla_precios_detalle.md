# Plan de rediseño — TablaOrigenCotizacionesComplejo

Deriva de `planificacion_tabla_precios.md`. La tabla deja de basarse en Excel y pasa a ser, por elemento (Zinc, Plomo, Plata), una **curva (ley → % pagable)** con la que se calcula el **Valor por Tonelada (VPT)** por interpolación lineal.

## 0. Decisiones confirmadas (2026-06-25)
1. **Filas (ley, % pagable)** → nuevo dominio hijo **`TablaPrecioPunto`** (belongsTo tabla; elemento; ley; porcentajePagable). Reemplaza los blobs `datosZinc/datosPlomo/datosPlata`.
2. **Campos de Excel** → **eliminar** `nombreArchivo` y `datosArchivo` (y los `datos*`). Columnas quedan huérfanas en la BD de pruebas (sin problema).
3. **Interpolación fuera de rango** → **VPT 0** para ese elemento (la ley final debe caer dentro de los puntos definidos; si no, ese elemento aporta 0).
4. **`beforeInsert`** → **eliminar** la creación automática del `TablaPrecioPorLme` auxiliar.

## 1. Modelo de cálculo (del spec)
**1.1 Cotización en tonelada por elemento** (a partir de una cotización diaria):
- Zinc / Plomo: `cotTon = cotDiaria · 2204.6223`
- Plata: `cotTon = cotDiaria · 1000 · 1000 / 31.1035`  (= cotDiaria · 1 000 000 / 31.1035)

**1.2 VPT por fila** (preview en las tablas de edición, con la cot diaria referencial):
- Zinc / Plomo: `VPT = cotTon · ley/100 · %pagable/100`
- Plata: `VPT = cotTon · ley/10000 · %pagable/100`

**1.3 VPT para la liquidación** (por elemento, con ley final + cot diaria DEL LOTE):
- `%pagableInterp` = interpolación lineal del % pagable según la **ley final**, entre el punto inferior `(leyInf, %Inf)` y el superior `(leySup, %Sup)` que la rodean:
  `%Interp = %Inf + (%Sup − %Inf) · (ley − leyInf) / (leySup − leyInf)`
  - Si la ley coincide con un punto → ese % pagable.
  - Si la ley queda **fuera del rango** de puntos → VPT de ese elemento = 0.
- `VPT_elem = cotTon_elem · ley/(100 Zn,Pb | 10000 Ag) · %pagableInterp/100`
- **`VPT_total = VPT_Zinc + VPT_Plomo + VPT_Plata`** (lo que consume LiquidacionDeComplejo modo TABLA).
- `cotTon_elem` y `ley` se toman de la **cotización diaria asociada al lote** (`recepcionDeComplejo.cotizacionDiariaDeMinerales`) y de las **leyes finales** de la liquidación; la cot diaria de la tabla es solo referencial.

## 2. Dominio
- **`TablaOrigenCotizacionesComplejo`**:
  - Mantener: `nombreTabla`, `empresa` (nullable), `naturalezaMineral`.
  - **Agregar**: `CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales` (referencial, nullable) y `Date fechaActualizacion` (se setea en save/update). (Se puede reusar `fechaSubida` renombrando.)
  - **Eliminar**: `nombreArchivo`, `datosArchivo`, `datosZinc`, `datosPlomo`, `datosPlata`.
  - `static hasMany = [puntos: TablaPrecioPunto]`.
  - **Quitar** el `beforeInsert` (auxiliar TablaPrecioPorLme).
- **`TablaPrecioPunto`** (nuevo): `belongsTo = [tabla: TablaOrigenCotizacionesComplejo]`, `String elemento` (inList ZINC/PLOMO/PLATA), `BigDecimal ley`, `BigDecimal porcentajePagable`. Ordenable por `ley`.

## 3. Controller + Service
- **`TablaPrecioComplejoService`** (puro, testeable):
  - `cotToneladaZincPlomo(cot)`, `cotToneladaPlata(cot)`.
  - `interpolarPorcentajePagable(List puntos, BigDecimal ley)` → %pagable o null si fuera de rango.
  - `vptElemento(elemento, cotDiaria, ley, puntos)` y `vptTotal(...)`.
- **`TablaOrigenCotizacionesComplejoController`**:
  - `create/save/edit/update/show/list` rediseñados; save/update persiste los `puntos` (de las 3 tablas del form) y `fechaActualizacion = new Date()`.
  - **`calcularVPT`** (reemplaza el stub aleatorio de Fase 1): recibe recepción + leyes finales + tabla; usa `recepcion.cotizacionDiariaDeMinerales`; calcula VPT por elemento por interpolación y devuelve la **suma** (JSON `{vpt, detalle:{zinc,plomo,plata}}`).

## 4. Vistas (AdminLTE/Bootstrap)
- **create/edit**: `nombreTabla`, `empresa`, `naturalezaMineral`, **select de cotización diaria referencial**, y **3 tablas editables** (Zinc, Plomo, Plata) con columnas `LEY [%]` (DM para plata) · `% PAGABLE` · `VPT $us`, con add/edit/delete de filas. Cálculo en vivo de cotTon y VPT por fila; al cambiar la cot diaria referencial se recalcula todo (JS asset).
- **show**: nombre, cot diaria, cot tonelada por elemento, y las 3 tablas con sus puntos + VPT.
- **list**: nombre, empresa, fecha de actualización.

## 5. Integración con LiquidacionDeComplejo
- El modo **VPT = TABLA** ya llama a `tablaOrigenCotizacionesComplejo/calcularVPT` (Fases 1/4/5); ahora devolverá el **VPT real** (suma interpolada por elemento). El form de liquidación no cambia.

## 6. Secuenciación (fases)
1. Dominio: `TablaPrecioPunto` + ajustes en `TablaOrigenCotizacionesComplejo` (agregar cot diaria + fechaActualizacion + hasMany; eliminar Excel/datos*/beforeInsert). DDL: columnas nuevas nullable; las viejas quedan huérfanas.
2. Service de cálculo (cotTon, interpolación, VPT) + test Spock (incl. interpolación dentro/fuera de rango y fórmulas Zn/Pb/Ag).
3. `calcularVPT` real en el controller (reemplaza stub).
4. Vistas create/edit con las 3 tablas editables + JS de cálculo en vivo.
5. show + list rediseñados.
6. Verificación en app (incl. liquidación modo TABLA tomando el VPT real).

## 7. Pendientes menores a confirmar
- ¿`empresa` y `naturalezaMineral` siguen siendo relevantes para filtrar/elegir la tabla en la liquidación? (Hoy la liquidación elige la tabla por id; se mantienen como metadatos.)
- Formato/decimales de ley y % pagable.
- `TablaPrecioPorLme` (otro dominio) queda fuera de alcance.
