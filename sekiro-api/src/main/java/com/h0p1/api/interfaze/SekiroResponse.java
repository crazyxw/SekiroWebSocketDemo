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
        getSekiroClient().send(sekiroRequest.getReqId()+string);
    }

    public void send(JSONObject jsonObject) {
        this.send(jsonObject.toJSONString());
    }

    public void success(String string){
        JSONObject res = new JSONObject();
        res.put("clientId", getSekiroClient().getClientId());
        res.put("data", string);
        res.put("status", 0);
        this.send(res);
    }

    public void fail(String string){
        JSONObject res = new JSONObject();
        res.put("clientId", getSekiroClient().getClientId());
        res.put("data", string);
        res.put("status", 1);
        this.send(res);
    }
}
