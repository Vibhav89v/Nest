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
import generics.AddDate;
import generics.MongoDBMorphia;
import restClientForPublit.AbstractRestClient;
import vo.Datum;
import vo.PublitVO;

public class StatusAndFormatCheckPublit 
{
	static String userid = "nextory_api_user";
	static String password = "tos559ntio8ge9ep";
	static String date = AddDate.addingDays(-1);
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
	 
	 public StatusAndFormatCheckPublit()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void statusFormatCheckPublit() throws InterruptedException, SQLException
	 {
	  log.info("--------------In Publit checking 'STATUS' and 'FORMAT'--------------------");
	  
	  log.info("Fetching all the ISBN's from the url");
	  
	    abstractRestClient.setHTTPBasicAuthFilter(userid, password);
	    clientReponse = abstractRestClient.get(URL, null, null);
		PublitVO vo = abstractRestClient.getEntity(clientReponse, PublitVO.class);
		//=============================System.out.println("Complete Information : "+vo);===================================
		
		List<Datum> data = vo.getData();
		List<String> isbnList = new ArrayList<>();
		for (Datum datum : data)
		{
			isbnList.add(datum.getIsbn());
		}
		//==============================================ISBN List========================================================
		System.out.println("ISBN being fetched for " + date+ ": " + isbnList);
		System.out.println("ISBN List Size : "+isbnList.size());
		System.out.println();
		//--------------------Fetching the Isbn from ISBN List--------------------------------------------------------- 
		for(int i=0;i<isbnList.size();i++)
		{
			String result=isbnList.get(i);
			System.out.println("ISBN "+(i+1)+" = "+isbnList.get(i));
			
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
	        	   System.out.println(" ");
	           }
	           
	           DBObject mObj1 = (DBObject) mObj.get("formats");
	           
	           if(((String) mObj.get("productstatus")).equalsIgnoreCase("Active") || mObj.get("formats") != null)
	           {
	        	   System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS 'ACTIVE' and 'FORMAT' IS PRESENT ");
	        	   System.out.println("'FORMAT' IN PRODUCT COLLECTION :"+mObj.get("formats"));
	        	   System.out.println();
	           }
	           else
	           {
	        	   System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS 'IN-ACTIVE' and 'FORMAT' IS 'NULL'");
	        	   System.out.println();
	           }
             }
		  }
	 }
}