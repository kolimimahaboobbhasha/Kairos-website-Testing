package Enums;

public enum Browsers {
	
	BROWSER("CHROME"),
	Cloud_Testing("https://kairostech.com/cloud-testing/");
String data;
	Browsers(String data)
	{
		this.data=data;
	}
	
	public String getdata()
	{
		return data;
	}
	

}
