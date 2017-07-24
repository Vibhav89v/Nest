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

public class ProcessingISBNPublit 
{
	static String userid = "nextory_api_user";
	static String password = "tos559ntio8ge9ep";
	static String date = AddDate.addingDays(-21);
	static String URL = "https://api.publit.com/trade/v2.0/products?only=isbn,updated_at&updated_at=" + date+ "&updated_at_args=greater_equal;combinator";
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	
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
	
	ClientResponse clientReponse;
	AbstractRestClient abstractRestClient = new AbstractRestClient();
	
	 Publit publit=new Publit();
	 Product product=new Product();
	 int count=0;
	 public Logger log;
	 
	 public ProcessingISBNPublit()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=3, groups={"All"})
	 public void calculatingProcessedISBNDiff() throws InterruptedException, SQLException
	 {
	  log.info("--------------Calculatiing Processed ISBN Difference---------------");
	  
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
			//System.out.println("ISBN "+(i+1)+" = " +isbnList.get(i));
			
			 DBCollection query = ds1.getDB().getCollection("product");           
	         DBCursor	prodCursor = query.find(new BasicDBObject("isbn", result));
	         
	         while( prodCursor.hasNext() )
	         {
	           DBObject mObj = prodCursor.next();
	           String s=(String) mObj.get("isbn");
	           
	           DBObject mObj1 = (DBObject) mObj.get("publisher");
	           if(((String) mObj.get("productstatus")).equalsIgnoreCase("Active") && mObj1.get("distributorname") !=null)
	             {
	        	   System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
	        	   System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
	        	   System.out.println("Count with status 'ACTIVE' : "+ ++countActive);
	        	   System.out.println();
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("Parked") && mObj1.get("distributorname") !=null)
	             {
	            	 System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 System.out.println("Count with status 'PARKED' : "+ ++countParked);
		        	 System.out.println();
	             } 
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("Upcoming") && mObj1.get("distributorname") !=null)
	             {
	            	 System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 System.out.println("Count with status 'UPCOMING' : "+ ++countUpcoming);
		        	 System.out.println();
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("A_Inactive") && mObj1.get("distributorname") !=null)
	             {
	            	 System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 System.out.println("Count with status 'A_Inactive' : "+ ++countA_Inactive);
		        	 System.out.println();
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("A_Omitted") && mObj1.get("distributorname") !=null)
	             {
	            	 System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 System.out.println("Count with status 'A_Omitted' : "+ ++countA_Omitted);
		        	 System.out.println();
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("L_Inactive") && mObj1.get("distributorname") !=null)
	             {
	            	 System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 System.out.println("Count with status 'L_Inactive' : "+ ++countL_Inactive);
		        	 System.out.println();
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("P_Inactive") && mObj1.get("distributorname") !=null)
	             {
	            	 System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 System.out.println("Count with status 'P_Inactive' : "+ ++countP_Inactive);
		        	 System.out.println();
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("P_Deferred") && mObj1.get("distributorname") !=null)
	             {
	            	 System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 System.out.println("Count with status 'P_Deferred' : "+ ++countP_Deferred);
		        	 System.out.println();
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("HighPrice") && mObj1.get("distributorname") !=null)
	             {
	            	 System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 System.out.println("Count with status 'HighPrice' : "+ ++countHighPrice);
		        	 System.out.println();
	             }
	             else if(((String) mObj.get("productstatus")).equalsIgnoreCase("Error") && mObj1.get("distributorname") !=null)
	             {
	            	 System.out.println("'PRODUCT STATUS' IN PRODUCT COLLECTION IS ==> "+mObj.get("productstatus"));
		        	 System.out.println("'DISTRIBUTORNAME' under 'Publisher' in PRODUCT COLLECTION ==> "+mObj1.get("distributorname"));
		        	 System.out.println("Count with status 'Error' : "+ ++countError);
		        	 System.out.println();
	             }
	             else
	            	 System.out.println("Invalid Status");
               }
          }
	    System.out.println("=========FINAL STATUS==========");
	    System.out.println("'ACTIVE : '"+countActive);
	    System.out.println("'PARKED' : "+countParked);
	    System.out.println("'UPCOMING' : "+countUpcoming);
	    System.out.println("'A_INACTIVE' : "+countA_Inactive);
	    System.out.println("'P_INACTIVE' : " +countP_Inactive);      
	    System.out.println("'A_OMITTED' : "+countA_Omitted);
	    System.out.println("'L_INACTIVE' : "+countL_Inactive);
	    System.out.println("'P_DEFERRED' : "+countP_Deferred);
	    System.out.println("'HIGH PRICE' : "+countHighPrice);
	    System.out.println("'ERROR' : "+countError);
	           /*try
	           {
	           product.setProvider_productid( (Integer.parseInt(s)) );
	           }
	           catch(NumberFormatException e)
	           {
	        	   System.out.println("Enterd a non-integer value");
	           }
*/	         }
          
}

