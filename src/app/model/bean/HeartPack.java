package app.model.bean;

/**
 * Created by wangtao on 2016-03-29.
 */
public class HeartPack {
    int status;
    String packData;
    String sendIp;
    int port;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPackData() {
        return packData;
    }

    public void setPackData(String packData) {
        this.packData = packData;
    }

    public String getSendIp() {
        return sendIp;
    }

    public void setSendIp(String sendIp) {
        this.sendIp = sendIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
