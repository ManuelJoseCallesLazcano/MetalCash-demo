# Checklist de verificación — Rediseño TablaOrigenCotizacionesComplejo (Fase 6)

App en `http://localhost:8090/demo-liquidaciones` · menú **PRECIOS → Tablas** (rol ROLE_ADMIN).
Marca cada ítem: ✅ ok · ❌ falla (anota lo observado).

## 0. Arranque / DDL
- [ ] La app levanta sin error.
- [ ] `dbCreate=update` creó la tabla **`tabla_precio_punto`** y agregó columnas a `tabla_origen_cotizaciones_complejo`: **`cotizacion_diaria_de_minerales_id`**, **`fecha_actualizacion`**.
- [ ] Ya **no** se crea el `TablaPrecioPorLme` "auxiliar" al guardar una tabla (se quitó el `beforeInsert`).

## 1. Listado (list.gsp)
- [ ] PRECIOS → Tablas muestra columnas: **Nombre, Empresa, Naturaleza, Cotización Diaria, # Puntos, Actualización**.
- [ ] Ya no aparecen las columnas viejas "Nombre Archivo" / "Fecha Subida".
- [ ] El orden por Nombre / Naturaleza / Actualización (sortableColumn) funciona.
- [ ] Tablas pre-existentes (de Excel) se listan sin error (Empresa/Cot. Diaria pueden salir "—", # Puntos = 0).

## 2. Alta (create.gsp + _form.gsp)
- [ ] "Nueva" abre el formulario con: Nombre, Naturaleza Mineral, Empresa (opcional, "(todas)"), Fecha de Actualización (solo lectura), **Cotización Diaria (referencial)**.
- [ ] Hay **3 tablas** (Zinc, Plomo, Plata). En Plata el encabezado de ley dice **"LEY [DM]"**; en Zinc/Plomo dice "LEY [%]".
- [ ] **Agregar fila** añade una fila editable (ley, % pagable) en la tabla correspondiente.
- [ ] **Quitar** (✕) elimina la fila.
- [ ] Al **elegir una Cotización Diaria** se llenan los campos **Cotización en Tonelada** Zinc/Plomo/Plata:
  - Zinc = cot.zinc · 2204.6223
  - Plomo = cot.plomo · 2204.6223
  - Plata = cot.plata · 1.000.000 / 31.1035
- [ ] Al escribir **ley** y **% pagable** en una fila, la columna **VPT $us** se calcula en vivo:
  - Zinc/Plomo: cotTon · ley/100 · %pag/100
  - Plata: cotTon · ley/10000 · %pag/100
- [ ] Cambiar la Cotización Diaria **recalcula** el VPT de todas las filas.
- [ ] Guardar con datos válidos crea la tabla y redirige al **show** con mensaje de éxito.
- [ ] La **Fecha de Actualización** queda registrada (fecha/hora del guardado).

### Cálculo de referencia (para validar a mano)
Con cotización diaria Zinc = 1.0, una fila Zinc ley = 100, % pagable = 100 ⇒ **VPT = 2204.6223**.
Con cotización diaria Plata = 31.1035, fila Plata ley = 10000, % pagable = 100 ⇒ **VPT = 1.000.000**.
Fila Zinc ley = 45 entre puntos (40→80) y (50→90) ⇒ %pag interpolado 85; con cot Zn = 1 ⇒ **VPT ≈ 843.27**.

## 3. Detalle (show.gsp)
- [ ] Muestra Datos Generales (nombre, empresa, naturaleza, cot diaria, fecha actualización).
- [ ] Muestra Cotización en Tonelada por elemento.
- [ ] Muestra las 3 tablas con LEY / % PAGABLE / **VPT $us** (VPT por punto coherente con el form).
- [ ] Botones **Editar** (header y footer) y **Eliminar** (con confirmación SweetAlert).

## 4. Edición (edit.gsp)
- [ ] "Editar" carga los puntos existentes en las 3 tablas y la cot diaria seleccionada.
- [ ] El cálculo en vivo funciona igual que en alta.
- [ ] Modificar filas (agregar/editar/quitar) y guardar **persiste** los cambios (reconstruye los puntos; no quedan duplicados ni huérfanos).
- [ ] La Fecha de Actualización se actualiza al guardar.

## 5. Eliminación
- [ ] Eliminar pide confirmación y borra la tabla **y sus puntos** (sin error de FK).

## 6. Integración con LiquidacionDeComplejo (modo VPT = TABLA)
- [ ] Crear/abrir una liquidación de un lote cuya **RecepcionDeComplejo tenga cotización diaria** asignada.
- [ ] Modo VPT = **TABLA** → seleccionar una tabla con puntos que cubran las leyes finales del lote.
- [ ] El **Valor por Tonelada** se llena automáticamente (suma de VPT Zinc + Plomo + Plata, interpolando por la **ley final** y usando la **cot diaria del lote**).
- [ ] Si una ley final cae **fuera del rango** de puntos de su elemento → ese elemento aporta **0** (VPT total menor).
- [ ] Seleccionar tabla "(ninguna)" → VPT = 0.
- [ ] El VPT ya **no es aleatorio** (consistente entre recargas con los mismos datos).

## 7. Regresión (no romper módulos legados)
- [ ] **Recepción** (RecepcionDeComplejo): el flujo que calcula valor por tonelada vía tabla sigue funcionando (usa `getValorPorTonelada*`, campos Excel intactos).
- [ ] **Cotización para Cliente** (CotizacionDeComplejo): idem, sin error.
- [ ] Tablas creadas por el flujo viejo (con Excel) siguen abriéndose en show/list sin excepción.

## 8. Casos borde
- [ ] Guardar una tabla **sin cot diaria** y/o **sin puntos** → guarda ok; en liquidación esa tabla da VPT 0.
- [ ] Filas con ley o % pagable vacíos/no numéricos → se ignoran al guardar (no se crean puntos inválidos).
- [ ] Un solo punto en un elemento → cualquier ley distinta a ese punto queda fuera de rango ⇒ VPT 0 de ese elemento.

---
**Notas / hallazgos:**
