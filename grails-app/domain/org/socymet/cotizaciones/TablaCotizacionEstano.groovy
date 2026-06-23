package org.socymet.cotizaciones

class TablaCotizacionEstano {
    String nombreDeTabla

    BigDecimal cotizacionInicial
    BigDecimal cotizacionFinal
    //rango de leyes e incrementos
    BigDecimal ley5valorIncrementable
    BigDecimal ley5valorInicial
    BigDecimal ley10valorIncrementable
    BigDecimal ley10valorInicial
    BigDecimal ley15valorIncrementable
    BigDecimal ley15valorInicial
    BigDecimal ley20valorIncrementable
    BigDecimal ley20valorInicial
    BigDecimal ley25valorIncrementable
    BigDecimal ley25valorInicial
    BigDecimal ley30valorIncrementable
    BigDecimal ley30valorInicial
    BigDecimal ley35valorIncrementable
    BigDecimal ley35valorInicial
    BigDecimal ley40valorIncrementable
    BigDecimal ley40valorInicial
    BigDecimal ley50valorIncrementable
    BigDecimal ley50valorInicial
    BigDecimal ley60valorIncrementable
    BigDecimal ley60valorInicial
    BigDecimal ley70valorIncrementable
    BigDecimal ley70valorInicial
    BigDecimal ley75valorIncrementable
    BigDecimal ley75valorInicial

    String tablaDeCotizaciones

    static constraints = {
        nombreDeTabla(blank: false)
        cotizacionInicial min: 0.0
        cotizacionFinal min: 0.0
        ley5valorIncrementable min: 0.0
        ley5valorInicial min: 0.0
        ley10valorIncrementable min: 0.0
        ley10valorInicial min: 0.0
        ley15valorIncrementable min: 0.0
        ley15valorInicial min: 0.0
        ley20valorIncrementable min: 0.0
        ley20valorInicial min: 0.0
        ley25valorIncrementable min: 0.0
        ley25valorInicial min: 0.0
        ley30valorIncrementable min: 0.0
        ley30valorInicial min: 0.0
        ley35valorIncrementable min: 0.0
        ley35valorInicial min: 0.0
        ley40valorIncrementable min: 0.0
        ley40valorInicial min: 0.0
        ley50valorIncrementable min: 0.0
        ley50valorInicial min: 0.0
        ley60valorIncrementable min: 0.0
        ley60valorInicial min: 0.0
        ley70valorIncrementable min: 0.0
        ley70valorInicial min: 0.0
        ley75valorIncrementable min: 0.0
        ley75valorInicial min: 0.0

        tablaDeCotizaciones()
    }

    static mapping = {
        tablaDeCotizaciones type: 'text'
    }

    String toString(){
        "${nombreDeTabla}"
    }
}
