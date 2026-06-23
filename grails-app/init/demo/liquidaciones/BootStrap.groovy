package demo.liquidaciones

import org.socymet.proveedor.Deposito
import org.socymet.recepcion.RecepcionDeComplejo
import org.socymet.seguridad.SecRole
import org.socymet.seguridad.SecUser
import org.socymet.seguridad.SecUserSecRole

class BootStrap {

    def init = { servletContext ->
        TimeZone.setDefault(TimeZone.getTimeZone("America/La_Paz"))

//        def deposito = Deposito.findByCodigoDeposito("AOM") ?: new Deposito(nombreDeposito: "CENTRAL", codigoDeposito: "AOM", direccion: "AV. 24 DE JUNIO").save(failOnError: true)

        SecRole.findByAuthority('ROLE_RECEPCION')     ?: new SecRole(authority: 'ROLE_RECEPCION').save(failOnError: true)
        SecRole.findByAuthority('ROLE_CONTROL_CALIDAD') ?: new SecRole(authority: 'ROLE_CONTROL_CALIDAD').save(failOnError: true)
        SecRole.findByAuthority('ROLE_LIQUIDACION')   ?: new SecRole(authority: 'ROLE_LIQUIDACION').save(failOnError: true)
        SecRole.findByAuthority('ROLE_REPORTES')      ?: new SecRole(authority: 'ROLE_REPORTES').save(failOnError: true)
        def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)

        SecUser admin = SecUser.findByUsername('admin') ?: new SecUser(
            deposito: deposito,
            nombre: 'ADMINISTRADOR',
            username: 'admin',
            password: 'admin',
            enabled: true).save(failOnError: true)

        if (!SecUserSecRole.findBySecUserAndSecRole(admin, adminRole)) {
            SecUserSecRole.create admin, adminRole, true
        }

//        SecUser admi = SecUser.findByUsername('admi') ?: new SecUser(
//            deposito: deposito,
//            nombre: 'ADMINISTRADOR',
//            username: 'admi',
//            password: 'admi',
//            enabled: true).save(failOnError: true, flush: true)
//
//        if (!SecUserSecRole.findBySecUserAndSecRole(admi, adminRole)) {
//            SecUserSecRole.create admi, adminRole, true
//        }

//        RecepcionDeComplejo.findAllByCodigoLote("-").each {
//            it.codigoLote = RecepcionDeComplejo.formatearCodigoLote(it.deposito, it.empresa, it.loteComplejo, it.gestionMinera)
//            it.save(failOnError: true)
//        }
    }

    def destroy = {
    }
}
