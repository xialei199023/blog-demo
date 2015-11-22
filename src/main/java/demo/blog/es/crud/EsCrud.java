package demo.blog.es.crud;

import org.elasticsearch.action.get.GetResponse;

/**
 * 对ES服务端的CRUD操作。
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7上午10:33:16
 */
public class EsCrud {
	
	private MyEsClient myClient;
	
	/**
	 * 创建一个实例。
	 * @param myClient 
	 */
	public EsCrud(MyEsClient myClient) {
		this.myClient = myClient;
	}

	/**
	 * 根据ID查询一条数据记录。
	 * @param id 要查询数据的ID。
	 * @return 返回查询出来的记录对象的json字符串。
	 */
	public String get(String id) {
		GetResponse getResponse = this.myClient
									.getEsClient()
									.prepareGet()   // 准备进行get操作，此时还有真正地执行get操作。（与直接get的区别）
									.setIndex("student")  // 要查询的
									.setType("student")
									.setId(id)
									.get();
		return getResponse.getSourceAsString();
	}
}
