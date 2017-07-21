package elibToProductTransformation;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.commons.lang.ArrayUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;

import restClientForPublit.AbstractRestClient;
import restClientForPublit.RestClient;

@Service("elibAPIImpl")
public class ElibAPIImpl implements ElibAPI{
	
	@Autowired
	RestClient abstractRestClient;
	
	
	private String serviceId;
	private String serviceKey;

	private String baseUrl;
	private static boolean  inStandaloneApp = false;
	static Logger logger=LoggerFactory.getLogger(ElibAPI.class);
	public String str;
	public static void main(String[] args) throws ParseException
	{
		inStandaloneApp = true;
		ElibAPI elibApi = new ElibAPIImpl();
		elibApi.getCompleteDataByDate("2017-06-22T13:00");
		//elibApi.getProductsWithDate("2017-02-23T13:00");
		//elibApi.isProductAvailable("1048919");
		//elibApi.getProductFileFrmProduct("1048919","1048919.pdf");
	}
		
	private void getProperties(){
		serviceId="2238";
		serviceKey = "ARUYiFCtxHNSvTI6ngXy1hBkQ7LJjVK0ZqrWPsz2wGMedo49mb";
		baseUrl="https://xdapi.elib.se/v1.0/products";
		abstractRestClient =new AbstractRestClient();
	}
	
	private static String ConvertStringToISODateString (String value){
		DateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			java.util.Date date = input.parse(value==null?getCurrentDate():value);
			return output.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static String getCurrentDate(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	private static Timestamp convertStringToTimeStamp(String value)  {
		if(org.apache.commons.lang.StringUtils.isBlank(value))
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Timestamp sqlDate = null;
		java.util.Date d = null;
		try{
			d = df.parse(value);
			sqlDate = new java.sql.Timestamp(d.getTime());
			
		}catch (Exception e) {
			
			String str1 = org.apache.commons.lang.StringUtils.substringAfter(value, "T");
			if(org.apache.commons.lang.StringUtils.isBlank(str1))
				value = value+"T00:00:00";
			else {
				int count = org.apache.commons.lang.StringUtils.countMatches(value, ":");
				if(count == 1)
					value =value+":00";
				if(count == 0)
					value = value+":00:00";
			}
			try {
				d =  df.parse(value);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			sqlDate = new java.sql.Timestamp(d.getTime());
		}
		return sqlDate;
	}
	
	private static int[] convert2int(Integer[] array) {
		return ArrayUtils.toPrimitive(array);
	}
	
	private static Document getJsonString(Object inputObj){
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = "";
		Document jsonDoc = null;
		try {
			jsonInString = mapper.writeValueAsString(inputObj);
			jsonDoc = Document.parse(jsonInString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonDoc;
	}
	private static java.sql.Date convertToSQLDate(Date utilDate) {
		  if(utilDate!=null)
			  return new java.sql.Date(utilDate.getTime());	
			return null;
		}
	
	private static Calendar ConvertISOStringToCalendar (String value){
		//Calendar cal = javax.xml.bind.DatatypeConverter.parseDateTime(value);
		if(org.apache.commons.lang.StringUtils.isBlank(value)) return null;
		
		Timestamp timeStamp = convertStringToTimeStamp(value);
		Calendar cal  = Calendar.getInstance();
		cal.setTimeInMillis(timeStamp.getTime());
		return cal;
	}
	
	private static long getJsonFormatDate(String value){
		DateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			java.util.Date date = input.parse(value==null?getCurrentDate():value);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private static Date convertDateTime(String date) {
		DateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date == null) {
			return new Date();
		}
		Date convertedCurrentDate = null;
		try {
			convertedCurrentDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertedCurrentDate;
	}
	private static Date convertDate(String date) {
		DateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedCurrentDate = null;
		try {
			convertedCurrentDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertedCurrentDate;
	}
	
	private static String ConvertProductStatusStringToISODateString (String value){
		if (value !=null){
			DateFormat input = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
			DateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			try {
				java.util.Date date = input.parse(value);
				//System.out.println(output.format(date));
				return output.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return ConvertProductStatusStringToISODateStringNew(value);
			}
		}
		return null;
	}
	
	private static String ConvertProductStatusStringToISODateStringNew (String value){
		if (value !=null){
			DateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			DateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			try {
				java.util.Date date = input.parse(value);
				//System.out.println(output.format(date));
				return output.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	private static Date convertStringToISODate(String date) {
		if (date == null) {
			return new Date();
		}
		DateFormat  input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat output = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedCurrentDate = null;
		try {
			java.util.Date datenew = input.parse(date);
			//System.out.println(output.format(date));
			String newDateStr = output.format(datenew);
			convertedCurrentDate = output.parse(newDateStr);
			/*cal = Calendar.getInstance();
			java.util.Date date1 = sdf.parse(date);
			output.format(date1);
			cal.setTime(output.parse(date1));*/
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return convertedCurrentDate;
		/*Date convertedCurrentDate = new DateTime(date).toDate();
		return convertedCurrentDate;*/
	}
	

	private static String convertExceptionToString (Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
	
	private static String getOtherPartURL(String serviceId, String serviceKey,
			String... params) {
		String urlStr = "?"
				+ getBaseRequestParams(serviceId, serviceKey, params) + "&"
				+ getCheckSumParam(serviceId, serviceKey, params);
		return urlStr;
	}
	
	private static String getBaseRequestParams(String serviceId, String serviceKey,
			String... params) {
		String paramsStr = "";
		if (params != null && params.length > 0) {

			String[] arrParams = params;

			if (arrParams.length == 1) {
				paramsStr = "&ProductID=" + arrParams[0];
			} else if (arrParams.length == 2) {
				paramsStr = "&ProductID=" + arrParams[0] + "&Filename="
						+ arrParams[1];
			} else if (arrParams.length == 3) {
				paramsStr = "&ProductID=" + arrParams[0] + "&Filename="
						+ arrParams[1] + "&Timestamp=" + arrParams[2];
			}
		}

		return "ServiceID=" + serviceId + "&ServiceKey=" + serviceKey
				+ paramsStr;
	}
	
	private static String getCheckSumParam(String serviceId, String serviceKey,
			String... params) {
		String toHash = serviceId;
		if (params != null && params.length > 0) {
			String[] arrParams = params;
			for (String strParam : arrParams) {
				toHash = toHash + strParam;
			}
		}
		toHash = toHash + serviceKey;
		MessageDigest md = null;
		byte[] hash = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			hash = md.digest(toHash.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "Checksum=" + convertToHex(hash);
	}
	private static String convertToHex(byte[] raw) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < raw.length; i++) {
			sb.append(Integer.toString((raw[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString();
	}
	//https://xdapi.elib.se/v1.0/products?ServiceID=2238&From=2017-06-22T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6) 
	
		public String getCompleteDataByDate(String date)
		{
		getProperties();
		String url = baseUrl+getOtherPartURL(serviceId, serviceKey, "")+"&format=json&From="+date;
		System.out.println(url);
		ClientResponse clientReponse = abstractRestClient.get(url,null,null);
		str = clientReponse.getEntity(String.class);
		//System.out.println(str);
		
		return str;
	    }
		public String isProductAvailable(String productId) {
		getProperties();
		String url = baseUrl+ "/"+productId+"/files"+getOtherPartURL(serviceId, serviceKey, "" + productId)+"&format=json";
		System.out.println(url);
		ClientResponse clientReponse = abstractRestClient.get(url,null,null);
		String str = clientReponse.getEntity(String.class);
		System.out.println(str);
		return str;
	}
	@Override
	public ClientResponse getProductFileFrmProduct(String productId, String productIdWithFormat) {
		getProperties();
		String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm";
		SimpleDateFormat dateFormatUTC=new SimpleDateFormat(DATE_FORMAT);
		dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
		String timStamp=dateFormatUTC.format(new Date());
		String url =baseUrl+ "/"+productId+"/files/"+productIdWithFormat+getOtherPartURL(serviceId, serviceKey, "" +productId, productIdWithFormat,timStamp);
		System.out.println(url);
		ClientResponse clientReponse = abstractRestClient.get(url,null,null);
		return clientReponse;
	}
	private static String replacefirstString(String token, int beginIndex, String replace) {		  
		return replace+token.substring(beginIndex).toString();
		}

	@Override
	public ClientResponse getProductsWithDate(String date) {
		getProperties();
		String url = baseUrl+"?"+"ServiceID=" + serviceId+"&From="+date+"&"+getCheckSumParam(serviceId, serviceKey)+"&format=json";
		System.out.println(url);
		ClientResponse clientReponse = abstractRestClient.get(url,null,null);
		return clientReponse;
	}
	
}
