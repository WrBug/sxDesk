package app.model;

import app.model.bean.Config;
import app.utils.Constant;
import app.utils.FileUtil;
import app.utils.Pin;
import app.utils.Sto16;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/28.
 */
public class Api {
    protected static String API_URL = "http://localhost:8081";

    protected static FetchResult post(String u, Map<String, Object> params) {
        try {
            URL url = new URL(API_URL + u);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("appName", "deskApp");
            connection.setRequestProperty("version", Constant.APP_VERSION);
            OutputStream outputStream = connection
                    .getOutputStream();
            outputStream.write(getParams(params).getBytes());
            connection.connect();
            if (connection.getResponseCode() == 200) {
                StringBuffer buffer = new StringBuffer();
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                int a;
                while ((a = reader.read()) != -1) {
                    buffer.append((char) a);
                }
                return FetchResult.instance(buffer.toString());
            }
            return null;
        } catch (Exception e) {
            return null;

        }
    }

    protected static FetchResult get(String u, Map<String, Object> params) {
        try {
            URL url = new URL(API_URL + u + "?" + getParams(params));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("appName", "deskApp");
            connection.setRequestProperty("version", Constant.APP_VERSION);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                StringBuffer buffer = new StringBuffer();
                int a;
                while ((a = reader.read()) != -1) {
                    buffer.append((char) a);
                }
                return FetchResult.instance(buffer.toString());
            }
            return null;
        } catch (Exception e) {
            return null;

        }
    }

    protected static boolean dial() {
        Config config = FileUtil.readConfig();
        try {
            String acount = getFinalUser(config.getSxAcount());
            String getRequest = "wan=0" + "&wantype=2" + "&acc=" + acount + "&psw="
                    + config.getSxPassword() + "&confirm=" + config.getSxPassword() + "&specialDial=0"
                    + "&SecType=0" + "&sta_ip=0.0.0.0" + "&sta_mask=0.0.0.0"
                    + "&linktype=4" + "&waittime2=0" + "&Connect=%C1%AC+%BD%D3";
            URL url = new URL("http://" + config.getRouterIpAddress() + "/userRpm/PPPoECfgRpm.htm?" + getRequest);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String ro = config.getRouterUsername() + ":" + config.getRouterPassword();
            String auth = "Basic%20" + new String(Base64.getEncoder().encodeToString((ro.getBytes("GB18030"))));
            connection.addRequestProperty("Authorization", auth);
            connection.addRequestProperty("Cookie", "Authorization=" + auth + "; ChgPwdSubTag=");
            connection.addRequestProperty("Host", config.getRouterIpAddress());
            connection.addRequestProperty("Referer", "http://" + config.getRouterIpAddress() + "/userRpm/PPPoECfgRpm.htm");
            connection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
            connection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.addRequestProperty("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            connection.addRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;

        }

    }

    protected static String getFinalUser(String sxAcount) {
        String after = Pin.getpin(sxAcount.getBytes());
        String yhm = Sto16.bin2hex(after);
        return yhm;
    }

    private static String getParams(Map<String, Object> params) {
        if (params == null) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            buffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }
}
