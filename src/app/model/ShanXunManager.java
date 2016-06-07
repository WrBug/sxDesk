package app.model;

import app.model.bean.Config;
import app.model.bean.HeartPack;
import app.utils.FileUtil;
import app.utils.UDP;
import com.google.gson.Gson;

import java.net.SocketException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ShanXunManager extends Api {
    public static int sendTotal;
    public static int successTotal;
    private static HeartPack heartPack;

    public static boolean doDial() {
        return dial();
    }

//    public static String getIp() {
//        return get("/heart/GetIp", null);
//    }

    public static void sendHeart(Event event) {
        Config config = FileUtil.readConfig();
        if (!config.getSendHeartBoolean()) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", config.getSxAcount());
        while (true) {
            sendTotal++;
            FetchResult data = post("/getHeart", map);
            if (data.isSuccess()) {
                heartPack = new Gson().fromJson(data.getData(), HeartPack.class);
                String packData = heartPack.getData();
                String sendIp = heartPack.getAddress();
                int port = heartPack.getPort();
                UDP.send(sendIp, port, Base64.getDecoder().decode(packData));
            }
            event.appendCount(sendTotal, successTotal);
            try {
                Thread.sleep(3000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static FetchResult getNotice() {
        return get("/getNotice", null);
    }

    public static FetchResult getVersion() {
        return get("/getVersion", null);
    }

}
