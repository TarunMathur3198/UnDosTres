package unDosTresTestScripts;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import applicationSupportLibraries.Report;
import applicationSupportLibraries.UnDosTresAppFunctions;
import frameworkSupportLibraries.*;
import unDosTresObjectRepository.ObjectRepository;
public class UnDosTres_Recharge_Verification extends DriverClass {
	public static WebDriver driver;
	
	@Test
	public static void run_UnDosTres_Recharge_Verification() throws InterruptedException, IOException {
		boolean expected =false;
		int failCounter = 0;
		String strTestCaseName = "UnDosTres_Recharge_Verification";
		String strCurrUrl;
		/******************** Test Data ****************************************************************************8*/
	
		String strOperator = ExcelDataUtils.getExcelData("ExecuteData", "Operator", "Test Data", strTestCaseName);
		String strNumber = ExcelDataUtils.getExcelData("ExecuteData", "Number", "Test Data", strTestCaseName);
		String strAmount = ExcelDataUtils.getExcelData("ExecuteData", "Amount", "Test Data", strTestCaseName);
		String strUrl = ExcelDataUtils.getExcelData("ExecuteData", "Url", "Test Data", strTestCaseName);
		String strEmailID2 = ExcelDataUtils.getExcelData("ExecuteData", "EmailID2", "Test Data", strTestCaseName);
		String strNewCardLabels = ExcelDataUtils.getExcelData("ExecuteData", "NewCardLabels", "Test Data", strTestCaseName);
		String strNewCardValues = ExcelDataUtils.getExcelData("ExecuteData", "NewCardValues", "Test Data", strTestCaseName);
		String strPassword = ExcelDataUtils.getExcelData("ExecuteData", "NewPassword", "Test Data", strTestCaseName);
		String strTestObjective = ExcelDataUtils.getExcelData("ExecuteData", "TestObjective", "Test Data", strTestCaseName);
		
		//Snippet To uncomment in case of issue with excel data
//		strNumber = "8465433546";
//		strAmount = "10";
//		strNewCardLabels = "Número de tarjeta,Mes,Año,CVV,Correo electrónico";
//		strNewCardValues = "4111111111111111,11,25,111,test@test.com";
//		strEmailID2 = "automationexcersise@test.com";
//		strPassword = "123456";
		
		String[] strNewCardLabel = strNewCardLabels.split(",");
		String[] strNewCardValue = strNewCardValues.split(",");
		
		
		
		/******************************************************************************************************************/
	
		driver.switchTo().defaultContent();		
		
		//Start Report
		Report.startReport(strTestCaseName+" <h3>OBJECTIVE :"+ strTestObjective + "</h3>");
		
		UnDosTresAppFunctions.optionSelection(strOperator);
		Thread.sleep(2000);
		
		
		//Under Recharge celular select operator, number and amount
		
		//Select operator
		CommonFunctions.clickElement(ObjectRepository.operatorColumn);
		Thread.sleep(2000);
		expected = UnDosTresAppFunctions.selectOperator(strOperator);
		System.out.println("Operator selected");
		Report.logReport(1, "Operator Selected: "+strOperator, "Operator not selected: "+strOperator, "Y", expected);
		
		//Enter Mobile Number
		WebElement el = CommonFunctions.findWithFluentWait(ObjectRepository.mobileNumColumn);
		el.sendKeys(strNumber);
		Report.logReport(2, "Mobile number selected: "+strNumber, "Mobile number not selected: "+strNumber, "Y", expected);

		
		// Select amount
		Thread.sleep(2000);
		CommonFunctions.clickElement(ObjectRepository.cellularAmountColumn);
		UnDosTresAppFunctions.selectAmount(strAmount);
		System.out.println("amount selected");
		Report.logReport(3, "Amount selected "+strAmount, "Amount not selected: "+strAmount, "Y", expected);

		
		// Click on `Siguiente`
		UnDosTresAppFunctions.clickButton("Siguiente");
		Report.logReport(4, "Clicked on siguiente ", "Siguiente is not clicked ", "Y", expected);

		Thread.sleep(5000);
		 
		 // Wait and confirm for the page to be loaded
		 if(CommonFunctions.findWithFluentWait(ObjectRepository.Tarjeta).isDisplayed()) {
				Report.logReport(5, "Page loaded after clicking on Siguiente ", "", "Y", true);
		 }
		 else {
				Report.logReport(5, "", "Page is not loaded after clicking on 'Siguiente ", "Y", false);

		 }
		 
		 
		 
		 Thread.sleep(5000);
		//Choose Tarjeta
		expected = CommonFunctions.clickElement(ObjectRepository.Tarjeta);
		Thread.sleep(2000);
		// Click on radio button user nueva tarjeta
		if(expected) {
		expected=CommonFunctions.clickElement(ObjectRepository.radioButtonTarjeta);
		Thread.sleep(2000);
		Report.logReport(5, "Clicked on radio button after expanding tarjeta dropdown ", "Radio button not clicked after expanding tarjeta", "Y", expected);

		}
		else {
			Report.logReport(5, "", "`Tarjeta` cannot be found", "Y", expected);

		}
		
	 
		
		
		//Enter new Credit card details
		failCounter = 0;
		for(int i=0;i<strNewCardLabel.length;i++) {
			expected = UnDosTresAppFunctions.enterCardDetails(strNewCardLabel[i], strNewCardValue[i]);
			if(!expected) {
				failCounter++;
			}
		}
		if(failCounter>0) {
			expected = false;
		}
		Report.logReport(6, "New Card details have been entered successfully ", "New Card details have not been entered", "Y", expected);


		//Click on Pagar Con Tarjeta
		expected = CommonFunctions.clickElement(ObjectRepository.pagarConTarjeta);
		Report.logReport(7, "Button `Pagar Con Tarjeta` has been clicked after entering card details", "Button `Pagar Con Tarjeta` hasn't been clicked after entering card details", "Y", expected);

		Thread.sleep(2000);
		
		
		// Enter login details in the popup
		UnDosTresAppFunctions.loginPopupEnterValue("email",strEmailID2);
		Thread.sleep(2000);
		expected = UnDosTresAppFunctions.loginPopupEnterValue("password",strPassword);

		Report.logReport(8, "Login details entered successfully", "Login details not entered successfully", "Y", expected);

	
	}
}
