package org.socymet.cotizaciones

class TablaCotizacionPlataComplejo {
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
    BigDecimal ley25valorIncrementable
    BigDecimal ley25valorInicial
    BigDecimal ley30valorIncrementable
    BigDecimal ley30valorInicial
    BigDecimal ley35valorIncrementable
    BigDecimal ley35valorInicial

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
        ley25valorIncrementable min: 0.0
        ley25valorInicial min: 0.0
        ley30valorIncrementable min: 0.0
        ley30valorInicial min: 0.0
        ley35valorIncrementable min: 0.0
        ley35valorInicial min: 0.0

        tablaDeCotizaciones()
    }

    static mapping = {
        tablaDeCotizaciones type: 'text'
    }

    String toString(){
        "${nombreDeTabla}"
    }
}
