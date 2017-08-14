
public class AddInteger 
{
	public static int addedValue;
	
	public static int getSum(int a, int b)
	{
		addedValue=a+b;
		return addedValue;
	}
	
	public static void main(String[] args) {
		System.out.println(getSum(20, 30));
		
	}
}
