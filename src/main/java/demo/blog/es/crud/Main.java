/**
 * 
 */
package demo.blog.es.crud;

/**
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7ÉÏÎç11:21:03
 */
public class Main {

	public static void main(String[] args) {
		EsCrud crud = new EsCrud(ClientFactory.transportClient());
		String index = "person";
		String type = "test";
		crud.createIndex(index);
		//crud.addMapping(index, type);
		//crud.deleteIndex(index);
		
		//crud.createDoc(index, type, "2");
		//crud.updateDoc(index, type, "2");
		//System.out.println(crud.get(index, type, "1"));
		//System.out.println(crud.queryByFilter(index, type));
		//crud.deleteByQuery(index, type);
		//crud.min(index, type);
	}
}
