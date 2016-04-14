package app.model.bean;

import java.io.Serializable;

/**
 * Created by wangtao on 2016-04-14.
 */
public class Version implements Serializable{
    private String version;
    private String url;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
