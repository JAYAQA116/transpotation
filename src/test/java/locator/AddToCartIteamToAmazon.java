package locator;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AddToCartIteamToAmazon {

	public static void main(String[] args) {
		
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.get("https://www.amazon.in");
		driver.findElement(By.xpath("//input[@id=\"twotabsearchtextbox\"]")).click();
		driver.findElement(By.xpath("//input[@id=\"twotabsearchtextbox\"]")).sendKeys("samsun s4");
		driver.findElement(By.xpath("//div[@class=\"nav-search-submit nav-sprite\"]")).click();
		driver.findElement(By.xpath("//h2[@class=\"a-size-medium a-spacing-none a-color-base a-text-normal\"]")).click();
		
		Set<String> s=driver.getWindowHandles();
		ArrayList ar= new ArrayList(s);
		 System.out.println(ar.get(0));
		 System.out.println(ar.get(1));
		 
		driver.switchTo().window((String)ar.get(1));
		
	driver.findElement(By.xpath("//span[contains(@id,\"wishListMainButton\")]")).click();
	

	}

}
