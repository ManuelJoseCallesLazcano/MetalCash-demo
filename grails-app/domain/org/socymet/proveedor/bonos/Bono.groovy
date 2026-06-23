package org.socymet.proveedor.bonos

import org.socymet.proveedor.Empresa

class Bono {
    Empresa empresa
    String elemento
    String simboloElemento
    BigDecimal bono

    static constraints = {
    }
}
