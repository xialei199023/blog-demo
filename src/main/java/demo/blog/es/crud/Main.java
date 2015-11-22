/**
 * 
 */
package demo.blog.es.crud;

/**
 * @author xialei(xialei199023@163.com)
 * @version v1.0 2015-10-7ионГ11:21:03
 */
public class Main {

	public static void main(String[] args) {
		EsCrud crud = new EsCrud(new MyNodeClient());
		String student1 = crud.get("1");
		System.out.println(student1);
	}
}
