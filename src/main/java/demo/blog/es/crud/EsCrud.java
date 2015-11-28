package demo.blog.es.crud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.min.InternalMin;

/**
 * 对ES服务端的CRUD操作。
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7上午10:33:16
 */
public class EsCrud {
	
	private Client client;
	
	/**
	 * 创建一个实例。
	 * @param client 
	 */
	public EsCrud(Client client) {
		this.client = client;
	}
	
	/***************** index ******************************/
	
	/**
	 * 创建一个索引
	 * @param indexName 索引名
	 */
	public void createIndex(String indexName) {
		try {
			CreateIndexResponse indexResponse = this.client
									.admin()
									.indices()
									.prepareCreate(indexName)
									.get();
			
			System.out.println(indexResponse.isAcknowledged()); // true表示创建成功
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 给索引增加mapping。
	 * @param index 索引名
	 * @param type mapping所对应的type
	 */
	public void addMapping(String index, String type) {
		try {
			// 使用XContentBuilder创建Mapping
			XContentBuilder builder = 
				XContentFactory.jsonBuilder()
							    .startObject()
							    	.field("properties")
							    		.startObject()
							    			.field("name")
							    				.startObject()
							    					.field("index", "not_analyzed")
							    					.field("type", "string")
							    				.endObject()
							    			.field("age")
							    				.startObject()
							    					.field("index", "not_analyzed")
							    					.field("type", "integer")
							    				.endObject()
							    		.endObject()
							    .endObject();
			System.out.println(builder.string());			
			PutMappingRequest mappingRequest = Requests.putMappingRequest(index).source(builder).type(type);
					this.client.admin().indices().putMapping(mappingRequest).actionGet();
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除索引
	 * @param index 要删除的索引名
	 */
	public void deleteIndex(String index) {
		DeleteIndexResponse deleteIndexResponse = 
			this.client
				.admin()
				.indices()
				.prepareDelete(index)
				.get();
		System.out.println(deleteIndexResponse.isAcknowledged()); // true表示成功
	}
	
	/******************** doc *************************************/
	
	/**
	 * 创建一个文档
	 * @param index index
	 * @param type type
	 */
	public void createDoc(String index, String type, String id) {
		
		try {
			// 使用XContentBuilder创建一个doc source
			XContentBuilder builder = 
				XContentFactory.jsonBuilder()
								.startObject()
								    .field("name", "zhangsan")
								    .field("age", 12)
								.endObject();
			
			IndexResponse indexResponse = this.client
											.prepareIndex()
											.setIndex(index)
											.setType(type)
											.setId(id) // 如果没有设置id，则ES会自动生成一个id
											.setSource(builder.string())
											.get();
			System.out.println(indexResponse.isCreated());
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新文档
	 * @param index
	 * @param type
	 * @param id
	 */
	public void updateDoc(String index, String type, String id) {
		try {
			XContentBuilder builder = 
				XContentFactory.jsonBuilder()
								.startObject()
								    .field("name", "lisi")
								    .field("age", 12)
								.endObject();
			
			UpdateResponse updateResponse = 
				this.client
					.prepareUpdate()
					.setIndex(index)
					.setType(type)
					.setId(id)
					.setDoc(builder.string())
					.get();
			System.out.println(updateResponse.isCreated()); // true表示成功
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除一条数据
	 * @param index
	 * @param type
	 * @param id
	 */
	public void deleteDoc(String index, String type, String id) {

		DeleteResponse deleteResponse  = this.client
				.prepareDelete()   
				.setIndex(index) 
				.setType(type)
				.setId(id)
				.get();
		System.out.println(deleteResponse.isFound()); // true表示成功
	}
	
	/**
	 * 根据查询条件删除文档。
	 */
	public void deleteByQuery(String index, String type) {
		try {
			QueryBuilder queryBuilder = QueryBuilders.termQuery("name", "zhangsan");
			DeleteByQueryResponse deleteByQueryResponse = this.client
					.prepareDeleteByQuery(index)
					.setTypes(type)
					.setQuery(queryBuilder)
					.get();
		} catch (ElasticsearchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据ID查询一条数据记录。
	 * @param id 要查询数据的ID。
	 * @return 返回查询出来的记录对象的json字符串。
	 */
	public String get(String index, String type, String id) {
		GetResponse getResponse = this.client
									.prepareGet()   // 准备进行get操作，此时还有真正地执行get操作。（与直接get的区别）
									.setIndex(index)  // 要查询的
									.setType(type)
									.setId(id)
									.get();
		return getResponse.getSourceAsString();
	}
	
	/**
	 * 使用filter方式查询数据。
	 * @param index 数据所在的索引名
	 * @param type 数据所在的type
	 * @return 
	 */
	public List<String> queryByFilter(String index, String type) {
		
		// 查询名为zhangsan的数据
		FilterBuilder filterBuilder = FilterBuilders.termFilter("name", "zhangsan");
		SearchResponse searchResponse = 
			this.client
				.prepareSearch()  
				.setIndices(index)
				.setTypes(type)
				.setPostFilter(filterBuilder)
				.get();
		
		List<String> docList = new ArrayList<String>();
		SearchHits searchHits = searchResponse.getHits();
		for (SearchHit hit : searchHits) {
			docList.add(hit.getSourceAsString());
		}
		return docList;
	}
	
	/**
	 * 使用min聚合查询某个字段上最小的值。
	 * @param index
	 * @param type
	 */
	public void min(String index, String type) {
		SearchResponse response = this.client
								.prepareSearch(index)
								.addAggregation(AggregationBuilders.min("min").field("age"))
								.get();
		
		InternalMin min = response.getAggregations().get("min");
		System.out.println(min.getValue());
	}
	
	public static void main(String[] args) {
		XContentBuilder builder = null;
		try {
			builder = XContentFactory.jsonBuilder()
			.startObject()
				.field("properties")
					.startObject()
						.field("name")
							.startObject()
								.field("index", "not_analyzed")
								.field("type", "string")
							.endObject()
					.endObject()
			.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			System.out.println(builder.string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
