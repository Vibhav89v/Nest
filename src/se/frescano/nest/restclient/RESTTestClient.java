/**
 * 
 */
package se.frescano.nest.restclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;

import valueObject.PublitVO;

public class RESTTestClient {
	
	private static Logger logger=LoggerFactory.getLogger(RESTTestClient.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientResponse clientReponse;
		AbstractRestClient abstractRestClient =new AbstractRestClient();
		String userid="nextory_api_user";
		String password="tos559ntio8ge9ep";
		abstractRestClient.setHTTPBasicAuthFilter(userid,password);
		clientReponse = abstractRestClient.get("https://api.publit.com/trade/v2.0/products?only=isbn,updated_at&updated_at=2017-06-30&updated_at_args=greater_equal;combinator",null,null);
		PublitVO vo = abstractRestClient.getEntity(clientReponse,PublitVO.class);
		System.out.println(vo);
		if(logger.isDebugEnabled())
			logger.debug(clientReponse.toString());
	}

}
