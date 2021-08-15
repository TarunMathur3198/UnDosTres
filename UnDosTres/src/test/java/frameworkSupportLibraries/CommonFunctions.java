package frameworkSupportLibraries;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import unDosTresTestScripts.UnDosTres_Recharge_Verification;

public class CommonFunctions extends UnDosTres_Recharge_Verification{

	public static WebElement wb;

	public static boolean clickElement(final By locator) {
		try {
			WebElement el= findWithFluentWait(locator);
			Actions actions = new Actions(driver);
			actions.moveToElement(el).click().build().perform();
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public static WebElement findWithFluentWait(final By locator) {
		try {
			Thread.sleep(2000);
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.withTimeout(Duration.ofSeconds(30))
					.pollingEvery(Duration.ofSeconds(1))
					.ignoring(Exception.class);
			wb = wait.until(new com.google.common.base.Function<WebDriver, WebElement>(){
				public WebElement apply(WebDriver driver) {
					WebElement findElement = driver.findElement(locator);
					if(findElement.isDisplayed())
						return findElement;
					return findElement;
				}
			});

		}
		catch(Exception e) {

		}

		return wb;
	}

}
