package com.opencart.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.opencart.base.BasePage;

public class LoginPage extends BasePage
{

	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	//Locators
	
	@FindBy(id="input-email")
	private WebElement emailAddressTextField;
	
	@FindBy(id="input-password")
	private WebElement passwordTextField;
	
	@FindBy(xpath="//input[@value='Login']")
	private WebElement loginButton;
	
	@FindBy(xpath="//div[@class='alert alert-danger alert-dismissible']")
	private WebElement invalidLoginErrorMessageElement;
	
	@FindBy(linkText="Forgotten Password")
	private WebElement forgottenPasswordLink;

	
	//Actions
	public void setEmailAddress(String loginEmailAddress)
	{
		sendData(emailAddressTextField, loginEmailAddress);
	}
	
	public void setPassword(String loginPassword)
	{
		sendData(passwordTextField, loginPassword);
	}
	
	public void clickOnLoginButton()
	{
		click(loginButton);
	}
	
	public String getInvalidLoginErrorMessage()
	{
		
		String invalidLoginErrorMessage=getTextContentUsingJavascript(invalidLoginErrorMessageElement).trim().toLowerCase();
		return invalidLoginErrorMessage;
	}
	
	public void clickOnForgottenPassword()
	{
		click(forgottenPasswordLink);
	}
		
	public void doLogin(String emailAddress, String password)
	{
		setEmailAddress(emailAddress);
		setPassword(password);
		clickOnLoginButton();
	}
	
	
}



