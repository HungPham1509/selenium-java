package pages;

import auxiliary.Auxiliary;
import config.InstagramElements;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LikeHashTag {
    private WebDriver driver;

    protected Auxiliary auxiliary = new Auxiliary();

    private Logger logger = Logger.getLogger(LikeHashTag.class);

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeButton = By.className("fr66n");

    private By recentlyPosts = By.className("yQ0j1");

    private By searchInput = By.className("XTCLo");

    private By searchResult = By.className("yCE8d");

    private By searhName = By.className("Ap253");

    private By nextArrow = By.className("coreSpriteRightPaginationArrow");

    private List<String> likedUsernameList = new ArrayList<>();

    private Map<String, String> likedNameList = new HashMap<>();

    private int numberOfLikes = 0;

    private int numberOfTopPosts = 1;

    private int numberOfNextTimes = 0;

    public void findHashTag(int number_of_likes, String tag, String postType, List<String> excluded_users) {
        try {
            WebElement search = driver.findElement(InstagramElements.searchInput);
            for (int i=0; i<tag.length(); i++) {
                char c = tag.charAt(i);
                String s = String.valueOf(c);
                int delayTime = auxiliary.delayBetween(300, 500);
                search.sendKeys(s);
                Thread.sleep(delayTime);
            }
            Thread.sleep(auxiliary.delayBetween(2000, 3000));
            List<WebElement> results = driver.findElements(InstagramElements.searchResult);
            List<WebElement> names = driver.findElements(InstagramElements.searhName);
            if(results.size() > 0) {
                for(int i=0; i<results.size(); i++) {
                    if(names.get(i).getText().equals(tag)) {
                        results.get(i).click();
                    }
                }
                logger.info("Moved to target hashtag: " + tag);
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
                this.getPost(postType, number_of_likes);
            }
            else {
                search.clear();
                logger.warn("Couldn't find hashtag.");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }
            for(Map.Entry entry : likedNameList.entrySet()) {
                System.out.println(entry.getKey() + "-" + entry.getValue());
            }
        }
        catch (Exception e) {
            logger.error("Failed to find hashtag.");
            e.printStackTrace();
        }
    }

    public void getPost(String postType, int number_of_likes) {
        try {
            List<WebElement> elements = driver.findElements(InstagramElements.post);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            this.numberOfLikes = 0;
            this.numberOfTopPosts = 1;
            if(elements.size() > 0) {
                if (postType.equals("Top")) {
                    elements.get(0).click();
                    logger.info("Clicked to the first top post");
                }
                if (postType.equals("Recent")) {
                    javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(InstagramElements.recentlyPosts));
                    Thread.sleep(auxiliary.delayBetween(2000, 3000));
                    elements.get(9).click();
                    logger.info("Clicked to the first recently post");
                }
                Thread.sleep(auxiliary.delayBetween(2500, 3500));
                this.likePost(postType, number_of_likes);
            }
            else {
                logger.warn("No post yet");
            }
        }catch (Exception e) {
            logger.error("Failed to get post.");
            e.printStackTrace();
        }
    }

    public void likePost(String postType, int number_of_likes) {
        try {
            List<WebElement> elements = driver.findElements(InstagramElements.svgElement);
            List<WebElement> usernames = driver.findElements(InstagramElements.commentUser);
            String liked = elements.get(5).getAttribute("aria-label");
            if(liked.equals("Like")) {
                    //driver.findElement(InstagramElements.likeButton).click();
                    numberOfLikes +=1;
                    logger.info("Liked the post.");
                    TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
                    if(usernames.size() > 0) {
                        likedUsernameList.add(usernames.get(0).getText());
                    }
            }
            List<WebElement> arrow = driver.findElements(InstagramElements.nextArrow);
            if(arrow.size() > 0 && this.numberOfLikes < number_of_likes && numberOfTopPosts < 9) {
                if(postType.equals("Top")) numberOfTopPosts+=1;
                arrow.get(0).click();
                numberOfNextTimes+=1;
                logger.info("Moved to the next post because the previous post had been liked");
                Thread.sleep(auxiliary.delayBetween(3000, 4000));
                this.likePost(postType, number_of_likes);
            }
            else {
                logger.warn("Out of post");
                for(int i=0; i<numberOfNextTimes+1; i++) {
                    usernames = driver.findElements(InstagramElements.commentUser);
                    if(usernames.size() > 0 && likedUsernameList.contains(usernames.get(0).getText())) {
                        String username = usernames.get(0).getText();
                        usernames.get(0).click();
                        logger.info("Moved to " + username + "'s profile");
                        Thread.sleep(auxiliary.delayBetween(3000, 4000));
                        List<WebElement> names = driver.findElements(InstagramElements.likerName);
                        if(names.size() > 0) {
                            likedNameList.put(username, names.get(0).getText());
                        }
                        driver.navigate().back();
                        logger.info("Back from " + username + "'s profile");
                        Thread.sleep(auxiliary.delayBetween(3500, 5000));
                        driver.navigate().back();
                        logger.info("Back to previous post");
                        Thread.sleep(auxiliary.delayBetween(3000, 4500));
                    }
                    else {
                        driver.navigate().back();
                        logger.info("Back to previous post");
                        Thread.sleep(auxiliary.delayBetween(3000, 4500));
                    }
                }
            }
        }catch (Exception e) {
            logger.error("Failed to like the post");
            e.printStackTrace();
        }
    }

    public void closePost() {
        List<WebElement> elements = driver.findElements(InstagramElements.svgElement);
        if(elements.get(elements.size() - 1).getAttribute("aria-label").equals("Close")) {
            try {
                elements.get(elements.size() - 1).click();
                logger.info("Closed the post");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
            }catch (Exception e) {
                logger.error("Couldn't close the post");
                e.printStackTrace();
            }
        }
        else {
            logger.warn("Couldn't find the close post button.");
        }
    }

    public LikeHashTag(WebDriver driver) {this.driver = driver;}
}
