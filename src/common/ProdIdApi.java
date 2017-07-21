package common;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import elibToProductTransformation.ElibAPIImpl;

public class ProdIdApi 
{
public static int prodId;
public static String[] result;



	public static void main(String[] args) throws InterruptedException, SQLException {
		ProdIdApi prodIdApi = new ProdIdApi();
		elibToProductTransformation();
	}
	
	 public static String[] elibToProductTransformation() 
	   {
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'00:00");
		  
		  Calendar c=new GregorianCalendar();
		   c.add(Calendar.DATE, -1);
		   Date addedDate=c.getTime();
		   String date=dateFormat.format(addedDate);
//		   System.out.println("Date after adding the "+-1+" days : " +date);
		  
	    ElibAPIImpl ref=new ElibAPIImpl();

	    System.out.println(ref.getCompleteDataByDate(date));
	    
	    String str=ref.str;
	    str= str.substring(1,str.length()-1);
	    
	    result = str.split(",");
	    
	    return result;
	   }
	 

}
