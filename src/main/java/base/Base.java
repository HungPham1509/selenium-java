package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Base {
    private WebDriver driver;

    private Logger logger;

    protected LoginPage loginPage;

    protected LikeFollowers likeFollowers;

    protected LikeList likeList;

    protected LikeComments likeComments;

    protected LikeHashTag likeHashTag;

    private String username = "kawaken.izakaya"; // kawaken.izakaya
    private String password = "kawaken080808"; //kawaken080808
    private List<String> taglist = new ArrayList<String>() {{add("chiba"); add("kanagawa");}};

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();

        logger = Logger.getLogger(Base.class.getName());

        try {
            // Login
            driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
            logger.log(Level.INFO, "Accessed Instagram log in page");
            TimeUnit.SECONDS.sleep(1);
            loginPage = new LoginPage(driver);
            loginPage.setUsernameField(username);
            logger.log(Level.INFO, "Filled in username input");
            TimeUnit.SECONDS.sleep(1);
            loginPage.setPasswordField(password);
            logger.log(Level.INFO, "Filled in password input");
            TimeUnit.SECONDS.sleep(1);
            loginPage.clickLoginButton();
            logger.log(Level.INFO, "Clicked log in button");
            TimeUnit.SECONDS.sleep((int)(Math.random()*((5-3)+1))+3);
            loginPage.profile(username);
            logger.log(Level.INFO, "Accessed profile");
            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);

            //Like follower
            likeFollowers = new LikeFollowers(driver);
            for(int i=0; i<3; i++) {
                likeFollowers.StartLikeFollowers();
                loginPage.profile(username);
                logger.log(Level.INFO, "Returned to profile");
            }
            logger.log(Level.INFO, "Finished like followers function");
//
//            //Like users who liked your post
            likeList = new LikeList(driver);
            likeList.StartLikeBack();
            likeList.backToProfile();

            logger.log(Level.INFO, "Finished like back users function");

            // Like comments
            LikeComments likeComments = new LikeComments(driver);
            likeComments.StartLikeComments();
            logger.log(Level.INFO, "Finished like comments function");

            TimeUnit.SECONDS.sleep(6);

            // like hashtag
//            LikeHashTag likeHashTag = new LikeHashTag(driver);
//            for(int i=0; i<taglist.size(); i++) {
//                likeHashTag.findHashTag(taglist.get(i));
//                TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
//                likeHashTag.getRecentlyPost();
//                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//                likeHashTag.likePost();
//                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//                likeHashTag.nextPost();
//                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//                likeHashTag.likePost();
//                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//                likeHashTag.closePost();
//                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tearDown() {
        driver.quit();
        logger.log(Level.INFO, "Closed the browser");
    }

    public static void main(String args[]) {
        Base base = new Base();
        base.setUp();
        base.tearDown();
    }
}
