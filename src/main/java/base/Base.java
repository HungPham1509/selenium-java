package base;

import auxiliary.Auxiliary;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Base {
    private WebDriver driver;

    protected LoginPage loginPage;

    protected LikeFollowers likeFollowers;

    protected LikeList likeList;

    protected LikeComments likeComments;

    protected LikeHashTag likeHashTag;

    protected Auxiliary auxiliary;

    private Logger logger = Logger.getLogger(Base.class);

    public void setUp(int shop_id,
                      String instagram_username,
                      String instagram_password,
                      int like_category,
                      int number_of_likes,
                      List<String> excluded_users,
                      List<String> hashtags,
                      String target) {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "warn");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");
        chromeOptions.addArguments("window-size=1600x900");
        driver = new ChromeDriver();
        auxiliary = new Auxiliary();


        try {
            // Login
            driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
            loginPage = new LoginPage(driver);
            FileReader accountsFileReader = new FileReader("accounts.data");
            BufferedReader accountsBufferedReader = new BufferedReader(accountsFileReader);
            String account;
            List<String> accounts = new ArrayList<>();
            while ((account=accountsBufferedReader.readLine()) != null) {
                accounts.add(account);
            }
            accountsBufferedReader.close();
            accountsFileReader.close();
            if(accounts.contains(instagram_username)) {
                loginPage.autoLogIn(instagram_username, instagram_password, accounts);
            }
            else {
                loginPage.saveCookies(instagram_username, instagram_password);
            }

            if(loginPage.isAuthenticated) {
                switch (like_category) {
                    case 2:
                        likeFollowers = new LikeFollowers(driver);
                        likeFollowers.StartLikeFollowers(shop_id, like_category, number_of_likes, excluded_users);
                        break;
                    case 4:
                        likeComments = new LikeComments(driver);
                        likeComments.StartLikeComments(number_of_likes, excluded_users);
                        break;
                    case 3:
                        likeList = new LikeList(driver);
                        likeList.StartLikeBack(number_of_likes, excluded_users);
                        break;
                    case 1:
                        likeHashTag = new LikeHashTag(driver);
                        for (String s : hashtags) likeHashTag.findHashTag(number_of_likes, s, target, excluded_users);
                        break;
                    default:
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void tearDown() {
        try {
            Thread.sleep(auxiliary.delayBetween(1500, 3000));
            driver.quit();
            logger.info("Job Complete.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String i_username = args[0];
        String i_password = args[1];
        int like_category = Integer.parseInt(args[2]);
        int number_of_likes = Integer.parseInt(args[3]);
        List<String> excluded_users = new ArrayList<>();
        List<String> hashtags = new ArrayList<>();
        hashtags.add("#chiba");
        String target = "Top";
        Base base = new Base();
        base.setUp(905, i_username, i_password, like_category, number_of_likes, excluded_users, hashtags, target);
        //base.tearDown();
    }
}
