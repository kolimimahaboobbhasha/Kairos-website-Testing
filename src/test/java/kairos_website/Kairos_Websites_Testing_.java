package kairos_website;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
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
import org.openqa.selenium.chrome.ChromeDriver;
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

	WebDriver driver;
	 ExtentReports extent = new ExtentReports();
	    ExtentTest test;
	JavaUtilities javaUtilitis =new JavaUtilities(); 
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
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
//		String[] urls = {Website_URLs.Customer_Stories.getURL(),Website_URLs.WhitePaper.getURL(),Website_URLs.Blog.getURL(),Website_URLs.News_letter.getURL(),Website_URLs.Overview.getURL(),Website_URLs.Leadership.getURL(),
//				Website_URLs.Life_at_Kairos.getURL(),Website_URLs.Current_openings.getURL(),Website_URLs.Home_page.getURL(),Website_URLs.Products_KiTAP.getURL(),Website_URLs.DQGateway.getURL()
//				,Website_URLs.API_Testing.getURL(),Website_URLs.Resources.getURL(),Website_URLs.Home_page_footer.getURL(),Website_URLs.Total_Quality_Assurance.getURL(),Website_URLs.Mobile_APP_Testing.getURL(),Website_URLs.Data_Analytics_Testing.getURL(),
//				Website_URLs.Salesforce_Testing.getURL(),Website_URLs.CX_Testing.getURL(),Website_URLs.Regression_Testing_Services.getURL(),Website_URLs.Cloud_Testing.getURL(),Website_URLs.Oracle_solutions.getURL(),Website_URLs.Application_Modernization.getURL(),
//				Website_URLs.Intelligent_RPA.getURL(),Website_URLs.Digital_App_Development.getURL()};

        
        List<String> shuffledUrls = new ArrayList<>(List.of(urls));
        Collections.shuffle(shuffledUrls);

       
        // Limit the number of URLs to 3
        List<String> selectedUrls = shuffledUrls.subList(0, Math.min(shuffledUrls.size(), 3));
        StringBuilder htmlTable = new StringBuilder("<table border='1'><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status</th></tr>");

        
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

            // Specify the destination folder and unique file name
            File destinationFolder = new File("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\Reports\\ScreenShots");
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }
            String fileName = "Kairos_website_"+ javaUtilitis.getSystemdate() + ".png";
            File destinationFile = new File(destinationFolder, fileName);

            // Copy the captured screenshot to the destination file
            FileUtils.copyFile(sourceFile, destinationFile);
            String URL = driver.getCurrentUrl();
            String Title = driver.getTitle();
            String DateandTime = javaUtilitis.getSystemdate();
            Capabilities capabilities = ((ChromeDriver) driver).getCapabilities();
            String Browser = capabilities.getBrowserName();
            String BrowserVersion = capabilities.getBrowserVersion();
            Platform deviceos=capabilities.getPlatformName();

            // Append data to the HTML table
            htmlTable.append("<tr>");
            htmlTable.append("<td>").append("Lenovo Thinkpad").append("</td>");
            htmlTable.append("<td>").append(deviceos).append("</td>");
            htmlTable.append("<td>").append(Browser).append("</td>");
            htmlTable.append("<td>").append(BrowserVersion).append("</td>");
            htmlTable.append("<td>").append(URL).append("</td>");
            htmlTable.append("<td>").append(Title).append("</td>");
            htmlTable.append("<td>").append(DateandTime).append("</td>");
            htmlTable.append("<td>").append("Working").append("</td>");
             System.out.println(driver.getTitle()+"    URL :"+driver.getCurrentUrl()); // Print the title of the page
             
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
      

         // Save the workbook to a file
         try (FileOutputStream fileOut = new FileOutputStream("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\kairos.xlsx")) {
             workbook.write(fileOut);
         }
         EmailAttachment attachment = new EmailAttachment();
		    attachment.setPath("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\kairos.xlsx");
	        attachment.setDisposition(EmailAttachment.ATTACHMENT);
	        attachment.setDescription("Report of the test execution");
	   

	        Email email = new MultiPartEmail();
	        email.setHostName("smtp.gmail.com");
	        email.setSmtpPort(587);
	        email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "oqzh ifii yklx zpgn"));
	        email.setSSLOnConnect(true);
	        email.setFrom("kmbb4578@gmail.com");
	        email.setSubject("Kairos Websites Testing");
	       
//	        StringBuilder htmlTable = new StringBuilder("<table border='1'><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status Code</th></tr>");
//	        StringBuilder htmlTable = new StringBuilder("<table border='1'><tr><th>Device</th><th>Device OS</th><th>Browser</th><th>Browser Version</th><th>URL</th><th>Website Page Verified</th><th>Date & Time</th><th>Status Code</th></tr>");

			email.setContent("Hi Team.\r\nKairos Websites are Working Fine"+htmlTable ,"text/html");
	        email.addTo("bhasha.k@kairostech.com");
	       
	       // email.addTo("pranathi.g@kairostech.com");
	      //  email.addTo("kmahaboobbhasha@gmail.com");
	     //   email.addTo("mahaboobbhasha17@gmail.com");
	       // email.addCc("sandhyarani.mandarapu@gmail.com");
	        ((MultiPartEmail) email).attach(attachment);
	        email.send();
        }
	
	@AfterMethod
	public void sendEmail() throws Throwable{
	
	        test.pass("Email sent successfully");

	}
		
		
	
	 public static void checkURLs() {
	        for (String url : urls) {
	            try {
	                URL urlObj = new URL(url);
	                HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
	                connection.setRequestMethod("GET");

	                int responseCode = connection.getResponseCode();
	                if (responseCode == HttpURLConnection.HTTP_OK) {
	                    System.out.println(url + " is reachable.");
	                   
	                } else {
	                    System.out.println(url + " is not reachable. Response Code: " + responseCode);
	                   
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
		 driver.quit();
		extent.flush();
	}
	
	
}
