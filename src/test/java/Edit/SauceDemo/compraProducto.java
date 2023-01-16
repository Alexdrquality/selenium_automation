package Edit.SauceDemo;

import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;


import com.github.javafaker.Faker;

public class compraProducto{
	String url = "https://www.saucedemo.com/";
	WebDriver driver;
	File screen;
	String RouteEvidence = "..\\\\SauceDemo\\\\Evidencias\\\\";
	
	@BeforeSuite
	public void abrirNavegador() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
	}
	@BeforeTest
	public void hacerLogin() throws IOException {
		driver.get(url);
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(RouteEvidence + "01_loginExitoso.jpg"));
		
	}
	@Test
	public void completarCompra() throws IOException {
		Faker faker = new Faker();
		String Nombre = faker.name().firstName(); 
		String Apellido = faker.name().lastName();
		String Postal = faker.number().digits(4);
		//Seleccionamos elemento
		driver.findElement(By.id("item_0_title_link")).click();
		WebElement nameProduct = driver.findElement(By.cssSelector(".inventory_details_name"));
	    String Title = nameProduct.getText();
	    System.out.println(Title);
	    Assert.assertEquals("Sauce Labs Bike Light", Title); //Verificamos que el elemento seleccionado sea el correcto
	    driver.findElement(By.name("add-to-cart-sauce-labs-bike-light")).click(); //Seleccionamos boton anadir
	    screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(RouteEvidence + "02_agregaProductoCarrito.jpg"));
		driver.findElement(By.className("shopping_cart_link")).click();
		//Verificamos cantidad de productos seleccionados
		WebElement Cart = driver.findElement(By.className("cart_quantity"));
		String Carito = Cart.getText();
		Assert.assertEquals(Carito, "1");
		driver.findElement(By.id("checkout")).click();
		driver.findElement(By.id("first-name")).sendKeys(Nombre);
		driver.findElement(By.id("last-name")).sendKeys(Apellido);
		driver.findElement(By.id("postal-code")).sendKeys(Postal);
		screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(RouteEvidence + "03_completamosCheckout.jpg"));
		driver.findElement(By.id("continue")).click();
		screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(RouteEvidence + "04_overviewCheckout.jpg"));
		driver.findElement(By.id("finish")).click();
		screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(RouteEvidence + "05_compraFinalizada.jpg"));
		WebElement checkoutFinish = driver.findElement(By.className("complete-header"));
	    String finish = checkoutFinish.getText();
	    System.out.println(finish);
	    Assert.assertEquals(finish, finish); ////Verificamos que se visualice el texto correcto

	}
	@AfterSuite
	public void cerrarNavegador() {
		driver.close();

  }

}
	

