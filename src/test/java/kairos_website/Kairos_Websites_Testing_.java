package kairos_website;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Enums.Website_URLs;
import Reusable.JavaUtilities;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Kairos_Websites_Testing_ {

	static WebDriver driver;
	ExtentReports extent = new ExtentReports();
	ExtentTest test;
	static JavaUtilities javaUtilitis =new JavaUtilities();
	String URL;
	String Title;
	String DateandTime;
	static String[] urls= {Website_URLs.Customer_Stories.getURL(),Website_URLs.WhitePaper.getURL(),Website_URLs.Blog.getURL(),Website_URLs.News_letter.getURL(),Website_URLs.Overview.getURL(),Website_URLs.Leadership.getURL(),
			Website_URLs.Life_at_Kairos.getURL(),Website_URLs.Current_openings.getURL(),Website_URLs.Home_page.getURL(),Website_URLs.Products_KiTAP.getURL(),Website_URLs.DQGateway.getURL()
			,Website_URLs.API_Testing.getURL(),Website_URLs.Resources.getURL(),Website_URLs.Home_page_footer.getURL(),Website_URLs.Total_Quality_Assurance.getURL(),Website_URLs.Mobile_APP_Testing.getURL(),Website_URLs.Data_Analytics_Testing.getURL(),
			Website_URLs.Salesforce_Testing.getURL(),Website_URLs.CX_Testing.getURL(),Website_URLs.Regression_Testing_Services.getURL(),Website_URLs.Cloud_Testing.getURL(),Website_URLs.Oracle_solutions.getURL(),Website_URLs.Application_Modernization.getURL(),
			Website_URLs.Intelligent_RPA.getURL(),Website_URLs.Digital_App_Development.getURL()};
	@BeforeSuite
	public void setupExtentReports()
	{
		ExtentSparkReporter spark = new ExtentSparkReporter("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\target\\surefire-reports\\emailable-report.html");
		extent.attachReporter(spark);
	}
	@BeforeMethod
	public void checkURLsConnection()
	{
		checkURLs();
	}
	@Test
	public void Websites() throws Throwable
	{

		// Create a new Excel workbook
		Workbook workbook = new XSSFWorkbook();
		// Create a new sheet in the workbook
		Sheet sheet = workbook.createSheet("Websites");

		// Create the header row

		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Browser");
		headerRow.createCell(1).setCellValue("Version");
		headerRow.createCell(2).setCellValue("URL");
		headerRow.createCell(3).setCellValue("Title");
		headerRow.createCell(4).setCellValue("Date and Time");

		int rowNum = 1; // Start from the second row
		// WebDriverManager.chromedriver().setup();
		// driver=new ChromeDriver();
//	    WebDriverManager.edgedriver().setup();
//	    driver=new EdgeDriver();
		// WebDriverManager.firefoxdriver().setup();
		// driver=new FirefoxDriver();
		WebDriverManager.edgedriver().setup();
		driver=new EdgeDriver();
		driver.manage().window().maximize();
		List<String> shuffledUrls = new ArrayList<>(List.of(urls));
		Collections.shuffle(shuffledUrls);
		List<String> selectedUrls = shuffledUrls.subList(0, Math.min(shuffledUrls.size(), 3));
//       StringBuilder htmlTable = new StringBuilder("<table border='2';style='border-collapse: collapse; border: 2px solid black;><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status</th></tr>");
//        StringBuilder htmlTable = new StringBuilder("<table style='border-collapse: collapse; border: 2px solid black;'><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status</th></tr>");
//        StringBuilder htmlTable = new StringBuilder("<table border='2' style='border-collapse: collapse; border: 2px solid black; font-weight: bold;'><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status</th></tr>");

		StringBuilder htmlTable = new StringBuilder("<table border='2' style='border-collapse: collapse; border: 2px solid black;'>");
		htmlTable.append("<tr style='background:#068fc9;'>")
				.append("<th style='font-weight: bold;'>Device</th>")
				.append("<th style='font-weight: bold;'>Device OS</th>")
				.append("<th style='font-weight: bold;'>Browser</th>")
				.append("<th style='font-weight: bold;'>Browser Version</th>")
				.append("<th style='font-weight: bold;'>URL</th>")
				.append("<th style='font-weight: bold;'>Website Page Verified</th>")
				.append("<th style='font-weight: bold;'>Date & Time</th>")
				.append("<th style='font-weight: bold;'>Status</th>")
				.append("</tr>");
		System.out.println("Testing Urls");
		for (String url : selectedUrls) {
			test = extent.createTest("Test for URL: " + url);
			driver.get(url);
			Thread.sleep(2000);
			String ActualURL=driver.getCurrentUrl();
			Assert.assertEquals(url, ActualURL);
			test.pass("Page loaded successfully");
			WebElement acceptall = driver.findElement(By.xpath("//button[text() ='Accept All']"));
			if(acceptall.isDisplayed())
			{
				acceptall.click();
			}

			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
			File destinationFolder = new File("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ScreenShots");
			if (!destinationFolder.exists()) {
				destinationFolder.mkdirs();
			}
			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();

			String fileName = "Kairos_website_"+ javaUtilitis.getSystemdate() + ".png";
			File destinationFile = new File(destinationFolder, fileName);
			FileUtils.copyFile(sourceFile, destinationFile);
			String URL = driver.getCurrentUrl();
			String Title = driver.getTitle();
			String DateandTime = javaUtilitis.IST();
			Capabilities capabilities = ((EdgeDriver) driver).getCapabilities();
			String Browser = capabilities.getBrowserName();
			String BrowserVersion = capabilities.getBrowserVersion();
			Platform deviceos=capabilities.getPlatformName();
			String osName = System.getProperty("os.name");
			String computerName = System.getenv("COMPUTERNAME");

			//status code

			String status = urls.equals(responseCode) ? "Not Working" : " Working";
			htmlTable.append("<tr>");
			htmlTable.append("<td>").append("Lenovo Thinkpad").append("</td>");
			htmlTable.append("<td>").append(osName).append("</td>");
			htmlTable.append("<td>").append(Browser).append("</td>");
			htmlTable.append("<td>").append(BrowserVersion).append("</td>");
			htmlTable.append("<td>").append(URL).append("</td>");
			htmlTable.append("<td style='color:#fa8405'>").append(Title).append("</td>");

			// htmlTable.append("<td>").append(Title).append("</td>");
			htmlTable.append("<td style='color:#0925db;'>").append(DateandTime).append("</td>");

//            htmlTable.append("<td>").append(status).append("</td>");
			htmlTable.append("<td style='color: " + (urls.equals(responseCode) ? "red" : "green") + ";'>").append(status).append("</td>");
			System.out.println(driver.getTitle() + ":" + driver.getCurrentUrl()+"    Status Code :"+responseCode); // Print the title of the page
//            int statusCode = checkURLs();

//             System.out.println(driver.getTitle()+":"+driver.getCurrentUrl()); // Print the title of the page

			Row dataRow = sheet.createRow(rowNum++);
			for (int row = 0; row < sheet.getPhysicalNumberOfRows(); row++) {
				sheet.autoSizeColumn(row);
				dataRow.createCell(0).setCellValue(Browser);
				dataRow.createCell(1).setCellValue(BrowserVersion);
				dataRow.createCell(2).setCellValue(URL);
				dataRow.createCell(3).setCellValue(Title);
				dataRow.createCell(4).setCellValue(DateandTime);
			}
			htmlTable.append("</tr>");
		}
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.gmail.com"); // Set your SMTP server
		email.setSmtpPort(587); // Set your SMTP port
		email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "mhch lpyv zjei snqg")); // Set your email credentials
		email.setSSLOnConnect(true); // Enable SSL (if required)

		email.setFrom("kmbb4578@gmail.com");
		email.setSubject("Kairos Website Testing Report");
		// Embed the HTML table into the email
		// email.setHtmlMsg(htmlTable.toString());
		StringBuilder emailContent = new StringBuilder("Hi Team.\r\nKairos Website is Working Fine");
		emailContent.append(htmlTable);
		email.setContent("Hi Team.\r\nKairos Website is Working Fine"+htmlTable ,"text/html");
		// email.addCc("prasad.k@kairostech.com");
		email.addCc("bhasha.k@kairostech.com");
		email.addTo("durgaprasad.b@kairostech.com");
		email.addCc("mounika.t@kairostech.com");

		// email.addCc("kmahaboobbhasha@gmail.com");
		// email.addCc("pranathi.g@kairostech.com");


		//   email.addTo("mahaboobbhasha17@gmail.com");


		// Send the email
		email.send();


		try (FileOutputStream fileOut = new FileOutputStream("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\kairos.xlsx")) {
			workbook.write(fileOut);
		}
//		   WebDriver driver = new FirefoxDriver();
//
//	        StringBuilder htmlTable = new StringBuilder("<table border='2' style='border-collapse: collapse; border: 2px solid black;'>");
//	        htmlTable.append("<tr style='background:#068fc9;'>")
//	                .append("<th style='font-weight: bold;'>Device</th>")
//	                .append("<th style='font-weight: bold;'>Device OS</th>")
//	                .append("<th style='font-weight: bold;'>Browser</th>")
//	                .append("<th style='font-weight: bold;'>Browser Version</th>")
//	                .append("<th style='font-weight: bold;'>URL</th>")
//	                .append("<th style='font-weight: bold;'>Website Page Verified</th>")
//	                .append("<th style='font-weight: bold;'>Date & Time</th>")
//	                .append("<th style='font-weight: bold;'>Status</th>")
//	                .append("</tr>");
//	        List<String> shuffledUrls = new ArrayList<>(List.of(urls));
//	        Collections.shuffle(shuffledUrls);
//	        List<String> selectedUrls = shuffledUrls.subList(0, Math.min(shuffledUrls.size(), 3));
//
//	        for (String url : selectedUrls) {
//	            String fileName = null;
//	            try {
//	                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//	                connection.setRequestMethod("GET");
//	                int responseCode = connection.getResponseCode();
//	                System.out.println(driver.getTitle()+ " responseCode: " + responseCode);
//	                driver.get(url);
//	                connection.disconnect();
//	                Thread.sleep(2000);
//	                String ActualURL = driver.getCurrentUrl();
////	                Assert.assertEquals(url, ActualURL);
//
////	                WebElement acceptall = driver.findElement(By.xpath("//button[text() ='Accept All']"));
////	                if (acceptall.isDisplayed()) {
////	                    acceptall.click();
////	                } else {
//
//	                    TakesScreenshot screenshot = (TakesScreenshot) driver;
//	                    File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
//	                    fileName = url.contains("invalidurl") ? "Kairos_website_failed_URLS" : "Kairos_website";
//	                    fileName += "_" + javaUtilitis.getSystemdate() + ".png";
//	                    File destinationFolder = new File(url.contains("invalidurl") ?
//	                            "C://DQG//Kairoswebsites//Reports//ScreenShots//failedUrls" :
//	                            "C://DQG//Kairoswebsites//Reports//ScreenShots//PassedUrls");
//	                    if (!destinationFolder.exists()) {
//	                        destinationFolder.mkdirs();
//	                    }
//	                    File destinationFile = new File(destinationFolder, fileName);
//	                    FileUtils.copyFile(sourceFile, destinationFile);
//
//	                    String URL = driver.getCurrentUrl();
//	                    String Title = driver.getTitle();
//	                    String DateandTime = javaUtilitis.IST();
//	                    Capabilities capabilities = ((FirefoxDriver) driver).getCapabilities();
//	                    String Browser = capabilities.getBrowserName();
//	                    String BrowserVersion = capabilities.getBrowserVersion();
//	                    Platform deviceos = capabilities.getPlatformName();
//	                    String osName = System.getProperty("os.name");
//
//	                    String status = responseCode != HttpURLConnection.HTTP_OK ? "Not Working" : "Working";
//
//	                    htmlTable.append("<tr>");
//	                    htmlTable.append("<td>").append("Lenovo Thinkpad").append("</td>");
//	                    htmlTable.append("<td>").append(osName).append("</td>");
//	                    htmlTable.append("<td>").append(Browser).append("</td>");
//	                    htmlTable.append("<td>").append(BrowserVersion).append("</td>");
//	                    htmlTable.append("<td>").append(URL).append("</td>");
//	                    htmlTable.append("<td style='color:#fa8405'>").append(Title).append("</td>");
//	                    htmlTable.append("<td style='color:#0925db;'>").append(DateandTime).append("</td>");
//	                    htmlTable.append("<td style='color:").append(status.equals("Not Working") ? "red" : "green").append(";'>").append(status).append("</td>");
//	                    htmlTable.append("</tr>");
//
////	                sendEmail(htmlTable.toString());
//	                }
//	            catch (IOException e) {
//	                System.out.println("Error: " + e.getMessage());
//
//	                TakesScreenshot screenshot = (TakesScreenshot) driver;
//	                File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
//	                File destinationFolder = new File("C://DQG//Kairoswebsites//Reports//ScreenShots//failedUrls");
//	                if (!destinationFolder.exists()) {
//	                    destinationFolder.mkdirs();
//	                }
//	                fileName += "_" + javaUtilitis.getSystemdate() + ".png";
//	                File destinationFile = new File(destinationFolder, fileName);
//	                FileUtils.copyFile(sourceFile, destinationFile);
//	                HtmlEmail email = new HtmlEmail();
//	                email.setHostName("smtp.gmail.com");
//	                email.setSmtpPort(587);
//	                email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "ntto lwsv rfwa plhk"));
//	                email.setSSLOnConnect(true);
//
//	                email.setFrom("kmbb4578@gmail.com");
//	                email.setSubject("Kairos Website Testing Report Error");
//	                email.setContent("Hi Team.\r\nKairos Website is not working:<br>" +url+ e.getMessage(), "text/html");
//	                email.addCc("prasad.k@kairostech.com");
//	    	        email.addCc("bhasha.k@kairostech.com");
//	    	        email.addCc("pranathi.g@kairostech.com");
//                    email.addCc("kmahaboobbhasha@gmail.com");
//	     	      	email.addTo("durgaprasad.b@kairostech.com");
////	                email.addCc("praneeth.k@kairostech.com");
//	                email.send();
//	            }
//
//	        }
//	        sendEmail(htmlTable.toString());
//
//	    }
//
//	    public static void sendEmail(String htmlContent) throws EmailException {
//	        HtmlEmail email = new HtmlEmail();
//	        email.setHostName("smtp.gmail.com");
//	        email.setSmtpPort(587);
//	        email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "ntto lwsv rfwa plhk"));
//	        email.setSSLOnConnect(true);
//
//	        email.setFrom("kmbb4578@gmail.com");
//	        email.setSubject("Kairos Website Testing Report");
//	        email.setContent("Hi Team.\r\nKairos Websites Testing Report:<br>" + htmlContent, "text/html");
////	        email.addCc("prasad.k@kairostech.com");
//	        email.addCc("bhasha.k@kairostech.com");
////	        email.addCc("pranathi.g@kairostech.com");
//           email.addCc("kmahaboobbhasha@gmail.com");
//// 	      	email.addTo("durgaprasad.b@kairostech.com");
//
//	        email.send();

	}

	@AfterMethod
	public void closeBrowser() {
		if (driver != null) {
			driver.quit(); // Close the browser instance
		}
	}


	public static void checkURLs() {
		for (String url : urls) {
			try {
				URL urlObj = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
				connection.setRequestMethod("GET");
				int responseCode = connection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
//	                    System.out.println(url + " is reachable.");
					System.out.println(url+"  Status Code :"+responseCode + " is reachable.");

				} else {
					System.out.println(url + " is not reachable. Response Code: " + responseCode);
//	                    TakesScreenshot screenshot = (TakesScreenshot) driver;
//		                File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
//		                File destinationFolder = new File("C://DQG//Kairoswebsites//Reports//ScreenShots//failedUrls");
//		                if (!destinationFolder.exists()) {
//		                    destinationFolder.mkdirs();
//		                }
//		                fileName += "_" + javaUtilitis.getSystemdate() + ".png";
//		                File destinationFile = new File(destinationFolder, fileName);
//		                FileUtils.copyFile(sourceFile, destinationFile);
					HtmlEmail email = new HtmlEmail();
					email.setHostName("smtp.gmail.com");
					email.setSmtpPort(587);
					email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "mhch lpyv zjei snqg"));
					email.setSSLOnConnect(true);

					email.setFrom("kmbb4578@gmail.com");
					email.setSubject("Kairos Website Testing Report Error");
					email.setContent("Hi Team.\r\nKairos Website is not working:<br>" +url + " is not reachable. Response Code: " + responseCode, "text/html");
					email.addCc("prasad.k@kairostech.com");
					email.addCc("bhasha.k@kairostech.com");
					email.addCc("pranathi.g@kairostech.com");
					email.addCc("kmahaboobbhasha@gmail.com");
					email.addTo("durgaprasad.b@kairostech.com");
//		                email.addCc("praneeth.k@kairostech.com");
					email.send();


				}

				connection.disconnect();
			} catch (Exception e) {
				System.out.println(url + " is not reachable. Exception: " + e.getMessage());

			}
		}
		System.out.println("Total Urls:"+urls.length);
	}
	@AfterSuite
	public void teardownExtentreports()
	{

		extent.flush();
	}


}
