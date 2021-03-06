
import com.h0p1.api.SekiroWebSocketClient;
import com.h0p1.api.fastjson.JSONObject;
import com.h0p1.api.interfaze.RequestHandler;
import com.h0p1.api.interfaze.SekiroRequest;
import com.h0p1.api.interfaze.SekiroResponse;

import java.net.URI;
import java.net.URISyntaxException;


public class QuickStart {

    public static void main(String[] args) throws URISyntaxException {
        SekiroWebSocketClient webSocketClient = new SekiroWebSocketClient(new URI("ws://127.0.0.1:5612/api/register?group=aaa&clientId=java01&vkey=test"));
        webSocketClient.registerSekiroHandler("hello", new RequestHandler() {
            public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
                sekiroResponse.success("hello java!");
            }
        });
    }
}
