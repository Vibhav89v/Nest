import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.joda.time.DateTime;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import Mongo.ElibDistributor.Elib;
import Mongo.ProductCollection.Product;
import Mongo.ProductCollection.Publisher;
import generics.AddDate;
import generics.MongoDBMorphia;

public class AllProducts 
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	 Elib elib=new Elib();
	
	 Product product=new Product();
	 String elibDate;
	 String productDate;
	 String eDate;
	 String pDate;
	 public Logger log;
	 public static WebDriver driver;
	 public String iso="ISODate";
	 
	 public AllProducts()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=1, groups={"All"})
	 public void elibToProductTransformation() throws InterruptedException, SQLException
	 {
	  System.out.println("--------------In Elib to Product Transformation flow Meta--------------------");
	  
	  System.out.println("Fetching all the Product Id's from the url");
	  
	  System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	  
	  List<String> status = Arrays.asList("DELETED","ERROR","A_INACTIVE","P_INACTIVE","A_OMITTED","PARKED","L_INACTIVE","P_DEFERRED","UPCOMING");
	
	  for(String s:status)
	  {
		 
		  DBCollection prodQuery = ds1.getDB().getCollection("product");            
		  DBCursor	prodCursor = prodQuery.find(new BasicDBObject("productstatus", "ACTIVE").append( s, new Document("$ne","ACTIVE")));
		  
          DBCollection elibQuery = ds.getDB().getCollection("elib_webshop_meta");            
          //DBCursor	elibCursor = elibQuery.find(new BasicDBObject("ProductID",));
		  //.append("$and", Arrays.asList(gt, lt)));

		  //------------------------------Accessing the Members and Inner Members in a collection---------------------------------------
		  List<Elib> list = new ArrayList<Elib>();
		  while( prodCursor.hasNext() )
		  {
			  DBObject mObj = prodCursor.next();
			  DBObject mObj2 = (DBObject) mObj.get("publisher");

			  System.out.println("'provider_productid' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("provider_productid"));
			  System.out.println("'publishername' IN ELIB_WEBSHOP_META COLLECTION : "+ mObj2.get("publishername") );
			  System.out.println("'distributorname' IN ELIB_WEBSHOP_META COLLECTION : "+ mObj2.get("distributorname") );
			  System.out.println("'iscontractavailable' IN ELIB_WEBSHOP_META COLLECTION : "+ mObj2.get("iscontractavailable") );
			  System.out.println("'productstatus' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("productstatus"));
			  System.out.println("'statusatpublisher' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("statusatpublisher"));
			  System.out.println("'isbn' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("isbn"));
			  System.out.println("'_id' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("_id"));
			  System.out.println();

	  }
	  
	 
	  }

	 }
}