package utils;

public class Sto16 {
    public static String bin2hex(String bin) {
        char[] digital = "0123456789ABCDEF".toCharArray();
        StringBuffer sb = new StringBuffer("");
        byte[] bs = bin.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            sb.append('%');
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(digital[bit]);
            bit = bs[i] & 0x0f;
            sb.append(digital[bit]);
        }
        String res = sb.toString();
        String a = "%58%59";
        int index = res.lastIndexOf(a);
        res = res.substring(0, index) + a;
        return res;
    }
}
