package org.socymet.seguridad

class MiCuenta {
    transient springSecurityService

    String nombre
    String cuenta
    String contrasena
    String confirmarContrasena

    static constraints = {
        nombre blank: false, nullable: false
        cuenta blank: false, nullable: false
        contrasena blank: false, nullable: false
        confirmarContrasena blank: false, nullable: false, validator: { val, obj, errors ->
            if (!(obj.confirmarContrasena == val)) errors.rejectValue('confirmarContrasena', 'noMatch')
        }
    }
}
