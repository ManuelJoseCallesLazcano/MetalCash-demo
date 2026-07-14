# PLAN — CONJUNTOS DE LOTES (COMPÓSITOS) — v01

## 1. Objetivo y alcance

Módulo para **conformar conjuntos (compósitos) de lotes de complejo** con la mayor
flexibilidad de selección, ver el resumen (pesos, finos, valores, leyes ponderadas) mientras
se arma, y registrarlo con destino (venta / exportación / ingenio) en estado provisional o
definitivo. Al registrar, los lotes quedan **reservados** (no disponibles para otro conjunto).

Solo aplica a **mineral complejo (ZN PB AG)**.

### 1.1 Requisitos originales (fuente)

Un conjunto se conforma por lotes (`RecepcionDeComplejo`) que **pueden estar liquidados o no**,
pero **sí deben tener el análisis de laboratorio realizado** (`ControlCalidadComplejo`). La app
debe permitir filtrar/ordenar/buscar con flexibilidad y mostrar el resumen del conjunto a medida
que se arma. Ver requisito narrativo completo al final (§9).

## 2. Decisiones de diseño (confirmadas con el usuario)

| # | Tema | Decisión |
|---|------|----------|
| D1 | **Persistencia** | **Rediseñar el dominio existente** `ReporteCompositoDeLotes` + hija `CompositoDeLotesDetalle`. No se crea dominio nuevo. |
| D2 | **Fuente de verdad de lotes** | La **tabla hija `CompositoDeLotesDetalle`** (patrón adoptado en retenciones de liquidación). Se **deprecan** los blobs JSON `lotes` / `lotesComposito` / `participacion`. |
| D3 | **Valor neto / líquido pagable** | **Solo lotes liquidados** aportan valor. PNS y kilos finos se calculan siempre desde recepción + control de calidad; valor neto y líquido pagable = 0/blanco si el lote no está liquidado. |
| D4 | **Fuente de leyes** | **Promexbol** (`porcentaje*Promexbol` de `ControlCalidadComplejo`) para filtros, orden, tabla, ponderados y detalle. |
| D5 | **Legacy** | **Deprecar** `AsignacionLoteConjuntoComplejo` y el campo `conjuntoComplejo` de la liquidación. La reserva se lleva **solo** en `RecepcionDeComplejo.nombreComposito`. Los reportes `ReporteCompositoDeLotes`/`ReporteLotesCompositos` se re-apuntan al nuevo modelo. |
| D6 | **Reserva de lotes** | Se reserva **al guardar, aun en PROVISIONAL**. Quitar un lote o eliminar el conjunto **libera** el lote (`nombreComposito = "-"`). |
| D7 | **Estado DEFINITIVO** | **Inmutable**: se bloquea edit/update/delete. Acciones de admin: **reabrir** (→ PROVISIONAL) y **anular** (libera lotes). |
| D8 | **Aprobación** | **Fuera de alcance.** Se ignoran/retiran `estadoDeAprobacion` / `aprobador` / `aprobadoPor`. |
| D9 | **Numeración** | **Por gestión minera**: `Date gestionMinera` + `numeroComposito` reinicia por gestión (`gestionMineraActiva()`), estilo AnticipoCuota/Liquidación. `sigla` sigue siendo única (identificador principal). |
| D10 | **Destino EXPORTACION** | **Se trata como comprador**: los tres destinos reusan el FK `comprador`; el comprador extranjero se registra como un `Comprador` más. Sin campos extra (país/aduana/incoterm) en esta versión. |
| D11 | **Liquidación anulada con lote en compósito** | **Bloqueo total**: no se puede anular una `LiquidacionDeComplejo` cuyo lote esté en **cualquier** compósito (provisional o definitivo). Hay que sacar el lote del compósito primero. El guard reusa la marca de reserva: `recepcion.nombreComposito != "-"`. |

### 2.1 Convenciones de cálculo (heredadas de `reporteLotesLiquidados`)

- **PNS** (peso neto seco) `= pesoBruto · (1 − Hprom/100) · (1 − merma/100)`, con `H` y `merma`
  Promexbol de `ControlCalidadComplejo` (merma default 1%).
- **Kilos finos**: `KFZn = PNS·%Zn/100`; `KFPb = PNS·%Pb/100`; `KFAg = PNS·(DM Ag)/10000`.
- **Ponderados del conjunto**: `%Zn = ΣKFZn/ΣPNS·100` (Pb ídem); `DM Ag = ΣKFAg/ΣPNS·10000`;
  `%Humedad = (ΣPNH − ΣPNS)/ΣPNH·100`, con `PNH = pesoBruto·(1 − merma/100)`.
- **Valor neto / líquido pagable** (solo liquidados): tomados de `LiquidacionDeComplejo`
  del lote (`valorNetoMineralEnBolivianos`, `totalLiquidoPagable`). Para no liquidados = 0.

## 3. Modelo de datos (rediseño)

### 3.1 `ReporteCompositoDeLotes` (cabecera del conjunto)

**Se conserva** (renombre conceptual a "Compósito"): `sigla` (unique), `destino`
(VENTA/EXPORTACION/INGENIO), `nombreDestino`, `comprador`/`ingenio` (nullable según destino),
`estadoDelComposito` (PROVISIONAL/DEFINITIVO), `elaboradoPor`, `fechaDeElaboracion`,
`observaciones`, filtros usados (`empresa`, `ordenarElemento`, `fecha*`, `ley*Min/Max`), totales
cacheados (`totalKilosBrutos`, `totalKilosNetosSecos`, `leyPromedio*`, `totalKilosFinos*`,
`totalValorNeto`, `totalValorDeCompra`).

**Cambios:**
- **+** `Date gestionMinera` (D9); `numeroComposito` reinicia por gestión en `beforeValidate`.
- **+** `totalLiquidoPagable` (BigDecimal) en cabecera y detalle (el resumen lo pide y hoy no existe).
- **–** Retirar `estadoDeAprobacion`, `aprobadoPor`, `aprobador` (D8).
- **–** Deprecar blobs `lotes` / `lotesComposito` / `participacion` (D2). Se dejan de leer/escribir;
  se eliminan cuando ya no haya datos que migrar.
- `constraints`: `comprador` requerido si destino∈{VENTA,EXPORTACION}; `ingenio` requerido si
  destino=INGENIO (validador cruzado).

### 3.2 `CompositoDeLotesDetalle` (lote del conjunto — fuente de verdad)

Se conserva. Ajustes:
- Renombrar semánticamente los `porcentaje*Final` → poblarlos con **Promexbol** (D4), o agregar
  columnas explícitas `porcentaje*Promexbol`. (Decidir en implementación; preferible reusar las
  columnas existentes para no romper DDL, documentando que ahora cargan Promexbol.)
- `liquidacionId`: **poblar de verdad** (hoy se hardcodea 0). Si el lote está liquidado, guardar el
  id de `LiquidacionDeComplejo` y copiar `valorNetoMineralEnBolivianos` + **`liquidoPagable`**;
  si no, dejar valores en 0 (D3).
- Se mantiene como **snapshot** (datos duplicados del lote) para el reporte, pero el vínculo vivo es
  `recepcionId`.

### 3.3 `CompositoLotesParticipacion`

Se conserva (participación por empresa/municipio = ΣPNS por proveedor / ΣPNS total). Recalcular en
el guardado desde la hija, no desde blob JSON.

## 4. Hooks / integridad (correcciones respecto al legacy)

- **Reserva de lote sin romper la recepción**: marcar/liberar `RecepcionDeComplejo.nombreComposito`
  con **`executeUpdate` (HQL)**, NO con `recepcion.save()` — `save()` re-dispara el
  `beforeValidate` de peso (`pesoBruto = pesoNeto − pesoTara`, `taraExcedeBruto`) y puede provocar
  rollback silencioso de toda la transacción. (Gotcha ya documentado en el módulo de liquidación.)
- **Unificar la marca en `RecepcionDeComplejo.nombreComposito`.** Eliminar el marcado/desmarcado de
  `LiquidacionDe*.nombreComposito` del `beforeDelete` legacy (D5).
- **Reservar en PROVISIONAL** (D6): al `save`/`update`, marcar los lotes agregados y liberar los
  quitados (diff contra la hija previa). Al `delete`/`anular`, liberar todos.
- **DEFINITIVO inmutable** (D7): sin `edit/update/delete`. Acciones `reabrir` (→PROVISIONAL, POST,
  admin) y `anular` (libera lotes + baja lógica, POST, admin). Confirmaciones SweetAlert2.
- **Numeración** (D9): `beforeValidate` asigna `gestionMinera = gestionMineraActiva()` y
  `numeroComposito = max(por gestión) + 1`.

## 5. Selección de lotes disponibles (pantalla principal)

### 5.1 Universo de lotes candidatos
`RecepcionDeComplejo` que cumplan: tiene `ControlCalidadComplejo` (con análisis) **y**
`nombreComposito == "-"` (no reservado). Estado liquidado o no, ambos válidos.

### 5.2 Filtros (todos combinables, AND entre grupos)
- **Rango de leyes** Zn / Pb / Ag (Promexbol), **individual, conjunta o combinada** (min/max por
  elemento; los que se dejan en su rango default no restringen).
- **Empresa** (Select2 async).
- **Búsqueda por lote específico** (`codigoLote` / `numeroRecepcion`).
- **Rango de fechas de recepción** (opcional).

### 5.3 Orden
Ascendente/descendente por la ley de cualquier elemento (Zn/Pb/Ag) o por lote/fecha.

### 5.4 Tabla de disponibles → selección
Columnas: lote, empresa, fecha, peso bruto, %Hum, %Zn, %Pb, DM Ag (Promexbol), PNS, finos,
estado (LIQUIDADO/NO LIQ.), valor neto y líquido (solo si liquidado). Checkbox / botón "agregar"
mueve el lote al panel del conjunto en construcción.

## 6. Resumen en vivo del conjunto

Al pie del listado seleccionado **y** en un cuadro-resumen destacado más abajo, mostrar totales y
ponderados recalculados en vivo (JS) a medida que se agregan/quitan lotes:
- Totales: **peso bruto, peso neto seco (PNS), kilos finos (Zn/Pb/Ag), valor neto, líquido pagable**.
- Ponderados: **humedad, ley Zn, ley Pb, ley Ag** (fórmulas §2.1).
- Valor neto / líquido pagable suman **solo lotes liquidados** (D3); indicar en UI cuántos lotes
  no liquidados quedan sin valorar.

Cálculo **híbrido** (mismo criterio que liquidación): JS en vivo + **recálculo autoritativo en el
backend** al guardar (fuente única de los totales cacheados en cabecera).

## 7. Fases de implementación

- **F0 — Auditoría/migración**: confirmar datos existentes en `reporte_composito_de_lotes` y
  child; script para poblar `liquidacionId`/gestión si hace falta; columnas nuevas **nullable**
  (gotcha DDL `dbCreate:update`).
- **F1 — Dominio**: ajustar `ReporteCompositoDeLotes` (gestionMinera, totalLiquidoPagable, retirar
  aprobación, validador destino↔comprador/ingenio) y `CompositoDeLotesDetalle` (Promexbol,
  liquidacionId real, liquidoPagable). Migrar numeración a `beforeValidate`.
- **F2 — Motor de resumen (service)**: `CompositoCalculoService.calcular(lista de recepcionIds)`
  puro y testeable (Spock) → totales + ponderados + participación, reusando fórmulas §2.1 y, para
  valor, `LiquidacionDeComplejo` del lote. Espeja el JS.
- **F3 — Endpoints de selección**: `lotesDisponiblesJSON(filtros)` (criteria sobre recepción⨝
  control calidad, no reservados), `empresaBusquedaJSON` (reuso), orden/paginación.
- **F4 — Vista de conformación**: create/edit AdminLTE con panel de filtros, tabla de disponibles,
  panel del conjunto, resumen al pie + cuadro-resumen; JS de cálculo en vivo por contrato de IDs
  (sin enganchar `document.querySelector('form')` — hay `logoutForm` en el navbar).
- **F5 — Persistencia + hooks**: `save`/`update` arman la hija, recalculan con el service, reservan
  lotes vía HQL; `reabrir`/`anular` (D7); `delete` libera lotes. show.gsp AdminLTE.
  **Guard cross-módulo (D11):** en `LiquidacionDeComplejoController.anular`, rechazar si
  `recepcion.nombreComposito != "-"` (mensaje: "el lote está en el compósito X; quítelo primero").
- **F6 — Reportes**: re-apuntar `ReporteCompositoDeLotes` (impresión del compósito) y
  `ReporteLotesCompositos` (EN/SIN compósito) al nuevo modelo; export XLSX con
  `ReporteXlsxBuilderService`. Deprecar `AsignacionLoteConjuntoComplejo`.
- **F7 — Verificación** integral en la app (filtros, reserva/liberación, definitivo inmutable,
  resumen liquidados vs no liquidados, numeración por gestión).

## 8. Riesgos / puntos abiertos

- **Consistencia Promexbol vs liquidación**: la liquidación usa `%final=(Promexbol+Cliente)/2`;
  el compósito usará Promexbol (D4). Los finos del resumen pueden diferir levemente de los de la
  liquidación del mismo lote — es esperado; documentar en UI.
- **Migración de blobs JSON**: si hay compósitos históricos que solo viven en los blobs, migrarlos a
  la hija antes de retirar los blobs.
- **Resuelto (D11)**: liquidación anulada con lote en compósito → **bloqueo total**; el guard vive en
  `LiquidacionDeComplejoController.anular` (`recepcion.nombreComposito != "-"`).
- **Resuelto (D10)**: destino EXPORTACION → se trata como comprador; sin campos de exportación.

## 9. Requisito narrativo original (referencia)

> Un conjunto de lotes se conforma por lotes (recepciones de complejo) que pueden estar liquidados o
> no pero sí deben tener el análisis de laboratorio realizado (control calidad complejo). La app debe
> ser capaz de brindar al usuario las maneras más flexibles para conformar un conjunto: permitir
> filtrar por rangos de leyes (zinc, plomo y plata) de forma individual, conjunta o en combinación,
> por empresa, ordenar por ley de cualquiera de los elementos asc/desc, buscar por un lote específico,
> etc., y desplegar la información en una tabla de lotes disponibles para la selección.
>
> Un conjunto puede tener como destino la venta, la exportación o el envío a un ingenio. Es necesario
> asignar un nombre o sigla único y poder mantenerlo provisional (se agregan/quitan lotes) o definitivo
> (sin modificaciones). En cualquier caso, los lotes seleccionados deben marcarse con el nombre del
> conjunto (`nombreComposito` en `RecepcionDeComplejo`) para no estar disponibles en un próximo conjunto.
>
> A medida que se conforma el conjunto debe mostrarse al pie del listado y en un cuadro-resumen más
> abajo: total de peso bruto, peso neto seco, kilos finos, valor neto y líquido pagable, y los
> promedios ponderados de humedad, ley de zinc, ley de plomo y ley de plata.
