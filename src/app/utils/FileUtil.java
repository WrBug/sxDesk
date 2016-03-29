package app.utils;

import com.google.gson.Gson;
import app.model.bean.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
        ExceptionUtil.putException(String.valueOf(!TextUtil.isEmpty(res)));
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
            ExceptionUtil.putException(e);
        }
    }
}
