package liquidaciones

import org.socymet.cotizaciones.CotizacionDiariaDeMinerales

class CotizacionesTagLib {
//    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def escribeDatos = {
        def cotizaciones = CotizacionDiariaDeMinerales.list([sort: 'fecha', order: 'desc'])
        if(!cotizaciones || cotizaciones.size()<2)
            out<<"INFORMACION NO DISPONIBLE EN ESTE MOMENTO"
        else{
            out << "<span id=\"fecha\" style=\"color: black; font-weight: bold\">COTIZACI&Oacute;N DIARIA (${new Date(cotizaciones.get(0).fecha.time).format("dd/MM/yyyy")}): </span>"
            out << "<span id=\"zinc\" style=\"color: black\">Zinc: ${cotizaciones.get(0).zinc} \$/LF</span>"
            out << "<span id=\"zinc\" style=\"color: #72BDA3\"> * </span>"
//            out << "<span id=\"zincUp\"><img style=\"max-width: 15px; max-height: 15px;\" src=\"${cotizaciones.get(0).zinc>cotizaciones.get(1).zinc?resource(dir: 'images', file: 'up.png'):resource(dir: 'images', file: 'down.png')}\" alt=\"PROMEX BOL\" /> </span>"
            out << "<span id=\"plomo\" style=\"color: black\">Plomo: ${cotizaciones.get(0).plomo} \$/LF</span>"
            out << "<span id=\"zinc\" style=\"color: #72BDA3\"> * </span>"
//            out << "<span id=\"plomoUp\"><img style=\"max-width: 15px; max-height: 15px;\" src=\"${cotizaciones.get(0).plomo>cotizaciones.get(1).plomo?resource(dir: 'images', file: 'up.png'):resource(dir: 'images', file: 'down.png')}\" alt=\"PROMEX BOL\" /> </span>"
            out << "<span id=\"plata\" style=\"color: black\">Plata: ${cotizaciones.get(0).plata} \$/OT</span>"
//            out << "<span id=\"oro\" style=\"color: #72BDA3\"> * </span>"
//            out << "<span id=\"oro\" style=\"color: black\">Oro: ${cotizaciones.get(0).oro} \$/OT</span>"
//            out << "<span id=\"plataUp\"><img style=\"max-width: 15px; max-height: 15px;\" src=\"${cotizaciones.get(0).plata>cotizaciones.get(1).plata?resource(dir: 'images', file: 'up.png'):resource(dir: 'images', file: 'down.png')}\" alt=\"PROMEX BOL\" /> </span>"
//            out << "<span id=\"cobre\" style=\"color: black\">Cobre: ${cotizaciones.get(0).cobre} \$/LF</span>"
//            out << "<span id=\"cobreUp\"><img style=\"max-width: 15px; max-height: 15px;\" src=\"${cotizaciones.get(0).cobre>cotizaciones.get(1).cobre?resource(dir: 'images', file: 'up.png'):resource(dir: 'images', file: 'down.png')}\" alt=\"PROMEX BOL\" /> </span>"
        }
    }
}
