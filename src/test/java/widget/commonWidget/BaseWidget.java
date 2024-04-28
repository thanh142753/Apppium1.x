package widget.commonWidget;

import factory.BaseClass;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.WebElement;

public class BaseWidget implements IBaseWidget {

    BaseClass baseClass = BaseClass.getInstance();

//    @Override
//    public WebElement getElementByXpath(String xpath) {
//        return baseClass.getDriver().findElement(By.xpath(xpath));
//    }

    @Override
    public WebElement getAndroidElementByXpath(String xpath) {
        return baseClass.getDriver().findElement(By.xpath(xpath));
    }


}
