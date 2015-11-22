/**
 * 
 */
package demo.blog.es.crud;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;

import static org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder;

/**
 * 使用{@link TransportClient}作为调用ES API的Client对象，使用{@link #getEsClient()}方法返回初始化完成的对象。
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7上午09:52:25
 */
public class MyTransportClient implements MyEsClient {

	private TransportClient transportClient;
	
	/**
	 * 创建{@link TransportClient}对象，连接地址为<code>localhost:9300</code>的ES服务端。
	 */
	public MyTransportClient() {
		
		// 进行
		Settings esSetting = settingsBuilder()
								.put("cluster.name", "elasticsearch")
								.build();
		transportClient = new TransportClient(esSetting);
		
		// 添加连接地址
		TransportAddress address = new InetSocketTransportAddress("192.168.1.110", 9300);
		transportClient.addTransportAddress(address);
		
		
	}

	public Client getEsClient() {
		return this.transportClient;
	}

	public void close() {
		this.transportClient.close();
	}
}
