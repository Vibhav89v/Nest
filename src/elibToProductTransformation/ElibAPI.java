package elibToProductTransformation;

import com.sun.jersey.api.client.ClientResponse;

public interface ElibAPI
{
	/**
	 * Check File  is present for perticular product in Elib System. 
	 * @param productId
	 * @param productIdWithFormat
	 * @return
	 */
	ClientResponse  getProductFileFrmProduct(String productId, String productIdWithFormat);
	ClientResponse  getProductsWithDate(String date);
	String isProductAvailable(String string);
	String getCompleteDataByDate(String date);
}
