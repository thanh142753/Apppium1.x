package widget.commonWidget;

import factory.BaseClass;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;
//import org.openqa.selenium.WebElement;

public class BaseWidget implements IBaseWidget {

    BaseClass baseClass = BaseClass.getInstance();

    String TextElement = "//*[text()='%s']";

    WebDriverWait wait = new WebDriverWait(baseClass.getDriver(),10);

    //private function

    private void changeDriverContextTo (String driverContext){
        Set<String> allContext = baseClass.getDriver().getContextHandles();
        System.out.println(allContext);
        for (String context : allContext) {
            if (context.contains(driverContext)) baseClass.getDriver().context(context); //WEBVIEW || NATIVE
        }
    }

    public void clickToElementByText(String text) {
        try {
            Thread.sleep(2000);
            waitForElementIsDisplayed(String.format(TextElement, text));
            getAndroidElementByXpath(String.format(TextElement, text)).click();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void waitForElementIsDisplayed(String elementXpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath)));
    }

    @Override
    public WebElement getAndroidElementByXpath(String xpath) {
        return baseClass.getDriver().findElement(By.xpath(xpath));
    }

    @Override
    public void inputValueToField(String xpath, String value) {
        getAndroidElementByXpath(xpath).clear();
        getAndroidElementByXpath(xpath).sendKeys(value);
    }

    @Override
    public void switchToNativeApp() {
        changeDriverContextTo("NATIVE");
    }

    @Override
    public void switchToWebViewApp() {
        changeDriverContextTo("WEBVIEW");
    }





}
