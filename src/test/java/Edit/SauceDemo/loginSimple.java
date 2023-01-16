package Edit.SauceDemo;

import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;




public class loginSimple {
	String url = "https://www.saucedemo.com/";
	File screen;
	@Test
	
	public void HacerLogin() throws IOException
	{
		WebDriver driver = new ChromeDriver();
		driver.get(url);
		WebElement User = driver.findElement(By.id("user-name"));
		User.sendKeys("standard_user");
		WebElement Pass = driver.findElement(By.id("password"));
		Pass.sendKeys("secret_sauce");
		WebElement Buttonlogin = driver.findElement(By.id("login-button"));
		Buttonlogin.click();
		
		
	}
	

}
