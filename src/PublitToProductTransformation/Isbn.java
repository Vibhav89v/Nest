package PublitToProductTransformation;

import java.util.ArrayList;
import java.util.List;

import com.sun.jersey.api.client.ClientResponse;

import generics.AddDate;
import restClientForPublit.AbstractRestClient;
import vo.Datum;
import vo.PublitVO;

public class Isbn
{
	
		static String userid = "nextory_api_user";
		static String password = "tos559ntio8ge9ep";
		static String date = AddDate.addingDays(-1);
		static String URL = "https://api.publit.com/trade/v2.0/products?only=isbn,updated_at&updated_at=" + date
				+ "&updated_at_args=greater_equal;combinator";

		// ----------------------------------Complete Publit information----------------------------------------

		// --------------------------------Only ISBN''s----------------------------------------------------------
		// List<String> isbnList = getIsbnList(vo);
		// System.out.println("Total ISBN being fetched = "+isbnList.size());
		// System.out.println("ISBN being fetched = "+isbnList);
	

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
	private static PublitVO getIsbnFrPublit(String URL, String userId, String password) {
		ClientResponse clientReponse;
		AbstractRestClient abstractRestClient = new AbstractRestClient();
		abstractRestClient.setHTTPBasicAuthFilter(userId, password);
		clientReponse = abstractRestClient.get(URL, null, null);
		PublitVO vo = abstractRestClient.getEntity(clientReponse, PublitVO.class);
		System.out.println(vo);
		return vo;
	}
}
