package com.h0p1.api.interfaze;


import com.h0p1.api.SekiroWebSocketClient;

public class ClientContext{
    private final SekiroWebSocketClient sekiroClient;

    public ClientContext(SekiroWebSocketClient parent) {
        sekiroClient = parent;
    }

    public ClientContext(ClientContext parent) {
        sekiroClient = parent.sekiroClient;
    }

    public SekiroWebSocketClient getSekiroClient() {
        return sekiroClient;
    }

}
