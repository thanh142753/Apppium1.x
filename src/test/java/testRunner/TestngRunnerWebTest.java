package testRunner;

import factory.BaseClass;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.*;
import utilities.ServerManager;


@CucumberOptions(
        plugin= {"pretty", "html:reports/myreport29.html"
//                "rerun:target/rerun.txt",
//                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        features = {"src/test/resources"},
        glue="stepDefinitions",

//        dryRun=false,    // checks mapping between scenario steps and step definition methods
//        monochrome=true,    // to avoid junk characters in output
//        publish=true,   // to publish report in cucumber server
//        strict = true,
        tags= "@WebApp"  // this will execute scenarios tagged with @sanity)
)

public class TestngRunnerWebTest {
    private TestNGCucumberRunner testNGCucumberRunner;
    BaseClass baseClass = BaseClass.getInstance();
    ServerManager serverManager = ServerManager.getInstance();

    @Parameters({"deviceName","udid","platformVersion","platformName","browserName","automationName"})
    @BeforeClass(alwaysRun = true)
    public void setUpClass(String deviceName, String udid, String platformVersion, String platformName, String browserName, String automationName) throws Exception {
        serverManager.startServer();
        baseClass.initializeWebDriver(deviceName, udid, platformVersion, platformName, browserName, automationName);
        System.out.println(serverManager.getServer().getUrl());

        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber scenarios", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickleEvent, FeatureWrapper cucumberFeature) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEvent.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        if(serverManager.getServer() != null){
            serverManager.getServer().stop();
            System.out.println(serverManager.getServer().getUrl() + " if it is null, the server is offed");
        }

        testNGCucumberRunner.finish();
    }

//    @AfterMethod
//    public void AddScreenshot(ITestResult result) throws IOException {
//        WebDriver driver = DriverManager.getDriver();
//        String imagePath = "screenshots" + File.separator + scenarioName + ".png";
//        String completeImagePath = System.getProperty("user.dir") + File.separator + "target" + File.separator + imagePath;
//        if (!(driver == null)) {
//            if (result.getStatus() == ITestResult.FAILURE) {
//                File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//                FileUtils.copyFile(sourcePath, new File(completeImagePath));
//                byte[] encoded = null;
//                try {
//                    encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(sourcePath));
//                } catch (IOException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
//                ExtentCucumberAdapter.getCurrentStep().fail("Test Failed",
//                        MediaEntityBuilder.createScreenCaptureFromBase64String((new String(encoded, StandardCharsets.US_ASCII)), "Failed image").build());
//            }
//            InitialDriver.quitDriver();
//        } else {
//            Assert.fail("Driver is not initialized. Could be error from server-side");
//        }
//    }

}
