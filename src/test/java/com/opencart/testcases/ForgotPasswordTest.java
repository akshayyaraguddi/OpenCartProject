package com.opencart.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.opencart.base.BaseTest;
import com.opencart.pages.ForgotPasswordPage;
import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.utils.Constants;
import com.opencart.utils.DataUtils;

public class ForgotPasswordTest extends BaseTest {
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		homePage = new HomePage(getDriver());
		loginPage = new LoginPage(getDriver());
		forgotPasswordPage = new ForgotPasswordPage(getDriver());
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		log.info("Navigating to login page");
		homePage.navigateToLoginPage();
		Assert.assertEquals(loginPage.getTitle(), Constants.LOGIN_PAGE_TITLE);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		log.info("Navigating back to login page");
		homePage.navigateToLoginPage();
		Assert.assertEquals(loginPage.getTitle(), Constants.LOGIN_PAGE_TITLE);
	}

	@Test(description = "TC_01_Verify Forget Password Functionality with Registered Email Address", priority = 1,groups= {"forgot password","regression","sanity","functional"})
	public void verifyForgetPasswordWithRegisteredEmailTest() {
		try {
			log.info("Clicking on forgotten password link");
			loginPage.clickOnForgottenPassword();
			Assert.assertEquals(forgotPasswordPage.getTitle().trim(), Constants.FORGOT_PASSWORD_PAGE_TITLE);
			log.info("Enter the registered email address and continue");
			forgotPasswordPage.setEmailAddress(prop.get("loginEmailAddress").toString());
			forgotPasswordPage.clickOnContinueButton();
			log.info("Verify the email sent confirmation success message");
			Assert.assertEquals(forgotPasswordPage.getEmailSentSuccessMessage().trim(), Constants.FORGOTPASSWORD_EMAIL_SUCCESS_MESSAGE);
			log.info("Email sent succesffuly and test case passed");
		} catch (AssertionError e) {
			log.error("Test case failed because of exception: " + e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(description = "TC_02_Verify Forget Password Functionality with Non-Registered Email Address", priority = 2,groups= {"forgot password","regression","functional"})
	public void verifyForgetPasswordWithNon_RegisteredEmailTest() {
		try {
			log.info("Clicking on forgotten password link");
			loginPage.clickOnForgottenPassword();
			Assert.assertEquals(forgotPasswordPage.getTitle().trim(), Constants.FORGOT_PASSWORD_PAGE_TITLE);
			log.info("Enter the registered email address and continue");
			DataUtils du = new DataUtils();
			forgotPasswordPage.setEmailAddress(du.generateRandomString(5) + "@gmail.com");
			forgotPasswordPage.clickOnContinueButton();
			log.info("Verify the error message");

			Assert.assertEquals(forgotPasswordPage.getInvalidEmailErrorMessage().trim(),
					Constants.FORGOTPASSWORD_INVALID_EMAIL_ERROR_MESSAGE);
			log.info("Invalid user error message is displayed and test case is passed");
		} catch (AssertionError e) {
			log.error("Test case failed because of exception: " + e);
			Assert.fail(e.getMessage());
		}
	}

}
