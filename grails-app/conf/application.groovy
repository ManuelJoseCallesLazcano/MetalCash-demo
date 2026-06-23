// ── Spring Security ───────────────────────────────────────────────────────────
grails.plugin.springsecurity.userLookup.userDomainClassName    = 'org.socymet.seguridad.SecUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'org.socymet.seguridad.SecUserSecRole'
grails.plugin.springsecurity.authority.className               = 'org.socymet.seguridad.SecRole'

// En spring-security-core 4.x el formato de staticRules es lista de mapas
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
    [pattern: '/',               access: ['permitAll']],
    [pattern: '/index',          access: ['permitAll']],
    [pattern: '/index.gsp',      access: ['permitAll']],
    [pattern: '/assets/**',      access: ['permitAll']],
    [pattern: '/**/js/**',       access: ['permitAll']],
    [pattern: '/**/css/**',      access: ['permitAll']],
    [pattern: '/**/images/**',   access: ['permitAll']],
    [pattern: '/**/favicon.ico', access: ['permitAll']],
]

// BCrypt como encoder de contraseñas (springSecurityService.encodePassword fue deprecado en 4.x)
grails.plugin.springsecurity.password.algorithm = 'bcrypt'
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/recepcionDeComplejo/index'

// CSRF deshabilitado — app interna con autenticación por formulario
grails.plugin.springsecurity.csrf.disabled = true

// ── Audit Logging ─────────────────────────────────────────────────────────────
// En 4.x el prefijo es grails.plugin.auditLog (no auditLog — ese se ignora con error)
grails.plugin.auditLog.auditDomainClassName = 'org.smart.observacion.Observador'
grails.plugin.auditLog.verbose              = true
// replacementPatterns no existe en 4.x — se quitó del plugin

// ── Autowiring de dominios ───────────────────────────────────────────────────
// En GORM 7 (Grails 4) las instancias de dominio creadas con `new` ya NO reciben
// inyección de beans de Spring por defecto. Varios dominios dependen de
// `springSecurityService` dentro de beforeInsert/beforeUpdate (auditoría del campo
// `usuario`, cifrado de contraseña en Usuario, etc.) y fallaban con NPE.
// Este default mapping global reactiva el autowiring para TODOS los dominios.
grails.gorm.default.mapping = {
    autowire true
}

// ── Locale por defecto (de spring/resources.groovy original) ─────────────────
// SessionLocaleResolver con Locale.US se configura en spring/resources.groovy
