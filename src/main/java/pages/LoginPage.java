package pages;

import auxiliary.Auxiliary;
import config.InstagramElements;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;


public class LoginPage {
    private WebDriver driver;
    protected Auxiliary auxiliary = new Auxiliary();
    private Logger logger = Logger.getLogger(LoginPage.class);
    public boolean isAuthenticated = true;

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
            Thread.sleep(auxiliary.delayBetween(1500, 2500));
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
            Thread.sleep(auxiliary.delayBetween(1500, 2500));
        }catch (Exception e) {
            logger.error("Failed to fill in password input.");
            e.printStackTrace();
        }
    }

    public void clickLoginButton() {
        try {
            List<WebElement> elements = driver.findElements(InstagramElements.logInFailedAlert);
            driver.findElement(InstagramElements.loginButton).click();
            logger.info("Clicked login button.");
            int delayTime = auxiliary.delayBetween(5, 8);
            TimeUnit.SECONDS.sleep(delayTime);
            if(elements.size() > 0) {
                if(elements.get(0).getText().equals("Sorry, your password was incorrect. Please double-check your password.")) {
                    logger.warn("Incorrect username and password.");
                    this.isAuthenticated = false;
                }
            }
        } catch (Exception e) {
            logger.error("Failed to log in.");
            e.printStackTrace();
        }
    }

    public void profile(String username) {
        try {
            logger.info("Login Successfully");
            driver.get(InstagramElements.instagramUrl + username);
            logger.info("Accessed profile.");
            int delayTime = auxiliary.delayBetween(4, 6);
            TimeUnit.SECONDS.sleep(delayTime);
        } catch (Exception e) {
            logger.error("Failed to accessed profile.");
            e.printStackTrace();
        }
    }

    public void startLogIn(String username, String password, boolean autoLogIn) {
        logger.info("Start like function.");
        this.accessInstagram();
        if(!autoLogIn) {
            this.setUsernameField(username);  //kawaken.izakaya
            this.setPasswordField(password);  //kawaken080808
            this.clickLoginButton();
        }
        if(this.isAuthenticated) {
            this.profile(username);
        }
    }

    public void saveCookies(String username, String password) {
        try {
            this.startLogIn(username, password, false);
            if(this.isAuthenticated) {
                // Write username to file
                FileWriter accountsFileWriter = new FileWriter("accounts.data", true);
                BufferedWriter accountsBufferedWriter = new BufferedWriter(accountsFileWriter);
                accountsBufferedWriter.write(username);
                accountsBufferedWriter.newLine();
                accountsBufferedWriter.close();
                accountsFileWriter.close();

                // Write cookies to file
                FileWriter cookiesFileWriter = new FileWriter("cookies.data", true);
                BufferedWriter cookiesBufferedWriter = new BufferedWriter(cookiesFileWriter);
                for(Cookie ck : driver.manage().getCookies())
                {
                    cookiesBufferedWriter.write((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));
                    cookiesBufferedWriter.newLine();
                }
                cookiesBufferedWriter.newLine();
                cookiesBufferedWriter.close();
                cookiesFileWriter.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void autoLogIn(String username, String password, List<String> accounts) {
        try {
            FileReader cookiesFileReader = new FileReader("cookies.data");
            BufferedReader cookiesBufferedReader = new BufferedReader(cookiesFileReader);
            String strline;
            List<List<String>> cookies = new ArrayList<>();
            List<String> cookie = new ArrayList<>();
            while ((strline = cookiesBufferedReader.readLine()) != null) {
                if (!strline.isEmpty()) {
                    cookie.add(strline);
                } else {
                    cookies.add(cookie);
                    cookie = new ArrayList<>();
                }
            }
            cookiesBufferedReader.close();
            cookiesFileReader.close();

            int accountIndex = accounts.indexOf(username);
            for (String s : cookies.get(accountIndex)) {
                StringTokenizer token = new StringTokenizer(s, ";");
                while (token.hasMoreTokens()) {
                    String name = token.nextToken();
                    String value = token.nextToken();
                    String domain = token.nextToken();
                    String path = token.nextToken();
                    Date expiry = null;
                    String val;
                    if (!(val = token.nextToken()).equals("null")) {
                        expiry = new SimpleDateFormat("E MMM dd HH:mm:ss zzz y").parse(val);
                    }
                    boolean isSecure = Boolean.parseBoolean(token.nextToken());
                    Date now = new Date();
                    if(name.equals("ds_user_id") && expiry.compareTo(now) <= 0) {
                        accounts.remove(accountIndex);
                        cookies.remove(accountIndex);
                        FileWriter accountsFileWriter = new FileWriter("accounts.data");
                        BufferedWriter accountsBufferedWriter = new BufferedWriter(accountsFileWriter);
                        accountsBufferedWriter.write("");
                        for(String acc : accounts) {
                            accountsBufferedWriter.append(acc);
                            accountsBufferedWriter.newLine();
                        }
                        accountsBufferedWriter.close();
                        accountsFileWriter.close();


                        FileWriter cookiesFileWriter = new FileWriter("cookies.data");
                        BufferedWriter cookiesBufferedWriter = new BufferedWriter(cookiesFileWriter);
                        cookiesBufferedWriter.write("");
                        for(int i=0; i<cookies.size(); i++) {
                            for(String ck : cookies.get(i)) {
                                cookiesBufferedWriter.append(ck);
                                cookiesBufferedWriter.newLine();
                            }
                            cookiesBufferedWriter.newLine();
                        }
                        cookiesBufferedReader.close();
                        cookiesBufferedWriter.close();
                        this.saveCookies(username, password);
                        return;
                    }
                    Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
                    driver.manage().addCookie(ck);
                }
            }
            this.startLogIn(username, username, true);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LoginPage(WebDriver driver) throws IOException {
        this.driver = driver;
    }
}
