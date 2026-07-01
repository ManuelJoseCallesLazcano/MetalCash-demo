package org.socymet.cotizaciones

/**
 * Punto de la curva (ley → % pagable) de una TablaOrigenCotizacionesComplejo, por elemento.
 * Con estos puntos se interpola el % pagable según la ley final para calcular el VPT.
 */
class TablaPrecioPunto {

    static belongsTo = [tabla: TablaOrigenCotizacionesComplejo]

    String elemento                 // ZINC | PLOMO | PLATA
    BigDecimal ley
    BigDecimal porcentajePagable

    static constraints = {
        elemento inList: ["ZINC", "PLOMO", "PLATA"], blank: false, nullable: false
        ley nullable: false
        porcentajePagable nullable: false
    }

    String toString() {
        "$elemento ley=$ley %pag=$porcentajePagable"
    }
}
