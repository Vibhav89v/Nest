package publitBooksProcessed;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.jersey.api.client.ClientResponse;

import Mongo.ProductCollection.Product;
import Mongo.PublitDistributor.Publit;
import common.AutomationConstants;
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBMorphia;
import generics.Property;
import restClientForPublit.AbstractRestClient;
import valueObject.Datum;
import valueObject.PublitVO;

public class NestStatusCurrentDay extends SuperTestScript implements AutomationConstants
{
	static String userid = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "USERID");
	static String password = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "PASSWORD");
	static String date = AddDate.addingDays(-1);
	static String URL = "https://api.publit.com/trade/v2.0/products?only=isbn,updated_at&updated_at=" + date+ "&updated_at_args=greater_equal;combinator";
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	
	ClientResponse clientReponse;
	AbstractRestClient abstractRestClient = new AbstractRestClient();
	
	 Publit publit=new Publit();
	 Product product=new Product();
	 int countActive=0;
	 int countParked=0;
	 int countUpcoming=0;
	 int countA_Inactive=0;
	 int countA_Omitted=0;
	 int countL_Inactive=0;
	 int countP_Inactive=0;
	 int countP_Deferred=0;
	 int countHighPrice=0;
	 int countError=0;
	 public Logger log;
	 
	 public NestStatusCurrentDay()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test
	 public void statusCurrentDayNest() throws InterruptedException, SQLException
	 {
	  log.info("--------------In PRODUCT Collection checking 'STATUS' of the Current day--------------------");
	  
	  log.info("Fetching all the ISBN's from the url");
	  
	    abstractRestClient.setHTTPBasicAuthFilter(userid, password);
	    clientReponse = abstractRestClient.get(URL, null, null);
		PublitVO vo = abstractRestClient.getEntity(clientReponse, PublitVO.class);
		//=============================log.info("Complete Information : "+vo);===================================
		
		List<Datum> data = vo.getData();
		List<String> isbnList = new ArrayList<>();
		for (Datum datum : data)
		{
			isbnList.add(datum.getIsbn());
		}
		//==============================================ISBN List========================================================
		log.info("ISBN being fetched for " + date+ ": " + isbnList);
		log.info("ISBN List Size : "+isbnList.size());
		//--------------------Fetching the Isbn from ISBN List--------------------------------------------------------- 
		for(int i=0;i<isbnList.size();i++)
		{
			String result=isbnList.get(i);
			log.info("ISBN "+(i+1)+" = "+isbnList.get(i));
			
			 DBCollection query = ds1.getDB().getCollection("product");           
	         DBCursor	prodCursor = query.find(new BasicDBObject("isbn", result));
	         
	         while( prodCursor.hasNext() )
	         {
	        	 DBObject mObj = prodCursor.next();

	        	 String s =  (String) mObj.get("isbn");
	        	 try
	        	 {
	        	   product.setProvider_productid( (Integer.parseInt(s)));
	        	 }
	        	 catch(Exception e)
	        	 {
	        	   log.info(" ");
	        	 }
	        	 DBObject mObj1 = (DBObject) mObj.get("publisher");
	           
	             if(((String) mObj.get("productstatus")).equalsIgnoreCase("Active") && mObj1.get("distributorname") !=null)
	             {
	        	   log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
	        	   log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
	        	   log.info("Count with status 'ACTIVE' : "+ ++countActive);
	        	   log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("Parked") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'PARKED' : "+ ++countParked);
		        	 log.info("");
	             } 
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("Upcoming") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'UPCOMING' : "+ ++countUpcoming);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("A_Inactive") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'A_Inactive' : "+ ++countA_Inactive);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("A_Omitted") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'A_Omitted' : "+ ++countA_Omitted);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("L_Inactive") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'L_Inactive' : "+ ++countL_Inactive);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("P_Inactive") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'P_Inactive' : "+ ++countP_Inactive);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("P_Deferred") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'P_Deferred' : "+ ++countP_Deferred);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("HighPrice") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'HighPrice' : "+ ++countHighPrice);
		        	 log.info("");
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("Error") && mObj1.get("distributorname") !=null)
	             {
	            	 log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 log.info("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 log.info("Count with status 'Error' : "+ ++countError);
		        	 log.info("");
	             }
	             else
	            	 log.info("Invalid Status");
	         }   
		  }
	 }
}
