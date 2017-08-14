import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Property 
{
	public static String getPropertyValue(String filepath,String key) throws FileNotFoundException, IOException
	{
		String value = "";
		Properties ppt = new Properties();
		ppt.load(new FileInputStream(filepath));

		ppt.getProperty(key);
		return key;
		
	}
}
