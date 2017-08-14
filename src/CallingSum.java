import common.AutomationConstants;
import generics.Excel;
import generics.Property;

public class CallingSum implements AutomationConstants
{
	public static void main(String[] args) 
	{
		AddInteger.getSum(20, 30);
		System.out.println(AddInteger.addedValue);
	
		long timeout= Long.parseLong(Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "IMPLICIT"));
		System.out.println(timeout);
	
	}
}
