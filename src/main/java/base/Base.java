package base;

import auxiliary.Auxiliary;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import java.io.IOException;
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

    private List<String> taglist = new ArrayList<String>() {{add("#chiba"); add("#fsafdfsdgsjd"); add("#kanagawa");}};

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "warn");
        driver = new ChromeDriver();
        auxiliary = new Auxiliary();

        try {
            // Login
            loginPage = new LoginPage(driver);
            loginPage.startLogIn();

            //Like follower
//            likeFollowers = new LikeFollowers(driver);
//            for(int i=0; i<3; i++) {
//                likeFollowers.StartLikeFollowers();
//                loginPage.profile("pham_hung99");
//            }
//
//            //Like users who liked your post
            likeList = new LikeList(driver);
            likeList.StartLikeBack();
            //likeList.backToProfile();
//
//            logger.log(Level.INFO, "Finished like back users function");

            // Like comments
//            likeComments = new LikeComments(driver);
//            likeComments.StartLikeComments();
//            logger.log(Level.INFO, "Finished like comments function");
//
//            TimeUnit.SECONDS.sleep(6);

            // like hashtag
//            likeHashTag = new LikeHashTag(driver);
//            for (String s : taglist) likeHashTag.findHashTag(s, "Top");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tearDown() {
        driver.quit();
    }

    public static void main(String[] args) {
        Base base = new Base();
        base.setUp();
        //base.tearDown();
    }
}
