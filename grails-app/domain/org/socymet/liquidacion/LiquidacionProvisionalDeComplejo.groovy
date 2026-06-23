package org.socymet.liquidacion

import org.socymet.proveedor.Cliente
import org.socymet.proveedor.Deposito
import org.socymet.proveedor.Empresa
import org.socymet.recepcion.RecepcionDeComplejo

class LiquidacionProvisionalDeComplejo {
    Cliente cliente
    Empresa empresa
    Deposito deposito
    Date fechaDeLiquidacionProvisional
    Integer numeroLiquidacionProvisionalComplejo

    RecepcionDeComplejo recepcionDeComplejo

    String lote
    String direccion

    String tipoDeMineral
    String nombreCliente
    String nombreEmpresa
    String fechaDeRecepcion
    String cantidadDeSacos
    String naturalezaMineral

    BigDecimal toneladasMetricasHumedas // wet metric tonnes
    BigDecimal humedadPromedio //moisture average
    BigDecimal toneladasMetricasSecas
    BigDecimal merma = 1
    BigDecimal toneladasMetricasSecasFinales
    BigDecimal partidaArancelaria
    String condicionesDeEntrega
    String origen
    //resultado laboratorio
    BigDecimal porcentajePlomo
    BigDecimal porcentajePlata

    //deducciones unitarias
    BigDecimal deduccionUnitariaPlomo
    BigDecimal deduccionUnitariaPlata
    //porcentaje pagable LME
    BigDecimal porcentajePagableLMEPlomo
    BigDecimal porcentajePagableLMEPlata
    //maquila, base, escalador
    BigDecimal maquilaZincPlata
    BigDecimal baseZincPlata
    BigDecimal escaladorZincPlata
    BigDecimal maquilaPlomoPlata
    BigDecimal basePlomoPlata

    BigDecimal escaladorPlomoPlata
    BigDecimal maquilaCobre
    BigDecimal baseCobre
    BigDecimal escaladorCobre
    //deducciones refinacion de onza pagable de plata
    BigDecimal deduccionRefinacionOnzaZincPlata
    BigDecimal deduccionRefinacionOnzaPlomoPlata
    BigDecimal deduccionRefinacionOnzaCobrePlata
    //deducciones refinacion de libra pagable
    BigDecimal deduccionRefinacionLibraZinc
    BigDecimal deduccionRefinacionLibraPlomo
    BigDecimal deduccionRefinacionLibraCobre
    //leyes de elementos a castigar
    BigDecimal porcentajeArsenico
    BigDecimal porcentajeAntimonio
    BigDecimal porcentajeBismuto
    BigDecimal porcentajeEstano
    BigDecimal porcentajeHierro
    BigDecimal porcentajeSilice
    BigDecimal porcentajeZinc
    //penalidades
    BigDecimal arsenicoLibre
    BigDecimal costoUnitarioArsenico
    BigDecimal porcentajeUnitarioArsenico
    BigDecimal antimonioLibre
    BigDecimal costoUnitarioAntimonio
    BigDecimal porcentajeUnitarioAntimonio
    BigDecimal bismutoLibre
    BigDecimal costoUnitarioBismuto
    BigDecimal porcentajeUnitarioBismuto
    BigDecimal estanoLibre
    BigDecimal costoUnitarioEstano
    BigDecimal porcentajeUnitarioEstano
    BigDecimal hierroLibre
    BigDecimal costoUnitarioHierro
    BigDecimal porcentajeUnitarioHierro
    BigDecimal siliceLibre
    BigDecimal costoUnitarioSilice
    BigDecimal porcentajeUnitarioSilice
    BigDecimal zincLibre
    BigDecimal costoUnitarioZinc
    BigDecimal porcentajeUnitarioZinc
    //otras deducciones
    BigDecimal transporteInterno
    BigDecimal laboratorio
    BigDecimal molienda
    BigDecimal manipuleo
    BigDecimal margenAdministrativo
    BigDecimal transporteAPuerto
    BigDecimal rollBack

    static constraints = {
    }
}
