package app.model;

import app.model.bean.Config;
import app.utils.CMD;
import app.utils.FileUtil;
import res.Res;

import java.io.File;
import java.io.InputStream;

/**
 * Created by wangtao on 2016-03-31.
 */
public class PPPOEManager extends Api {
    public static boolean connect(Event event) {
        Config config = FileUtil.readConfig();
        String sxAcount = config.getSxAcount();
        String password = config.getSxPassword();
        try {
            InputStream is = Res.class.getResourceAsStream("pppoe.pbk");
            File file = new File("pppoe");
            FileUtil.inputstream2file(is, file);
            sxAcount = getFinalUser(sxAcount);
            String adslCmd = "rasdial  \"闪讯拨号\" " + sxAcount + " "
                    + password + "  /phonebook:" + file.getPath();
            String tempCmd = CMD.execute(adslCmd);
            if (tempCmd.indexOf("已连接") > 0) {
                event.setFootView("已成功建立连接.");
                return true;
            } else {
                String[] strings = tempCmd.substring(tempCmd.indexOf("\n\n") + "\n\n".length()).split(" ");
                event.setFootView(strings[0] + " 代码：" + strings[1]);
            }
        } catch (Exception e) {
            event.setFootView("拨号失败");
        }
        return false;
    }
}
