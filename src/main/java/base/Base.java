package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Base {
    private WebDriver driver;

    protected LoginPage loginPage;

    protected LikeFollowers likeFollowers;

    protected LikeList likeList;

    protected LikeComments likeComments;

    protected LikeHashTag likeHashTag;

    private String username = "hung_pham99"; // kawaken.izakaya
    private String password = "thtmhfc1509"; //kawaken080808
    private List<String> taglist = new ArrayList<String>() {{add("chiba"); add("kanagawa");}};

    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();

        try {
            // Login
            driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
            TimeUnit.SECONDS.sleep(1);
            loginPage = new LoginPage(driver);
            loginPage.setUsernameField(username);
            TimeUnit.SECONDS.sleep(1);
            loginPage.setPasswordField(password);
            TimeUnit.SECONDS.sleep(1);
            loginPage.clickLoginButton();
            TimeUnit.SECONDS.sleep((int)(Math.random()*((5-3)+1))+3);
            loginPage.profile(username);
            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);

            //Like follower
            likeFollowers = new LikeFollowers(driver);
            likeFollowers.getFollowerList();
            loginPage.profile(username);

//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//            likeFollowers.getFollower();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//            likeFollowers.getPost();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//            likeFollowers.likePost();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((2-1)+1))+1);
//            likeFollowers.close();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//            likeFollowers.getBack();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//            likeFollowers.close();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);

            //Like users who liked your post
//            likeList = new LikeList(driver);
//            likeList.getPost();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
//            likeList.getLikeList();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
//            likeList.getLiker();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
//            likeList.getPost();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//            likeList.likePost();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
//            likeList.closePost();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//            likeList.getBack();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
//            likeList.backToProfile();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);

            // Like comments
//            LikeComments likeComments = new LikeComments(driver);
//            likeComments.getPost();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
//            likeComments.likeComment();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
//            likeComments.moreComments();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
//            likeComments.closePost();
//            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);

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
    }

    public static void main(String args[]) {
        Base base = new Base();
        base.setUp();
    }
}
