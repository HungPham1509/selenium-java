package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.Followers;
import pages.LoginPage;

import java.util.concurrent.TimeUnit;

public class Base {
    private WebDriver driver;

    protected LoginPage loginPage;

    protected Followers followers;

    private String username = "hung_pham99";

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();

        try {
            driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
            TimeUnit.SECONDS.sleep(1);

            loginPage = new LoginPage(driver);
            loginPage.setUsernameField(username);
            TimeUnit.SECONDS.sleep(1);
            loginPage.setPasswordField("thtmhfc1509");
            TimeUnit.SECONDS.sleep(1);
            loginPage.clickLoginButton();
            TimeUnit.SECONDS.sleep(5);
            loginPage.profile(username);
            TimeUnit.SECONDS.sleep(3);

            followers = new Followers(driver);
            followers.getFollowerList();
            TimeUnit.SECONDS.sleep(2);
            followers.getFollower();
            TimeUnit.SECONDS.sleep(3);
            followers.getPost();
            TimeUnit.SECONDS.sleep(3);
            followers.likePost();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tearDown() {
        driver.quit();
    }

    public static void main(String args[]) {
        Base base = new Base();
        base.setUp();
    }
}
