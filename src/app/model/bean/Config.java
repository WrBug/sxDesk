package app.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/28.
 */
public class Config implements Serializable {
    private String sxAcount;
    private String sxPassword;
    private String routerIpAddress;
    private String routerUsername;
    private String routerPassword;
    private Boolean sendHeart;
    private String wifiName;
    private String wifiPswd;
    private Notice notice;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public Boolean getSendHeart() {
        return sendHeart;
    }

    public boolean getSendHeartBoolean() {
        return sendHeart == null ? false : sendHeart;
    }

    public void setSendHeart(Boolean sendHeart) {
        this.sendHeart = sendHeart;
    }

    public String getSxAcount() {
        return sxAcount == null ? "" : sxAcount;
    }

    public void setSxAcount(String sxAcount) {
        this.sxAcount = sxAcount;
    }

    public String getSxPassword() {
        return sxPassword == null ? "" : sxPassword;
    }

    public void setSxPassword(String sxPassword) {
        this.sxPassword = sxPassword;
    }

    public String getRouterIpAddress() {
        return routerIpAddress == null ? "" : routerIpAddress;
    }

    public void setRouterIpAddress(String routerIpAddress) {
        this.routerIpAddress = routerIpAddress;
    }

    public String getRouterUsername() {
        return routerUsername == null ? "" : routerUsername;
    }

    public void setRouterUsername(String routerUsername) {
        this.routerUsername = routerUsername;
    }

    public String getRouterPassword() {
        return routerPassword == null ? "" : routerPassword;
    }

    public void setRouterPassword(String routerPassword) {
        this.routerPassword = routerPassword;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public String getWifiPswd() {
        return wifiPswd;
    }

    public void setWifiPswd(String wifiPswd) {
        this.wifiPswd = wifiPswd;
    }
}
