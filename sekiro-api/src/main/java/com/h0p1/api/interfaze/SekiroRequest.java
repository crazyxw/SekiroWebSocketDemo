package com.h0p1.api.interfaze;

import com.h0p1.api.SekiroWebSocketClient;
import com.h0p1.api.fastjson.JSONObject;

public class SekiroRequest extends ClientContext {

    public String getReqId() {
        return req_id;
    }

    private String req_id;

    private JSONObject requestJSONObject;


    public JSONObject getRequestJSONObject() {
        return requestJSONObject;
    }


    public SekiroRequest(SekiroWebSocketClient sekiroClient, String req_id, String requestString) {
        super(sekiroClient);
        this.requestJSONObject = JSONObject.parseObject(requestString);
        this.req_id = req_id;
    }

    public SekiroRequest(SekiroWebSocketClient sekiroClient, String req_id, JSONObject requestJSONObject) {
        super(sekiroClient);
        this.requestJSONObject = requestJSONObject;
        this.req_id = req_id;
    }
}
