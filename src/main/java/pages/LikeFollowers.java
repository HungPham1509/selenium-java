package pages;

import auxiliary.Auxiliary;
import auxiliary.InstagramUtils;
import com.google.gson.JsonObject;
import config.InstagramElements;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LikeFollowers {
    private WebDriver driver;

    private Logger logger = Logger.getLogger(LikeFollowers.class);

    protected Auxiliary auxiliary = new Auxiliary();

    private List<String> chosenFollowerList = new ArrayList<>();

    private List<JsonObject> likedFollowerList = new ArrayList<>();

    private int numberOfClikingNextTimes = 0;

    InstagramUtils instagramUtils;

    List<String> followerList = new ArrayList<>();

    public void StartLikeFollowers(int shop_id, int category, int number_of_likes, List<String> excluded_users) {
        try {
            instagramUtils = new InstagramUtils(driver);
            int number_of_followers = instagramUtils.getNumberOfFollowers();
            followerList = instagramUtils.getFollowerList("Hello: ", number_of_followers);
            if(number_of_followers > 0) {
                for(int i=0; i<number_of_likes; i++) {
                    if(this.chosenFollowerList.size() < number_of_followers) {
                        this.getFollower(number_of_followers);
                        if(this.numberOfClikingNextTimes >= 0) {
                            this.getBack();
                        }
                    }
                }
            }
            else {
                logger.warn("No follower yet");
                this.close();
            }
        }catch (Exception e) {
            logger.error("Failed to get follower list");
            e.printStackTrace();
        }
    }

    public void getFollower(int number_of_followers) {
        try {
            List<WebElement> elements = driver.findElements(InstagramElements.follower);
            List<WebElement> followerName = driver.findElements(InstagramElements.followerName);
            if(followerName.size() == 0) {
                followerName = driver.findElements(InstagramElements.followerName2);
            }

            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            int randomFollowerIndex = new Random().nextInt(number_of_followers);
            while (this.chosenFollowerList.contains(elements.get(randomFollowerIndex).getText())) {
                randomFollowerIndex = new Random().nextInt(elements.size());
            }
            this.chosenFollowerList.add(elements.get(randomFollowerIndex).getText());
            javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(randomFollowerIndex));
            String username = elements.get(randomFollowerIndex).getText();
            String name = followerName.get(randomFollowerIndex).getText();
            logger.info("Moved to picked follower: " + elements.get(randomFollowerIndex).getText());
            TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
            elements.get(randomFollowerIndex).click();
            this.numberOfClikingNextTimes = 0;
            logger.info("Clicked to picked follower");
            TimeUnit.SECONDS.sleep(auxiliary.delayBetween(4, 6));
            this.getPost(username, name);
        }catch (Exception e) {
            logger.error("Failed to get follower");
            e.printStackTrace();
        }
    }

    public void getPost(String username, String name) {
        List<WebElement> elements = driver.findElements(InstagramElements.post);
        if(elements.size() > 0) {
            try {
                elements.get(0).click();
                logger.info("Clicked to the latest post of picked follower");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
                this.likePost(username, name);
            }catch (Exception e) {
                logger.error("Failed to get post");
                e.printStackTrace();
            }
        }
        else {
            logger.warn("No post yet");
            try {
                this.numberOfClikingNextTimes = -1;
                driver.navigate().back();
                logger.info("Returned to follower list");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }catch (Exception e) {
                logger.error("Failed to return");
                e.printStackTrace();
            }
        }
    }

    public void close() {
        List<WebElement> elements = driver.findElements(InstagramElements.svgElement);
        if(elements.get(elements.size() - 1).getAttribute("aria-label").equals("Close")) {
            try {
                elements.get(elements.size() - 1).click();
                logger.info("Closed the post");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
            }catch (Exception e) {
                logger.error("Failed to closed the post");
                e.printStackTrace();
            }
        }else {
            logger.warn("Couldn't find close button");
        }
    }

    public void likePost(String username, String name) {
        List<WebElement> elements = driver.findElements(InstagramElements.svgElement);
        String liked = elements.get(8).getAttribute("aria-label");
        if(elements.get(2).getAttribute("aria-label").equals("Posts")) {
            liked = elements.get(9).getAttribute("aria-label");
        }

        if(liked.equals("Like")) {
            try {
                //driver.findElement(InstagramElements.likeButton).click();
                logger.info("Liked the post");
                Thread.sleep(auxiliary.delayBetween(3000, 4500));
                this.close();
                instagramUtils = new InstagramUtils(driver);
                JsonObject user = new JsonObject();
                user.addProperty("username", username);
                user.addProperty("name", name);
                likedFollowerList.add(user);
            }catch (Exception e) {
                logger.error("Failed to like the post");
                e.printStackTrace();
            }
        }
        else if(liked.equals("Unlike")){
            logger.info("You have liked this post.");
            try {
                List<WebElement> arrow = driver.findElements(InstagramElements.nextArrow);
                if(arrow.size() > 0) {
                    arrow.get(0).click();
                    this.numberOfClikingNextTimes +=1;
                    logger.info("Moved to the next post because the previous post had been liked");
                    Thread.sleep(auxiliary.delayBetween(3000, 4000));
                    this.likePost(username, name);
                }
                else {
                    Thread.sleep(auxiliary.delayBetween(1000, 2000));
                    logger.warn("Out of post");
                    Thread.sleep(auxiliary.delayBetween(3000, 4000));
                    this.close();
                }
            }catch (Exception e) {
                logger.error("Failed to move to the next post");
                e.printStackTrace();
            }
        }
        else {
            logger.warn("Couldn't find like button");
        }
    }

    public void getBack() {
        try {
            if(this.numberOfClikingNextTimes > 0) {
                for(int i=0; i<this.numberOfClikingNextTimes; i++) {
                    driver.navigate().back();
                    Thread.sleep(auxiliary.delayBetween(2000, 3000));
                }
            }
            driver.navigate().back();
            Thread.sleep(auxiliary.delayBetween(2000, 3000));
            driver.navigate().back();
            logger.info("Returned to the follower list");
            TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
        }catch (Exception e) {
            logger.error("Failed to return the follower list.");
            e.printStackTrace();
        }
    }



    public LikeFollowers(WebDriver driver) {this.driver = driver;}
}
