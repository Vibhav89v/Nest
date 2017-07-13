package elibToProductTransformation;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


public class E2PFile
{
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNestVer2();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	
	 Elib elib=new Elib();
	 Product product=new Product();
	 String elibDate;
	 String productDate;
	 String eDate;
	 String pDate;
	 public Logger log;
	 public static WebDriver driver;
	 public String iso="ISODate";
	 
	 public E2PFile()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test(enabled=true, priority=1, groups={"All"})
	 public void elibToProductTransformation() throws InterruptedException, SQLException
	 {
	  System.out.println("--------------In Elib to Product Transformation flow File Download--------------------");
	  
	  System.out.println("Fetching all the Product Id's from the url");
	  
	  System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
	  
	  driver=new ChromeDriver();
		//----------------------------------ELIB---------------------------------------------------------------
		String date=AddDate.addingDays(-1);
	    driver.get("https://xdapi.elib.se/v1.0/products?ServiceID=2238&From="+date+"T13:00&Checksum=c5b42fb8f285b45983bc5325eebd23021a75626bd7edcf4103dce80e71457a50d48dda42ea571dda91511f51854deb9d99a0841c9f36f82ab54f6d19dd5ec6a6");
        String products = driver.findElement(By.id("content")).getText();
	    
        products=products.replaceAll(",","");
        
        StringTokenizer t = new StringTokenizer(products);
        String ProductID ="";
        Elib prodList=null;
        Product proCollection=null;
        Publisher publish=null;
        //String actual="";
        //String expected="";
        while(t.hasMoreTokens())
        {
        	ProductID = t.nextToken();
        	int result = Integer.parseInt(ProductID);
            System.out.println("'ProductID' IN ELIB_WEBSHOP_META COLLECTION: "+result);
            DateTime gte = null;
            try
            {
            	 gte = new DateTime(AddDate.currentStringToDate(AddDate.currentDate()));
            	 //System.out.println(gte);
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
             //-------------------------------Fetching data from elib_webshop_meta---------------------------------------------
            
            gte				= gte.withTimeAtStartOfDay();
            gte				= gte.minusDays(1);
            //System.out.println(gte);
            Date	start = new Date(gte.withTimeAtStartOfDay().getMillis());
            gte	= gte.plusDays(1);
            Date	end  = new Date(gte.withTimeAtStartOfDay().getMillis());
            
 //---------Setting the criteria of Getting the elib information based on the current and previous day's date-----------
            DBCollection query = ds.getDB().getCollection("elib_webshop_meta");            
            Document	gt = new Document("lastupdatedon", new Document("$gte", start));
            Document	lt = new Document("lastupdatedon", new Document("$lt", end)); 
            DBCursor	cursor = query.find(new BasicDBObject("ProductID", result));
            		//.append("$and", Arrays.asList(gt, lt)));
         
            
            //Retriving the fields from the elib collection
            //System.out.println("LastUpdatedOn="+elib.getLastupdatedon());
            
            /*query.criteria("ProductID"+result);
            query.criteria("lastupdatedon"+iso+("2017-06-26T18:42:06.835Z"));*/
            
 //------------------------------Accessing the Members and Inner Members in a collection---------------------------------------
            List<Elib> list = new ArrayList<Elib>();
            
			//Integer	id = null;
            while( cursor.hasNext() )
            {
             DBObject mObj = cursor.next();
            // System.out.println(mObj.toString());
             elib.setProductId( (Integer) mObj.get("ProductID") );
             //System.out.println(ProductID);
             
             BasicDBList dbList = (BasicDBList) mObj.get("Statuses");
             BasicDBObject[] dbArr = dbList.toArray(new BasicDBObject[0]);
             for(BasicDBObject dbObj : dbArr) 
             {
              //System.out.println(dbObj);
              System.out.println("'STATUS' IN ELIB_WEBSHOP_META COLLECTION : "+ dbObj.get("Name") );
              System.out.println("'LAST_UPDATED_ON' IN ELIB_WEBSHOP_META COLLECTION : "+mObj.get("lastupdatedon"));
         
     elibDate = dateFormat.format((Date) mObj.get("lastupdatedon"));
     System.out.println("ELIB DATE = "+elibDate);
              
              Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
              
              //db.product.find({"provider_productid":ProductID,"updateddate":updateddate,}).pretty();
              DBCollection query1 = ds1.getDB().getCollection("product");
              DBCursor cursor1 = query1.find(new BasicDBObject("provider_productid", result));
              List<Product> list1 = new ArrayList<Product>();
              while( cursor1.hasNext() )
              {
               DBObject mObj1 = cursor1.next();
              // System.out.println(mObj1.toString());
               product.setProvider_productid((Integer) mObj1.get("provider_productid"));//setProductId( (Integer) mObj1.get("provider_productid"));
               System.out.println("'UPDATED_DATE' IN PRODUCT COLLECTION :"+mObj1.get("updateddate"));
               //====================================================
               System.out.println("File Download Source : "+mObj1.get("filedownloadsource"));
               System.out.println("File Download Date : "+mObj1.get("filedownloaddate"));
               System.out.println("File RE-Download Soyrce : "+mObj1.get("fileredownloadsource"));
               System.out.println("File RE-Download Date : "+mObj1.get("fileredownloaddate"));
               //===================================================
     productDate = dateFormat.format((Date)mObj1.get("updateddate"));
     System.out.println("PRODUCT DATE = "+productDate);
               DBObject mObj2 = (DBObject) mObj1.get("publisher");
               System.out.println("'IS_CONTRACT_AVAILABLE' IN PRODUCT COLLECTION :"+mObj2.get("iscontractavailable"));
               
//               BasicDBList dbList2 = (BasicDBList) mObj1.get("publisher");
//               for(Object dbObj2 : dbList2)
//               {
//            	   System.out.println("STATUS IN PRODUCT COLLECTION : "+ dbObj2.get("name") );
//               }
               BasicDBList dbList1 = (BasicDBList) mObj1.get("productlist");
               BasicDBObject[] dbArr1 = dbList1.toArray(new BasicDBObject[0]);
               for(BasicDBObject dbObj1 : dbArr1) 
               {
                //System.out.println(dbObj1);
                System.out.println("'STATUS' IN PRODUCT COLLECTION : "+ dbObj1.get("name") );
               }
           
  	}
             }
             
        }
            if(elibDate.equals(productDate))
            {
          	  System.out.println("'lastupdatedon' of 'Elib' and 'updateddate' of 'Product' collection MATCHED");
            }
            else
            {
          	  System.out.println("'lastupdatedon' of 'Elib' and 'updateddate' of 'Product' collection DIDN'T MATCHED");
            }
            System.out.println();
   }
        driver.close();
  }
 }


