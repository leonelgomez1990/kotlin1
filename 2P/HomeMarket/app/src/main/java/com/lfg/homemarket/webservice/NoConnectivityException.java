package com.lfg.homemarket.webservice;

import java.io.IOException;

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        //return Resources.getSystem().getString(R.string.internet_msg_not_connection);
        return "Sin conexión a internet, chequee su wifi o datos";
    }
}
