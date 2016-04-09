package app.model.bean;

/**
 * Created by Administrator on 2016/4/9.
 */
public class RasStatus {
    private int status;
    private int errorCode;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    public boolean isConnected(){
        return status==1;
    }
}
