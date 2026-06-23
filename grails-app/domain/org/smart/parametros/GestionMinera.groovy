package org.smart.parametros

class GestionMinera {
    static auditable = true

    Date gestion
    String estado

    static constraints = {
        gestion()
        estado(inList: ["ACTIVO","INACTIVO"])
    }
    
    def beforeInsert = {
        def gestionAnterior = GestionMinera.findByEstado("ACTIVO")
        if(gestionAnterior){
            gestionAnterior.estado = "INACTIVO"
            gestionAnterior.save()
        }
        this.estado = "ACTIVO"
    }

    String toString(){
        gestion ? new java.text.SimpleDateFormat('yyyy').format(gestion) : "?"
    }
}
