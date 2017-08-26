package top.ializr.snowflake.demo3;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdWorkerTest {
	
    protected static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);

 
    static class IdWorkThread implements Runnable {
        private IdWorker idWorker;
 
        public IdWorkThread(Set<Long> set, IdWorker idWorker) {
//            this.set = set;
            this.idWorker = idWorker;
        }
 
        @Override
        public void run() {
            while (true) {
                long id = idWorker.nextId();
                LOG.info("{}",id);
//                if (!set.add(id)) {
//                	LOG.error("duplicate: {}",id);
//                }
            }
        }
    }
 
    public static void main(String[] args) throws IOException {
        Set<Long> set = new HashSet<Long>();
        final IdWorker idWorker1 = new IdWorker(0, 0);
        final IdWorker idWorker2 = new IdWorker(1, 0);
        Thread t1 = new Thread(new IdWorkThread(set, idWorker1));
        Thread t2 = new Thread(new IdWorkThread(set, idWorker2));
        t1.setDaemon(true);
        t2.setDaemon(true);
        t1.start();
        t2.start();
        System.in.read();
    }
}