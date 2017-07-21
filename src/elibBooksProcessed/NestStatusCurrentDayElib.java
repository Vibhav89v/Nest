package elibBooksProcessed;

import java.sql.SQLException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import Mongo.ProductCollection.Product;
import generics.AddDate;
import generics.MongoDBMorphia;

public class NestStatusCurrentDayElib 
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	 Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	 Product product=new Product();
	 public Logger log;
	 public static WebDriver driver;
	 
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
	  
	 public NestStatusCurrentDayElib()
	  {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	  }
	  
	  @Test(enabled=true, priority=1, groups={"All"})
	  public void detailOfActiveStatusAndNetPriceZeroInNest() throws InterruptedException, SQLException
	   {
		System.out.println("--------Details of Active PID's NEST------------------");
	    System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	   
	    driver=new ChromeDriver();
	  
	    String date=AddDate.addingDays(-1);
	    driver.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+date+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
	    String products = driver.findElement(By.id("content")).getText();
	     
	    products=products.replaceAll(",","");
	        
	    StringTokenizer t = new StringTokenizer(products);
	    String ProductID ="";
	    while(t.hasMoreTokens())
	    {
	    	  ProductID = t.nextToken();
	          int result = Integer.parseInt(ProductID);
	         
	          DBCollection prodQuery = ds1.getDB().getCollection("product");            
	          DBCursor prodCursor = prodQuery.find(new BasicDBObject("provider_productid", result));
	            
	          while( prodCursor.hasNext() )
	          {
	           DBObject mObj = prodCursor.next();
	           product.setProvider_productid( (Integer) mObj.get("provider_productid"));
	           
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
	    driver.close();
	   }
}