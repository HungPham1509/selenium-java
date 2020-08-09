package pages;

import auxiliary.Auxiliary;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage {
    private WebDriver driver;
    protected Auxiliary auxiliary;
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.className("L3NKy");

    public void setUsernameField(String username) {
        auxiliary = new Auxiliary();
        try {
            for (int i=0; i<username.length(); i++) {
                char c = username.charAt(i);
                String s = String.valueOf(c);
                int delayTime = auxiliary.delayBetween(100, 300);
                driver.findElement(usernameField).sendKeys(s);
                Thread.sleep(delayTime);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setPasswordField(String password) {
        auxiliary = new Auxiliary();
        try {
            for (int i=0; i<password.length(); i++) {
                char c = password.charAt(i);
                String s = String.valueOf(c);
                int delayTime = auxiliary.delayBetween(100, 300);
                driver.findElement(passwordField).sendKeys(s);
                Thread.sleep(delayTime);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void profile(String username) {driver.get("https://www.instagram.com/" + username);}

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

}
