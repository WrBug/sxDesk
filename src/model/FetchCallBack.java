package model;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface FetchCallBack<T> {
    void onSuccess(T t);

    void onError();
}
