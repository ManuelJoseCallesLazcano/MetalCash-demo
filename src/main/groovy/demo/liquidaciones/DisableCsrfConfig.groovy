package demo.liquidaciones

import org.springframework.beans.factory.SmartInitializingSingleton
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.web.FilterChainProxy
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.security.web.util.matcher.RequestMatcher

import javax.servlet.http.HttpServletRequest

@Configuration
class DisableCsrfConfig implements SmartInitializingSingleton {

    @Autowired
    FilterChainProxy springSecurityFilterChain

    @Override
    void afterSingletonsInstantiated() {
        springSecurityFilterChain.filterChains.each { chain ->
            chain.filters.findAll { it instanceof CsrfFilter }.each { CsrfFilter csrfFilter ->
                csrfFilter.requireCsrfProtectionMatcher = { HttpServletRequest req -> false } as RequestMatcher
            }
        }
    }
}
