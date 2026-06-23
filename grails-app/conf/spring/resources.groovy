import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

// Place your Spring DSL code here
beans = {
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
        defaultLocale = new Locale("en","US")
        java.util.Locale.setDefault(defaultLocale)
    }

    // Forzar BCryptPasswordEncoder directo — el plugin usa DelegatingPasswordEncoder por defecto
    // que espera prefijo {bcrypt} pero SecUser.encodePassword() guarda hashes sin prefijo
    passwordEncoder(BCryptPasswordEncoder)
}
