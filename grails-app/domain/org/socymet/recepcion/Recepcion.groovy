package org.socymet.recepcion

import org.socymet.cotizaciones.Alicuota
import org.socymet.cotizaciones.CotizacionDeDolar
import org.socymet.cotizaciones.CotizacionDiariaDeMinerales
import org.socymet.cotizaciones.CotizacionQuincenalDeMinerales
import org.socymet.proveedor.*
import org.socymet.seguridad.SecUser

class Recepcion {
    Deposito deposito
    Cliente cliente
    Empresa empresa
    Chofer chofer
    Automovil automovil

    Date fechaDeRecepcion

    String tipoDeMaterial

    String cantidadDeSacos="0"
    BigDecimal pesoBruto
    BigDecimal pesoTara=0
    BigDecimal costoDeTransporte=0
    BigDecimal costoManipuleo=0

    String estadoAnticipo = "-"// sin anticipo, con anticipo, anticipo pagado
    String transportePagado //0=no pagado o 1=pagado
    String manipuleoPagado

    BigDecimal anticipoAutorizado = 0

    String numeroDeDocumento="0"
    Integer documentacionCompleta = 1 // completo=1, incompleto=0

    String observaciones="-"
    SecUser usuario

    Alicuota alicuota
    CotizacionDeDolar cotizacionDeDolar
    CotizacionDiariaDeMinerales cotizacionDiariaDeMinerales
    CotizacionQuincenalDeMinerales cotizacionQuincenalDeMinerales



    static constraints = {
    }


}
