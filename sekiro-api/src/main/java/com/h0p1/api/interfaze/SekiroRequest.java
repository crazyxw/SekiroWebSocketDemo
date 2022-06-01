package com.h0p1.api.interfaze;

import com.h0p1.api.SekiroWebSocketClient;
import com.h0p1.api.fastjson.JSONObject;

public class SekiroRequest extends ClientContext {


    private JSONObject requestJSONObject;


    public JSONObject getRequestJSONObject() {
        return requestJSONObject;
    }


    public SekiroRequest(SekiroWebSocketClient sekiroClient, String requestString) {
        super(sekiroClient);
        this.requestJSONObject = JSONObject.parseObject(requestString);
    }

    public SekiroRequest(SekiroWebSocketClient sekiroClient, JSONObject requestJSONObject) {
        super(sekiroClient);
        this.requestJSONObject = requestJSONObject;
        // 请注意jsonMode结构解析需要放到执行的时候进行，构造函数时机在netty线程中
        // json结构解析需要放到工作线程中执行，避免netty线程阻塞
    }
}
