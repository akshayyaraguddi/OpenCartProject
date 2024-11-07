package com.opencart.testcases;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.opencart.base.BaseTest;
import com.opencart.pages.HomePage;
import com.opencart.pages.RegistrationPage;
import com.opencart.utils.Constants;

public class HomePageTest extends BaseTest {
	
	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		homePage = new HomePage(getDriver());
		registrationPage = new RegistrationPage(getDriver());
		softAssert = new SoftAssert();

	}

	@Test(description = "TC_01_Verifying the opencart logo", priority = 1,groups = {"homepage","regression"})
	public void verifyOpenCartLogoTest() {
		try {
			log.info("Verifying the opencart logo");
			assertTrue(homePage.isOpencartLogoDisplayed(), "Opencart logo is not being displayed");
		} catch (AssertionError e) {
			log.error("Opencart logo is not being displayed");
			Assert.fail();

		}
	}

	@Test(description = "TC_02_Verifying the opencart page title", priority = 2, groups= {"homepage"})
	public void verifyOpenCartPageTitleTest() {
		log.info("Verifying the OpenCart page title");
		try {

			Assert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE);
			log.info("Page title verification passed.");
		} catch (AssertionError e) {
			log.error("Page title verification failed. Expected: " + Constants.HOME_PAGE_TITLE + ", but found: "
					+ homePage.getTitle());
			Assert.fail();

		}
	}

	@Test(description = "TC_03_Verifying the featured section cards count", priority = 3,groups= {"homepage","regression","sanity"})
	public void verifyFeaturedCardsCountTest() {
		try {
			log.info("Verifying the featured section cards count");
			Assert.assertTrue(homePage.getFeaturedSectionCardsCount() == 4,
					"Featured section cards count is not equal to 4");
		} catch (AssertionError e) {
			log.error("Featured section cards count is not equal to 4");
			Assert.fail();
		}
	}

	@Test(description = "TC_04_navigate to registration page", priority = 4, groups= {"homepage","regression","sanity","functional"})
	public void navigateToRegistrationPageTest() {
		try {
			homePage.clickOnMyAccount();
			log.info("Clicking on registration link");
			homePage.clickOnRegisterLink();

			log.info("Verify the Registration page title");
			softAssert.assertEquals(registrationPage.getTitle(), Constants.REGISTRATION_PAGE_TITLE,
					"Registration page title mismatch");
			log.info("Navigated to registration page");

			registrationPage.navigateBrowserBack();

			softAssert.assertEquals(homePage.getTitle(), Constants.HOME_PAGE_TITLE,
					"Home page title mismatch after navigating back.");
			log.info("Navigated to home page");

		} catch (AssertionError e) {
			log.error("Test failed due to assertion error: ", e);
		}
		softAssert.assertAll();

	}
}
