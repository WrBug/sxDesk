package app.model;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface Event {
    void action(Action action);
    void setFootView(String s);
    void appendCount(int sendTotal,int successTotal);
}
