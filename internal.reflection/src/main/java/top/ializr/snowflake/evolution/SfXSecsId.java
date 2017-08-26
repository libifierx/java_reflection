package top.ializr.snowflake.evolution;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.ializr.util.Timex;

public class SfXSecsId {
	
	static Logger log = LoggerFactory.getLogger(SfXSecsId.class);
    
    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private final long sequenceBits = 15L;

    /** 机器ID向左移15位 */
    private final long workerIdShift = sequenceBits;

    /** 数据标识id向左移20位(15+5) */
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间戳向左移25位(5+5+15) */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为32767 (0b111111111111=0xfff=32767) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;

    /** 秒内序列(0~32767) */
    private long sequence = 0L;

    /** 上次生成ID的时间戳 */
    private long lastDaySeconds = -1L;

    //==============================Constructors=====================================
    /**
     * 构造函数
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SfXSecsId(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long daySeconds = daySeconds();
        
        long compareSecs = daySeconds ;
        if(daySeconds == 0) {
        	compareSecs = 24*60*60; 
        }

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (compareSecs < lastDaySeconds) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastDaySeconds - daySeconds));
        }

        //如果是同一时间生成的，则进行秒内序列
        if (lastDaySeconds == compareSecs) {
            sequence = (sequence + 1) & sequenceMask; 
            //秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个秒,获得新的时间
                daySeconds = tilNextSecs(lastDaySeconds);
            }
        }
        //时间戳改变，秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间戳
        lastDaySeconds = daySeconds;

        //移位并通过或运算拼到一起组成64位的ID
        return ((daySeconds+100000) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个秒，直到获得新的秒
     * @param lastSecs 上次生成ID的秒
     * @return 当前秒
     */
    protected long tilNextSecs(long lastSecs) {
    	log.info("wait....");
        long currSecs = daySeconds();
        if(currSecs == 0) {
        	currSecs = 0;
        }
        while (currSecs <= lastSecs) {
        	try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
			}
            currSecs = daySeconds();
        }
        return currSecs;
    }
    
    protected long daySeconds() {
        return Timex.currDayMillis()/1000;
    }

    //==============================Test=============================================
    /** 测试 
     * @throws IOException */
    public static void main(String[] args) throws IOException {
    	final SfXSecsId idWorker = new SfXSecsId(1, 1);
        Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
		        	  long id = idWorker.nextId();
		              log.info(Timex.currDay() +id);
		        }
			}
		});
        t1.start();
        Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
		        	  long id = idWorker.nextId();
		              log.info(Timex.currDay() +id);
		        }
			}
		});
        t2.start();
        System.in.read();
    }
}