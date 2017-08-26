package top.ializr.snowflake.evolution;

import top.ializr.util.Stringx;

public class TestTimeSeconds18 {
	//时间用18bit表达，机房5bit，主机数量5bit,序列18bit
	//24*60*60 = 86400 秒，需要17bit来表达，【65536,131072】
	//24*60*60*2 = (86400-1)*2 秒，需要18bit来表达，【131072,262144】
	public static void main(String[] args) {
		//每日0开始
		long time = 0;
		String binary = null;
		//每日100000开始
		time=0+100000;
		binary = Long.toBinaryString(time) + Stringx.leftPad("", 28, '1');
		System.out.println("time=" + Stringx.leftPad(time+"", 5, '0')+",binary=" + Stringx.leftPad(binary,40,'0'));
		System.out.println("time=" + Stringx.leftPad(time+"", 5, '0')+",result="+Long.parseLong(binary, 2));
		//每日86400-1+100000结束
		time = 86400-1+100000;
		binary = Long.toBinaryString(time) + Stringx.leftPad("", 28, '1');
		System.out.println("time=" + Stringx.leftPad(time+"", 5, '0')+",binary=" + Stringx.leftPad(binary,40,'0'));
		System.out.println("time=" + Stringx.leftPad(time+"", 5, '0')+",result="+Long.parseLong(binary, 2));
	}
	
	
}