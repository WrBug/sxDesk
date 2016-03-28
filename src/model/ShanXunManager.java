package model;

import app.Main;
import com.google.gson.JsonObject;
import model.bean.Config;
import net.sf.json.JSONObject;
import utils.FileUtil;
import utils.Udp;

import java.net.SocketException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ShanXunManager extends Api {
    public static boolean doDial() {
        return dial();
    }

    public static String getIp() {
        return get("/heart/GetIp", null);
    }

    public static void sendHeart(Event event, String ip) {
        Config config = FileUtil.readConfig();
        if (!config.getSendHeartBoolean()) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", config.getSxAcount());
        map.put("ip", ip);
        Runnable runnable = () -> {
            String data = post("/heart/getByRouter", map);
            JSONObject jsonObject = JSONObject.fromObject(data);
            if (jsonObject.getInt("status") == 200) {
                jsonObject = jsonObject.getJSONObject("data");
                String packData = jsonObject.getString("packData");
                String sendIp = jsonObject.getString("sendIp");
                int port = jsonObject.getInt("sendPort");
                try {
                    Udp.instance(sendIp, port, Base64.getDecoder().decode(packData)).send();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 1, 180, TimeUnit.SECONDS);
    }
}
