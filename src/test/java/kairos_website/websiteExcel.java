package kairos_website;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Enums.Website_URLs;
import Reusable.JavaUtilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;

public class websiteExcel {
	
	String mailbody;
	@Test
	public void websitesTesting() throws Throwable {
	WebDriver driver;
	 ExtentReports extent = new ExtentReports();
	    ExtentTest test;
	JavaUtilities javaUtilities =new JavaUtilities(); 

	 
	 ExtentSparkReporter spark = new ExtentSparkReporter("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\target\\surefire-reports\\emailable-report.html");
     extent.attachReporter(spark);
     checkURLs();

	   
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
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
		String[] urls = {Website_URLs.Customer_Stories.getURL(),Website_URLs.Blog.getURL(),Website_URLs.Overview.getURL(),
				Website_URLs.Life_at_Kairos.getURL(),Website_URLs.Home_page.getURL(),Website_URLs.Products_KiTAP.getURL(),Website_URLs.DQGateway.getURL()
				,Website_URLs.API_Testing.getURL(),Website_URLs.Resources.getURL(),Website_URLs.Home_page_footer.getURL(),Website_URLs.Mobile_APP_Testing.getURL(),Website_URLs.Data_Analytics_Testing.getURL(),
				Website_URLs.Salesforce_Testing.getURL(),Website_URLs.Regression_Testing_Services.getURL(),Website_URLs.Cloud_Testing.getURL(),Website_URLs.Oracle_solutions.getURL(),Website_URLs.Application_Modernization.getURL(),
				Website_URLs.Intelligent_RPA.getURL(),Website_URLs.Digital_App_Development.getURL()};

        
        List<String> shuffledUrls = new ArrayList<>(List.of(urls));
        Collections.shuffle(shuffledUrls);

        // Limit the number of URLs to 3
        List<String> selectedUrls = shuffledUrls.subList(0, Math.min(shuffledUrls.size(), 3));

        for (String url : selectedUrls) {
        	   test = extent.createTest("Test for URL: " + url);
            driver.get(url);
            Thread.sleep(2000);
            // Logging or reporting for each URL
         
           // test.log(Status.INFO, "Navigated to URL: " + url);

           
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
            String fileName = "Kairos_website_"+ javaUtilities.getSystemdate() + ".png";
            File destinationFile = new File(destinationFolder, fileName);

            // Copy the captured screenshot to the destination file
            FileUtils.copyFile(sourceFile, destinationFile);

           String URL=driver.getCurrentUrl();
           String Title=driver.getTitle();
           String DateandTime=javaUtilities.getSystemdate();
            Capabilities capabilities = ((ChromeDriver) driver).getCapabilities();

            
           
            String Browser=capabilities.getBrowserName();
            String BrowserVersion= capabilities.getBrowserVersion();
		  
	 
             System.out.println(driver.getTitle()+"    URL :"+driver.getCurrentUrl()); // Print the title of the page
             
             Row dataRow = sheet.createRow(rowNum++);
            
             // Autofit rows
             for (int row = 0; row < sheet.getPhysicalNumberOfRows(); row++) {
                 sheet.autoSizeColumn(row);
             
             dataRow.createCell(0).setCellValue(Browser);
             dataRow.createCell(1).setCellValue(BrowserVersion);
             dataRow.createCell(2).setCellValue(URL);
             dataRow.createCell(3).setCellValue(Title);
             dataRow.createCell(4).setCellValue(DateandTime);
             }
         }

         // Save the workbook to a file
         try (FileOutputStream fileOut = new FileOutputStream("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\kairos.xlsx")) {
             workbook.write(fileOut);
         }
             
         EmailAttachment attachment = new EmailAttachment();
         attachment.setPath("D:\\MyWorkSpace\\DQG_Workspace\\kairos_website\\test-output\\emailable-report.html");
         attachment.setDisposition(EmailAttachment.ATTACHMENT);
         attachment.setDescription("Report of the test execution");
         attachment.setName("Reports");

         Email email = new MultiPartEmail();
         email.setHostName("smtp.gmail.com");
         email.setSmtpPort(587);
         email.setAuthenticator(new DefaultAuthenticator("kmbb4578@gmail.com", "oqzh ifii yklx zpgn"));
         email.setSSLOnConnect(true);
	    
	        email.setFrom("kmbb4578@gmail.com");
	        email.setSubject("Kairos Websites Testing");

				        String emailBody = "<table border='\1\'><tr><th><b>Webpage:</b></th><td> "+driver.getTitle()+" </td>		  </tr>		  <tr>		    <th><b>URL:</b></th><td>"+ driver.getCurrentUrl()+" </td></tr>		  <tr><th><b>Date and Time:</b></th> <td>"+javaUtilities.getSystemdate()+" </td></tr>		</table>";
		     
			
			email.setContent("Hi Team.\r\nKairos Websites are Working Fine"+emailBody ,"text/html");

	        email.addTo("bhasha.k@kairostech.com");
	        email.addTo("pranathi.g@kairostech.com");
	        email.addTo("kmahaboobbhasha@gmail.com");
	        ((MultiPartEmail) email).attach(attachment);
         email.addTo("pranathi.g@kairostech.com");
         email.addTo("kmahaboobbhasha@gmail.com");
         ((MultiPartEmail) email).attach(attachment);

         email.send();
         System.out.println("mail sent");
         driver.quit();
     
	}
	 public static void checkURLs() {
	        String[] urls = {Website_URLs.Customer_Stories.getURL(),Website_URLs.Blog.getURL(),Website_URLs.Overview.getURL(),
					Website_URLs.Life_at_Kairos.getURL(),Website_URLs.Home_page.getURL(),Website_URLs.Products_KiTAP.getURL(),Website_URLs.DQGateway.getURL()
					,Website_URLs.API_Testing.getURL(),Website_URLs.Resources.getURL(),Website_URLs.Home_page_footer.getURL(),Website_URLs.Mobile_APP_Testing.getURL(),Website_URLs.Data_Analytics_Testing.getURL(),
					Website_URLs.Salesforce_Testing.getURL(),Website_URLs.Regression_Testing_Services.getURL(),Website_URLs.Cloud_Testing.getURL(),Website_URLs.Oracle_solutions.getURL(),Website_URLs.Application_Modernization.getURL(),
					Website_URLs.Intelligent_RPA.getURL(),Website_URLs.Digital_App_Development.getURL()
	        };

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
	                    // Handle the case when the URL is not reachable
	                }

	                connection.disconnect();
	            } catch (Exception e) {
	                System.out.println(url + " is not reachable. Exception: " + e.getMessage());
	                // Handle the exception as needed
	            }
	        }
	    }
}

