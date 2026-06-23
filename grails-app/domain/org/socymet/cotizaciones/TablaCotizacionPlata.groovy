package org.socymet.cotizaciones

class TablaCotizacionPlata {
    String nombreDeTabla

    BigDecimal cotizacionInicial
    BigDecimal cotizacionFinal
    //rango de leyes e incrementos
    BigDecimal ley10valorIncrementable
    BigDecimal ley10valorInicial
    BigDecimal ley20valorIncrementable
    BigDecimal ley20valorInicial
    BigDecimal ley30valorIncrementable
    BigDecimal ley30valorInicial    
    BigDecimal ley40valorIncrementable
    BigDecimal ley40valorInicial
    BigDecimal ley50valorIncrementable
    BigDecimal ley50valorInicial
    BigDecimal ley60valorIncrementable
    BigDecimal ley60valorInicial
    BigDecimal ley70valorIncrementable
    BigDecimal ley70valorInicial
    BigDecimal ley80valorIncrementable
    BigDecimal ley80valorInicial
    BigDecimal ley90valorIncrementable
    BigDecimal ley90valorInicial
    BigDecimal ley100valorIncrementable
    BigDecimal ley100valorInicial
    BigDecimal ley150valorIncrementable
    BigDecimal ley150valorInicial
    BigDecimal ley200valorIncrementable
    BigDecimal ley200valorInicial
    BigDecimal ley300valorIncrementable
    BigDecimal ley300valorInicial
    BigDecimal ley400valorIncrementable
    BigDecimal ley400valorInicial
    BigDecimal ley500valorIncrementable
    BigDecimal ley500valorInicial
    BigDecimal ley600valorIncrementable
    BigDecimal ley600valorInicial
    BigDecimal ley700valorIncrementable
    BigDecimal ley700valorInicial
    BigDecimal ley800valorIncrementable
    BigDecimal ley800valorInicial
    BigDecimal ley900valorIncrementable
    BigDecimal ley900valorInicial
    BigDecimal ley1000valorIncrementable
    BigDecimal ley1000valorInicial
    String tablaDeCotizaciones

    static constraints = {
        nombreDeTabla(blank: false)
        cotizacionInicial min: 0.0
        cotizacionFinal min: 0.0

        ley10valorIncrementable min: 0.0
        ley10valorInicial min: 0.0
        ley20valorIncrementable min: 0.0
        ley20valorInicial min: 0.0
        ley30valorIncrementable min: 0.0
        ley30valorInicial min: 0.0
        ley40valorIncrementable min: 0.0
        ley40valorInicial min: 0.0
        ley50valorIncrementable min: 0.0
        ley50valorInicial min: 0.0
        ley60valorIncrementable min: 0.0
        ley60valorInicial min: 0.0
        ley70valorIncrementable min: 0.0
        ley70valorInicial min: 0.0
        ley80valorIncrementable min: 0.0
        ley80valorInicial min: 0.0
        ley90valorIncrementable min: 0.0
        ley90valorInicial min: 0.0
        ley100valorIncrementable min: 0.0
        ley100valorInicial min: 0.0
        ley150valorIncrementable min: 0.0
        ley150valorInicial min: 0.0
        ley200valorIncrementable min: 0.0
        ley200valorInicial min: 0.0
        ley300valorIncrementable min: 0.0
        ley300valorInicial min: 0.0
        ley400valorIncrementable min: 0.0
        ley400valorInicial min: 0.0
        ley500valorIncrementable min: 0.0
        ley500valorInicial min: 0.0
        ley600valorIncrementable min: 0.0
        ley600valorInicial min: 0.0
        ley700valorIncrementable min: 0.0
        ley700valorInicial min: 0.0
        ley800valorIncrementable min: 0.0
        ley800valorInicial min: 0.0
        ley900valorIncrementable min: 0.0
        ley900valorInicial min: 0.0
        ley1000valorIncrementable min: 0.0
        ley1000valorInicial min: 0.0

        tablaDeCotizaciones()
    }

    static mapping = {
        tablaDeCotizaciones type: 'text'
    }

    String toString(){
        "${nombreDeTabla}"
    }
}
