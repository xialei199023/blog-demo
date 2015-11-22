package demo.blog.es.crud;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * 使用{@link NodeClient}作为调用ES API的Client对象，使用{@link #getEsClient()}方法返回初始化完成的对象。
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7上午09:33:23
 */
public class MyNodeClient implements MyEsClient {
	
	private Client nodeClient;
	
	/**
	 * 创建并连接子网内的名为“elasticsearch”集群的节点，然后启动节点。该节点可以保存数据，并且数据可以被索引。
	 */
	public MyNodeClient() {
		
		// 启动一个本地节点，并加入子网内的ES集群
		Node node = nodeBuilder()
					.clusterName("elasticsearch") // 要加入的集群名为elasticsearch
					.data(true) // 本嵌入式节点可以保存数据
					.node(); // 构建并启动本节点
		
		// 获得一个Client对象，该对象可以对子网内的“elasticsearch”集群进行相关操作。
		nodeClient = node.client();
	}

	public Client getEsClient() {
		return nodeClient;
	}

	public void close() {
		nodeClient.close();
	}

	
}
