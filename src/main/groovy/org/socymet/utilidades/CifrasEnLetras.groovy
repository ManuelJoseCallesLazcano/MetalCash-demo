package org.socymet.utilidades

import java.math.RoundingMode

/**
 * Puerto Groovy de CifrasEnLetras (grails-app/assets/javascripts/NumerosALetras.js) — la MISMA
 * conversión número→letras que usan pago de transporte, amortización y anticipos en sus formularios
 * (client-side). Aquí se usa server-side (p. ej. el literal del reporte Jasper de liquidación) para
 * producir un literal idéntico al de esos módulos.
 *
 * `bolivianosLiteral(monto)` replica exactamente pagoTransporteForm.js#actualizarLiteral:
 *   "[UN ]&lt;PALABRAS EN MAYÚSCULAS&gt; NN/100 BOLIVIANOS"  (el "UN " solo para montos en [1000, 2000)).
 */
class CifrasEnLetras {

    static final List<String> listaUnidades = [
        "cero","un","dos","tres","cuatro","cinco","seis","siete","ocho","nueve",
        "diez","once","doce","trece","catorce","quince","dieciseis","diecisiete","dieciocho","diecinueve",
        "veinte","veintiún","veintidos","veintitres","veinticuatro","veinticinco","veintiseis","veintisiete","veintiocho","veintinueve"
    ]
    static final List<String> listaDecenas = ["","diez","veinte","treinta","cuarenta","cincuenta","sesenta","setenta","ochenta","noventa"]
    static final List<String> listaCentenas = ["","cien","doscientos","trescientos","cuatrocientos","quinientos","seiscientos","setecientos","ochocientos","novecientos"]
    static final List<String> ordenesMillonSingular = [
        "","millon","billon","trillon","cuatrillon","quintillon","sextillon","septillon","octillon","nonillon","decillon",
        "undecillon","duodecillon","tridecillon","cuatridecillon","quidecillon","sexdecillon","septidecillon","octodecillon","nonidecillon","vigillon"
    ]
    static final List<String> ordenesMillonPlural = [
        "","millones","billones","trillones","cuatrillones","quintillones","sextillones","septillones","octillones","nonillones","decillones",
        "undecillones","duodecillones","tridecillones","cuatridecillones","quidecillones","sexdecillones","septidecillones","octodecillones","nonidecillones","vigillones"
    ]

    static String convertirUnidades(int unidades, String genero) {
        if (unidades == 1) { if (genero == "masculino") return "uno"; if (genero == "femenino") return "una" }
        else if (unidades == 21) { if (genero == "masculino") return "veintiuno"; if (genero == "femenino") return "veintiuna" }
        listaUnidades[unidades]
    }

    static String convertirCentenas(int centenas, String genero) {
        String r = listaCentenas[centenas]
        (genero == "femenino") ? r.replace("iento", "ienta") : r
    }

    static String convertirDosCifras(int cifras, String genero) {
        int unidad = cifras % 10
        int decena = cifras.intdiv(10)
        if (cifras < 30) return convertirUnidades(cifras, genero)
        else if (unidad == 0) return listaDecenas[decena]
        else return listaDecenas[decena] + " y " + convertirUnidades(unidad, genero)
    }

    static String convertirTresCifras(int cifras, String genero) {
        int du = cifras % 100
        int centenas = cifras.intdiv(100)
        if (cifras < 100) return convertirDosCifras(cifras, genero)
        else if (du == 0) return convertirCentenas(centenas, genero)
        else if (centenas == 1) return "ciento " + convertirDosCifras(du, genero)
        else return convertirCentenas(centenas, genero) + " " + convertirDosCifras(du, genero)
    }

    static String convertirSeisCifras(int cifras, String genero) {
        int primerMillar = cifras % 1000
        int grupoMiles = cifras.intdiv(1000)
        String generoMiles = (genero == "masculino") ? "neutro" : genero
        if (grupoMiles == 0) return convertirTresCifras(primerMillar, genero)
        else if (grupoMiles == 1) return (primerMillar == 0) ? "mil" : "mil " + convertirTresCifras(primerMillar, genero)
        else if (primerMillar == 0) return convertirTresCifras(grupoMiles, generoMiles) + " mil"
        else return convertirTresCifras(grupoMiles, generoMiles) + " mil " + convertirTresCifras(primerMillar, genero)
    }

    /** Puerto de convertirCifrasEnLetras (JS) para enteros no negativos (ignora signo/no dígitos). */
    static String convertirCifrasEnLetras(String cifras, String genero = "neutro") {
        cifras = (cifras ?: "").replaceAll(/[^0-9]/, '')
        int numeroCifras = cifras.length()
        if (numeroCifras == 0) return listaUnidades[0]
        int numeroGrupos = numeroCifras.intdiv(6) + 1
        cifras = ('0' * (numeroGrupos * 6 - numeroCifras)) + cifras
        int ordenMillon = numeroGrupos - 1
        List<String> resultado = []
        for (int i = 0; i < numeroGrupos * 6; i += 6) {
            int seis = Integer.parseInt(cifras.substring(i, i + 6))
            if (seis != 0) {
                if (resultado.size() > 0) resultado.add(" ")
                if (ordenMillon == 0) resultado.add(convertirSeisCifras(seis, genero))
                else if (seis == 1) resultado.add("un " + ordenesMillonSingular[ordenMillon])
                else resultado.add(convertirSeisCifras(seis, "neutro") + " " + ordenesMillonPlural[ordenMillon])
            }
            ordenMillon--
        }
        if (resultado.isEmpty()) resultado.add(listaUnidades[0])
        resultado.join("")
    }

    /** Equivalente a convertirNumeroEnLetras(entero) del JS: parte entera en género masculino. */
    static String convertirNumeroEnLetras(String enteroStr) {
        convertirCifrasEnLetras(enteroStr ?: "0", "masculino")
    }

    /**
     * Literal monetario en Bolivianos, réplica de pagoTransporteForm.js#actualizarLiteral.
     * Ej.: 20313.04 → "VEINTE MIL TRESCIENTOS TRECE 04/100 BOLIVIANOS".
     * Usa el valor absoluto (el módulo cliente nunca imprime negativos).
     */
    static String bolivianosLiteral(Number monto) {
        BigDecimal m = ((monto ?: 0G) as BigDecimal).abs().setScale(2, RoundingMode.HALF_UP)
        long entero = m.toBigInteger().longValue()
        int cent = m.subtract(new BigDecimal(entero)).movePointRight(2).intValue()   // 0..99
        String decimal = cent < 10 ? "0${cent}" : "${cent}"
        String palabras = convertirNumeroEnLetras(entero.toString()).toUpperCase()
        String prefijo = (m >= 1000.0G && m < 2000.0G) ? "UN " : ""
        "${prefijo}${palabras} ${decimal}/100 BOLIVIANOS".toString()
    }
}
