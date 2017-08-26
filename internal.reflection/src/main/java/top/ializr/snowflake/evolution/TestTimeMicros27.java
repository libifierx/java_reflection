package top.ializr.snowflake.evolution;

import top.ializr.util.Stringx;

public class TestTimeMicros27 {
	//时间用27bit表达，机房5bit，主机数量5bit,序列15bit
	//24*60*60 = 86400 秒，需要17bit来表达，【65536,131072】
	public static void main(String[] args) {
		for (long i = 1; i < 1000; i++) {
			long v = 1 << i;
			if(v > 86400000) {
				System.out.println(i);
				break;
			}
		}
		
		//每日0开始
		long time = 0;
		String binary = Long.toBinaryString(time) + Stringx.leftPad("", 22, '0');
		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",binary=" + Stringx.leftPad(binary,40,'0'));
		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",result="+Long.parseLong(binary, 2));

		//每日86400-1结束
		time = 86400*1000-1;
		binary = Long.toBinaryString(time) + Stringx.leftPad("", 22, '0');
		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",binary=" + Stringx.leftPad(binary,40,'0'));
		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",result="+Long.parseLong(binary, 2));
		
	}
	
	
}