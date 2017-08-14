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

public class BooksMissedInNestPublit extends SuperTestScript implements AutomationConstants
{

	static String userid = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "USERID");
	static String password = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "PASSWORD");
	static String date = AddDate.addingDays(-21);
	static String URL = "https://api.publit.com/trade/v2.0/products?only=isbn,updated_at&updated_at=" + date+ "&updated_at_args=greater_equal;combinator";
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	
	ClientResponse clientReponse;
	AbstractRestClient abstractRestClient = new AbstractRestClient();
	
	 Publit publit=new Publit();
	 Product product=new Product();
	 int count=0;
	 public Logger log;
	 
	 public BooksMissedInNestPublit()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test
	 public void MissedBooksFrompublit() throws InterruptedException, SQLException
	 {
	  log.info("-------------Books missed in Product Collection from Nest------------------");
	  
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
			//log.info("ISBN "+(i+1)+" = " +isbnList.get(i));
			
			 DBCollection query = ds1.getDB().getCollection("product");           
	         DBCursor	cursor = query.find(new BasicDBObject("isbn", result));
	            
	            while( cursor.hasNext() )
	            {
	            	DBObject mObj = cursor.next();
	            	product.setProvider_productid( (Integer) mObj.get("provider_productid"));
	            	DBObject mObj1 = (DBObject) mObj.get("publisher");
	   	            product.setProvider_productid( (Integer) mObj.get("provider_productid"));
	 	           
	 	           if(((String) mObj.get("productstatus")).equalsIgnoreCase("PARKED") || ((String) mObj.get("productstatus")).equalsIgnoreCase("UPCOMING"))
	 	           {
	 	        	   /*log.info("'ISBN' missed : "+mObj.get("isbn"));
	 	        	   log.info("'PRODUCT STATUS' IN PRODUCT COLLECTION : "+mObj.get("productstatus"));*/
	 	        	  log.info("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publisher_publishername -> "+ mObj1.get("publishername")+" || iscontractavailable -> "+mObj1.get("iscontractavailable")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher"));
	 	        	   log.info("");
	 	        	   count++;
	 	           }
	 	          /* else
	 	           {
	 	        	   log.info("'PRODUCT STATUS' other than 'Upcoming' and 'Parked' ");
	 	        	   log.info();
	 	           }*/
	             }
	      }
	        log.info("Total number of Books 'MISSED' in NEST : "+count);
	}
}
