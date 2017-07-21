package elibToProductTransformation;

import java.util.StringTokenizer;

import generics.AddDate;

public class MetaWithoutBrowser 
{
	static String date = AddDate.addingDays(-1);
	//static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	public static void main(String [] args)
	{
		ElibAPIImpl ref = new ElibAPIImpl();
		//ref.getCompleteDataByDate(date);
		System.out.println(ref.getCompleteDataByDate(date));
		
		StringTokenizer t = new StringTokenizer(date);
		String ProductID ="";
		
	}
	
}
