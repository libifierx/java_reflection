package top.ializr.util;

public class Stringx {
	
	public static String leftPad(String ori,int expectedLen,char pad) {
			int packLen = expectedLen - ori.length();
			if(packLen > 0) {
				StringBuilder sb = new StringBuilder();
				while(packLen > 0) {
					sb.append(pad);
					packLen--;
				}
				sb.append(ori);
				return sb.toString();
			}
			return ori;
		}
}

