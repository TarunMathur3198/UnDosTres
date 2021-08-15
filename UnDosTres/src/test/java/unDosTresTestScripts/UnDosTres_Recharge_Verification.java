package unDosTresTestScripts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import applicationSupportLibraries.UnDosTresAppFunctions;
import frameworkSupportLibraries.*;
import unDosTresObjectRepository.ObjectRepository;
public class UnDosTres_Recharge_Verification {
	public static WebDriver driver;
	
	@Test
	public static void run_UnDosTres_Recharge_Verification() throws InterruptedException {
		boolean expected =false;
		String strTestCaseName = "UnDosTres_Recharge_Verification";
		String strCurrUrl;
		/******************** Test Data ****************************/
		String strOperator ="Telcel";
		String strNumber = "8465433546";
		String strAmount = "10";
		String strUrl = "https://sanbox.undostres.com.mx/payment.php";
		String strCardNum = "1";
		String strDate = "11";
		String strMonth = "2";
		String strCVV = "123";
		String strEmailID = "tset@test.com";
		String strEmailID2 = "automationexcersise@test.com";

		String strPassword = "123456";
		/*******************************************************/
		
		System.setProperty("webdriver.chrome.driver", "D:/Selenium/undostres/chromedriver.exe");
		 driver = new ChromeDriver();
		driver.get("http://sanbox.undostres.com.mx");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);				

		
		driver.switchTo().defaultContent();		//Navigate to URL
		
		expected = UnDosTresAppFunctions.optionSelection(strOperator);
		if(!expected)System.out.println("Option not selected");
		Thread.sleep(2000);
		
		
		//Under Recharge celular select operator, number and amount
		
		//Select operator
		CommonFunctions.clickElement(ObjectRepository.operatorColumn);
		Thread.sleep(2000);
		UnDosTresAppFunctions.selectOperator(strOperator);
		System.out.println("Operator selected");
		
		
		//Enter Mobile Number
		WebElement el = driver.findElement(ObjectRepository.mobileNumColumn);
		el.sendKeys(strNumber);
		System.out.println("Mobile number selected");
		
		
		// Select amount
		Thread.sleep(2000);
		CommonFunctions.clickElement(ObjectRepository.cellularAmountColumn);
		UnDosTresAppFunctions.selectAmount(strAmount);
		System.out.println("amount selected");
		//Verify next screen is received after clicking on  'siguiente'
		
		
		UnDosTresAppFunctions.clickButton("Siguiente");
		System.out.println("Clicked on siguiente");
		
		Thread.sleep(5000);
		 strCurrUrl = driver.getCurrentUrl();
		 if(strUrl.equals(strCurrUrl)) {
			 System.out.println("url is passed");
		 }
		 
		 
		 
		 Thread.sleep(5000);
		//Choose Tarjeta
		CommonFunctions.clickElement(ObjectRepository.Tarjeta);
		Thread.sleep(2000);
		// Click on radio button user nueva tarjeta
		
		CommonFunctions.clickElement(ObjectRepository.radioButtonTarjeta);
		Thread.sleep(2000);
	
		//Enter new Credit card details

		driver.findElement(By.xpath("//input[@placeholder='Número de tarjeta']")).sendKeys(strCardNum);
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@placeholder='Mes']")).sendKeys(strMonth);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@placeholder='Año']")).sendKeys(strDate);
		Thread.sleep(2000);

		driver.findElement(By.xpath("//input[@placeholder='Correo electrónico']")).sendKeys(strEmailID);
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//div[@id='new-card-button-desktop']//span[contains(text(),'Pagar con Tarjeta')]")).click();;
		Thread.sleep(2000);
		
		
		UnDosTresAppFunctions.loginPopupEnterValue("email",strEmailID2);
		Thread.sleep(2000);
		UnDosTresAppFunctions.loginPopupEnterValue("email",strPassword);


	
	}
}
