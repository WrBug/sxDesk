package app.model;

import app.model.bean.Config;
import app.model.bean.RasStatus;
import app.utils.CMD;
import app.utils.FileUtil;
import app.utils.Pin;
import com.google.gson.Gson;
import res.Res;

import java.io.File;
import java.io.InputStream;
import java.util.Base64;

/**
 * Created by wangtao on 2016-03-31.
 */
public class RasDial extends Api {
    public static boolean connect(Event event) {
        Config config = FileUtil.readConfig();
        String sxAcount = config.getSxAcount();
        String password = config.getSxPassword();
        try {
            InputStream is = Res.class.getResourceAsStream("dial.exe");
            File file = new File("dial.exe");
            FileUtil.inputstream2file(is, file);
            sxAcount = Pin.getpin(sxAcount.getBytes());
            sxAcount = sxAcount.substring(0, sxAcount.lastIndexOf(".XY") + 3);
            String adslCmd = "\"" + file.getAbsolutePath() + "\" " + Base64.getEncoder().encodeToString(sxAcount.getBytes()) + " " + password;
            String tempCmd = CMD.execute(adslCmd);
            System.out.println(tempCmd);
            tempCmd = tempCmd.substring(0, tempCmd.indexOf("}") + 1);
            Gson gson = new Gson();
            RasStatus status = gson.fromJson(tempCmd, RasStatus.class);
            file.delete();
            if (status.isConnected()) {
                event.setFootView("已成功建立连接.");
                return true;
            } else {
                event.setFootView("拨号失败,代码"+status.getErrorcode());
            }
        } catch (Exception e) {
            event.setFootView("拨号失败");
        }
        return false;
    }
}
