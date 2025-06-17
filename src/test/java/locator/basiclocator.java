package locator;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class basiclocator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
              
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.get("https://www.amazon.in/");
		WebElement  search=driver.findElement(By.name("field-keywords"));
		search.sendKeys("iphone 16pro max");
		search.submit();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,550)", "");
		String mainpage =driver.getWindowHandle();
	    System.out.println("mainpage"+ mainpage);
	    //driver.findElement(By.xpath("//h2[@aria-label='iPhone 16 Pro Max 256 GB: 5G Mobile Phone with Camera Control, 4K 120 fps Dolby Vision and a Huge Leap in Battery Life. Works with AirPods; Desert Titanium']//span[contains(text(),'iPhone 16 Pro Max 256 GB: 5G Mobile Phone with Cam')]")).click();

	
	Set<String> allpage=driver.getWindowHandles();
	for(String nextpage:allpage )
	{
		if(!nextpage.equals(allpage))
		{
			driver.switchTo().window(mainpage);
			break;
		}
	}
	System.out.println(driver.getCurrentUrl());
	

driver.findElement(By.xpath("//h2[@aria-label='iPhone 16 Pro Max 256 GB: 5G Mobile Phone with Camera Control, 4K 120 fps Dolby Vision and a Huge Leap in Battery Life. Works with AirPods; Desert Titanium']//span[contains(text(),'iPhone 16 Pro Max 256 GB: 5G Mobile Phone with Cam')]")).click();

driver.findElement(By.id("//input[@id='add-to-cart-button")).click();
      
	
	}

}
