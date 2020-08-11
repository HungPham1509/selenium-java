package pages;

import auxiliary.Auxiliary;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class LoginPage {
    private WebDriver driver;
    protected Auxiliary auxiliary = new Auxiliary();
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.className("L3NKy");
    private String username = "pham_hung99"; // kawaken.izakaya
    private String password = "thtmhfc1509"; //kawaken080808

    public void accessInstagram() {
        try {
            Date date = new Date();
            fileWriter = new FileWriter("resources/history.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(String.valueOf(date));
            bufferedWriter.newLine();
            driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
            bufferedWriter.write("Accessed Instagram log in page");
            bufferedWriter.newLine();
            bufferedWriter.close();
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsernameField(String username) {
        try {
            fileWriter = new FileWriter("resources/history.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (int i=0; i<username.length(); i++) {
                char c = username.charAt(i);
                String s = String.valueOf(c);
                int delayTime = auxiliary.delayBetween(100, 300);
                driver.findElement(usernameField).sendKeys(s);
                Thread.sleep(delayTime);
            }
            bufferedWriter.write("Filled in username input");
            bufferedWriter.newLine();
            bufferedWriter.close();
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setPasswordField(String password) {
        try {
            fileWriter = new FileWriter("resources/history.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (int i=0; i<password.length(); i++) {
                char c = password.charAt(i);
                String s = String.valueOf(c);
                int delayTime = auxiliary.delayBetween(100, 300);
                driver.findElement(passwordField).sendKeys(s);
                Thread.sleep(delayTime);
            }
            bufferedWriter.write("Filled in password input");
            bufferedWriter.newLine();
            bufferedWriter.close();
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void clickLoginButton() {
        try {
            fileWriter = new FileWriter("resources/history.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            driver.findElement(loginButton).click();
            bufferedWriter.write("Clicked log in button");
            bufferedWriter.newLine();
            bufferedWriter.close();
            int delayTime = auxiliary.delayBetween(5, 8);
           TimeUnit.SECONDS.sleep(delayTime);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void profile(String username) {
        try {
            fileWriter = new FileWriter("resources/history.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            driver.get("https://www.instagram.com/" + username);
            bufferedWriter.write("Accessed profile");
            bufferedWriter.newLine();
            bufferedWriter.close();
            int delayTime = auxiliary.delayBetween(4, 6);
            TimeUnit.SECONDS.sleep(delayTime);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startLogIn() {
        this.accessInstagram();
        this.setUsernameField(this.username);
        this.setPasswordField(this.password);
        this.clickLoginButton();
        this.profile("romelulukaku");
    }

    public LoginPage(WebDriver driver) throws IOException {
        this.driver = driver;
    }
}
