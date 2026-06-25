# Plan de mejora y rediseño — LiquidacionDeComplejo

Deriva de `planificacion_liquidacion.md` + hoja **COMPLEJO** de `liquidacion_complejo.xlsx`.

## 0. Decisiones confirmadas (2026-06-23)
1. **Motor de cálculo HÍBRIDO**: cálculo en vivo en el form (JS) para UX + **recálculo autoritativo en backend** al guardar/actualizar (fuente única que valida y persiste). El VPT automático consulta endpoints de Tablas/Contrato.
2. **Saldo negativo** → generar `AnticipoContraFuturaEntrega` por el monto en contra + asiento en `EstadoDeCuenta` (trazable por `tipoMovimiento`/`origenId`). Refina lo que ya hace el código.
3. **Retenciones** → **tabla hija real** (`LiquidacionDeComplejoRetenciones`) poblada desde `EmpresaRetenciones`/`Retencion`, editable y con eliminación por fila. Reemplaza el blob String `retenciones`.
4. **Leyes** → `% final` = promedio simple `(registrada ControlCalidad + cliente)/2`, precargado y editable; mostrar diferencia vs la ley registrada. Merma fija 1% (oculta).
5. **Bonos** (Calidad/Transporte/Lealtad) → **por ahora todos manuales**: campos editables en el form (Bs), sin fórmula ni precarga. *Futuro (diferido):* dominio configurable **`ParametroBono`** (tipoBono + modo FIJO/POR_TONELADA/POR_RANGO/PORCENTAJE + valor + condición + gestionMinera/activo) para calcularlos automáticamente.
6. **Tipo de cambio** → `recepcionDeComplejo.cotizacionDeDolar.tipoDeCambioOficial` (capturado en la recepción). Fallback `CotizacionDeDolar.findByActivo(1)` si la recepción no tiene uno (campo nullable).
7. **Cotizaciones y alícuotas** → de los registros **ya relacionados al lote** en la recepción: cot. diaria `recepcionDeComplejo.cotizacionDiariaDeMinerales.{zinc,plomo,plata}`, cot. quincenal `…cotizacionQuincenalDeMinerales.{zinc,plomo,plata}`, alícuotas `recepcionDeComplejo.alicuota.{zinc,plomo,plata}`. (No por fecha genérica; ya se capturan al recibir.)
8. **Numeración por gestión** → agregar `Date gestionMinera` (estilo `AnticipoContraFuturaEntrega`/`AnticipoCuota`): `numeroLiquidacionComplejo unique: 'gestionMinera'`, cómputo en `beforeValidate` (`max(numero) where gestionMinera=activa +1`), `gestionMinera = RecepcionDeComplejo.gestionMineraActiva()`. Reinicia en cada nueva gestión. `toString` = `numero/yy`. Ver [[feedback-gestion-minera-date]].
9. **No edición ni eliminación** → reemplazar `edit/update/delete` por **anulación** (campo `Boolean anulado`), con confirmaciones SweetAlert2 al registrar y al anular.

## 1. Modelo de cálculo (referencia xlsx → dominio)
Notación: PB=pesoBruto húmedo, T=tara, H=humedad%, M=merma%, TC=tipo de cambio.

**1.1 Peso Neto Seco (PNS)** `= (PB − T)·(1 − H/100)·(1 − M/100)`  → kilosNetosSecos
**1.2 Pesos finos** (por ley final):
- Zinc finos (Kg) `= PNS·leyZn/100`; Plomo `= PNS·leyPb/100`
- Plata: ley en DM → finos (Kg) `= PNS·leyAg/10000`  (xlsx: `F/1000·H·100/1000`)
- librasFinas = finosKg/1000·2204.6223 (Zn, Pb); onzasTroy = finosKg·1000/31.1035 (Ag)

**1.3 VBV (Valor Bruto de Venta)** con **cotización quincenal** por mineral:
- Zn/Pb `VBV$ = librasFinas · cotQuincenal`; Ag `VBV$ = onzasTroy · cotQuincenal`
- `VBV Bs = VBV$ · TC`; totales `VBV$tot=ΣVBV$`, `VBVBs_tot=ΣVBV Bs`

**1.4 Regalía Minera (RM)** = VBV · alícuota:
- `RM$ = VBV$ · alicuota/100`; `RM Bs = RM$ · TC` (≡ VBV Bs · alícuota/100)
- totales `RM$tot`, `RMBs_tot` (RMBs_tot es la "Regalía Minera" de deducciones)

**1.5 Valoración para venta** con **cotización diaria** (paralelo a VBV pero diaria) → "Valor para la Venta al 100%" `$ = Σ`, `Bs = $·TC`. (Informativo; el pago usa VNV vía VPT.)

**1.6 Valor Neto de Venta (VNV)** con **VPT** (Valor por Tonelada):
- `VNV$ = VPT · PNS/1000`; `VNV Bs = VNV$ · TC`

**1.7 Deducciones** (sobre VNV Bs, salvo Regalía que viene de 1.4):
- Regalía Minera = RMBs_tot
- CNS (1.8%), COMIBOL (1%), FENCOMIN (0.4%), FEDECOMIN (1.5%) → `VNV Bs · %/100`
- (Las tasas y la lista vienen de `EmpresaRetenciones`/`Retencion`, ver §4)
- `TotalDeducciones = Σ`

**1.8 Valor Pagable del Mineral** `= VNV Bs − TotalDeducciones`
**1.9 Bonos** (Bs): Calidad + Transporte + Lealtad → `TotalBonos`
**1.10 Anticipos y otros saldos** (Bs): Anticipo contra entrega + Anticipo contra futura entrega + Saldo anterior → `TotalAnticipos`
**1.11 Líquido Pagable** `= ValorPagable + TotalBonos − TotalAnticipos`
**1.12 Precio calculado** `$us/TM = LiquidoPagable / TC / PNS · 1000`

## 2. Cambios de dominio
El dominio ya tiene la mayoría de campos (cotización diaria/quincenal/alícuota por mineral, TC oficial/comercial, porcentajes Promexbol/Cliente/Final, kilosFinos/librasFinas/onzasTroy, valorOficialBruto por mineral $us/Bs, `modoValoracion`). Ajustes:
- **Agregar** (si faltan): VBV/RM por mineral en $us y Bs + totales; VNV $us/Bs; ValorPagable; TotalBonos; TotalAnticipos; SaldoAnterior; LiquidoPagable; PrecioCalculado; `modoVPT` (MANUAL/TABLAS/CONTRATO) y `vpt`.
- **Retenciones**: dejar de depender del blob `retenciones`; usar `LiquidacionDeComplejoRetenciones` (hasMany). Mantener blob solo durante migración.
- `cliente` y `fechaRecepcion` (Date) ya agregados.
- Reusar `porcentaje*Final` (negociado), `porcentaje*Promexbol` (ControlCalidad), `porcentaje*Cliente` (cliente).
- **`Date gestionMinera`** (nullable) + `numeroLiquidacionComplejo unique: 'gestionMinera'`; mover el cálculo del correlativo de `beforeInsert` (hoy `max` global) a **`beforeValidate`** por gestión activa. `toString` → `numero/yy`.
- **`Boolean anulado`** (nullable, default false).
- DDL: agregar columnas **nullable** (gotcha: `dbCreate=update` no puede agregar NOT NULL a tabla poblada).

## 3. Motor de cálculo
- **JS** (nuevo asset `liquidacionDeComplejo/calculoLiquidacion.js`): implementa §1 completo, recalcula en cascada ante cambios de ley, VPT, alícuotas, retenciones, bonos, anticipos.
- **Backend autoritativo**: método/`Service` `recalcular(liq)` que reaplica §1 en `save`/`update` (no confía en los valores del form). Validaciones (PNS>0, leyes 0–100/rango Ag, etc.).
- **VPT**:
  - MANUAL: usuario ingresa `vpt`.
  - AUTOMÁTICO (TABLAS / CONTRATO): **[HECHO Fase 1]** acción `calcularVPT` en `TablaOrigenCotizacionesComplejoController` (GET `/tablaOrigenCotizacionesComplejo/calcularVPT?recepcionDeComplejoId&leyZinc&leyPlomo&leyPlata&tablaId` → `{vpt, modo:'TABLAS', …}`) y en `TerminosDeContratoController` (`/terminosDeContrato/calcularVPT?…&terminoId` → `{vpt, modo:'CONTRATO', …}`). Por ahora **devuelven un VPT aleatorio** (150–250 $us/TM, `@Secured ROLE_ADMIN/ROLE_LIQUIDACION`). Falta la lógica real.
  - Al fijar VPT (cualquier modo) → recálculo total (JS y backend).

## 4. Retenciones (tabla hija)
- Al abrir create: poblar filas desde `EmpresaRetenciones` de la empresa del lote (Regalía + CNS/COMIBOL/FENCOMIN/FEDECOMIN, con sus % y base "DE LEY"/"OTRA").
- Tabla **AdminLTE/Bootstrap** editable: columnas Concepto, Base, %, Monto Bs, acción eliminar (✕). Recalcula TotalDeducciones al eliminar/editar.
- Persistir en `LiquidacionDeComplejoRetenciones` (en la misma transacción del save).

## 5. Leyes negociables + promediado
- `% final` editable por mineral; precargado = `(Promexbol + Cliente)/2` si el cliente tiene leyes, si no = Promexbol.
- Mostrar **diferencia** vs ley registrada (Promexbol) al lado del input.
- Merma = 1% fija/oculta; humedad de ControlCalidad.

## 6. Anticipos
- Si el lote tiene `Anticipo` y ese anticipo se relaciona **solo con ese lote** → descontar el `totalPorPagar` completo.
- Si el anticipo abarca **varios lotes** → permitir descontar una **fracción** del `totalPorPagar` (saldo restante queda para los otros lotes).
- Incluir Anticipo contra futura entrega y Saldo anterior (de `EstadoDeCuenta`).

## 7. Saldo negativo → ACFE + ledger
- Si LiquidoPagable < 0: crear `AnticipoContraFuturaEntrega` por |monto| + asiento `EstadoDeCuenta` (debe/haber según corresponda), con `tipoMovimiento`/`origenId`. Refinar el código actual (quitar anti-patrón `withNewTransaction`, misma transacción).

## 7b. No edición ni eliminación → Anulación
- Quitar `edit`/`update`/`delete` del controller; `allowedMethods = [save:"POST", anular:"POST", ...]`.
- `anular(id)` marca `anulado=true` y **revierte todos los efectos en la misma transacción** (sin `withNewTransaction`), usando como base la lógica actual del `beforeDelete`:
  - `recepcionDeComplejo.estadoDelLote = "NO LIQUIDADO"` (y `estadoAnticipo` si aplica).
  - Eliminar/anular `LiquidacionDeComplejoRetenciones` y `RetencionPorPagarComplejo` del lote.
  - Revertir el anticipo descontado (`Anticipo`/`AnticipoDetalle`: restaurar `totalPorPagar`/`totalPagado`, `estadoAnticipo`).
  - Revertir en `EstadoDeCuenta` los asientos generados (incluida la `AnticipoContraFuturaEntrega` por saldo negativo): asiento inverso ligado por `tipoMovimiento`/`origenId`.
- Bloqueos: no anular si el lote/los asientos ya fueron cancelados/pagados en caja (a definir cuando exista caja); no anular dos veces.
- Confirmaciones **SweetAlert2** al **registrar** y al **anular**. Badge `ANULADA` en `show`/`list`; ocultar acción si ya está anulada.
- Vistas: como ya no hay edición, eliminar `edit.gsp`; el alta es la única captura.

## 8. UI/UX
- Rediseño del form a **AdminLTE/Bootstrap** alineado a lo trabajado (cards, `form-section-title`, Select2 donde aplique, SweetAlert2).
- Secciones espejando el xlsx: Datos cliente/transacción · Características del mineral (leyes con diff) · VBV/RM · Valoración/VPT · Deducciones (tabla retenciones) · Bonos · Anticipos/Saldos · Líquido Pagable · Precio calculado.
- `show` y reportes: aprovechar `cliente`/`fechaRecepcion`.

## 9. Secuenciación (fases)
1. ✅ **Stubs VPT** (Tablas/Contrato controllers, random) + endpoints. **HECHO (2026-06-23).**
2. ✅ **Dominio**: campos faltantes + child retenciones (`hasMany detalleRetenciones`) + `gestionMinera`/`anulado` + numeración por gestión en `beforeValidate` (unique:'gestionMinera'). **HECHO (2026-06-23).** Notas: se **reusó `modoValoracion`** como modo VPT (se le agregó `MANUAL`); `tablaComplejo`/`tablaPrecioPorLme`/`terminosDeContrato` pasaron a `nullable:true`; muchos campos ya existían en la base `Liquidacion` (VPT=`valorPorTonelada`, VNV=`valorNetoMineral`/`EnBolivianos`, RM total=`regaliaMinera`, deducciones=`totalRetenciones`, VBV total $us=`valorOficialBruto`, `bonoCalidad`/`bonoIncentivo`, anticipos, `totalLiquidoPagable`). **`toString` NO se cambió a `numero/yy`** (hoy devuelve el lote y lo usan reportes/audit/`searchable`) — pendiente de confirmar. DDL: columnas nuevas nullable (tabla `liquidacion` table-per-hierarchy compartida).
3. ✅ **Motor backend** `LiquidacionComplejoCalculoService` (`calcular(Map)` puro + `recalcular(liq)`) + test Spock `LiquidacionComplejoCalculoServiceSpec` que verifica los 11 valores de referencia del xlsx. **HECHO (2026-06-23, test verde).** Correr con `./gradlew test --tests "...LiquidacionComplejoCalculoServiceSpec" -x configureChromeDriverBinary -x configureGeckoDriverBinary` (los tasks de webdriver Geb requieren red).
4. ✅ **JS** de cálculo en vivo + integración VPT. **HECHO (2026-06-23).** Asset `liquidacionDeComplejo/calculoLiquidacion.js`: función pura `calcular()` (espeja el backend, verificada en Node con los valores del xlsx) + binding DOM por **contrato de IDs** (tolerante a elementos ausentes) + VPT (manual / auto vía endpoints stub `calcularVPT`). El form de la Fase 5 debe usar los IDs del contrato documentado en el asset. (Binding/VPT se ejercitan con el form real en Fase 5.)
5. ✅ **UI** rediseñada + tabla retenciones. **HECHO (2026-06-24)** create.gsp/_form.gsp: AdminLTE, contrato de IDs (cálculo en vivo), selector de lote (NO LIQUIDADO), prefill server-side desde la recepción (cotizaciones/alícuotas/TC/leyes promedio/retenciones de EmpresaRetenciones), tabla de deducciones editable/eliminable, secciones del xlsx, confirmación SweetAlert. `create()` precarga; `save()` recalcula (service) + persiste `LiquidacionDeComplejoRetenciones`. Compila OK. `show.gsp` también rediseñado (AdminLTE, secciones del xlsx, tabla de retenciones hija, badge ANULADA, reporte Jasper). **Pendiente:** verificación en app (binding live/VPT, render GSP). Los hooks existentes beforeInsert/afterInsert (lote→LIQUIDADO, EstadoDeCuenta/ACFE/RetencionPorPagar) corren en save y se reconcilian en Fase 6/7; `edit.gsp` viejo se reemplaza por anulación en Fase 7.
6. ✅ **Anticipos** (full/fracción) + **saldo negativo** (ACFE/ledger). **HECHO (2026-06-24).** afterInsert reconciliado con el flujo nuevo: retenciones por pagar se generan desde `detalleRetenciones` (no del blob; se quitó el `withNewTransaction` y la doble creación que crasheaba). Anticipo: prefill completo si el anticipo cubre un solo lote, fracción editable si cubre varios; descuento aplica totalPagado/totalPorPagar y estado PAGADO/PARCIAL; validación en save (fracción ≤ totalPorPagar) + hint en el form. Saldo negativo: se quitó el cómputo muerto/erróneo de numeroAnticipo; el ACFE se crea y su beforeValidate/afterInsert hacen numeración+gestión y el asiento de deuda en EstadoDeCuenta. Compila OK; **verificar flujo completo en app**.
7. ✅ **Anulación** (sin edit/delete). **HECHO (2026-06-24).** Quitadas edit/update/delete; acción `anular` (`@Secured ROLE_ADMIN`, POST) que revierte en una transacción: lote→NO LIQUIDADO, borra RetencionPorPagarComplejo, revierte descuento de anticipo (estado SIN PAGAR / recepción CON ANTICIPO), anula el ACFE por saldo negativo + reversa en ledger, revierte pago de ACFE previo, y marca anulado=true (beforeUpdate omite su lógica si anulado). show/list con badge ANULADA y confirmación SweetAlert; `edit.gsp` borrado. Compila OK; verificar en app.
8. ✅ **Reportes** con nuevos campos. **HECHO (2026-06-24).** Comprobante **imprimible HTML** (`imprimir.gsp` standalone + CSS de impresión + acción `imprimir(id)`), espeja el xlsx con todos los campos del rediseño (VBV/RM por mineral, VNV, deducciones, valor pagable, bonos, anticipos, líquido, precio, literal, marca ANULADA). Botón "Imprimir comprobante" en show. El Jasper oficial `liquidacion_complejo.jrxml` (query SQL) queda como **legado** — actualizarlo con la app corriendo si se requiere el PDF oficial con los campos nuevos.

**TODAS LAS FASES (1–8) IMPLEMENTADAS Y COMPILANDO.** Pendiente transversal: verificación integral en la app (flujo crear→calcular→guardar→show→imprimir→anular) y, si se necesita, actualizar el Jasper oficial.

## 10. Pendientes menores a confirmar durante implementación
- Fuente exacta de **bonos** (Calidad/Transporte/Lealtad): ¿de `Empresa`/`BonoCalidad`/`BonoCantidad` o ingreso manual?
- **Tipo de cambio**: ¿`CotizacionDeDolar` (oficial vs comercial) automático o manual?
- Fuente de **cot. diaria/quincenal/alícuota**: ¿autocompletar desde `CotizacionDiariaDeMinerales`/`CotizacionQuincenalDeMinerales`/`Alicuota` por fecha, con override manual?
- ¿Persistir VBV/RM por mineral o recalcular siempre en `show`/reportes?
