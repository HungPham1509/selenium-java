package pages;

import auxiliary.Auxiliary;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class LoginPage {
    private WebDriver driver;
    protected Auxiliary auxiliary = new Auxiliary();
    private Logger logger = Logger.getLogger(LoginPage.class);
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.className("L3NKy");
    private String username = "pham_hung99"; // kawaken.izakaya
    private String password = "thtmhfc1509"; //kawaken080808

    public void accessInstagram() {
        try {
            driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
            logger.info("Accessed Instagram login page.");
            TimeUnit.SECONDS.sleep(1);
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
                driver.findElement(usernameField).sendKeys(s);
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
                driver.findElement(passwordField).sendKeys(s);
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
            driver.findElement(loginButton).click();
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
            driver.get("https://www.instagram.com/" + username);
            logger.info("Accessed profile.");
            int delayTime = auxiliary.delayBetween(4, 6);
            TimeUnit.SECONDS.sleep(delayTime);
        } catch (Exception e) {
            logger.error("Failed to accessed profile.");
            e.printStackTrace();
        }
    }

    public void startLogIn() {
        logger.info("Start like function.");
        this.accessInstagram();
        this.setUsernameField(this.username);
        this.setPasswordField(this.password);
        this.clickLoginButton();
        this.profile(this.username);
    }

    public LoginPage(WebDriver driver) throws IOException {
        this.driver = driver;
    }
}
