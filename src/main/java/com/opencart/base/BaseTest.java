package com.opencart.base;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.opencart.pages.ForgotPasswordPage;
import com.opencart.pages.HomePage;
import com.opencart.pages.LoginPage;
import com.opencart.pages.MyAccountPage;
import com.opencart.pages.RegistrationPage;
import com.opencart.utils.PropertyFileReader;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected Logger log;
    protected ChromeOptions chromeOptions;
    protected EdgeOptions edgeOptions;
    protected Properties prop;
    protected HomePage homePage;
    protected RegistrationPage registrationPage;
    protected SoftAssert softAssert;
    protected MyAccountPage myAccountPage;
    protected LoginPage loginPage;
    protected ForgotPasswordPage forgotPasswordPage;

    // Thread-safe method to get WebDriver instance
    public static WebDriver getDriver() {
        return driver.get();
    }

    @SuppressWarnings("deprecation")
	@BeforeClass(alwaysRun = true)
    @Parameters({ "os", "browser" })
    public void setUp(String operatingSystem, String browserName) throws MalformedURLException {
        prop = PropertyFileReader.getProperties();
        log = LogManager.getLogger(this.getClass());
        String url = prop.getProperty("URL");

        // Choose local or remote setup based on execution environment
        if (prop.getProperty("execution_environment").equalsIgnoreCase("remote")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            switch (operatingSystem.toLowerCase()) {
                case "windows":
                    capabilities.setPlatform(Platform.WIN11);
                    break;
                case "mac":
                    capabilities.setPlatform(Platform.MAC);
                    break;
                    
                case "linux":
                    capabilities.setPlatform(Platform.LINUX);
                    break;
                default:
                    log.error("Invalid OS type! Please provide 'windows' or 'mac'");
                    throw new IllegalArgumentException("Invalid OS specified: " + operatingSystem);
            }

            // Set the browser
            switch (browserName.toLowerCase()) {
                case "chrome":
                    capabilities.setBrowserName("chrome");
                    break;
                case "edge":
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;
                default:
                    log.error("Invalid browser type! Please provide 'chrome' or 'edge'");
                    throw new IllegalArgumentException("Invalid browser specified: " + browserName);
            }
            driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));
        }

        // Local setup for Chrome or Edge with unique profiles
        if (prop.getProperty("execution_environment").equalsIgnoreCase("local")) {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--incognito");
                    chromeOptions.addArguments("user-data-dir=C:\\Temp\\ChromeProfile_" + UUID.randomUUID());
                    log.info("Launching the Chrome browser with a unique profile");
                    driver.set(new ChromeDriver(chromeOptions));
                    break;

                case "edge":
                    edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--inprivate");
                    edgeOptions.addArguments("user-data-dir=C:\\Temp\\EdgeProfile_" + UUID.randomUUID());
                    log.info("Launching the Edge browser with a unique profile");
                    driver.set(new EdgeDriver(edgeOptions));
                    break;

                default:
                    log.error("Invalid browser type! Please provide 'chrome' or 'edge'");
                    throw new IllegalArgumentException("Invalid browser specified: " + browserName);
            }
        }

        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        log.info("Opening the URL: " + url);
        getDriver().get(url);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        log.info("Closing the browser");
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        } else {
            log.warn("Driver is null, skipping driver.quit()");
        }
    }

    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);
        return targetFilePath;
    }
}
