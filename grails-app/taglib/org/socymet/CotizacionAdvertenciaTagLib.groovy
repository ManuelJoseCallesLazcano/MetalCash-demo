package org.socymet

import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.cotizaciones.CotizacionDiariaDeMinerales
import org.socymet.cotizaciones.CotizacionQuincenalDeMinerales

/**
 * Ícono + modal de advertencia de cotizaciones desactualizadas para el navbar del layout.
 * La diaria y la de dólar deben ser de HOY; la quincenal debe cubrir la quincena actual
 * (día 1–15 o 16–fin de mes). Se renderiza en todas las páginas (main.gsp).
 */
class CotizacionAdvertenciaTagLib {
    static namespace = "cot"

    /** Renderiza el ítem del navbar (ícono con contador) y el modal, si hay cotizaciones vencidas. */
    def cotizacionesAdvertencia = { attrs ->
        Date hoy = new Date()
        def cotDolar = CotizacionDeDolar.findByActivo(1)
        def cotDiaria = CotizacionDiariaDeMinerales.findByActivo(1)
        def cotQuincenal = CotizacionQuincenalDeMinerales.findByActivo(1)

        def vencidas = []
        if (!cotDiaria || !mismoDia(cotDiaria.fecha, hoy))
            vencidas << [nombre: 'Cotización diaria de minerales', fecha: cotDiaria?.fecha, controlador: 'cotizacionDiariaDeMinerales']
        if (!cotDolar || !mismoDia(cotDolar.fecha, hoy))
            vencidas << [nombre: 'Cotización de dólar', fecha: cotDolar?.fecha, controlador: 'cotizacionDeDolar']
        if (!cotQuincenal || !quincenaVigente(cotQuincenal.fecha, hoy))
            vencidas << [nombre: 'Cotización quincenal de minerales', fecha: cotQuincenal?.fecha, controlador: 'cotizacionQuincenalDeMinerales']

        out << render(template: '/dashboard/cotizacionesAdvertencia', model: [cotizacionesVencidas: vencidas, hoy: hoy])
    }

    /** ¿a y b son el mismo día calendario? */
    private static boolean mismoDia(Date a, Date b) {
        if (!a || !b) return false
        def ca = Calendar.instance; ca.setTime(a)
        def cb = Calendar.instance; cb.setTime(b)
        ca.get(Calendar.YEAR) == cb.get(Calendar.YEAR) && ca.get(Calendar.DAY_OF_YEAR) == cb.get(Calendar.DAY_OF_YEAR)
    }

    /** Inicio de la quincena de una fecha: día 1 (si día ≤ 15) o día 16 del mismo mes, a medianoche. */
    private static Date inicioQuincena(Date d) {
        def c = Calendar.instance; c.setTime(d)
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) <= 15 ? 1 : 16)
        c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE, 0); c.set(Calendar.SECOND, 0); c.set(Calendar.MILLISECOND, 0)
        c.time
    }

    /** La cotización quincenal está vigente si su fecha cae en la misma quincena que hoy. */
    private static boolean quincenaVigente(Date fechaCot, Date hoy) {
        fechaCot && mismoDia(inicioQuincena(fechaCot), inicioQuincena(hoy))
    }
}
