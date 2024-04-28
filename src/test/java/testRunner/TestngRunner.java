package testRunner;

import factory.BaseClass;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.*;
import utilities.ServerManager;


@CucumberOptions(features = {"src/test/resources"},
        glue="stepDefinitions",
        plugin= {"pretty", "html:reports/myreport.html",
                "rerun:target/rerun.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },

        dryRun=false,    // checks mapping between scenario steps and step definition methods
        monochrome=true,    // to avoid junk characters in output
        publish=true,   // to publish report in cucumber server
        tags="@Mobile"  // this will execute scenarios tagged with @sanity)
)

public class TestngRunner {
    private TestNGCucumberRunner testNGCucumberRunner;
    BaseClass baseClass = BaseClass.getInstance();
    ServerManager serverManager = ServerManager.getInstance();

    @Parameters({"deviceName","udid","platformVersion","platformName","appPackage","appActivity","automationName"})
    @BeforeClass(alwaysRun = true)
    public void setUpClass(String deviceName, String udid, String platformVersion, String platformName, String appPackage, String appActivity, String automationName) throws Exception {
        serverManager.startServer();
        baseClass.initializeDriver(deviceName, udid, platformVersion, platformName, appPackage, appActivity, automationName);
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
            System.out.println(serverManager.getServer().getUrl() + "if it is null, the server is offed");
        }

        testNGCucumberRunner.finish();
    }

}
