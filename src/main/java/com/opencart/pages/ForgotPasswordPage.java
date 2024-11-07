package com.opencart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.opencart.base.BasePage;

public class ForgotPasswordPage extends BasePage
{
 public ForgotPasswordPage(WebDriver driver)
 {
	 super(driver);
 }
 
 //Locators
  @FindBy(id="input-email")
  private WebElement emailAddressTextField;
  
  @FindBy(xpath="//input[@value='Continue']")
  private WebElement continueButton;
 
 @FindBy(xpath="//div[@class='alert alert-success alert-dismissible']")
 private WebElement emailSentConfirmationElement;
 
 @FindBy(xpath="//div[@class='alert alert-danger alert-dismissible']")
 private WebElement invalidEmailErrorElement;
 
 //Actions
 
 public void setEmailAddress(String emailAddress)
 {
	 sendData(emailAddressTextField, emailAddress);
 }
 
 public void clickOnContinueButton()
 {
	 click(continueButton);
 }
 
 public String getInvalidEmailErrorMessage()
 {
	 return getTextContentUsingJavascript(invalidEmailErrorElement);
 }
 
 public String getEmailSentSuccessMessage()
 {
	 return getTextContentUsingJavascript(emailSentConfirmationElement);
 }
 
}
