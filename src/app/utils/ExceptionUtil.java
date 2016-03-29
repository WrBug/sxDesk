package app.utils;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by wangtao on 2016-03-29.
 */
public class ExceptionUtil {
    public static void putException(Exception e) {
        putException(e.getMessage());
    }

    public static void putException(String s) {
        try {
            File file = new File("exception.log");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file);
            byte[] bytes = s.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
