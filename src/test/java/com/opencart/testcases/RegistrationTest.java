package com.opencart.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.opencart.base.BaseTest;
import com.opencart.pages.HomePage;
import com.opencart.pages.MyAccountPage;
import com.opencart.pages.RegistrationPage;
import com.opencart.utils.Constants;
import com.opencart.utils.DataUtils;

public class RegistrationTest extends BaseTest {
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		homePage = new HomePage(getDriver());
		registrationPage = new RegistrationPage(getDriver());
		myAccountPage= new MyAccountPage(getDriver());
		softAssert = new SoftAssert();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() {
		log.info("Navigating to registeration page");
		homePage.navigateToRegisterPage();
		Assert.assertEquals(registrationPage.getTitle(), Constants.REGISTRATION_PAGE_TITLE);
	}
	

	@Test(description = "TC_01_Verifying the account registration with valid data", priority = 1, groups= {"registration","regression","sanity","functional"})
	public void verifyAccountRegistrationWithEmailId() {
		try {
			log.info("Starting registration test case");
			DataUtils du = new DataUtils();
			log.info("Entering random first name");
			registrationPage.setFirstName(du.generateRandomString(5));
			log.info("Entering random last name");
			registrationPage.setLastName(du.generateRandomString(4));
			log.info("Entering random email");
			registrationPage.setEmailId(du.generateRandomString(4) + "@gmail.com");
			log.info("Entering random telephone number");
			registrationPage.setTelephoneNumber(du.generateRandomNumericString(10));
			String password = du.generateRandomPassword();
			log.info("Setting password");
			registrationPage.setPassword(password);
			log.info("Confirming password");
			registrationPage.setConfirmPassword(password);
			log.info("Accepting privacy policy");
			registrationPage.selectPrivacyPolicy();
			log.info("Clicking on Continue button to submit the registration form");
			registrationPage.clickOnContinueButton();
			log.info("Verifying account creation success message");
			if (registrationPage.getAccountCreationSuccessMessage()
					.equalsIgnoreCase(Constants.REGISTRATION_SUCCESS_MESSAGE)) {
				log.info("Click on continue button after registration");
				registrationPage.clickOnContinueButtonAfterRegistration();
				log.info("Verify the my account title and then logout");
				Assert.assertEquals(myAccountPage.getTitle(),Constants.MY_ACCOUNT_PAGE_TITLE);
				myAccountPage.clickOnLogoutLink();
				log.info("Registration test case completed successfully");
				Assert.assertTrue(true);
			} else {
				log.info("Expected message not displayed");
				Assert.fail();
			}
		} catch (Exception e) {
			log.error("Test case failed due to an exception: ", e);
			Assert.fail("Test case failed due to an exception: " + e.getMessage());
		}
	}

	@Test(description = "TC_02_Verifying the account registration with already used email address", priority = 2, groups= {"registration","regression","functional"})
	public void verifyAccountRegistration() {
		log.info("Starting registration test case");
		DataUtils du = new DataUtils();
		log.info("Entering random first name");
		registrationPage.setFirstName(du.generateRandomString(5));
		log.info("Entering random last name");
		registrationPage.setLastName(du.generateRandomString(4));
		log.info("Entering used email");
		registrationPage.setEmailId(prop.get("loginEmailAddress").toString());
		log.info("Entering random telephone number");
		registrationPage.setTelephoneNumber(du.generateRandomNumericString(10));
		String password = du.generateRandomPassword();
		log.info("Setting password");
		registrationPage.setPassword(password);
		log.info("Confirming password");
		registrationPage.setConfirmPassword(password);
		log.info("Accepting privacy policy");
		registrationPage.selectPrivacyPolicy();
		log.info("Clicking on Continue button to submit the registration form");
		registrationPage.clickOnContinueButton();
		log.info("Verifying the email already in use error message");
		if (registrationPage.warningMessageInRegistrationPage().equalsIgnoreCase(Constants.EMAIL_ALREADY_USED)) {
			log.info("Email already in use error displayed, registration test case completed successfully.");
			Assert.assertTrue(true); // Test passes if error is displayed
		} else {
			log.error("Expected email already in use message not displayed.");
			Assert.fail();
		}

		// After checking for the error message, confirm that the account creation
		// message should not appear
		if (registrationPage.getAccountCreationSuccessMessage()
				.equalsIgnoreCase(Constants.REGISTRATION_SUCCESS_MESSAGE)) {
			log.error("User was able to register using an already used email address.");
			Assert.fail("Account creation succeeded using an already registered email.");
		} else {
			log.info("Registration was correctly blocked for an already used email.");
		}

	}

	@Test(description = "TC_03_Verify that an error message is displayed when the Password and Confirm Password fields do not match", priority = 3, groups= {"registration","regression","functional"})
	public void verifyPasswordMismatchErrorMessage() {
		log.info("Starting registration test case");
		DataUtils du = new DataUtils();
		log.info("Entering random first name");
		registrationPage.setFirstName(du.generateRandomString(5));
		log.info("Entering random last name");
		registrationPage.setLastName(du.generateRandomString(4));
		log.info("Entering used email");
		registrationPage.setEmailId(prop.get("loginEmailAddress").toString());
		log.info("Entering random telephone number");
		registrationPage.setTelephoneNumber(du.generateRandomNumericString(10));
		log.info("Setting password");
		registrationPage.setPassword(du.generateRandomPassword());
		log.info("Confirm with different password");
		registrationPage.setConfirmPassword(du.generateRandomPassword());
		log.info("Accepting privacy policy");
		registrationPage.selectPrivacyPolicy();
		log.info("Clicking on Continue button to submit the registration form");
		registrationPage.clickOnContinueButton();
		log.info("Verifying the error message when the Password and Confirm Password fields do not match");
		if (registrationPage.getPasswordTextFieldErrorMessage().equals(Constants.PASSWORD_MISMATCH_ERROR_MESSAGE)) {
			log.info("Password mismatch error message displayed and test case is passed");
			Assert.assertTrue(true);
		} else {
			log.error("Password mismatch error message didn't display");
			Assert.fail();
		}

	}
	
	@Test(description = "TC_04_Verify that an error message is displayed when the privacy policy checkbox is not selected", priority = 4, groups= {"registration","regression","functional"})
	public void verifyPrivacyPolicyNotSelectedErrorMessageTest() 
	{
		log.info("Starting registration test case");
		DataUtils du = new DataUtils();
		log.info("Entering random first name");
		registrationPage.setFirstName(du.generateRandomString(5));
		log.info("Entering random last name");
		registrationPage.setLastName(du.generateRandomString(4));
		log.info("Entering used email");
		registrationPage.setEmailId(prop.get("loginEmailAddress").toString());
		log.info("Entering random telephone number");
		registrationPage.setTelephoneNumber(du.generateRandomNumericString(10));
		log.info("Setting password");
		registrationPage.setPassword(du.generateRandomPassword());
		log.info("Confirm with different password");
		registrationPage.setConfirmPassword(du.generateRandomPassword());
		log.info("Clicking on Continue button without accepting privacy policy");
		registrationPage.clickOnContinueButton();
		log.info("Verifying the error message for selecting the privacy policy");
		if (registrationPage.warningMessageInRegistrationPage().equals(Constants.PRIVACY_POLICY_ERROR_MESSAGE)) {
			log.info("Accept privacy policy error message displayed and test case is passed");
			Assert.assertTrue(true);
		} else {
			log.error("Privacy policy error message didn't display");
			Assert.fail();
		}
	}
	
	@Test(description = "TC_05_Verify that the Privacy Policy pop-up is displayed when the privacy policy link is clicked", priority = 5, groups= {"registration","regression","functional"})
	public void verifyPrivacyPolicyPopupDisplayedTest() 
	{
		try {
		log.info("Starting registration test case");
		log.info("Clicking on privacy policy link");
		registrationPage.clickOnPrivacyPolicyLink();
		log.info("Verifying the privacy policy pop-up is displayed");
		Assert.assertTrue(registrationPage.isPrivacyPolicyPopupDisplayed());
		log.info("Privacy policy pop-up is being displayed");
		if(registrationPage.isPrivacyPolicyPopupDisplayed())
		{
			log.info("Verify the privacy policy content");
			Assert.assertEquals(registrationPage.getPrivacyPolicyContent().trim(), Constants.PRIVACY_POLICY_CONTENT);
			log.info("Privacy policy content is verified and test case is passed");
		}
		}catch (AssertionError e) {
			log.error("Test failed because of "+ e);
			Assert.fail(e.getMessage());
		}
		
	}
	
}
