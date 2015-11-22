package demo.blog.reentrant2sync;

/**
 * 使用synchronized时的字节码分析。
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-9-27上午11:22:54
 */
public class ByteSync {

	public void test() {
		synchronized (this) {
			
		}
	}
}
