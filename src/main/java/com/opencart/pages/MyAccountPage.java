package com.opencart.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.opencart.base.BasePage;

public class MyAccountPage extends BasePage 
{

	//Constructor
	public MyAccountPage(WebDriver driver)
	{
		super(driver);
	}
	
	//Locators
	
	@FindBy(xpath="//ul[@class='list-unstyled']//a[normalize-space()='My Account']")
	private WebElement myAccountLink;
	
	@FindBy(xpath="//a[@title='My Account']")
	private WebElement myAccountMenuDropdown;
	
	@FindBy(xpath="//h2[normalize-space()='My Account']")
	private WebElement myAccountTitle;
	
	@FindBy(xpath="//a[@class='list-group-item'][normalize-space()='Logout']")
	private WebElement logoutLink;
	
	@FindBy(xpath="//a[normalize-space()='Continue']")
	private WebElement continueToLoginPageButton;
	

	@FindBy(xpath="//*[@id='content']/h2")
	private List<WebElement> myAccountHeaderList;
	
	@FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//li")
	private List<WebElement> myAccountOptionsList;
	
	@FindBy(tagName="a")
	private List<WebElement> allLinksInMyAccountPage;
	
	@FindBy(xpath="//input[@placeholder='Search']")
	private WebElement productSearchField;
	
	@FindBy(xpath="//button[@class='btn btn-default btn-lg']")
	private WebElement SearchButton;
	
	@FindBy(xpath="//div[@id='content']//h1")
	private WebElement searchedProductNameElement;
	
	@FindBy(className="product-thumb")
	private List<WebElement> searchedProducts;
	
	//Actions
	
	public void clickOnMyAccountLink()
	{
		scrollToParticularElement(myAccountLink);
		click(myAccountLink);
	}
	public boolean isMyAccountTitleDisplayed()
	{
		return isDisplayed(myAccountTitle);
	}
	
	public void clickOnLogoutLink()
	{
		click(logoutLink);
	}
	
	public void clickOnContinueToLoginButton()
	{
		click(continueToLoginPageButton);
	}
	
	public boolean isLogoutLinkDisplayed()
	{
		return isDisplayed(logoutLink);
	}
	public void clickOnMyAccountMenuDropdown()
	{
		click(myAccountMenuDropdown);
	}
	
	public List<String> getMyAccountHeaderList()
	{
		wait.until(ExpectedConditions.visibilityOfAllElements(myAccountHeaderList));
		List<String> myAccountHeaderTextList= new ArrayList<String>();
		for (WebElement myAccountHeader: myAccountHeaderList) {
			String myAccountHeaderText=myAccountHeader.getText();
			myAccountHeaderTextList.add(myAccountHeaderText.trim());
		}
		return myAccountHeaderTextList;
	}
	
	public List<String> getMyAccountOptionsList()
	{
		List<String> myAccountOptionsText= new ArrayList<String>();
		clickOnMyAccountMenuDropdown();
		wait.until(ExpectedConditions.visibilityOfAllElements(myAccountOptionsList));
		for (WebElement myAccountOption : myAccountOptionsList) {
			String myAccountOptionText=myAccountOption.getText();
			myAccountOptionsText.add(myAccountOptionText.trim());
		}
		clickOnMyAccountMenuDropdown();
		return myAccountOptionsText;
	}
	
	public List<String> getAllLinksStatusInMyAccountPage() throws IOException {
	    List<String> allLinkStatus = new ArrayList<>(); // To store the status of all links
	    
	    for (WebElement linkInMyAccountPage : allLinksInMyAccountPage) {
	        String urlLink = linkInMyAccountPage.getAttribute("href");
	        
	        if (urlLink == null || urlLink.isEmpty()) {
	            System.out.println("URL is either not configured for anchor tag or it is empty");
	            allLinkStatus.add("URL is either not configured for anchor tag or it is empty");
	            continue;
	        }
	        
	        // Call the method to verify if the link is broken
	        try {
	            String status = verifyLinkActive(urlLink); // Call the method and get the response
	            allLinkStatus.add(status); // Add each link's status to the list
	        } catch (IOException e) {
	            System.out.println("Exception in checking the URL: " + urlLink);
	            e.printStackTrace();
	            allLinkStatus.add("Exception in checking the URL: " + urlLink);
	        }
	    }
	    
	    return allLinkStatus; // Return the list of statuses
	}
	
	public void searchProduct(String productName)
	{
		wait.until(ExpectedConditions.visibilityOf(productSearchField));
		productSearchField.sendKeys(productName);
	}
	
	public void clickOnSearchButton()
	{
		click(SearchButton);
	}
	
	public String getSearchedProductName()
	{
		wait.until(ExpectedConditions.visibilityOf(searchedProductNameElement));
		return searchedProductNameElement.getText();
	}
	
	public int getSearchedProductCount()
	{
		wait.until(ExpectedConditions.visibilityOfAllElements(searchedProducts));
		return searchedProducts.size();
	}

}
