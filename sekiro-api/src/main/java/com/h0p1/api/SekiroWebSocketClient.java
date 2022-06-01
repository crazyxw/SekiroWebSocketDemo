package com.h0p1.api;

import com.h0p1.api.fastjson.JSONObject;
import com.h0p1.api.interfaze.RequestHandler;
import com.h0p1.api.interfaze.SekiroRequest;
import com.h0p1.api.interfaze.SekiroResponse;
import com.h0p1.api.utils.StringUtil;
import com.h0p1.api.utils.TextUtil;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
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
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
         System.out.println("sekiro正在启动...");
    }

    @Override
    public void onMessage(String s) {
        JSONObject jsonObject = JSONObject.parseObject(s);
        String action = jsonObject.getString("action");
        RequestHandler requestHandler = this.handlerMap.get(action);
        if (requestHandler != null){
            SekiroRequest sekiroRequest = new SekiroRequest(this, jsonObject);
            SekiroResponse sekiroResponse = new SekiroResponse(sekiroRequest);
            requestHandler.handleRequest(sekiroRequest, sekiroResponse);
        }else{
            jsonObject.put("clientId", this.getClientId());
            jsonObject.put("message", "当前设备没有注册此action");
            this.send(jsonObject.toJSONString());
        }

    }

    @Override
    public void onClose(int i, String s, boolean b) {
         System.out.println("sekiro已关闭！");
        ReconnectThreadEnum.getInstance().reconnectWs(this);
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