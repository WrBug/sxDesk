package app.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Administrator on 2016/6/6.
 */
public class FetchResult {
    private boolean isSuccess;
    private int code;
    private String data;

    private FetchResult() {
    }

    public static FetchResult instance(String data) {
        FetchResult fetchResult = new FetchResult();
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(data);
        JsonObject jsonObject = je.getAsJsonObject();
        fetchResult.code = jsonObject.get("code").getAsInt();
        if (fetchResult.code == 1) {
            fetchResult.isSuccess = true;
            fetchResult.data = jsonObject.get("data").toString();
        }
        return fetchResult;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
