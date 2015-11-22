/**
 * 
 */
package demo.blog.reentrant2sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock的字节码分析。
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-9-27上午11:24:12
 */
public class ByteReentrantLock {

	private Lock reentrantLock = new ReentrantLock();
	
	public void test() {
		reentrantLock.lock();
		try {
			
		} catch (Exception e) {
			
		} finally {
			reentrantLock.unlock();
		}
	}
}
