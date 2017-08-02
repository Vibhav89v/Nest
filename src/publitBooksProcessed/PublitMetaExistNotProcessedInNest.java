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
import generics.AddDate;
import generics.MongoDBMorphia;
import generics.Property;
import restClientForPublit.AbstractRestClient;
import valueObject.Datum;
import valueObject.PublitVO;

public class PublitMetaExistNotProcessedInNest implements AutomationConstants
{
	static String userid = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "USERID");
	static String password = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "PASSWORD");
	static String date = AddDate.addingDays(-21);
	static String URL = "https://api.publit.com/trade/v2.0/products?only=isbn,updated_at&updated_at=" + date+ "&updated_at_args=greater_equal;combinator";
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	
	ClientResponse clientReponse;
	AbstractRestClient abstractRestClient = new AbstractRestClient();
	
	 Publit publit=new Publit();
	 Product product=new Product();
	 public Logger log;
	 
	 public PublitMetaExistNotProcessedInNest()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void missingISBNNest() throws InterruptedException, SQLException
	 {
		 log.info("--------------Missing ISBN in Nest--------------------");
	  
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
			//log.info();
			
			 DBCollection prodQuery = ds.getDB().getCollection("product");           
	         DBCursor	prodCursor = prodQuery.find(new BasicDBObject("isbn", result));
	            
	         if(prodCursor.count()==0)   
	         {
	        	 log.info("ISBN not processed in Nest :"+result);
	           
	        	 DBCollection publitQuery = ds.getDB().getCollection("publit_meta_info");
   			   	 DBCursor publitCursor = publitQuery.find(new BasicDBObject("isbn",result));
	       
   			   	 while( publitCursor.hasNext() )
   			   	 {
   			   		 DBObject mObj = publitCursor.next();
   			   		 publit.setIsbn((String)mObj.get("isbn"));
   			   		 log.info("_id -> "+mObj.get("_id")+"|| active -> "+mObj.get("active")+"|| available_at -> "+mObj.get("available_at"));
   			   		 log.info("");
   			   	 }
   			 }
	           
	          while( prodCursor.hasNext() )
	          {
	        	  prodCursor.next();
	          // log.info("ISBN processsed by the Nest :" +result);
	           //log.info();
	          }
	     }
	}
}
