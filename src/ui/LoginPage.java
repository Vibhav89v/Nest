package ui;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import common.AutomationConstants;
import generics.Property;

public class LoginPage implements AutomationConstants
{
	public static String un;
	public static String pwd;
	public static long timeout;
	public static String adminUrl;
	
    public static void main(String[] args) 
    {
    	un = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE,"ADMINUN");
    	pwd = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE,"ADMINPWD");
    	timeout = Long.parseLong(Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "IMPLICIT"));
    	adminUrl = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE,"ADMINURL");
    	
    	System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
    	WebDriver driver = new ChromeDriver();
    	driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout,TimeUnit.SECONDS);
		driver.get(adminUrl);
    	driver.findElement(By.name("j_username")).sendKeys(un);
    	driver.findElement(By.name("j_password")).sendKeys(pwd);
    	driver.findElement(By.name("submit")).click();
    	/*driver.findElement(By.name("reset"));
    	String expectedErrMsg = " Your login attempt was not successful, try again. Caused : Bad credentials ";
    	WebElement actualErrMsg = driver.findElement(By.xpath("//div[@class='errorblock']"));
    	assertEquals(expectedErrMsg, actualErrMsg);*/
    	//driver.close();
	}
}
