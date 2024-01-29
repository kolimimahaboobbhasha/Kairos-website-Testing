package Enums;

public enum Website_URLs {

	Customer_Stories("https://kairostech.com/customer-stories/"), WhitePaper("https://kairostech.com/whitepaper/"),
	Blog("https://kairostech.com/blog/"), News_letter("https://kairostech.com/newsletter/"),
	Overview("https://kairostech.com/about-us/"), Leadership("https://kairostech.com/leadership/"),
	Life_at_Kairos("https://kairostech.com/careers/"), Current_openings("https://kairostech.com/current-openings/"),
	Home_page("https://klabs.kairostech.com/"),
	Products_KiTAP("https://klabs.kairostech.com/kitap-for-total-automation/"),
	DQGateway("https://klabs.kairostech.com/dq-gateway-for-data-quality/"),
	API_Testing("https://klabs.kairostech.com/modern-api-testing-solutions-for-modern-applications/"),
	Resources("https://klabs.kairostech.com/insights/"), contact_us("https://klabs.kairostech.com/contact-us/"),
	Home_page_footer("https://kairostech.com/"), Total_Quality_Assurance("https://kairostech.com/quality-assurance/"),
	Mobile_APP_Testing("https://kairostech.com/mobile-app-testing/"),
	Data_Analytics_Testing("https://kairostech.com/data-analytics-testing/"),
	Salesforce_Testing("https://kairostech.com/salesforce-testing/"), CX_Testing("https://kairostech.com/cx-testing/"),
	Regression_Testing_Services("https://kairostech.com/regression-testing/"),
	Cloud_Testing("https://kairostech.com/cloud-testing/"),
	Oracle_solutions("https://kairostech.com/oracle-solutions/"),
	Application_Modernization("https://kairostech.com/application-modernization/"),

	Intelligent_RPA("https://kairostech.com/robotic-process-automation/"),
	Digital_App_Development("https://kairostech.com/digital-application-development/");
	
	String data;
	Website_URLs(String data)
	{
		this.data=data;
		
	}
	
	public String getURL()
	{
		return data;
	}
}
