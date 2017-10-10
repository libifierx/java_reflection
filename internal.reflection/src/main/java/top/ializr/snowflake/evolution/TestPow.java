package top.ializr.snowflake.evolution;

public class TestPow {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		for (long i = 1; i < 1000; i++) {
//			long v = 1 << i;
//			if(v > 86400000) {
//				System.out.println(i);
//				break;
//			}
//		}
		
		System.out.println(Long.MAX_VALUE);
		
		for (long i = 1L; i < 64; i++) {
			long v = 1L << i;
			System.out.println("2^" + i +"="+ v);
		}
		/**
		2^27=134217728
		2^28=268435456
		 */
		
		System.out.println(268435456-86400000-1);
	}
	
	
}