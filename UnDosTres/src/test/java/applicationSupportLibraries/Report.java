package applicationSupportLibraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.testng.Assert;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import frameworkSupportLibraries.CommonFunctions;
 

public class Report {

	static ExtentHtmlReporter htmlReporter;
	static ExtentReports extent;
	static ExtentTest logger;
	public static String dir = null;
	public static String dirScr = null;
	public static String path = null;
	public static String zipdir = null;
	static int AssertFailCount = 0;
	
	// Method to initialize the report
	public void startReportSession(String className, String timeStamp) {
		String workingDirectory = System.getProperty("user.dir");
		dir = workingDirectory +File.separator+"Report"+File.separator +className + "_" + timeStamp;
		dirScr = dir + File.separator +"Screenshot";
		path = dir+File.pathSeparator +className+"_"+timeStamp+"_TestCase.html";
		zipdir = workingDirectory + File.pathSeparator +"Report" +File.pathSeparator +"Zip";
		
		File file =  new File(dir);
		File file1 = new File(dirScr);
		File file2 = new File(zipdir);
		
		if(!file.exists() && !file1.exists()) {
			file.mkdir();
			file1.mkdir();
		}
		
		
		if(!file2.exists()) {
			file2.mkdir();
		}
	
		htmlReporter = new ExtentHtmlReporter(path);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);		
		
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("UnDosTres");
		htmlReporter.config().setReportName(className);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("dd MMM YYY HH:mm:ss");
		
	}

	// Method to start report
	public static String startReport(String name) {
		logger = extent.createTest(name);
		return name;
	}
	
	// Method to fetch system and link details
	public static void getDashboardDetails(String browserName, String appName,String appUrl, String appEnv,String appUserName) throws java.net.UnknownHostException {
		String systemName;
		try {
			systemName = InetAddress.getLocalHost().getHostName();
			String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			extent.setSystemInfo("Application Name", appName);
			extent.setSystemInfo("Login User", appUserName);
			extent.setSystemInfo("OS Detail", OS);
			extent.setSystemInfo("System Name", systemName);
			extent.setSystemInfo("Browser Details", browserName);
			extent.setSystemInfo("Url", appUrl);
			extent.setSystemInfo("Environment", appEnv);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Log the report
	public static void logReport(int stepNumber, String strPassDescription, String strFailDescription, String screenshot,boolean status) throws IOException {
		if(status) {
			if(screenshot.equalsIgnoreCase("Y")) {
				String val = CommonFunctions.capture(dirScr).toString();
				String Final = "./Screenshot/" +val + ".png";
				logger.log(Status.PASS, "<font style = 'color;LimeGreen'> Step#: "+stepNumber+"<br></font>" + strPassDescription+"<br>");
				MediaEntityBuilder.createScreenCaptureFromPath(Final).build();
			}
			else if(screenshot.equalsIgnoreCase("N")) {
				logger.log(Status.PASS, "<font style = 'color;LimeGreen'> Step#: "+stepNumber+"<br></font>" + strPassDescription+"<br>");
			}
			else {
				logger.log(Status.INFO, strPassDescription);
			}
			
		}
		else {
			String val = CommonFunctions.capture(dirScr).toString();
			String Final = "./Screenshot" + val + ".png";
			logger.log(Status.FAIL, "<font style = 'color;Red'> Step#: "+stepNumber+"<br></font>" + strFailDescription+"<br>");
			MediaEntityBuilder.createScreenCaptureFromPath(Final).build();
			AssertFailCount++;
		}
		
	}
	
	// Method to get Result
	public static void getResult(ITestResult result) {
		if(result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() +"-Test Case failed", ExtentColor.RED));
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() +"-Test Case failed", ExtentColor.RED));
			
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() +"-Test Case skipped", ExtentColor.ORANGE));

		}
	}
	
	// Check if any step has failed
	public static void ReportOverAllExecutionStatus() {
		if(!(AssertFailCount==0)) {
			Assert.fail();
		}
	}
	
	public void flushReport() {
		extent.flush();		
	}

	// Convert into zip files
	public void zipFiles() {
		File directoryToZip = new File(dir);
		List<File> fileList = new ArrayList<File>();
		getAllFiles(directoryToZip,fileList);
		writeZipFile(directoryToZip, fileList);
	}
	
	// Get files by file address
	public static void getAllFiles(File dir, List<File> fileList) {
		try {
			File[] files = dir.listFiles();
			for(File file: files) {
				fileList.add(file);
				if(file.isDirectory()) {
					getAllFiles(file, fileList);
					
				}
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	// Add files inside zip folder
	public static void writeZipFile(File directoryToZip, List<File> fileList) {
		try {
			String zipPath = zipdir + File.separator + directoryToZip.getName() + ".zip";
			FileOutputStream fos = new FileOutputStream(zipPath);
			ZipOutputStream zos = new ZipOutputStream(fos);
			for(File file : fileList) {
				if(!file.isDirectory()) {
					if(!file.isDirectory()) {
						addToZip(directoryToZip,file, zos);
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Add content to zip
	public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() +1,file.getCanonicalPath().length());
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while((length = fis.read(bytes))>=0) {
			zos.write(bytes,0,length);
			
		}
		zos.closeEntry();
		fis.close();
	}

}
