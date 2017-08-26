package top.ializr.snowflake.evolution;

import top.ializr.util.Stringx;

public class TestTimeMicros28 {
	//时间用28bit表达，机房5bit，主机数量5bit,序列12bit
	public static void main(String[] args) {
		
		//每日100000000开始
//		long time = 0+100000000;
//		String binary = Long.toBinaryString(time) + Stringx.leftPad("", 22, '1');
//		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",binary=" + Stringx.leftPad(binary,40,'0'));
//		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",result="+Long.parseLong(binary, 2));
//
//		//每日86400000-1结束
//		time = 86400*1000-1+100000000;
//		binary = Long.toBinaryString(time) + Stringx.leftPad("", 22, '1');
//		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",binary=" + Stringx.leftPad(binary,40,'0'));
//		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",result="+Long.parseLong(binary, 2));
//		
		
		long time = 0;
		String binary = null;
		//每日100000000开始
		time = 0+ (1<<28);
		binary = Long.toBinaryString(time) + Stringx.leftPad("", 22, '1');
		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",binary=" + Stringx.leftPad(binary,40,'0'));
		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",result="+Long.parseLong(binary, 2));

		//每日86400000-1结束
		time = 86400*1000-1+ (1<<28);
		binary = Long.toBinaryString(time) + Stringx.leftPad("", 22, '1');
		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",binary=" + Stringx.leftPad(binary,40,'0'));
		System.out.println("time=" + Stringx.leftPad(time+"", 8, '0')+",result="+Long.parseLong(binary, 2));
		
	}
	
	
}