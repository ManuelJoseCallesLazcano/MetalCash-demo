# Checklist de verificación — Rediseño LiquidacionDeComplejo

Marcar cada ítem al verificar en la app corriendo (`:8090`, autenticado con rol ROLE_LIQUIDACION o ROLE_ADMIN).

## 0. Prerrequisitos / esquema
- [ ] La app arranca sin errores (revisar `stacktrace.log`).
- [ ] DDL aplicado: columnas nuevas en la tabla `liquidacion` (`gestion_minera`, `anulado`, `regalia_minera_de_zinc`/`plomo`/`plata` (+`_en_bolivianos`), `total_regalia_minera_dolares`, `valor_oficial_bruto_en_bolivianos`, `valor_pagable_mineral`, `bono_transporte`, `bono_lealtad`, `total_bonos`, `saldo_anterior`, `total_anticipos`, `precio_calculado`). Como son nullable, `dbCreate=update` debería agregarlas solo; si alguna falta → `ALTER TABLE liquidacion ADD COLUMN ... NULL;`.
- [ ] Existe una `GestionMinera` en estado ACTIVO (si no, falla `gestionMineraActiva()`).
- [ ] Hay al menos un lote `RecepcionDeComplejo` en estado **NO LIQUIDADO** con su `ControlCalidadComplejo` (leyes) y cotizaciones/alícuota/dólar capturadas.

## 1. Test automático del motor de cálculo
- [ ] `./gradlew test --tests "org.socymet.liquidacion.LiquidacionComplejoCalculoServiceSpec" -x configureChromeDriverBinary -x configureGeckoDriverBinary` → **verde** (11 valores del xlsx).

## 2. Create — selector y prefill
- [ ] `/liquidacionDeComplejo/create` muestra el **selector de lote** (Select2) con lotes NO LIQUIDADO; sin lote seleccionado aparece el aviso "Seleccione un lote…".
- [ ] Al elegir un lote → recarga con `?recepcionId=` y **precarga**: cliente/empresa/lote/fecha, peso bruto, cotizaciones (diaria/quincenal), alícuotas y tipo de cambio (de las refs del lote), leyes registrada/cliente y **ley final = promedio simple**.
- [ ] Sin cotización de dólar en el lote → usa `CotizacionDeDolar.findByActivo(1)` (TC no queda vacío).

## 3. Cálculo en vivo (JS) — usar los datos del xlsx para validar
Ingresar: PesoBruto=18360, Humedad=1, Merma=0; leyes finales Zn=7.88, Pb=0.18, Ag=0.98; cot. quincenal Zn=1.27/Pb=0.9/Ag=38.22; alícuotas 3/3/3.6; cot. diaria 1.2492/0.8702/37.35; TC=6.96; VPT=200; retenciones %VNV: 1.8, 1.0, 0.4, 1.5; bonos 100/200/300; anticipo c/entrega=2500, c/futura=500, saldo=0.
- [ ] PNS ≈ **18176.40**; VBV Bs total ≈ **43597.57**; RM Bs total ≈ **1399.33**.
- [ ] VNV Bs ≈ **25301.55**; Total Deducciones ≈ **2588.51**; Valor Pagable ≈ **22713.04**.
- [ ] Total Bonos = **600**; Total Anticipos = **3000**; **Líquido Pagable ≈ 20313.04**; Precio ≈ **160.57**.
- [ ] Los valores recalculan **al instante** al cambiar cualquier input (ley, VPT, retención, bono, anticipo).
- [ ] Columna **"Dif. vs registrada"** de leyes se actualiza (verde si sube, rojo si baja).

## 4. VPT (modo)
- [ ] Modo **MANUAL** → `#vpt` editable; al cambiarlo recalcula.
- [ ] Modo **TABLA** → al elegir tabla y "Calcular VPT" llama `/tablaOrigenCotizacionesComplejo/calcularVPT` y trae un VPT (aleatorio, stub) → recalcula. Sin 404/403.
- [ ] Modo **TERMINOS DE CONTRATO** → idem con `/terminosDeContrato/calcularVPT`.

## 5. Retenciones (tabla)
- [ ] Se precargan las retenciones de `EmpresaRetenciones` de la empresa del lote.
- [ ] La fila **Regalía Minera (calculada)** muestra el RM Bs total.
- [ ] Editar una cantidad recalcula su monto y el total.
- [ ] El botón **✕** elimina la fila y recalcula.

## 6. Anticipo del lote (full/fracción)
- [ ] Lote con anticipo de **un solo lote** → aviso azul "Se descuenta completo" y el campo precargado con el total por pagar.
- [ ] Lote con anticipo de **varios lotes** → aviso **amarillo** "ingrese la fracción…", campo en 0.
- [ ] Intentar guardar con descuento **mayor** al total por pagar → se **rechaza** con mensaje.

## 7. Guardar (save — backend autoritativo)
- [ ] Confirmación SweetAlert al "Guardar liquidación".
- [ ] Redirige a `show` con los valores **iguales a los calculados en vivo** (recálculo backend coincide).
- [ ] El **lote pasa a LIQUIDADO** (verlo en RecepcionDeComplejo).
- [ ] Se crean `LiquidacionDeComplejoRetenciones` (detalle) y `RetencionPorPagarComplejo` (uno por retención).
- [ ] Si había anticipo y se descontó → `Anticipo.totalPorPagar/totalPagado` actualizados; `AnticipoDetalle.estadoAnticipo` = PAGADO (saldado) o PARCIAL; recepción CON ANTICIPO/PAGADO.
- [ ] Saldo negativo (VPT bajo a propósito) → se genera un **AnticipoContraFuturaEntrega** (numerado por su gestión) y un asiento de **deuda** en `EstadoDeCuenta` (debe). El show enlaza al ACFE.

## 8. Show
- [ ] Todas las secciones muestran los valores (VBV/RM por mineral, VNV, deducciones con detalle, valor pagable, bonos, anticipos, líquido, precio).
- [ ] Badge **N°** y, si aplica, **ANULADA**.
- [ ] Sin errores de render (closures `g.formatNumber`).

## 9. Imprimir
- [ ] Botón "Imprimir comprobante" abre `imprimir` en pestaña nueva.
- [ ] El comprobante refleja el xlsx con todos los campos; "Imprimir" abre el diálogo; al imprimir se ocultan los botones.

## 10. Anular
- [ ] Botón **Anular** (solo ROLE_ADMIN) con confirmación SweetAlert.
- [ ] Tras anular: badge ANULADA; el **lote vuelve a NO LIQUIDADO**.
- [ ] Se **borran** los `RetencionPorPagarComplejo` del lote.
- [ ] Se **revierte el descuento de anticipo** (totalPorPagar restaurado, AnticipoDetalle SIN PAGAR).
- [ ] Si había ACFE por saldo negativo → queda **ANULADO** y hay asiento inverso (haber) en `EstadoDeCuenta`.
- [ ] El saldo del cliente en `EstadoDeCuenta` queda consistente (debe/haber netos = 0 por la liquidación anulada).
- [ ] No aparece botón Editar/Eliminar (ya no existen).

## 11. Regresiones (cambios previos relacionados)
- [ ] Datepicker uniforme y estilizado en LiquidacionDeComplejo y demás módulos (RecepcionDeComplejo, ControlCalidadComplejo, CotizacionQuincenal/Diaria, Alicuota, CotizacionDeDolar, Anticipo, ACFE, Amortizacion).
- [ ] Sin 404 en consola por `webapp/js`/`css` en esos forms.
- [ ] Buscadores de Anticipo, ACFE y Amortizacion (comprobante/cliente/empresa) funcionan.
- [ ] Logo del sidebar cambia a la versión mínima al colapsar el menú.
