/**
 * Generic methods for the REST Client. 
 */
package se.frescano.nest.restclient;

import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.ClientResponse;

public interface RestClient {
	
	void setHTTPBasicAuthFilter(String username, String password);

	/**
	 * 
	 * @param url
	 * @param customHeaders
	 * @param queryParams
	 * @return
	 */
	ClientResponse get(String url, Map<String, Object> customHeaders, MultivaluedMap<String, String> queryParams);

	/**
	 * 
	 * @param url
	 * @param customHeaders
	 * @param queryParams
	 * @param body Object
	 * @return
	 */
	<E> ClientResponse post(String url, Map<String, Object> customHeaders, MultivaluedMap<String, String> queryParams, E body);

	/**
	 * 
	 * @param url
	 * @param customHeaders
	 * @param queryParams
	 * @param body Object
	 * @return
	 */
	<E> ClientResponse put(String url, Map<String, Object> customHeaders, MultivaluedMap<String, String> queryParams, E body);

	/**
	 * 
	 * @param url
	 * @param customHeaders
	 * @param queryParams
	 * @param body Object
	 * @return
	 */
	<E> ClientResponse delete(String url, Map<String, Object> customHeaders, MultivaluedMap<String, String> queryParams,
			E body);
	/**
	 * Convert response to required entity.
	 * @param response
	 * @param responseType 
	 * @return
	 */
	<T extends Object> T getEntity(ClientResponse response,Class<T> responseType);
}
