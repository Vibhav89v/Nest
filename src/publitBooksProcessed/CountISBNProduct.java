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

public class CountISBNProduct 
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
	 
	 public CountISBNProduct()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void gettingCountISBN() throws InterruptedException, SQLException
	 {
	  log.info("--------------In Publit Counting ISBN--------------------");
	  
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
		//--------------------Fetching the Isbn from ISBN List--------------------------------------------------------- 
		for(int i=0;i<isbnList.size();i++)
		{
			String result=isbnList.get(i);
			System.out.println("ISBN "+(i+1)+" = " +isbnList.get(i));
			
			 DBCollection query = ds1.getDB().getCollection("product");           
	         DBCursor	cursor = query.find(new BasicDBObject("isbn", result));
	            
	            List<Publit> list = new ArrayList<Publit>();
	            
	            while( cursor.hasNext() )
	            {
	            	DBObject mObj = cursor.next();
	            	DBObject mObj2 = (DBObject) mObj.get("publisher");
	            	product.setIsbn( (String) mObj.get("isbn") );
	            	System.out.println("'ISBN' OF PRODUCT COLLECTION : "+result);
	            	if(((String) mObj.get("productstatus")).equalsIgnoreCase("Active"))
	            	{
	            		System.out.println("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publisher_publishername -> "+ mObj2.get("publishername")+" || iscontractavailable -> "+mObj2.get("iscontractavailable")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher"));
	            		System.out.println();
	            		count++;
	            	}
	            	else if(!((String) mObj.get("productstatus")).equalsIgnoreCase("Active"))
	            	{
	            		System.out.println("_id -> "+mObj.get("_id")+"|| provider_productid -> "+mObj.get("provider_productid")+"|| isbn -> "+mObj.get("isbn")+"|| publisher_publishername -> "+ mObj2.get("publishername")+" || iscontractavailable -> "+mObj2.get("iscontractavailable")+" || productstatus -> "+mObj.get("productstatus")+" || statusatpublisher -> "+mObj.get("statusatpublisher"));
	            		System.out.println();
	            		count++;
	            	}
	            }
		}
	        System.out.println("Total number of Books Processed by NEST : "+count);
		
	}
}
