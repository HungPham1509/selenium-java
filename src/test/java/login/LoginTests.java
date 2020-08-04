package login;

import base.BaseTests;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginTests extends BaseTests {
    @Test
    public void testSuccessfulLogin() {
        try {
            loginPage.setUsernameField("kawaken.izakaya");
            TimeUnit.SECONDS.sleep(1);
            loginPage.setPasswordField("kawaken080808");
            TimeUnit.SECONDS.sleep(1);
            loginPage.clickLoginButton();
            TimeUnit.SECONDS.sleep(3);
        }
        catch (InterruptedException e) {
            System.out.println("Interrupted "
                    + "while Sleeping");
        }
    }
}
