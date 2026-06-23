package org.socymet.proveedor

class BonoEmpresa {
    Empresa empresa
    //Estano
    String bonoCalidadEstano
    String bonoCantidadEstano
    String bonoIncentivoEstano
    //Plata
    String bonoCalidadPlata
    String bonoCantidadPlata
    String bonoIncentivoPlata
    //Wolfran
    String bonoCalidadWolfran
    String bonoCantidadWolfran
    String bonoIncentivoWolfran
    //Antimonio
    String bonoCalidadAntimonio
    String bonoCantidadAntimonio
    String bonoIncentivoAntimonio
    //Complejo
    String bonoCalidadComplejo
    String bonoCantidadComplejo
    String bonoIncentivoComplejo
    
    static constraints = {
        empresa(nullable: true)
        //Estano
        bonoCalidadEstano nullable: true
        bonoCantidadEstano nullable: true
        bonoIncentivoEstano nullable: true
        //Plata
        bonoCalidadPlata nullable: true
        bonoCantidadPlata nullable: true
        bonoIncentivoPlata nullable: true
        //Wolfran
        bonoCalidadWolfran nullable: true
        bonoCantidadWolfran nullable: true
        bonoIncentivoWolfran nullable: true
        //Antimonio
        bonoCalidadAntimonio nullable: true
        bonoCantidadAntimonio nullable: true
        bonoIncentivoAntimonio nullable: true
        //Complejo
        bonoCalidadComplejo nullable: true
        bonoCantidadComplejo nullable: true
        bonoIncentivoComplejo nullable: true
    }

    static mapping = {
        //Estano
        bonoCalidadEstano type: 'text'
        bonoCantidadEstano type: 'text'
        bonoIncentivoEstano type: 'text'
        //Plata
        bonoCalidadPlata type: 'text'
        bonoCantidadPlata type: 'text'
        bonoIncentivoPlata type: 'text'
        //Wolfran
        bonoCalidadWolfran type: 'text'
        bonoCantidadWolfran type: 'text'
        bonoIncentivoWolfran type: 'text'
        //Antimonio
        bonoCalidadAntimonio type: 'text'
        bonoCantidadAntimonio type: 'text'
        bonoIncentivoAntimonio type: 'text'
        //Complejo
        bonoCalidadComplejo type: 'text'
        bonoCantidadComplejo type: 'text'
        bonoIncentivoComplejo type: 'text'
    }
}
