package demo.blog.reentrant2sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 比较ReetrantLock与synchronized的性能对比。
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-9-27上午11:12:20
 */
public class ReetrantLockVsSync {
	
	private int threadNum = 800;
	
	private CountDownLatch reentrantLockCdl = new CountDownLatch(threadNum);
	
	private CountDownLatch synchronizedCdl = new CountDownLatch(threadNum);
	
	private Lock lock = new ReentrantLock();

	public void doReetrantLockTest() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < threadNum; i++) {
			new Thread() {
				public void run() {
					lock.lock();
					try {
						doBusiness(reentrantLockCdl);
					} finally {
						lock.unlock();
					}
				};
			}.start();
		}
		
		try {
			reentrantLockCdl.await();
			long end = System.currentTimeMillis();
			System.out.println("ReetrantLock use time : "+ (end - start));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void doSynchronized() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < threadNum; i++) {
			new Thread() {
				public void run() {
					synchronized (this) {
						doBusiness(synchronizedCdl);
					}
				};
			}.start();
		}
		
		try {
			synchronizedCdl.await();
			long end = System.currentTimeMillis();
			System.out.println("synchronized use time : "+ (end - start));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void doBusiness(CountDownLatch latch) {
		
		for (long i = 0l; i < 10000000l; i++) {
			//System.out.println(Thread.currentThread().getName());
		}
		latch.countDown();
		
	} 
	
	public static void main(String[] args) {
		ReetrantLockVsSync test = new ReetrantLockVsSync();
		test.doReetrantLockTest();
		test.doSynchronized();
	}
}