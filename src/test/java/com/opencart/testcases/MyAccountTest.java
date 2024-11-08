package com.opencart.testcases;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.opencart.base.BaseTest;
import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.pages.MyAccountPage;
import com.opencart.utils.Constants;
import com.opencart.utils.DataProviders;

public class MyAccountTest extends BaseTest 
{
	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		myAccountPage= new MyAccountPage(getDriver());
		homePage= new HomePage(getDriver());
		loginPage= new LoginPage(getDriver());
		softAssert= new SoftAssert();
		homePage.navigateToLoginPage();
		loginPage.doLogin(prop.get("loginEmailAddress").toString(), prop.getProperty("loginPassword").toString());
		log.info("Verify My Account page title");
		Assert.assertEquals(myAccountPage.getTitle().trim(),Constants.MY_ACCOUNT_PAGE_TITLE);
		log.info("Start executing my account test cases");
	}
	
	
	@Test(description="TC_01_Verify My account Header List", priority = 1,groups= {"my account","regression","functional"})
	public void verifyMyAccountHeaderList()
	{
		try {
		log.info("Verifying my account header list");
		Assert.assertEquals(myAccountPage.getMyAccountHeaderList(),Constants.MYACCOUNT_HEADERLIST);
		log.info("My account header list is verified and test case is passed");
		}catch (AssertionError e) {
			log.error("My account header list is different from expected header list", e);
			Assert.fail(e.getMessage());
			
		}
	}
	
	@Test(description="TC_02_Verify My account options List", priority = 2,groups= {"my account","regression","sanity","functional"})
	  public void verifyMyAccountMenuOptionTest()
	  {
		try {
		log.info("Verifying my account options list");
		Assert.assertEquals(myAccountPage.getMyAccountOptionsList(), Constants.MYACCOUNT_MENU_OPTIONSLIST);
		log.info("MY account options are verified and test case passed");
		}catch (AssertionError e) {
			log.error("Actual options are different from expected options",e);
			Assert.fail(e.getMessage());
			
		}
	  }
	
	@Test(description="TC_03_Verify the links status in my account page",priority = 3, groups= {"my account"})
	  public void verifyBrokenLinksInMyAccountPageTest()
	  {
		log.info("Getting the status of all links in my account page");
		try {
			List<String> allLinkStatus=myAccountPage.getAllLinksStatusInMyAccountPage();
			for (String linkStatus : allLinkStatus) {
				System.out.println(linkStatus);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Test failed because of exception: ", e);
			Assert.fail();
			
		}
	  }
	  
	  @Test(description="TC_04_Perform the product search", dataProvider = "productSearchData", dataProviderClass = DataProviders.class, priority = 4,groups= {"my account","regression","sanity","functional"})
	  public void performProductSearchTest(String productName)
	  {
		  try {
		  log.info("Searching for the product: "+ productName);
		  myAccountPage.searchProduct(productName);
		  myAccountPage.clickOnSearchButton();
		  log.info("Verifying the results are displayed for the product: "+productName);
		  softAssert.assertTrue(myAccountPage.getSearchedProductName().contains(productName));
		  log.info("Verifying that the searched products are displaying");
		  softAssert.assertTrue(myAccountPage.getSearchedProductCount()>0);
		  myAccountPage.clickOnMyAccountLink();
		  softAssert.assertAll();
		  }catch (Exception e) {
			log.error("Test case failed due to the exception ", e);
			Assert.fail();
		}
	  }
	  
	  @Test(description = "TC_05_Verify searching with a non existing Product Name", priority = 5,groups= {"my account","functional"})
	  public void searchWithNonExistingProductTest() {
		    try {
		        log.info("Searching for a non-existing product");
		        
		        // Searching for a non-existing product
		        myAccountPage.searchProduct("test123");
		        myAccountPage.clickOnSearchButton();
		        
		        // Verifying that a proper 'no results' message is displayed
		        String actualMessage = myAccountPage.getNoProductMatchText();
		        Assert.assertEquals(actualMessage, Constants.NOPRODUCT_TEXT_MESSAGE, "No product match message is incorrect.");
		        
		        log.info("Message is correct, and the test case has passed.");
		    } catch (Exception e) {
		        log.error("Test case failed due to an exception: ", e);
		        Assert.fail("Test case failed due to an exception: " + e.getMessage());
		    }
		}

	

}
