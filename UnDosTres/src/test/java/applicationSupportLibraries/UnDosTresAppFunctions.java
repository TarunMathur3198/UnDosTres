package applicationSupportLibraries;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import unDosTresTestScripts.*;

public class UnDosTresAppFunctions extends UnDosTres_Recharge_Verification{
	Actions ac = new Actions(driver);

	//Method to select option from top menu of Home page
	public static boolean optionSelection(String strOptionName) throws InterruptedException {
		boolean expected = false;
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

		return expected;
	}

	public static boolean selectOperator(String strOperator) throws InterruptedException {
		boolean expected = false;
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

		return expected;
	}

	public static void highlightElement(WebElement el) throws InterruptedException {
		Thread.sleep(500);
		((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid green'",el);
		Thread.sleep(500);
	}

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
	
	
	public static boolean selectAmount(String strAmount) throws InterruptedException {
		boolean expected = false;
		Actions ac = new Actions(driver);

		WebElement wb = driver.findElement(By.xpath("//div[@class='suggestion']//li[@data-name='"+strAmount+"']"));
		highlightElement(wb);

		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",wb);
		ac.moveToElement(wb).click().build().perform();
		return expected;
	}
	
	public static boolean enterCardDetails(String strLabel,String strValue) {
		boolean expected = false;
		
		
		return expected;
	}
	
	public static boolean loginPopupEnterValue(String strLabel,String strValue) throws InterruptedException {
		boolean expected = false;
		Actions ac = new Actions(driver);

		WebElement wb = driver.findElement(By.xpath("//form[@id='loginForm']//input[@name='"+strLabel+"']"));
		ac.click(wb).build().perform();
		highlightElement(wb);
		Thread.sleep(1500);
		wb.sendKeys(strValue);
		Thread.sleep(2500);
		wb.sendKeys(Keys.ENTER);
		expected = true;
		
		return expected;
	}
	
	
	
}