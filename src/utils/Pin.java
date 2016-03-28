package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Pin {

	public static String getpin(byte[] userName) {
		int i, j,z = 0;
		long timedivbyfive;
		long timenow;
		byte[] RADIUS = new byte[16];
		byte[] timeByte = new byte[4];
		byte[] beforeMD5 = new byte[32];
		byte[] afterMD5 = new byte[16];
		byte[] MD501H = new byte[2];
		byte[] MD501 = new byte[3];
		byte[] timeHash = new byte[4];
		byte[] temp = new byte[32];
		byte[] PIN27 = new byte[6];
		String s="singlenet01";
		RADIUS = s.getBytes();
		timenow = System.currentTimeMillis();
		timenow=timenow/1000;
		timedivbyfive = timenow / 5;
		for (i = 0; i < 4; i++) {
			timeByte[i] = (byte) (timedivbyfive >> (8 * (3 - i)) & 0xFF);
		}
		for (i = 0; i < 4; i++) {
			beforeMD5[i] = timeByte[i];
		}
		for (i = 4; i < 16 && userName[i - 4] != '@'; i++) {
			beforeMD5[i] = userName[i - 4];
		}
		j = 0;
		while (j<RADIUS.length)
			beforeMD5[i++] = RADIUS[j++];
		if(i==26)
		{
			z=1;
		}
		for(int k=i;k<32;k++)
		{
			int a=204;
			beforeMD5[k]=(byte) a;
		}
		MessageDigest mdInst;
		try {
			mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(beforeMD5, 0, i);
			afterMD5 = mdInst.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MD501H[0] = (byte) (afterMD5[0] >> 4 & 0xF);
		MD501H[1] = (byte) (afterMD5[0] & 0xF);
		if (MD501H[0] > 9) {
			MD501[0] = (byte) (MD501H[0] + 87);
		} else {
			MD501[0] = (byte) (MD501H[0] + 48);
		}
		if (MD501H[1] > 9) {
			MD501[1] = (byte) (MD501H[1] + 87);
		} else {
			MD501[1] = (byte) (MD501H[1] + 48);
		}
		MD501[2] = (byte) 0;
		for (i = 0; i < 32; i++) {
			temp[i] = (byte) (timeByte[(31 - i) / 8] & 1);
			timeByte[(31 - i) / 8] = (byte) (timeByte[(31 - i) / 8] >> 1);
		}

		for (i = 0; i < 4; i++) {
			timeHash[i] = (byte) (temp[i] * 128 + temp[4 + i] * 64
					+ temp[8 + i] * 32 + temp[12 + i] * 16 + temp[16 + i] * 8
					+ temp[20 + i] * 4 + temp[24 + i] * 2 + temp[28 + i]);
		}

		temp[1] = (byte) ((timeHash[0] & 3) << 4);
		temp[0] = (byte) ((timeHash[0] >> 2) & 0x3F);
		temp[2] = (byte) ((timeHash[1] & 0xF) << 2);
		temp[1] = (byte) ((timeHash[1] >> 4 & 0xF) + temp[1]);
		temp[3] = (byte) (timeHash[2] & 0x3F);
		temp[2] = (byte) (((timeHash[2] >> 6) & 0x3) + temp[2]);
		temp[5] = (byte) ((timeHash[3] & 3) << 4);
		temp[4] = (byte) ((timeHash[3] >> 2) & 0x3F);

		for (i = 0; i < 6; i++) {
			PIN27[i] = (byte) (temp[i] + 0x020);
			if (PIN27[i] >= 0x40) {
				PIN27[i]++;
			}
		}
		byte[] PIN;
		if(z==1)
		{
			 PIN = new byte[29];
		}
		else PIN = new byte[30];
		PIN[0] = '\r';
		PIN[1] = '\n';
		for(int k=2;k<8;k++)
		{
			PIN[k]=PIN27[k-2];
		}

		PIN[8] = MD501[0];
		PIN[9] = MD501[1];
		for(int k=0;k<userName.length;k++)
		{
			PIN[k+10]=userName[k];
		}
		String after=new String(PIN);
		return after;
	}
}
