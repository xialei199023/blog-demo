/**
 * 
 */
package demo.blog.es.crud;

import org.elasticsearch.client.Client;

/**
 * 自定义的ES Client接口。实现这个接口的类要实现
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7上午09:34:33
 */
public interface MyEsClient {

	/**
	 * 获取已经初始化完成的{@link Client}对象，便于进一步地API调用。
	 * @return 已经初始化完成的{@link Client}对象。
	 */
	Client getEsClient();
	
	/**
	 * 关闭连接。请务必要及时关闭，以免内存泄露。
	 */
	void close();
}
