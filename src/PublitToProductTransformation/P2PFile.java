package PublitToProductTransformation;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.joda.time.DateTime;
import org.mongodb.morphia.Datastore;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.jersey.api.client.ClientResponse;

import Mongo.ElibDistributor.Elib;
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

public class P2PFile extends SuperTestScript implements AutomationConstants
{
	static String userid = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "USERID");
	static String password = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "PASSWORD");
	static String date = AddDate.addingDays(-1);
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
	 String publitDate;
	 String productDate;
	 public static WebDriver driver;
	 
	 public P2PFile()
	 {
	  log = Logger.getLogger(this.getClass());
	  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	 
	 @Test
	 public void publitToProductTransformationMeta() throws InterruptedException, SQLException
	 {
	  log.info("--------------In Publit to Product Transformation flow File--------------------");
	  
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
			log.info(isbnList.get(i));
			DateTime gte = null;
            try
            {
            	 gte = new DateTime(AddDate.currentStringToDate(AddDate.currentDate()));
            	 //log.info(gte);
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
            gte				= gte.withTimeAtStartOfDay();
            gte				= gte.minusDays(1);
            //log.info(gte);
            Date	start = new Date(gte.withTimeAtStartOfDay().getMillis());
            gte	= gte.plusDays(1);
            Date	end  = new Date(gte.withTimeAtStartOfDay().getMillis());
          //---------Setting the criteria of Getting the publit information based on the current and previous day's date-----------
            DBCollection query = ds.getDB().getCollection("publit_meta_info");           
            DBCursor	cursor = query.find(new BasicDBObject("isbn", result));
            
         List<Publit> list = new ArrayList<Publit>();
            
            while( cursor.hasNext() )
            {
            	DBObject mObj = cursor.next();
            	publit.setIsbn( (String) mObj.get("isbn"));
            	
            	DBObject mObj1 = (DBObject) mObj.get("manifestation");
            	log.info("'STATUS' IN PUBLIT COLLECTION :"+mObj1.get("status"));
            	log.info("'LAST UPDATED ON' IN PUBLIT COLLECTION : "+mObj.get("lastupdatedon_nest"));
            	
         publitDate = dateFormat.format((Date) mObj.get("lastupdatedon_nest"));
         log.info("PUBLIT DATE = "+publitDate);
            	
            	DBCollection query1 = ds1.getDB().getCollection("product");
                DBCursor cursor1 = query1.find(new BasicDBObject("isbn", result));
                List<Product> list1 = new ArrayList<Product>();
                while( cursor1.hasNext() )
                {
                  DBObject mObj2 = cursor1.next();
                  product.setIsbn((String) mObj1.get("isbn"));
                  log.info("'UPDATED_DATE' IN PRODUCT COLLECTION :"+mObj1.get("updateddate"));
            
                  if(mObj1.get("updateddate") != null)
                  {
                	  productDate = dateFormat.format((Date)mObj1.get("updateddate"));
                	  log.info("PRODUCT DATE = "+productDate);
                  }
                  else
                  {
                	  log.info("Updated date is null");
                  }
                //====================================================
                  log.info("File Download Source : "+mObj1.get("filedownloadsource"));
                  log.info("File Download Date : "+mObj1.get("filedownloaddate"));
                  log.info("File RE-Download Soyrce : "+mObj1.get("fileredownloadsource"));
                  log.info("File RE-Download Date : "+mObj1.get("fileredownloaddate"));
                  //===================================================
                   DBObject mObj3 = (DBObject) mObj1.get("publisher");
                   log.info("'IS_CONTRACT_AVAILABLE' IN PRODUCT COLLECTION :"+mObj2.get("iscontractavailable"));
                   
                   if(mObj2.get("iscontractavailable") != null)
                   {
                	   BasicDBList dbList1 = (BasicDBList) mObj1.get("productlist");
                	   BasicDBObject[] dbArr1 = dbList1.toArray(new BasicDBObject[0]);
                	   for(BasicDBObject dbObj1 : dbArr1) 
                	   {
                		   //log.info(dbObj1);
                		   log.info("'STATUS' IN PRODUCT COLLECTION : "+ dbObj1.get("name") );
                	   }
                   }
                   else
                   {
                	   log.info("No CONTRACT exist with this distributor");
                   }
                }
            }
            if(publitDate != null || productDate!=null)
            {
            	if(publitDate.equals(productDate))
            	{
            		log.info("'lastupdatedon' of 'Publit' and 'updateddate' of 'Product' collection MATCHED");
            	}
            	else
            	{
          	  		log.info("'lastupdatedon' of 'Publit' and 'updateddate' of 'Product' collection DIDN'T MATCHED");
            	}
            }
            else
            {
            	log.info("Publit Date or the Product date are Null");
            }
            log.info("");
		}
    }
 }