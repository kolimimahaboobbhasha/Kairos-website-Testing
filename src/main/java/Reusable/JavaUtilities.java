package Reusable;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
/* This class contains all the java specicfic methods
 * @Author Bhasha.k */
public class JavaUtilities {
	WebDriver driver;
	/* This method will generate the system date and time
	 * @Param message */
	public String getSystemdate() {
		String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
		return timeStamp;
	}
	 public String IST() {
	        // Set the time zone to IST (Indian Standard Time)
	        ZoneId istZone = ZoneId.of("Asia/Kolkata");

	        // Get the current date and time in the IST time zone
	        LocalDateTime currentDateTime = LocalDateTime.now(istZone);

	        // Define the format you want for the output
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	        // Format and print the current date and time
	        String formattedDateTime = currentDateTime.format(formatter);
	      //  System.out.println("Current Date and Time in IST: " + formattedDateTime);
			return formattedDateTime;
	    }
	 
	public int randomNumber()
	{
  
		Random rand=new Random();
		int randomInRange = rand.nextInt(100);
		return randomInRange;
	}
	
	public String getSystemVersion()
	{
	
		
		  Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
	        return caps.getBrowserVersion();
	      //  String deviceVersion = Build.VERSION.RELEASE;
	}
}
