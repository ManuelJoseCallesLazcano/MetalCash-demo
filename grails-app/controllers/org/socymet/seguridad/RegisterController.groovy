package org.socymet.seguridad
import grails.gorm.transactions.Transactional

import org.springframework.security.access.annotation.Secured

@Secured(['ROLE_ADMIN'])
@Transactional
class RegisterController {
    // spring-security-ui no tiene release estable para Grails 4.
    // La gestión de usuarios se realiza directamente desde la BD o via BootStrap.
    def index() { render "Registro de usuarios no disponible." }
}
