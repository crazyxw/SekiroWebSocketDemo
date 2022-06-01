package com.h0p1.api.interfaze;

import com.h0p1.api.fastjson.JSONObject;


public class SekiroResponse extends ClientContext {

    private SekiroRequest sekiroRequest;

    public SekiroRequest getSekiroRequest() {
        return sekiroRequest;
    }

    public SekiroResponse(SekiroRequest sekiroRequest) {
        super(sekiroRequest.getSekiroClient());
        this.sekiroRequest = sekiroRequest;
    }

    public void send(String string) {
        getSekiroClient().send(string);
    }

    public void send(JSONObject jsonObject) {
        jsonObject.put("clientId", getSekiroClient().getClientId());
        getSekiroClient().send(jsonObject.toJSONString());
    }
}
