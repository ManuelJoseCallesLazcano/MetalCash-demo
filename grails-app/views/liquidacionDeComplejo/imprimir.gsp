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
        .liquido { font-size: 14px; font-weight: bold; background: #eef7ee; border: 1px solid #9c9; padding: 8px; text-align: center; margin-top: 8px; }
        .anulada { color: #c00; border: 2px solid #c00; padding: 2px 8px; font-weight: bold; transform: rotate(-3deg); display: inline-block; }
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
<div class="hoja">
    <h1>LIQUIDACIÓN DEL MINERAL Pb - Ag - Zn</h1>
    <h2>Comprobante N° ${i?.numeroLiquidacionComplejo}
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
        LÍQUIDO PAGABLE: ${f2(i?.totalLiquidoPagable)} Bs
        &nbsp;|&nbsp; Precio calculado: ${f2(i?.precioCalculado)} $us/TM
    </div>
    <g:if test="${i?.totalLiquidoPagableLiteral && i.totalLiquidoPagableLiteral != '-'}">
        <p style="text-align:center; font-style:italic; margin-top:4px;">Son: ${i?.totalLiquidoPagableLiteral}</p>
    </g:if>
</div>
</body>
</html>
