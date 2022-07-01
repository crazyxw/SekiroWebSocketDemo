package com.h0p1.api;

import com.h0p1.api.fastjson.JSONObject;
import com.h0p1.api.interfaze.RequestHandler;
import com.h0p1.api.interfaze.SekiroRequest;
import com.h0p1.api.interfaze.SekiroResponse;
import com.h0p1.api.utils.StringUtil;
import com.h0p1.api.utils.TextUtil;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * websocket client 客户端端控制
 */
public class SekiroWebSocketClient extends org.java_websocket.client.WebSocketClient {

    private String group;
    private String clientId;

    private boolean isRegister = false;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    private final HashMap<String, RequestHandler> handlerMap = new HashMap<String, RequestHandler>();

    public SekiroWebSocketClient(URI uri) {
        super(uri);
        Map<String, String> map = StringUtil.urlQueryToMap(uri.getQuery());
        String group = map.get("group");
        if (TextUtil.isBlank(group)){
            throw new IllegalArgumentException("group can not be empty");
        }

        String clientId = map.get("clientId");
        if (TextUtil.isBlank(clientId)){
            throw new IllegalArgumentException("clientId can not be empty");
        }
        this.clientId = clientId;
        this.group = group;

        this.connect();
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
         System.out.println("sekiro正在启动...");
    }

    @Override
    public void onMessage(String msg) {
        System.out.println("sekiro message:" + msg);
        String msgType = msg.substring(0, 1);
        String body = msg.substring(1);
        switch (msgType){
            case "0":
                // 系统消息
                handlerSystemMsg(body);
                break;
            case "1":
                // 用户消息
                handlerUserMsg(body);
                break;
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
         System.out.println("sekiro已关闭！");
         if (this.isRegister){
             return;  // 已经注册过, 就不再尝试重连
         }
        ReconnectThreadEnum.getInstance().reconnectWs(this);
    }

    public void handlerSystemMsg(String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);
        if(jsonObject.getInteger("code") == 1){
            this.isRegister = true;
        }
    }

    public void handlerUserMsg(String msg){
        String req_id = msg.substring(0, 36);
        String data = msg.substring(36);
        JSONObject jsonObject = JSONObject.parseObject(data);
        String action = jsonObject.getString("action");
        RequestHandler requestHandler = this.handlerMap.get(action);
        SekiroRequest sekiroRequest = new SekiroRequest(this, req_id, jsonObject);
        SekiroResponse sekiroResponse = new SekiroResponse(sekiroRequest);
        if (requestHandler != null){
            requestHandler.handleRequest(sekiroRequest, sekiroResponse);
        }else{
            sekiroResponse.fail("当前设备没有注册此action");
        }
    }

    @Override
    public void onError(Exception e) {
         System.out.println("sekiro连接出现错误！！"+ e.getMessage());
    }

    public void registerSekiroHandler(String action, RequestHandler requestHandler){
        this.handlerMap.put(action, requestHandler);
    }


    public enum ReconnectThreadEnum {

        WebSocketInstance(){

            @Override
            public void reconnectWs(final SekiroWebSocketClient demoWebSocketClient) {
                cachedThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //重连间隔一秒
                            Thread.sleep(2000);
                            System.out.println("sekiro 正在重新连接, 间隔2秒");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        demoWebSocketClient.reconnect();
                    }

                });
            }

        };

        private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        public abstract void reconnectWs(SekiroWebSocketClient demoWebSocketClient);

        public static ReconnectThreadEnum getInstance(){
            return WebSocketInstance;
        }
    }
}