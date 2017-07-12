/**
 * 
 */
package se.frescano.nest.restclient;

import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

@Service("abstractRestClient")
public class AbstractRestClient implements RestClient {

	private static final Logger logger = LoggerFactory.getLogger(AbstractRestClient.class);
	private Client client = null;
	private Gson gson = new Gson();
	private String mediaType;

	private void initializeClient() {
		if (logger.isTraceEnabled()) {
			logger.trace("[AbstractRestClient] [ initializeClient ] [ start ] ");
		}
		client = Client.create();
		if (logger.isTraceEnabled()) {
			logger.trace("[AbstractRestClient] [ initializeClient ] [ end ] ");
		}
	}

	/**
	 * Default media type will be MediaType.APPLICATION_JSON.
	 */
	public AbstractRestClient() {
		this.mediaType = MediaType.APPLICATION_JSON;
		getClient();
	}

	/**
	 * Set Media type or Default is MediaType.APPLICATION_JSON.
	 * 
	 * @param mediaType
	 */
	public void setMediaType(String mediaType) {
		if (mediaType == null) {
			this.mediaType = MediaType.APPLICATION_JSON;
		}
		this.mediaType = mediaType;
		getClient();
	}

	/**
	 * Adding BASIC Auth in restClient.
	 * 
	 * @param username
	 * @param password
	 */
	public void setHTTPBasicAuthFilter(String username, String password) {
		if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
			client.addFilter(new HTTPBasicAuthFilter(username, password));
		}
	}

	/**
	 * Initialize client.
	 * @return
	 */
	private Client getClient() {
		if (client == null)
			initializeClient();
		return this.client;
	}
	
	/**
	 * 
	 */
	@Override
	public ClientResponse get(String url, Map<String, Object> customHeaders, MultivaluedMap<String, String> queryParams) {
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ get ] [ start ] ");
		}
		getClient(); 
		WebResource webResource;
		ClientResponse response;
		// setting query params
		if (null != queryParams) {
			webResource = client.resource(url).queryParams(queryParams);
		} else {
			webResource = client.resource(url);
		}
		// setting headers
		if (null != customHeaders) {
			for (String header : customHeaders.keySet()) {
				webResource.header(header, customHeaders.get(header));
			}
		}
		// setting request body
		response = webResource.accept(mediaType).get(ClientResponse.class);
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ get ] [ end ] ");
		}
		return response;
	}

	/**
	 * 
	 */
	@Override
	public <E> ClientResponse post(String url, Map<String, Object> customHeaders, MultivaluedMap<String, String> queryParams, E body) {
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ post ] [ start ] ");
		}
		getClient();
		String jsonInString = null;
		WebResource webResource;
		ClientResponse response;
		// setting query params
		if (null != queryParams) {
			webResource = client.resource(url).queryParams(queryParams);
		} else {
			webResource = client.resource(url);
		}
		// setting headers
		if (null != customHeaders) {
			for (String header : customHeaders.keySet()) {
				webResource.header(header, customHeaders.get(header));
			}
		}
		// setting request body
		if (null != body) {
			jsonInString = gson.toJson(body);
			response = webResource.type(mediaType).entity(jsonInString).post(ClientResponse.class);
		} else
			response = webResource.type(mediaType).post(ClientResponse.class);
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ post ] [ end ] ");
		}
		return response;

	}

	/**
	 * 
	 */
	@Override
	public <E> ClientResponse put(String url, Map<String, Object> customHeaders, MultivaluedMap<String, String> queryParams, E body) {
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ put ] [ start ] ");
		}
		getClient();
		WebResource webResource;
		ClientResponse response;
		// setting query params
		if (null != queryParams) {
			webResource = client.resource(url).queryParams(queryParams);
		} else {
			webResource = client.resource(url);
		}
		// setting headers
		if (null != customHeaders) {
			for (String header : customHeaders.keySet()) {
				webResource.header(header, customHeaders.get(header));
			}
		}
		// setting request body
		if (null != body) {
			String jsonInString = gson.toJson(body);
			response = webResource.accept(mediaType).entity(jsonInString).put(ClientResponse.class);
		} else
			response = webResource.accept(mediaType).put(ClientResponse.class);
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ put ] [ end ] ");
		}
		return response;
	}

	/**
	 * 
	 */
	@Override
	public <E> ClientResponse delete(String url, Map<String, Object> customHeaders, MultivaluedMap<String, String> queryParams, E body) {
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ delete ] [ start ] ");
		}
		getClient();
		WebResource webResource;
		ClientResponse response;
		// setting query params
		if (null != queryParams) {
			webResource = client.resource(url).queryParams(queryParams);
		} else {
			webResource = client.resource(url);
		}
		// setting headers
		if (null != customHeaders) {
			for (String header : customHeaders.keySet()) {
				webResource.header(header, customHeaders.get(header));
			}
		}
		// setting request body
		if (null != body) {
			String jsonInString = gson.toJson(body);
			response = webResource.accept(mediaType).entity(jsonInString).delete(ClientResponse.class);
		} else
			response = webResource.accept(mediaType).delete(ClientResponse.class);
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ delete ] [ end ] ");
		}
		return response;
	}

	/**
	 * 
	 */
	public <T extends Object> T getEntity(ClientResponse response, Class<T> responseEntity) {
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ getEntity ] [ start ] ");
		}
		String str = response.getEntity(String.class);
		T payload = (T) gson.fromJson(str, responseEntity);
		if (logger.isTraceEnabled()) {
			logger.trace( ": [AbstractRestClient] [ getEntity ] [ end ] ");
		}
		return payload;
	}

}
