package frameworkSupportLibraries;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import applicationSupportLibraries.Report;
import applicationSupportLibraries.UnDosTresAppFunctions;
import unDosTresTestScripts.UnDosTres_Recharge_Verification;

public class DriverClass {
	public static WebDriver driver;
	public String className = null;
	public String timeStamp = null;
	public String url;
	@BeforeTest
	public void methodPath() {
		className = this.getClass().getSimpleName();
		timeStamp = new SimpleDateFormat("YYYMMdd_HHmmss").format(Calendar.getInstance().getTime());
		
	}
	Report report = new Report();
	@BeforeClass
	public void setWebDriverInitialization() throws Exception{
		Properties obj1 = new Properties();
		FileInputStream objFile = new FileInputStream(System.getProperty("user.dir")+"\\Config.properties");
		obj1.load(objFile);
		String browser = obj1.getProperty("Browser");
		String chromeDriver = obj1.getProperty("ChromeDriverPath");
		String IEDriver = obj1.getProperty("InternetExplorerDriverPath");
		String firefoxDriver = obj1.getProperty("GeckoDriverPath");
		String EdgeBrowserDriver = obj1.getProperty("EdgeBrowserDriverPath");
		
		switch(browser)
		{
		case "CHROME":
			System.setProperty("webdriver.chrome.driver", chromeDriver);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			url = (ConfigDataUtils.getproperty("URL"));
			driver.get(url);
			UnDosTresAppFunctions.driver = driver;
			break;
		
		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver", firefoxDriver);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			url = (ConfigDataUtils.getproperty("URL"));
			driver.get(url);
			UnDosTresAppFunctions.driver = driver;
			break;
		
		case "INTERNET EXPLORER":
			System.setProperty("webdriver.ie.driver", IEDriver);
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			url = (ConfigDataUtils.getproperty("URL"));
			driver.get(url);
			UnDosTresAppFunctions.driver = driver;
			break;
		
		case "EDGE":
			System.setProperty("webdriver.edge.driver", EdgeBrowserDriver);
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			url = (ConfigDataUtils.getproperty("URL"));
			driver.get(url);
			UnDosTresAppFunctions.driver = driver;
			break;
		
		default:
			System.out.println("Unsupported browser!");
		
		}
		report.startReportSession(className,timeStamp);
		UnDosTresAppFunctions.driver = driver;
		UnDosTres_Recharge_Verification.driver =driver;
		CommonFunctions.driver = driver;
	}
	
	public static WebDriver getWebDriver() {
		return driver;
	}
	
	
	@AfterTest
	public void endReport() throws InterruptedException {
		driver.quit();
		report.flushReport();
		Thread.sleep(2000);
		report.zipFiles();
		Thread.sleep(2000);
		report.ReportOverAllExecutionStatus();
	}
	
	
}
