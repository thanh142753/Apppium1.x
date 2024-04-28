package widget.commonWidget;

import org.openqa.selenium.WebElement;

public interface IBaseWidget {

//    WebElement getElementByXpath(String xpath);

    WebElement getAndroidElementByXpath(String xpath);
}
