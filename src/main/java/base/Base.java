package base;

import auxiliary.Auxiliary;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class Base {
    private WebDriver driver;

    protected LoginPage loginPage;

    protected LikeFollowers likeFollowers;

    protected LikeList likeList;

    protected LikeComments likeComments;

    protected LikeHashTag likeHashTag;

    protected Auxiliary auxiliary;

    private Logger logger = Logger.getLogger(Base.class);

    public void setUp(String instagram_username,
                      String instagram_password,
                      String like_category,
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

            switch (like_category) {
                case "like_followers":
                    likeFollowers = new LikeFollowers(driver);
                    for(int i=0; i<number_of_likes; i++) {
                        likeFollowers.StartLikeFollowers(excluded_users);
                        loginPage.profile(instagram_username);
                    }
                    break;
                case "like_comments":
                    likeComments = new LikeComments(driver);
                    likeComments.StartLikeComments(number_of_likes, excluded_users);
                    break;
                case "like_back":
                    likeList = new LikeList(driver);
                    likeList.StartLikeBack(number_of_likes, excluded_users);
                    break;
                case "like_hashtag":
                    likeHashTag = new LikeHashTag(driver);
                    for (String s : hashtags) likeHashTag.findHashTag(s, target, excluded_users);
                    break;
                default:
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
        String like_category = args[2];
        int number_of_likes = Integer.parseInt(args[3]);
        List<String> excluded_users = new ArrayList<>();
        List<String> hashtags = new ArrayList<>();
        String target = "Top";
        Base base = new Base();
        base.setUp(i_username, i_password, like_category, number_of_likes, excluded_users, hashtags, target);
        //base.tearDown();
    }
}
