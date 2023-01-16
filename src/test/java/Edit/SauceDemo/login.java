package Edit.SauceDemo;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utilities.CapturaEvidencia;
import Utilities.DatosExcel;



public class login {
	String url = "https://www.saucedemo.com/";
	WebDriver driver;
	File screen;
	String RouteEvidence = "demo";


	@BeforeSuite
	public void abrirNavegador() throws IOException, InvalidFormatException, InterruptedException {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		CapturaEvidencia.escribirTituloEnDocumento(RouteEvidence + "CompraCompleta.docx", "SauceDemo Evidencias", 11);		CapturaEvidencia.escribirTituloEnDocumento("CompraCompleta", "SauceDemo Evidencias", 11);

	}  
	@Test(dataProvider="Datos Order") 
	public void loginordenCompleta(String user, String pass,String name, String apellido, String code,String escenario) throws IOException, InvalidFormatException, InterruptedException {
			driver.get(url);
			WebElement username = driver.findElement(By.id("user-name"));
			WebElement password = driver.findElement(By.id("password"));
		   //Utilizamos clear para limpiar campos y no se sobreescriba
			username.clear();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5000));
			System.out.println(user + " " + pass );
			username.sendKeys(user);
			password.clear();
			password.sendKeys(pass);
			driver.findElement(By.id("login-button")).click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8000));
		
			
		
		if (escenario.equals("Valido")) {
			   // Bloque de codigo que sera ejecutado si la condicion es VERDADERA 
			String actualUrl= driver.getCurrentUrl();
			String actualTitle = "PRODUCTS";
			String header = driver.findElement(By.className("title")).getText();// Obtenemos valor titulo.getAttribute("textContent");
			Assert.assertNotEquals(url,actualUrl);
			driver.findElement(By.className("header_secondary_container")).isDisplayed(); //Verificamos existencia del header
			Assert.assertEquals(header, actualTitle);//Validamos titulo sea el correcto
			System.out.println("Ingresamos correctamente a la Home");
			CapturaEvidencia.escribirTituloEnDocumento(RouteEvidence + "CompraCompleta.docx" , "TEST CASES 01: Realizar login y proceso de checkout exitoso", 11);
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx" , "01_loginExitoso.jpg" );
			System.out.println("iniciamos proceso de compra");
			driver.findElement(By.id("item_0_title_link")).click();
			WebElement nameProduct = driver.findElement(By.cssSelector(".inventory_details_name"));
		    String Title = nameProduct.getText();
		    System.out.println("Seleccionamos el producto: " + Title);
		    Assert.assertEquals("Sauce Labs Bike Light", Title); //Verificamos que el elemento seleccionado sea el correcto
		    driver.findElement(By.name("add-to-cart-sauce-labs-bike-light")).click(); //Seleccionamos boton anadir
		    CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx"  , "02_agregaProductoCarrito.jpg" );
			driver.findElement(By.className("shopping_cart_link")).click();
			//Verificamos cantidad de productos seleccionados
			WebElement Cart = driver.findElement(By.className("cart_quantity"));
			String Carrito = Cart.getText();
			Assert.assertEquals(Carrito, "1");
			driver.findElement(By.id("checkout")).click();
			driver.findElement(By.id("first-name")).sendKeys(name);
			driver.findElement(By.id("last-name")).sendKeys(apellido);
			driver.findElement(By.id("postal-code")).sendKeys(code);
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx"  , "03_completamosCheckout.jpg" );
			driver.findElement(By.id("continue")).click();
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx"  , "04_overviewCheckout.jpg" );
			driver.findElement(By.id("finish")).click();
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx" , "05_compraFinalizada.jpg" );
			WebElement checkoutFinish = driver.findElement(By.className("complete-header"));
		    String finish = checkoutFinish.getText();
		    System.out.println(finish);
		    Assert.assertEquals(finish, finish); ////Verificamos que se visualice el texto correcto
	
			}
	

			else if (escenario.equals("Invalido")) {
			CapturaEvidencia.escribirTituloEnDocumento("CompraCompleta", "TEST CASE 01:Login Invalido por usuario y pass incorrecto", 11);
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx"  , "loginFailUserPass.jpg" );
			System.out.println("Test Case: Login Invalido por usuario y pass incorrecto");
			String errorMessage = driver.findElement(By.className("error-message-container")).getText();
			Assert.assertEquals(errorMessage, "Epic sadface: Username and password do not match any user in this service");
			System.out.println("Test Pass");
		
			}
			else  if (escenario.equals("ErrorUser")) {
			CapturaEvidencia.escribirTituloEnDocumento("CompraCompleta", "TEST CASE 02:Login Invalido por campo usuario vacio", 11);
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx"  , "loginFailUser.jpg" );
			System.out.println("Test Case: Login Invalido por usuario vacio");
			String errorMessage = driver.findElement(By.className("error-message-container")).getText();
			Assert.assertEquals(errorMessage, "Epic sadface: Username is required");
			System.out.println("Test Pass");
		
			}
		
			  else if (escenario.equals("ErrorPass")) {
			CapturaEvidencia.escribirTituloEnDocumento("CompraCompleta", "TEST CASE 03:Login Invalido por campo pass vacio", 11);
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx"  , "loginFailPass.jpg" );
			System.out.println("Test Case: Login Invalido por password vacio");
			String errorMessage = driver.findElement(By.className("error-message-container")).getText();
			Assert.assertEquals(errorMessage, "Epic sadface: Password is required");
			System.out.println("Test Pass");
			}
			 else if (escenario.equals("Locked")) {
			CapturaEvidencia.escribirTituloEnDocumento("CompraCompleta", "TEST CASE 04:Login Invalido por usuario bloqueado", 11);
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx"  , "loginFailBloqueado.jpg" );
			System.out.println("Test Case: Login invalido por usuario bloqueado");
			String errorMessage = driver.findElement(By.className("error-message-container")).getText();
			Assert.assertEquals(errorMessage, "Epic sadface: Sorry, this user has been locked out.");
			System.out.println("Test Pass");
			}
			 else if (escenario.equals("ProblemUser")) {
			{  //Bloque de codigo que sera ejecuto SI la condicion es FALSA
			CapturaEvidencia.escribirTituloEnDocumento("CompraCompleta", "TEST CASES 5: LOGIN INCORRECTO", 11);
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence, "CompraCompleta" , "01_loginFail.jpg" );
			CapturaEvidencia.escribirTituloEnDocumento("CompraCompleta", "TEST CASE 04:Login Invalido por usuario bloqueado", 11);
			CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence + "img.png", RouteEvidence + "CompraCompleta.docx"  , "loginFailBloqueado.jpg" );
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
			String errorMessage = driver.findElement(By.className("error-message-container")).getText();
			Assert.assertEquals(errorMessage, "Epic sadface: Username and password do not match any user in this service");
			System.out.println(errorMessage);//Validamos existencia del popup de error. Seria mejor el mensaje de error en si pero no consegui el elemento correcto
			System.out.println("Login Invalido");
		    //else  {
			//CapturaEvidencia.escribirTituloEnDocumento("CompraCompleta", "TEST CASE 01:Login Invalido por usuario y pass incorrecto", 11);
			//CapturaEvidencia.capturarPantallaEnDocumento(driver, RouteEvidence, "CompraCompleta" , "loginFailUserPass.jpg" );
			//System.out.println("Escenario: Login Invalido");
			//String errorMessage = driver.findElement(By.className("error-message-container")).getText();
			//Assert.assertEquals(errorMessage, "Epic sadface: Username and password do not match any user in this service");
			//System.out.println(errorMessage);
			//System.out.println("Test Pass");
			 
				
			}
	}

}

	
	@DataProvider(name="Datos Order")
	public Object[][] datosCompra() throws Exception {
			 return DatosExcel.leerExcel("..\\SauceDemo\\Datos\\Datos_Order.xlsx", 
			"Datos");

  }

	@AfterSuite
	public void cerrarNavegador() {
		driver.close();

  }	
}