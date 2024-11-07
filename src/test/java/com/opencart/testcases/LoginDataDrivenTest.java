package com.opencart.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.opencart.base.BaseTest;
import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.pages.MyAccountPage;
import com.opencart.utils.DataProviders;

public class LoginDataDrivenTest extends BaseTest {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups= {"login","regression","functional"})
	public void verifyLogin(String userName, String password, String expectedResult, String errorMessage)
			throws InterruptedException {

		try {
			
		log.info("Starting login test case with username: " + userName + " and expected result: " + expectedResult);

		HomePage homePage = new HomePage(getDriver());
		log.info("Clicking on My Account dropdown.");
		homePage.clickOnMyAccount();

		log.info("Clicking on the Login link.");
		homePage.clickOnLoginLink();

		LoginPage loginPage = new LoginPage(getDriver());
		log.info("Entering email address: " + userName);
		loginPage.setEmailAddress(userName);

		log.info("Entering password.");
		loginPage.setPassword(password);

		log.info("Clicking on the Login button.");
		loginPage.clickOnLoginButton();

		MyAccountPage myAccountPage = new MyAccountPage(getDriver());
		log.info("Expected result: " + expectedResult);

		if (expectedResult.equalsIgnoreCase("Valid")) {
			log.info("Valid credentials. Verifying if My Account page is displayed.");

			boolean isMyAccountTitleDisplayed = myAccountPage.isMyAccountTitleDisplayed();

			if (isMyAccountTitleDisplayed) {
				log.info("Login successful. Logging out.");
				myAccountPage.clickOnLogoutLink();
				myAccountPage.clickOnContinueToLoginButton();
				log.info("Logged out and back to the login page.");
			} else {
				log.error("Login failed despite valid credentials.");
			}

			Assert.assertTrue(isMyAccountTitleDisplayed, "Login failed for valid credentials.");

		} else if (expectedResult.equalsIgnoreCase("Invalid")) {
			log.info("Invalid credentials. Verifying error message.");

			String actualErrorMessage = loginPage.getInvalidLoginErrorMessage();
			log.info("Expected error message: " + errorMessage);
			log.info("Actual error message: " + actualErrorMessage);

			if (actualErrorMessage.equals(errorMessage.trim().toLowerCase())) {
				log.info("Error message matched as expected for invalid login.");
			} else {
				log.error("Error message mismatch. Expected: " + errorMessage + ", but found: " + actualErrorMessage);
			}

			Assert.assertEquals(actualErrorMessage, errorMessage.trim().toLowerCase(), "Error message mismatch.");
		} else {
			log.error("Invalid 'expectedResult' value: " + expectedResult);
			Assert.fail("Invalid 'expectedResult' value provided: " + expectedResult);
		}

		log.info("Login test case completed for username: " + userName);
	}catch (Exception e) 
		{
		log.error("Test case failed due to exception", e);
		Assert.fail("Test case failed due to exception"+ e.getMessage());
	}
	}
}
