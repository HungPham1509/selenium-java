package login;

import base.BaseTests;
import org.testng.annotations.Test;

public class LoginTests extends BaseTests {
    @Test
    public void testSuccessfulLogin() {
        loginPage.setUsernameField("kawaken.izakaya");
        loginPage.setPasswordField("kawaken080808");
        loginPage.clickLoginButton();
    }
}
