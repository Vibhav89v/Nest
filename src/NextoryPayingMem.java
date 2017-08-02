import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import generics.Excel;
import generics.Property;
import common.AutomationConstants;

public class NextoryPayingMem implements AutomationConstants
{
	public static WebDriver driver;
	public static long timeout; 
	public static String url;
	public static void main(String[] args) 
	{
		timeout = Long.parseLong(Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE,"IMPLICIT"));
		url = Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE,"NEXTADMINURL");
		System.setProperty(CHROME_KEY,DRIVER_PATH+CHROME_FILE);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout,TimeUnit.SECONDS);
		driver.get(url);
		driver.findElement(By.id("e-mail")).sendKeys("varsha_admin@frescano.se");
		driver.findElement(By.id("password")).sendKeys("password",Keys.ENTER);
		/*WebElement ele = driver.findElement(By.id("memberType"));
		Select sel= new Select(ele);
		sel.selectByVisibleText("MEMBER_PAYING");
		driver.findElement(By.xpath("//input[@value='Search']")).click();*/
		String un = Excel.getCellValue(INPUT_PATH,"trial", 1, 0);
		driver.findElement(By.id("email")).sendKeys(un,Keys.ENTER);
		
			String actual_memType = driver.findElement(By.id("membertype")).getAttribute("value");
			System.out.println(actual_memType);
			String expected_memType = "MEMBER_PAYING";
			Assert.assertEquals(actual_memType, expected_memType);
			String actual_subs = driver.findElement(By.id("subscriptionType")).getAttribute("value");
			System.out.println(actual_subs);
		
		driver.findElement(By.xpath("//label[contains(text(),'Tmp Password')]/../following-sibling::td[1]/*")).click();
		String pwd = driver.findElement(By.xpath("//input[@name='realPassword']")).getAttribute("value");
		System.out.println(pwd);
		driver.get(Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE,"NEXTURL"));
		driver.findElement(By.xpath("//nav[@class='topmenu-screen']//a[contains(text(),'Logga in ')]")).click();
		driver.findElement(By.id("e-mail")).sendKeys(un);
		driver.findElement(By.id("password")).sendKeys(pwd,Keys.ENTER);
		String subs = driver.findElement(By.xpath("//div[@class='col-xs-12 col-sm-8 ']//h3[contains(text(),'Du har abonnemanget')]")).getText();
		driver.findElement(By.xpath("//button[contains(text(),'Avsluta abonnemang')]")).click();
		if(subs.contains("STANDARD"))
		{
			driver.findElement(By.xpath("//button[contains(text(),'Avsluta abonnemang')]")).click();
			//expected_subs="STANDARD";
		}
		else if( subs.contains("PREMIUM"))
		{
			driver.findElement(By.xpath("//button[contains(text(),'Avsluta abonnemang')]")).click();
			//expected_subs="PREMIUM";
		}

		WebElement dropDown = driver.findElement(By.id("cancel-comment-new"));
		Select sel = new Select(dropDown);
		sel.selectByVisibleText("Tjänsten är inte tillräckligt prisvärd");
		driver.findElement(By.id("cancel-unlimited")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Klar')]")).click();
		driver.findElement(By.xpath("//li[@class='logIn-li']//a[contains(text(),'Logga ut')]")).click();
		
		driver.get(url);
		driver.findElement(By.id("e-mail")).sendKeys("varsha_admin@frescano.se");
		driver.findElement(By.id("password")).sendKeys("password",Keys.ENTER);
		un = Excel.getCellValue(INPUT_PATH,"trial", 1, 0);
		driver.findElement(By.id("email")).sendKeys(un,Keys.ENTER);
		
			String actual_memType1 = driver.findElement(By.id("membertype")).getAttribute("value");
			System.out.println(actual_memType1);
			String expected_memType1 = "MEMBER_PAYING_CANCELLED";
			Assert.assertEquals(actual_memType1, expected_memType1);
			String expected_subs = driver.findElement(By.id("subscriptionType")).getAttribute("value");
			System.out.println(expected_subs);
			Assert.assertEquals(actual_memType1,expected_memType1);
			Assert.assertEquals(actual_subs, expected_subs);
		Excel.shiftingRowsUp(INPUT_PATH, "trial", 1);
		driver.close();
	}
}
