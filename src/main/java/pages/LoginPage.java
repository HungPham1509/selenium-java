package pages;

import auxiliary.Auxiliary;
import config.InstagramElements;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class LoginPage {
    private WebDriver driver;
    protected Auxiliary auxiliary = new Auxiliary();
    private Logger logger = Logger.getLogger(LoginPage.class);

    public void accessInstagram() {
        try {
            driver.get(InstagramElements.instagramLoginUrl);
            logger.info("Accessed Instagram login page.");
            TimeUnit.SECONDS.sleep(auxiliary.delayBetween(4, 7));
            List<WebElement> elements = driver.findElements(InstagramElements.notNowButton);
            if(elements.size() > 0 && elements.get(0).getText().equals("Not Now")) {
                elements.get(0).click();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }
        } catch (Exception e) {
            logger.info("Failed to Accessed Instagram login page.");
            e.printStackTrace();
        }
    }

    public void setUsernameField(String username) {
        try {
            for (int i=0; i<username.length(); i++) {
                char c = username.charAt(i);
                String s = String.valueOf(c);
                int delayTime = auxiliary.delayBetween(100, 300);
                driver.findElement(InstagramElements.usernameField).sendKeys(s);
                Thread.sleep(delayTime);
            }
            logger.info("Filled in username input.");
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e) {
            logger.error("Failed to fill in username input.");
            e.printStackTrace();
        }
    }

    public void setPasswordField(String password) {
        try {
            for (int i=0; i<password.length(); i++) {
                char c = password.charAt(i);
                String s = String.valueOf(c);
                int delayTime = auxiliary.delayBetween(100, 300);
                driver.findElement(InstagramElements.passwordField).sendKeys(s);
                Thread.sleep(delayTime);
            }
            logger.info("Filled in password input.");
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e) {
            logger.error("Failed to fill in password input.");
            e.printStackTrace();
        }
    }

    public void clickLoginButton() {
        try {
            driver.findElement(InstagramElements.loginButton).click();
            logger.info("Clicked login button.");
            logger.info("Login Successfully");
            int delayTime = auxiliary.delayBetween(5, 8);
           TimeUnit.SECONDS.sleep(delayTime);
        } catch (Exception e) {
            logger.error("Failed to log in.");
            e.printStackTrace();
        }
    }

    public void profile(String username) {
        try {
            driver.get(InstagramElements.instagramUrl + username);
            logger.info("Accessed profile.");
            int delayTime = auxiliary.delayBetween(4, 6);
            TimeUnit.SECONDS.sleep(delayTime);
        } catch (Exception e) {
            logger.error("Failed to accessed profile.");
            e.printStackTrace();
        }
    }

    public void startLogIn(String username, String password) {
        logger.info("Start like function.");
        this.accessInstagram();
        // kawaken.izakaya
        //this.setUsernameField(username);
        //kawaken080808
        //this.setPasswordField(password);
        //this.clickLoginButton();
        this.profile(username);
    }

    public LoginPage(WebDriver driver) throws IOException {
        this.driver = driver;
    }
}
