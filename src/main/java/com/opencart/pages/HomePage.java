package com.opencart.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.opencart.base.BasePage;

import java.util.List;

public class HomePage extends BasePage
{
	public HomePage(WebDriver driver)
	{
		super(driver);
	}
	
	//Locators
	
	
	
	@FindBy(xpath="//img[@title='naveenopencart']")
	private WebElement openCartLogo;
	
	@FindBy(xpath="//div[@id='content']/div[2]/div")
	private List<WebElement> featuredSectionCardsList; 
	
	@FindBy(xpath="//span[normalize-space()='My Account']")
	private WebElement myAccountLink;
	
	@FindBy(xpath="//a[normalize-space()='Register']")
	private WebElement regsiterLink;
	
	@FindBy(xpath="//a[normalize-space()='Login']")
	private WebElement loginLink;
	
	//Actions
	public void clickOnMyAccount()
	{
		click(myAccountLink);
	}
	
	public void clickOnRegisterLink()
	{
		click(regsiterLink);
		
	}
	
	public void clickOnLoginLink()
	{
		click(loginLink);
	}
	
	public boolean isOpencartLogoDisplayed()
	{
		return isDisplayed(openCartLogo);
	}
	
	public int getFeaturedSectionCardsCount() {
		return featuredSectionCardsList.size();
	}
	
	public void navigateToRegisterPage()
	{
		clickOnMyAccount();
		clickOnRegisterLink();
	}
	public void navigateToLoginPage()
	{
		clickOnMyAccount();
		clickOnLoginLink();
	}
	
	
}
