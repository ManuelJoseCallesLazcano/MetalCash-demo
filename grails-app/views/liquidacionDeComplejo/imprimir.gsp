<%@ page import="org.socymet.liquidacion.LiquidacionDeComplejo" %>
<g:set var="i" value="${liquidacionDeComplejoInstance}"/>
<g:set var="f2" value="${{ v -> g.formatNumber(number: v ?: 0, type: 'number', minFractionDigits: 2, maxFractionDigits: 2) }}"/>
<g:set var="f3" value="${{ v -> g.formatNumber(number: v ?: 0, type: 'number', minFractionDigits: 3, maxFractionDigits: 3) }}"/>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <title>Liquidación ${i?.lote}</title>
    <style>
        * { box-sizing: border-box; }
        body { font-family: 'Segoe UI', Arial, sans-serif; font-size: 12px; color: #222; margin: 0; padding: 20px; }
        .hoja { max-width: 820px; margin: 0 auto; }
        h1 { font-size: 15px; text-align: center; margin: 0 0 2px; }
        h2 { font-size: 11px; text-align: center; font-weight: normal; color: #555; margin: 0 0 12px; }
        .sec { font-size: 11px; font-weight: bold; text-transform: uppercase; background: #f0f0f0; border-left: 3px solid #555; padding: 3px 6px; margin: 12px 0 4px; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 6px; }
        th, td { border: 1px solid #bbb; padding: 3px 6px; }
        th { background: #f7f7f7; }
        .r { text-align: right; } .c { text-align: center; }
        .kv { width: 100%; }
        .kv td { border: none; padding: 1px 6px; }
        .kv .lbl { color: #555; width: 22%; }
        .tot td { font-weight: bold; background: #fafafa; }
        .liquido { display: flex; align-items: center; justify-content: space-between; background: #e6f4ea; border: 3px solid #2e7d32; border-radius: 6px; padding: 12px 20px; margin-top: 14px; box-shadow: 0 2px 6px rgba(46,125,50,0.25); }
        .liquido-lbl { font-size: 16px; font-weight: bold; text-transform: uppercase; letter-spacing: 0.06em; color: #1b5e20; }
        .liquido-monto { font-size: 26px; font-weight: bold; color: #1b5e20; }
        .anulada { color: #c00; border: 2px solid #c00; padding: 2px 8px; font-weight: bold; transform: rotate(-3deg); display: inline-block; }
        .borrador-banner { text-align: center; color: #b26a00; background: #fff3cd; border: 2px dashed #d39e00; padding: 6px; font-weight: bold; letter-spacing: 0.15em; margin-bottom: 10px; }
        .marca-borrador { position: fixed; top: 42%; left: 0; right: 0; text-align: center; font-size: 90px; color: rgba(210, 158, 0, 0.12); font-weight: bold; letter-spacing: 0.2em; transform: rotate(-20deg); pointer-events: none; z-index: 0; }
        .toolbar { text-align: center; margin-bottom: 14px; }
        .btn { padding: 6px 14px; font-size: 13px; cursor: pointer; }
        @media print { .toolbar { display: none; } body { padding: 0; } }
    </style>
</head>
<body onload="if(location.hash!=='#noprint'){}">
<div class="toolbar">
    <button class="btn" onclick="window.print()">Imprimir</button>
    <button class="btn" onclick="window.close()">Cerrar</button>
</div>
<g:if test="${borrador}"><div class="marca-borrador">BORRADOR</div></g:if>
<div class="hoja">
    <g:if test="${borrador}">
        <div class="borrador-banner">BORRADOR — DOCUMENTO EN NEGOCIACIÓN, SIN VALIDEZ OFICIAL</div>
    </g:if>
    <h1>LIQUIDACIÓN DEL MINERAL</h1>
    <h2><g:if test="${borrador}">Borrador</g:if><g:else>Comprobante N° ${i?.numeroLiquidacionComplejo}</g:else>
        <g:if test="${i?.anulado}"> &nbsp; <span class="anulada">ANULADA</span></g:if>
    </h2>

    <table class="kv">
        <tr><td class="lbl">Cliente:</td><td>${i?.nombreCliente}</td><td class="lbl">Lote:</td><td>${i?.lote}</td></tr>
        <tr><td class="lbl">Empresa:</td><td>${i?.nombreEmpresa}</td><td class="lbl">Fecha recepción:</td><td>${i?.fechaDeRecepcion}</td></tr>
        <tr><td class="lbl">Fecha liquidación:</td><td><g:formatDate date="${i?.fechaDeLiquidacion}" format="dd/MM/yyyy"/></td><td class="lbl">Tipo de cambio:</td><td>${f2(i?.tipoDeCambioOficial)} Bs/$us</td></tr>
    </table>

    <div class="sec">Características del Mineral</div>
    <table class="kv">
        <tr><td class="lbl">Peso Bruto Húmedo:</td><td>${f2(i?.pesoBruto)} Kg</td><td class="lbl">Humedad:</td><td>${f3(i?.porcentajeHumedadFinal)} %</td><td class="lbl">Merma:</td><td>${f3(i?.porcentajeMermaFinal)} %</td></tr>
        <tr><td class="lbl">Peso Neto Seco:</td><td><strong>${f2(i?.kilosNetosSecos)} Kg</strong></td><td></td><td></td><td></td><td></td></tr>
    </table>
    <table>
        <thead><tr><th>Mineral</th><th class="r">Ley registrada</th><th class="r">Ley cliente</th><th class="r">Ley final</th><th class="r">Peso fino [Kg]</th><th class="r">Peso fino [lf/ot]</th></tr></thead>
        <tbody>
            <tr><td>ZINC [%]</td><td class="r">${f3(i?.porcentajeZincPromexbol)}</td><td class="r">${f3(i?.porcentajeZincCliente)}</td><td class="r">${f3(i?.porcentajeZincFinal)}</td><td class="r">${f3(i?.kilosFinosZinc)}</td><td class="r">${f3(i?.librasFinasDeZinc)} lf</td></tr>
            <tr><td>PLOMO [%]</td><td class="r">${f3(i?.porcentajePlomoPromexbol)}</td><td class="r">${f3(i?.porcentajePlomoCliente)}</td><td class="r">${f3(i?.porcentajePlomoFinal)}</td><td class="r">${f3(i?.kilosFinosPlomo)}</td><td class="r">${f3(i?.librasFinasDePlomo)} lf</td></tr>
            <tr><td>PLATA [DM]</td><td class="r">${f3(i?.porcentajePlataPromexbol)}</td><td class="r">${f3(i?.porcentajePlataCliente)}</td><td class="r">${f3(i?.porcentajePlataFinal)}</td><td class="r">${f3(i?.kilosFinosPlata)}</td><td class="r">${f3(i?.onzasTroyDePlata)} ot</td></tr>
        </tbody>
    </table>

    <div class="sec">Valor Bruto de Venta y Regalía Minera</div>
    <table>
        <thead><tr><th>Mineral</th><th class="r">VBV [$us]</th><th class="r">VBV [Bs]</th><th class="r">RM [$us]</th><th class="r">RM [Bs]</th></tr></thead>
        <tbody>
            <tr><td>ZINC</td><td class="r">${f2(i?.valorOficialBrutoDeZinc)}</td><td class="r">${f2(i?.valorOficialBrutoDeZincEnBolivianos)}</td><td class="r">${f2(i?.regaliaMineraDeZinc)}</td><td class="r">${f2(i?.regaliaMineraDeZincEnBolivianos)}</td></tr>
            <tr><td>PLOMO</td><td class="r">${f2(i?.valorOficialBrutoDePlomo)}</td><td class="r">${f2(i?.valorOficialBrutoDePlomoEnBolivianos)}</td><td class="r">${f2(i?.regaliaMineraDePlomo)}</td><td class="r">${f2(i?.regaliaMineraDePlomoEnBolivianos)}</td></tr>
            <tr><td>PLATA</td><td class="r">${f2(i?.valorOficialBrutoDePlata)}</td><td class="r">${f2(i?.valorOficialBrutoDePlataEnBolivianos)}</td><td class="r">${f2(i?.regaliaMineraDePlata)}</td><td class="r">${f2(i?.regaliaMineraDePlataEnBolivianos)}</td></tr>
            <tr class="tot"><td>TOTAL</td><td class="r">${f2(i?.valorOficialBruto)}</td><td class="r">${f2(i?.valorOficialBrutoEnBolivianos)}</td><td class="r">${f2(i?.totalRegaliaMineraDolares)}</td><td class="r">${f2(i?.regaliaMinera)}</td></tr>
        </tbody>
    </table>

    <div class="sec">Valor Neto de Venta</div>
    <table class="kv">
        <tr><td class="lbl">Valor por Tonelada:</td><td>${f2(i?.valorPorTonelada)} $us/TM (${i?.modoValoracion})</td><td class="lbl">VNV:</td><td>${f2(i?.valorNetoMineral)} $us &nbsp;=&nbsp; ${f2(i?.valorNetoMineralEnBolivianos)} Bs</td></tr>
    </table>

    <div class="sec">Deducciones</div>
    <table>
        <thead><tr><th>Descripción</th><th>Tipo</th><th>Asignación</th><th class="r">Cantidad</th><th class="r">Monto [Bs]</th></tr></thead>
        <tbody>
            <tr><td colspan="4" class="r"><strong>Regalía Minera</strong></td><td class="r"><strong>${f2(i?.regaliaMinera)}</strong></td></tr>
            <g:each in="${i?.detalleRetenciones}" var="r">
                <tr><td>${r.descripcion}</td><td>${r.tipoDeRetencion}</td><td>${r.asignacionDelDescuento}</td><td class="r">${f2(r.cantidadDescuento)} ${r.unidadDeDescuento}</td><td class="r">${f2(r.monto)}</td></tr>
            </g:each>
            <tr class="tot"><td colspan="4" class="r">Total Deducciones</td><td class="r">${f2(i?.totalRetenciones)}</td></tr>
        </tbody>
    </table>
    <table class="kv"><tr><td class="lbl">Valor Pagable del Mineral:</td><td><strong>${f2(i?.valorPagableMineral)} Bs</strong></td></tr></table>

    <div class="sec">Bonos y Anticipos</div>
    <table class="kv">
        <tr><td class="lbl">Bono Calidad:</td><td class="r">${f2(i?.bonoCalidad)}</td><td class="lbl">Anticipo contra entrega:</td><td class="r">${f2(i?.totalAnticiposContraEntrega)}</td></tr>
        <tr><td class="lbl">Bono Transporte:</td><td class="r">${f2(i?.bonoTransporte)}</td><td class="lbl">Anticipo c/futura entrega:</td><td class="r">${f2(i?.totalAnticiposContraFuturaEntrega)}</td></tr>
        <tr><td class="lbl">Bono Lealtad:</td><td class="r">${f2(i?.bonoLealtad)}</td><td class="lbl">Saldo anterior:</td><td class="r">${f2(i?.saldoAnterior)}</td></tr>
        <tr class="tot"><td class="lbl">Total Bonos:</td><td class="r">${f2(i?.totalBonos)}</td><td class="lbl">Total Anticipos:</td><td class="r">${f2(i?.totalAnticipos)}</td></tr>
    </table>

    <div class="liquido">
        <span class="liquido-lbl">LÍQUIDO PAGABLE</span>
        <span class="liquido-monto">${f2(i?.totalLiquidoPagable)} Bs</span>
    </div>
    <g:if test="${i?.totalLiquidoPagableLiteral && i.totalLiquidoPagableLiteral != '-'}">
        <p style="text-align:center; font-style:italic; margin-top:4px;">Son: ${i?.totalLiquidoPagableLiteral}</p>
    </g:if>
</div>
</body>
</html>
