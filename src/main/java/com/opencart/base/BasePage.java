package com.opencart.base;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage 
{
	private WebDriver driver;
	private JavascriptExecutor js;
	protected WebDriverWait wait;

	//Constructor
	public BasePage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		js = (JavascriptExecutor) driver;
		wait=new WebDriverWait(driver, Duration.ofSeconds(20));
		
	}
	
	//JavaScriptMethods
	protected String getTextContentUsingJavascript(WebElement element)
	{
	    String text = (String) js.executeScript("return arguments[0].innerText;", element);
	    return text;
	}
	
	protected void scrollToParticularElement(WebElement element)
	{
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	//Webdriver methods
	public void refresh()
	{
		driver.navigate().refresh();
	}
	
	public void navigateBrowserBack()
	{
		driver.navigate().back();
	}
	
	public void navigateBrowserForward()
	{
		driver.navigate().forward();
	}
	
	public String getTitle()
	{
		String title= driver.getTitle();
		return title;
	}
	
	protected void click(WebElement element)
	{
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
	protected void sendData(WebElement element, String text)
	{
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.clear();
		element.sendKeys(text);
	}
	
	protected String getText(WebElement element)
	{
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.getText();
		
	}
	
	protected boolean isDisplayed(WebElement element)
	{
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		return element.isDisplayed();
		}catch (Exception e) {
		return false;
		}
	}
	

	//
	public static String verifyLinkActive(String linkUrl) throws IOException {
	    String response = ""; // Declare and initialize response at the start
	    
	    try {
	        @SuppressWarnings("deprecation")
			URL url = new URL(linkUrl);
	        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	        httpURLConnection.setConnectTimeout(3000); // Set timeout for 3 seconds
	        httpURLConnection.connect();

	        int responseCode = httpURLConnection.getResponseCode();

	        // Check the response code and assign to response variable
	        if (responseCode == 200) {
	            response = linkUrl + " - " + httpURLConnection.getResponseMessage(); // Successful response
	        } else if (responseCode >= 400) {
	            response = linkUrl + " - " + httpURLConnection.getResponseMessage() + " - " + responseCode; // Error response
	        } else {
	            response = linkUrl + " - " + httpURLConnection.getResponseMessage(); // Other response codes
	        }
	    } catch (Exception e) {
	        response = "Error in connecting to URL: " + linkUrl; // Exception handling
	    }
	    return response;// Return the response
	}


	
	
}
