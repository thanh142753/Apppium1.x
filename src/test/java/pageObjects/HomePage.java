package pageObjects;

import widget.commonWidget.ILoginWidget;
import widget.commonWidget.LoginWidget;

public class HomePage {

    private ILoginWidget iLoginWidget;

    public ILoginWidget getLoginWidget() {
        return iLoginWidget = new LoginWidget();
    }
}
