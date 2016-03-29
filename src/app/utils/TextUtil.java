package app.utils;

/**
 * Created by Administrator on 2016/3/28.
 */
public class TextUtil {
    public static boolean isEmpty(String... s) {
        if(s==null||s.length==0){
            return true;
        }
        for (String a : s) {
            if (a == null || a.length() == 0) {
                return true;
            }
        }
        return false;
    }
}
