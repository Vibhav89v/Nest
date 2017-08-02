package PublitToProductTransformation;

import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.client.ClientResponse;

import common.AutomationConstants;
import generics.AddDate;
import generics.Property;
import restClientForPublit.AbstractRestClient;
import valueObject.Datum;
import valueObject.PublitVO;

public class Isbn implements AutomationConstants
{
	
	static String userid = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "USERID");
	static String password = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "PASSWORD");
	static String date = AddDate.addingDays(-1);
	static String URL = "https://api.publit.com/trade/v2.0/products?only=isbn,updated_at&updated_at=" + date
				+ "&updated_at_args=greater_equal;combinator";
	static String URL1 = "https://api.publit.com/trade/v2.0/products?with=publisher,files&updated_at=&updated_at_args=greater_equal;combinator";

	/**
	 * Returns List of isbn from VO Object.
	 * 
	 * @param vo
	 * @return
	 */
	static List<String> getIsbnList(PublitVO vo)
	{
		PublitVO vo1 = getIsbnFrPublit(URL, userid, password);
		List<Datum> data = vo1.getData();
		List<String> isbnList = new ArrayList<>();
		for (Datum datum : data) {
			isbnList.add(datum.getIsbn());
		}
		System.out.println("ISBN being fetched = " + isbnList);
		return isbnList;
	}
    
	/**
	 * Fetch all the Data from the Publit.
	 * 
	 * @param URL
	 * @param userId
	 * @param password
	 * @return
	 */
	public static PublitVO getIsbnFrPublit(String URL, String userId, String password) {
		ClientResponse clientReponse;
		AbstractRestClient abstractRestClient = new AbstractRestClient();
		abstractRestClient.setHTTPBasicAuthFilter(userId, password);
		clientReponse = abstractRestClient.get(URL, null, null);
		PublitVO vo = abstractRestClient.getEntity(clientReponse, PublitVO.class);
		System.out.println(vo);
		return vo;
	}
	
	public static PublitVO getCompleteInfoPublit(String URL1, String userId, String password) {
		ClientResponse clientReponse;
		AbstractRestClient abstractRestClient = new AbstractRestClient();
		abstractRestClient.setHTTPBasicAuthFilter(userId, password);
		clientReponse = abstractRestClient.get(URL1, null, null);
		PublitVO vo = abstractRestClient.getEntity(clientReponse, PublitVO.class);
		System.out.println(vo);
		return vo;
	}
}
