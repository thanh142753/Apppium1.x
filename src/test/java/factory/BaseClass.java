package factory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.eo.Se;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.GlobalParams;
import utilities.ServerManager;

public class BaseClass {

    private static BaseClass baseClass = null;

//    private AppiumDriver driver;
    ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    static Properties p;
    static Logger logger;

    ServerManager serverManager = ServerManager.getInstance();

    private BaseClass() {
        System.out.println("This is Singleton Class");
    }

    public void initializeDriver(String deviceName, String udid, String platformVersion, String platformName, String appPackage, String appActivity, String automationName) {
        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

            desiredCapabilities.setCapability("deviceName", deviceName);
            desiredCapabilities.setCapability("udid", udid);
            desiredCapabilities.setCapability("platformVersion", platformVersion);
            desiredCapabilities.setCapability("platformName", platformName);
            desiredCapabilities.setCapability("appPackage", appPackage);
            desiredCapabilities.setCapability("appActivity", appActivity);
            desiredCapabilities.setCapability("automationName",automationName);

//            desiredCapabilities.setCapability("systemPort", params.getSystemPort());
//            desiredCapabilities.setCapability("chromeDriverPort", params.getChromeDriverPort());

//            String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
//                    + File.separator + "resources" + File.separator + "apps" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.2.1.apk";
//            desiredCapabilities.setCapability("app", androidAppUrl);

//            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
            System.out.println(serverManager.getServer());
            driver.set(new AndroidDriver(serverManager.getServer(), desiredCapabilities));
            driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("DRIVER IS NULL");
            System.out.println(e);
        }
    }

    public void initializeWebDriver(String deviceName, String udid, String platformVersion, String platformName, String browserName, String automationName) {
        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

            desiredCapabilities.setCapability("deviceName", deviceName);
//            desiredCapabilities.setCapability("udid", udid);
            desiredCapabilities.setCapability("platformVersion", platformVersion);
            desiredCapabilities.setCapability("platformName", platformName);
            desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
            desiredCapabilities.setCapability("chromeOptions", ImmutableMap.of("w3c", false));
            desiredCapabilities.setCapability("automationName",automationName);
//            System.out.println(System.getProperty("user.dir") + File.separator + "src/test/resources/drivers/chromedriver.exe");
            desiredCapabilities.setCapability("chromedriverExecutable",System.getProperty("user.dir") + File.separator + "src/test/resources/drivers/chromedriver.exe");

//            desiredCapabilities.setCapability("systemPort", params.getSystemPort());
//            desiredCapabilities.setCapability("chromeDriverPort", params.getChromeDriverPort());

//            String androidAppUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
//                    + File.separator + "resources" + File.separator + "apps" + File.separator + "Android.SauceLabs.Mobile.Sample.app.2.2.1.apk";
//            desiredCapabilities.setCapability("app", androidAppUrl);

//            driver.set(new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities));
//            System.out.println(serverManager.getServer());
            driver.set(new AndroidDriver(serverManager.getServer(), desiredCapabilities));
//            driver.get().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//            driver.get().manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
//            driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("DRIVER IS NULL");
            System.out.println(e);
        }
    }

    //declared static method that returns the object of singleton class
    public static BaseClass getInstance() {
        if (baseClass == null) {
            baseClass = new BaseClass();
        }
        return baseClass;
    }

    public AppiumDriver getDriver() {
        return driver.get();
    }

//    public void setDriver(WebDriver webDriver) {
//        driver = webDriver;
//    }

    public static Properties getProperties() throws IOException {
        FileReader file = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");

        p = new Properties();
        p.load(file);
        return p;
    }

    public static Logger getLogger() {
        logger = LogManager.getLogger(); //Log4j
        return logger;
    }

    public static String randomeString() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }


    public static String randomeNumber() {
        String generatedString = RandomStringUtils.randomNumeric(10);
        return generatedString;
    }


    public static String randomAlphaNumeric() {
        String str = RandomStringUtils.randomAlphabetic(5);
        String num = RandomStringUtils.randomNumeric(10);
        return str + num;
    }


}
