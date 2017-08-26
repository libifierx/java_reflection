package top.ializr.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Timex {
	
	/**
     * @return 当前天过了多少毫秒
     */
    public static long currDayMillis() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
        return (System.currentTimeMillis() - cal.getTimeInMillis());
    }
    
    /**
     * @return 当前天过了多少毫秒
     */
    public static String currDay() {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	return sdf.format(cal.getTime());
    }
}
