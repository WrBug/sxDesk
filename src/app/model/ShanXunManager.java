package app.model;

import app.model.bean.Config;
import app.model.bean.HeartPack;
import app.utils.FileUtil;
import app.utils.Udp;
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
        while (true) {
            sendTotal++;
            String data = post("/heart/getByDeskApp", map);
            heartPack = new Gson().fromJson(data, HeartPack.class);
            if (heartPack.getStatus() == 200) {
                String packData = heartPack.getPackData();
                String sendIp = heartPack.getSendIp();
                int port = heartPack.getPort();
                try {
                    Udp.instance(sendIp, port, Base64.getDecoder().decode(packData)).send();
                } catch (SocketException e) {
                    event.setFootView("error");
                }
            }
            event.appendCount(sendTotal, successTotal);
            try {
                Thread.sleep(3000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
