package demo.liquidaciones
import grails.gorm.transactions.Transactional

@Transactional
class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'recepcionDeComplejo', action: 'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
