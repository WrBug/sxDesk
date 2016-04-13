package app.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/9.
 */
public class RasStatus implements Serializable{
    private int status;
    private int errorcode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorCode) {
        this.errorcode = errorCode;
    }
    public boolean isConnected(){
        return status==1;
    }
}
