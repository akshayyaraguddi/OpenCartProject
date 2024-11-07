package com.opencart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.opencart.base.BasePage;

public class RegistrationPage extends BasePage
{
	public RegistrationPage(WebDriver driver)
	{
		super(driver);
	}
	
	//Locators
	@FindBy(id="input-firstname")
	private WebElement firstNameTextField;
	
	@FindBy(id="input-lastname")
	private WebElement lastNameTextField;
	
	@FindBy(id="input-email")
	private WebElement emailIdTextField;
	
	@FindBy(id="input-telephone")
	WebElement telephoneTextField;
	
	@FindBy(id="input-password")
	private WebElement passwordTextField;
	
	@FindBy(id="input-confirm")
	private WebElement confirmPasswordTextField;
	
	@FindBy(xpath="//input[@name='agree']")
	private WebElement privacyAgreeCheckBox;
	
	@FindBy(xpath="//input[@value='Continue']")
	private WebElement continueButton;
	
	@FindBy(xpath="//div[@class='pull-right']//a")
	private WebElement continueButtonAfterRegistration;
	
	@FindBy(xpath="//div[@id='content']//h1")
	private WebElement accountCreatedText;
	
	@FindBy(xpath="//div[@class='alert alert-danger alert-dismissible']")
	private WebElement emailAlreadyExistsElement;

	@FindBy(xpath="//div[@class='text-danger']")
	private WebElement passwordTextFieldErrorMessageElement;
	
	@FindBy(xpath="//b[normalize-space()='Privacy Policy']")
	private WebElement privacyPolicyLink;
	
	@FindBy(className="modal-content")
	private WebElement privacyPolicyPopup;
	
	@FindBy(xpath="//div[@class='modal-body']//p")
	private WebElement privacyPolicyContentElement;
	
	
	//Actions
	public void setFirstName(String firstName)
	{
		sendData(firstNameTextField, firstName);
	}
	
	public void setLastName(String lastName)
	{
		sendData(lastNameTextField, lastName);
	}
	
	public void setEmailId(String emailId)
	{
		sendData(emailIdTextField, emailId);
		
	}
	
	public void setTelephoneNumber(String telephoneNumber)
	{
		sendData(telephoneTextField, telephoneNumber);
	}
	
	public void setPassword(String password)
	{
		sendData(passwordTextField, password);
	}
	
	public void setConfirmPassword(String confirmPassword)
	{
		sendData(confirmPasswordTextField, confirmPassword);
	}
	
	public void selectPrivacyPolicy()
	{
		click(privacyAgreeCheckBox);
	}
	
	public void clickOnContinueButton()
	{
		click(continueButton);
	}
	
	public void clickOnContinueButtonAfterRegistration()
	{
		click(continueButtonAfterRegistration);
	}
	
	public String getAccountCreationSuccessMessage()
	{
		String accountCreatedMessage=getText(accountCreatedText);
		return accountCreatedMessage;
	}
	
	public String warningMessageInRegistrationPage()
	{
		return getTextContentUsingJavascript(emailAlreadyExistsElement);
	}
	
	public String getPasswordTextFieldErrorMessage()
	{
		return getText(passwordTextFieldErrorMessageElement);
	}
	
	public void clickOnPrivacyPolicyLink()
	{
		click(privacyPolicyLink);
	}
	public boolean isPrivacyPolicyPopupDisplayed()
	{
		return isDisplayed(privacyPolicyPopup);
	}
	
	public String getPrivacyPolicyContent()
	{
		return getText(privacyPolicyContentElement);
	}
	
	
}
