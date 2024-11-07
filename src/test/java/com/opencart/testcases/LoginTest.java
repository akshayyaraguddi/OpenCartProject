package com.opencart.testcases;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.opencart.base.BaseTest;
import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.pages.MyAccountPage;


public class LoginTest extends BaseTest
{
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass()
	{
		homePage=new HomePage(getDriver());
		loginPage= new LoginPage(getDriver());
	}
	@Test(description = "Tc_01_Verifying login with registered credentials",groups= {"login","regression","sanity","functional"})
	public void verifyLogin()
	{
		try {
		log.info("Starting login test case");
		log.info("Clicking on my account");
		homePage.clickOnMyAccount();
		log.info("Clicking on login link");
		homePage.clickOnLoginLink();
		log.info("Entering login email address");
		loginPage.setEmailAddress(prop.getProperty("loginEmailAddress"));
		log.info("Entering login password");
		loginPage.setPassword(prop.getProperty("loginPassword"));
		log.info("Clicking on the login button");
		loginPage.clickOnLoginButton();
		MyAccountPage myAccountPage= new MyAccountPage(getDriver());
		log.info("Verifying if 'My Account' is present");
		Assert.assertEquals(myAccountPage.isMyAccountTitleDisplayed(),true);
		log.info("Login test case completed successfully");
		}catch (AssertionError assertionError) {
			log.error("Actual account title is different from expected title", assertionError);
			Assert.fail();
		}
		catch (Exception e) {
			log.error("Test execution failed because of exception: ",e);
			Assert.fail("Test execution failed because of exception: "+ e.getMessage());
		}
	}
}
