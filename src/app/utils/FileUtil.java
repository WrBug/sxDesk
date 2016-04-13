package app.utils;

import app.model.bean.Notice;
import com.google.gson.Gson;
import app.model.bean.Config;

import java.io.*;

/**
 * Created by Administrator on 2016/3/28.
 */
public class FileUtil {
    public static Config readConfig() {
        String res = "";
        try {
            File file = new File("config.json");
            if (!file.exists()) {
                file.createNewFile();
                return new Config();
            }
            FileInputStream fin = new FileInputStream(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer);
            fin.close();
        } catch (Exception e) {
            return new Config();
        }
        if (!TextUtil.isEmpty(res)) {
            Gson gson = new Gson();
            Config config = gson.fromJson(res, Config.class);
            return config;
        }
        return new Config();
    }

    public static void writeConfig(Config config) {
        Config g = readConfig();
        if (!TextUtil.isEmpty(config.getRouterIpAddress())) {
            g.setRouterIpAddress(config.getRouterIpAddress());
        }
        if (!TextUtil.isEmpty(config.getRouterPassword())) {
            g.setRouterPassword(config.getRouterPassword());
        }
        if (!TextUtil.isEmpty(config.getRouterUsername())) {
            g.setRouterUsername(config.getRouterUsername());
        }
        if (!TextUtil.isEmpty(config.getSxAcount())) {
            g.setSxAcount(config.getSxAcount());
        }
        if (!TextUtil.isEmpty(config.getSxPassword())) {
            g.setSxPassword(config.getSxPassword());
        }
        if (config.getSendHeart() != null) {
            g.setSendHeart(config.getSendHeart());
        }
        if (config.getWifiName() != null) {
            g.setWifiName(config.getWifiName());
        }
        if (config.getWifiPswd() != null) {
            g.setWifiPswd(config.getWifiPswd());
        }
        if (config.getNotice() != null) {
            g.setNotice(config.getNotice());
        }
        try {
            Gson gson = new Gson();
            File file = new File("config.json");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file);
            byte[] bytes = gson.toJson(g).getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
        }
    }

    public static Notice readNotice() {
        return readConfig().getNotice();
    }

    public static void writeNotice(Notice notice) {
        Config config = new Config();
        config.setNotice(notice);
        writeConfig(config);
    }

    public static void inputstream2file(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
