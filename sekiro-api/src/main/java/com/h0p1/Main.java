package com.h0p1;


import com.h0p1.api.SekiroWebSocketClient;
import com.h0p1.api.fastjson.JSONObject;
import com.h0p1.api.interfaze.RequestHandler;
import com.h0p1.api.interfaze.SekiroRequest;
import com.h0p1.api.interfaze.SekiroResponse;

import java.net.URI;

public class Main {


    public static void main(String[] args) throws Exception {

        SekiroWebSocketClient webSocketClient = new SekiroWebSocketClient(new URI("ws://127.0.0.1:5612/business-demo/register?group=aaa&clientId=qqqq"));

        webSocketClient.connect();
        webSocketClient.registerSekiroHandler("hello", new RequestHandler() {
            @Override
            public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
                System.out.println("成功接收消息");
                JSONObject jsonObject = sekiroRequest.getRequestJSONObject();
                sekiroResponse.send(jsonObject);
            }
        });
        System.out.println(123);
    }
}
