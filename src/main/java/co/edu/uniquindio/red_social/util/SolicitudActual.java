package co.edu.uniquindio.red_social.util;

import co.edu.uniquindio.red_social.clases.social.SolicitudAyuda;

public class SolicitudActual {
    static SolicitudActual instance;
     SolicitudAyuda solicitudAyuda;

    public static SolicitudActual getInstance(){
        if(instance == null){
            instance = new SolicitudActual();
        }
        return instance;
    }

    public static void setInstance(SolicitudActual instance) {
        SolicitudActual.instance = instance;
    }

    public  SolicitudAyuda getSolicitudAyuda() {
        return solicitudAyuda;
    }

    public  void setSolicitudAyuda(SolicitudAyuda solicitudAyuda) {
        this.solicitudAyuda = solicitudAyuda;
    }
}
