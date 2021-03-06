package priceCalculation;

import generics.AddDate;
import generics.Database;
import generics.MongoDBMorphia;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.testng.annotations.Test;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import Mongo.ProductCollection.Product;
import Mongo.PublitDistributor.Publit;
import common.SuperTestScript;


public class DeferredStatusCheck extends SuperTestScript
{
	public static Logger log;
	Publit publit=new Publit();
	static Product product=new Product();
	public static String query =" ";
	public static String result;
	public static String publishedDate;
	String curdate=AddDate.currentDate();
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static long age;
	String prodStatus;
	
	MongoDBMorphia mongoutil = new MongoDBMorphia();
	Datastore ds1=mongoutil.getMorphiaDatastoreForProduct();
	
	 public DeferredStatusCheck()
	 {
		 log = Logger.getLogger(this.getClass());
		  Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	 }
	
	 @Test
	public void getDataFromPublisher()
	{
		 
		List<Integer> nullList = new ArrayList<Integer>();
	    int x; 
	    
	    List<Integer> trueList = new ArrayList<Integer>();
	    int y;
	    
	    List<Integer> falseList = new ArrayList<Integer>();
	    int z;
	
		query = "select publisher_id from publisher_settings where isdeferred=1 and deferreddays=180";
		
		result = Database.executeQuery(query);
		//System.out.println("publisher_id picked are : " +Database.li.size());
		
		for(int i=0;i<Database.li.size();i++)
		{
			//log.info("'publisher_id' satisfying 'isdeferred==true' and 'deferreddays==180' are as follows:");
			String str= Database.li.get(i);
			int pubId= Integer.parseInt(str); 
			
			DBCollection query = ds1.getDB().getCollection("product");           
	        DBCursor	cursor = query.find(new BasicDBObject("publisher.publisherid", pubId));
	        while( cursor.hasNext() )
	        {
	        	try
	        	{
	        		DBObject mObj = cursor.next();
	            	
	            	publishedDate = dateFormat.format((Date) mObj.get("publisheddate"));
	            	age = AddDate.calculateNumberOfDays(publishedDate, curdate);
	            	
	            	if(age<=180)
	            	{
	            		//log.info("Provider_productid : " +mObj.get("provider_productid")+ " || ProductStatus : "+mObj.get("productstatus")+ " || publisheddate : "+mObj.get("publisheddate")+" || publisher.publisherid : "+pubId+" || age : "+age+" || deferred.statusoverride : "+mObj.get("deferred"));
	            		if(mObj.get("deferred")==null)
	            		{
	            			prodStatus = (String) mObj.get("productstatus");
	            			if(prodStatus.equals("P_DEFERRED"))
	            			{
	            				//log.info("FOR AGE <= 180 and deferred.statusoverride=null: PRODUCT STATUS IS P_DEFERRED");
	            			}
	            			else
		            		{
		            			 x= (int) mObj.get("provider_productid");
		            			 nullList.add(x);
		     	    			 log.info("FOR PID "+mObj.get("provider_productid")+" and publisher.publisherid : "+pubId+" ,AGE <= 180 and deferred.statusoverride=null: PRODUCT STATUS IS OTHER THAN P_DEFERRED i.e. : " +mObj.get("productstatus"));
		            		}
	            		}
	            		
	            		else if(!(mObj.get("deferred")==null))
	            		{
	            			DBObject mObj1 = (DBObject) mObj.get("deferred");
	            			Boolean boo = (Boolean) mObj1.get("statusoverride");
	            		
	            			//log.info("Provider_productid : " +mObj.get("provider_productid")+ " || ProductStatus : "+mObj.get("productstatus")+ " || publisheddate : "+mObj.get("publisheddate")+" || publisher.publisherid : "+pubId+" || age : "+age+" || deferred.statusoverride : "+mObj1.get("statusoverride"));
		            	
	            			if(boo == true)
	            			{
	            				prodStatus = (String) mObj.get("productstatus");
	    	            		if(prodStatus.equals("ACTIVE"))
	    	            		{
	    	            			//log.info("FOR AGE <= 180 and deferred.statusoverride=true: PRODUCT STATUS IS ACTIVE");
	    	            		}
	    	            		
	    	            		else
	    	            		{
	    	            			 y= (int) mObj.get("provider_productid");
	    	            			 trueList.add(y);
	    	     	    			 log.info("FOR PID "+mObj.get("provider_productid")+" and publisher.publisherid : "+pubId+" ,AGE <= 180 and deferred.statusoverride=true: PRODUCT STATUS IS OTHER THAN ACTIVE i.e. : " +mObj.get("productstatus"));
	    	            		}
	            			}
	            			
	            			else if(boo == false)
	            			{
	            				prodStatus = (String) mObj.get("productstatus");
	    	            		if(prodStatus.equals("P_DEFERRED"))
	    	            		{
	    	            			//log.info("FOR AGE <= 180 and deferred.statusoverride= false : PRODUCT STATUS IS P_DEFERRED");
	    	            		}
	    	            		
	    	            		else
	    	            		{
	    	            			 z= (int) mObj.get("provider_productid");
	    	            			 falseList.add(z);
	    	     	    			 log.info("FOR PID "+mObj.get("provider_productid")+" and publisher.publisherid : "+pubId+" ,AGE <= 180 and deferred.statusoverride=false: PRODUCT STATUS IS OTHER THAN P_DEFERRED i.e. : " +mObj.get("productstatus"));
	    	            		}
	            			}	
	            		}
	            	}
	        	}
	            
	        	catch(Exception e)
	        	{
	        		// e.printStackTrace();
	        	}
	        }
		}
		
		log.info("FOR AGE <= 180 and deferred.statusoverride=null; 'provider_productid' for PRODUCT STATUS other than P_DEFERRED are "+nullList.size()+" and mentioned Below: ");
		log.info(nullList);
		log.info("FOR AGE <= 180 and deferred.statusoverride=true: 'provider_productid' for PRODUCT STATUS other than ACTIVE are "+trueList.size()+" and mentioned Below: ");
		log.info(trueList);
		log.info("FOR AGE <= 180 and deferred.statusoverride=false: 'provider_productid' for PRODUCT STATUS other than P_DEFERRED are "+falseList.size()+" and mentioned Below: ");
 		log.info(falseList);
 		
	}
}
