package applicationSupportLibraries;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import frameworkSupportLibraries.CommonFunctions;
import unDosTresTestScripts.*;

public class UnDosTresAppFunctions extends UnDosTres_Recharge_Verification{
	Actions ac = new Actions(driver);

	//Method to select option from top menu of Home page
	public static boolean optionSelection(String strOptionName) throws InterruptedException {
		boolean expected = false;
		
		try {
		List<WebElement> wb = driver.findElements(By.xpath("//div[@class='col-sm-12 menulist selector ']//a//div//span[@class='menu-text']"));

		for ( WebElement it: wb) { 
			if ( it.getText().contains( strOptionName ) ) 
			{ 
				highlightElement(it);

				it.click();
				expected = true;
				break;
			}
		}
		}catch(Exception e) {
			expected = false;
		}
		return expected;
	}

	// Method to select Operator
	public static boolean selectOperator(String strOperator) throws InterruptedException {
		boolean expected = false;
		
		try {
		List<WebElement> wb = driver.findElements(By.xpath("//div[@class='perform']//div[@class='suggestion']//li"));

		for ( WebElement it: wb) { 
			if ( it.getText().contains( strOperator ) ) 
			{ 
				highlightElement(it);

				it.click();
				expected = true;
				break;
			}
		}
		}catch(Exception e) {
			return false;
		}
		return expected;
	}

	// Method to highlight any web element
	public static void highlightElement(WebElement el) throws InterruptedException {
		Thread.sleep(500);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid green'",el);
		Thread.sleep(500);
	}

	// Method to click a button
	public static boolean clickButton(String strButtonName) throws InterruptedException {
		boolean expected = false;
		Actions ac = new Actions(driver);
		Thread.sleep(2500);
		try {
			WebElement buttonEl = driver.findElement(By.xpath("//button[contains(text(),'"+strButtonName+"')][1]"));
			if(buttonEl.isDisplayed()) {
				highlightElement(buttonEl);

				((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",buttonEl);
				ac.moveToElement(buttonEl).click().build().perform();
			}
			expected = true;
		}
		catch(Exception e){
			expected = false;
		}
		return expected;
	}
	
	// Select any amount from given dropdown
	public static boolean selectAmount(String strAmount) throws InterruptedException {
		boolean expected = false;
		
		Actions ac = new Actions(driver);
		try {
		WebElement wb = driver.findElement(By.xpath("//div[@class='suggestion']//li[@data-name='"+strAmount+"']"));
		highlightElement(wb);

		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",wb);
		ac.moveToElement(wb).click().build().perform();
		}catch(Exception e){
			expected = false;
		}
		return expected;
	}
	
	// Generic method to enter card details
	public static boolean enterCardDetails(String strLabel,String strValue) {
		boolean expected = false;
		Actions ac = new Actions(driver);
		try {
		
		WebElement we = CommonFunctions.findWithFluentWait(By.xpath("//input[@placeholder='"+strLabel+"']"));
		ac.click(we).build().perform();
		highlightElement(we);
		Thread.sleep(1500);
		Thread.sleep(1000);
		we.sendKeys(strValue);
		expected = true;
		}catch(Exception e){
			expected = false;
			e.printStackTrace();
		}
		
		return expected;
	}
	
	// Method to enter values in login popup
	public static boolean loginPopupEnterValue(String strLabel,String strValue) throws InterruptedException {
		boolean expected = false;
		Actions ac = new Actions(driver);
		try {
		WebElement wb = CommonFunctions.findWithFluentWait(By.xpath("//form[@id='loginForm']//input[@name='"+strLabel+"']"));
		ac.click(wb).build().perform();
		highlightElement(wb);
		Thread.sleep(1500);
		wb.sendKeys(strValue);
		Thread.sleep(2500);
		wb.sendKeys(Keys.ENTER);
		expected = true;
		}
		catch(Exception e) {
			expected = false;
		}
		
		return expected;
	}
	
	
	
}
