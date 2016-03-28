package utils;

import com.google.gson.Gson;
import model.bean.Config;

import java.io.*;
import java.nio.CharBuffer;

/**
 * Created by Administrator on 2016/3/28.
 */
public class FileUtil {
    public static Config readConfig() {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream("/config.json");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer);
            fin.close();
        } catch (Exception e) {
            return new Config();
        }
        Gson gson = new Gson();
        if (!TextUtil.isEmpty(res)) {
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
            FileOutputStream fout = new FileOutputStream("/config.json");
            byte[] bytes = gson.toJson(g).getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
