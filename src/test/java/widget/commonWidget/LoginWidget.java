package widget.commonWidget;

import org.junit.Assert;
import widget.elementLocator.BaseWidgetElementLocator;

public class LoginWidget extends BaseWidget implements ILoginWidget {

    // String xpath
    String IF_USERNAME = "//android.widget.EditText[@content-desc='test-Username']";
    String IF_PASSWORD = "//android.widget.EditText[@content-desc=\"test-Password\"]";

    @Override
    public void loginToAppSuccessfully() {
        getAndroidElementByXpath(IF_USERNAME).clear();
        getAndroidElementByXpath(IF_USERNAME).sendKeys("Abc");
//        Assert.fail("fail");
    }
}
