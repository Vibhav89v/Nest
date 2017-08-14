package priceCalculation;

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

import Mongo.ProductCollection.Product;
import common.SuperTestScript;
import generics.Database;
import generics.MongoDBMorphia;

public class PublisherContract extends SuperTestScript
{
	 MongoDBMorphia mongoutil = new MongoDBMorphia();
	 Datastore ds1 = mongoutil.getMorphiaDatastoreForProduct();
	 Product product=new Product();
	 static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 public Logger log;
	 String distributorQuery;
	 String publisherResult;
	 int distributorResult;
	 String publisherQuery;
	 
	  public PublisherContract()
	  {
	   log = Logger.getLogger(this.getClass());
	   Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
	  }
	  
	  @Test
	  public void statusCurrentDayNest() 
	  {
		  List<Integer> distList = new ArrayList<Integer>();
		  int distrIdList;
		  
		  
		  List<Integer> contractList = new ArrayList<Integer>();
		  int contractAvailList;
		  
		  
		  List<Integer> prodList = new ArrayList<Integer>();
		  int prodStatusList;
	     
	     publisherQuery ="select distinct pub.publisher_name from publisher pub, bart_publisher_association ass where  pub.publisher_id=ass.publisherid and ass.assc_status='APPROVED' and ass.status='ACTIVE' and CURDATE() >=date(ass.contract_start_date)  and ( ass.contract_end_date is null or CURDATE() <=date(ass.contract_end_date))";
	     publisherResult = Database.executeQuery(publisherQuery);
	     List<String> pubList = new ArrayList<String>();
	     pubList.addAll(Database.li);
	     
	     
	     for(int i=0;i<pubList.size();i++)
	     {
	    	 //System.out.println(pubList.get(i));
	    	try
	    	{
	    	 String str= pubList.get(i);
	    	 distributorQuery ="select distinct distributor_id  from publisher pub, bart_publisher_association ass where  pub.publisher_id=ass.publisherid and ass.assc_status='APPROVED'  and ass.status='ACTIVE' and  CURDATE() >=date(ass.contract_start_date)  and ( ass.contract_end_date is null or   CURDATE() <=date(ass.contract_end_date) ) and pub.publisher_name= '" +str+"'";
	    	 distributorResult = Integer.parseInt(Database.executeQuery(distributorQuery));
	    	 
	    	 
	    	 DBCollection prodQuery = ds1.getDB().getCollection("product");
	    	 DBCursor prodCursor = prodQuery.find(new BasicDBObject("publisher.publishername",str));
	    	 
	    	 while(prodCursor.hasNext())
	    	 {
	    		 try
	    		 {
	    			 DBObject mObj = prodCursor.next();
	    			 DBObject mObj1 = (DBObject) mObj.get("publisher");
	    			 
	    			 int distributorId = (int) mObj1.get("distributorid");
	    			 String contract = (String) mObj1.get("iscontractavailable");
	    			 
	    			 if(distributorId == distributorResult)
	    			 {	}
	    			 else
	    			 {
	    				 distrIdList= (int) mObj.get("provider_productid");
	    				 distList.add(distrIdList);
	     	    		 log.info("FOR PID :"+mObj.get("provider_productid")+ "|| isbn : "+mObj.get("isbn")+" THE 'distributorid' DOESN'T MATCHES i.e distributorid is :" + mObj1.get("distributorid") );
	    			 }
	    			 
	    			 if(contract.equalsIgnoreCase("true"))
	    			 {
	    				 	String prodStatus = (String) mObj.get("productstatus");
		    				if(!prodStatus.equalsIgnoreCase("A_OMITTED"))
		    				{
		    					//log.info("FOR PID :"+mObj.get("provider_productid")+ "|| isbn : "+mObj.get("isbn")+" || publisher.publishername : "+str+" || PRODUCT STATUS IS OTHER THAN 'A_OMITTED' i.e. : "+mObj.get("productstatus")+ "distributorid is :"+ mObj1.get("distributorid") + " and 'iscontractavailable' is :" +mObj1.get("iscontractavailable")+ ", PRODUCT STATUS :"+ mObj.get("productstatus") );
		    				}
		    				else
		    				{
		    					log.info("FOR PID :"+mObj.get("provider_productid")+ "|| isbn : "+mObj.get("isbn")+" || publisher.publishername : "+str+" || PRODUCT STATUS IS 'A_OMITTED' i.e. : "+mObj.get("productstatus") );
		    					prodStatusList = (int) mObj.get("provider_productid");
		    					prodList.add(prodStatusList);
		    				}
	    			 }
	    			 else
	    			 {
	    				 contractAvailList= (int) mObj.get("provider_productid");
	    				 contractList.add(contractAvailList);
	     	    		 log.info("FOR PID :"+mObj.get("provider_productid")+ "|| isbn : "+mObj.get("isbn")+" THE 'iscontractavailable' DOESN'T MATCHES i.e iscontractavailable is :" + mObj1.get("iscontractavailable") );
	    			 }
	    		 }
	    		 catch(Exception e)
	    		 {
	    			 
	    		 }
	    	 }
	     }
	     catch(Exception e)
	     {
	    	 
	     }
	     
	  }
	     log.info("TOTAL PID NOT MATCHING THE DISTRIBUTOR ID:"+ distList.size() + " and are mentioned below:");
	     log.info(distList);
	     System.out.println();
	    
	     log.info("TOTAL PID NOT MATCHING THE IS_CONTRACT_AVAILABLE:"+ contractList.size()+ " and are mentioned below:");
	     log.info(contractList);
	     System.out.println();
	    
	     log.info("TOTAL PID NOT MATCHING THE PRODUCTSTATUS :"+ prodList.size()+ " and are mentioned below:");
	     log.info(prodList);
	   	}
}