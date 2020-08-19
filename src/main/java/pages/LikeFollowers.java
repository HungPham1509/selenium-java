package pages;

import auxiliary.Auxiliary;
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

    private By followerList = By.className("-nal3");

    private By follower = By.className("FPmhX");

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeButton = By.className("fr66n");

    private By nextArrow = By.className("coreSpriteRightPaginationArrow");

    private List<String> likedFollowerList = new ArrayList<>();

    private int numberOfClikingNextTimes = 0;

    public void StartLikeFollowers(int  number_of_likes, List<String> excluded_users) {
        try {
            List<WebElement> elements1 = driver.findElements(InstagramElements.followerList);
            int numberOFFollowers = 0;
            if(elements1.get(1).getText().contains("follower")) {
                numberOFFollowers = Integer.parseInt(elements1.get(1).getText().split("\\s")[0]);
            }
            else {
                logger.error("Couldn't get the number of followers");
            }
            if(numberOFFollowers > 0) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                if(elements1.get(1).getText().contains("follower")) {
                    elements1.get(1).click();
                    logger.info("Clicked follower list");
                    int delayTime = auxiliary.delayBetween(2, 4);
                    TimeUnit.SECONDS.sleep(delayTime);
                }
                else {
                    logger.warn("Couldn't find follower list button.");
                }
                List<WebElement> elements = driver.findElements(InstagramElements.follower);
                if (numberOFFollowers > 12) {
                    try {
                        int x = numberOFFollowers / 12;
                        for (int i = 0; i < x; i++) {
                            javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(elements.size() - 1));
                            Thread.sleep(auxiliary.delayBetween(800, 1500));
                            elements = driver.findElements(InstagramElements.follower);
                        }
                        logger.info("Scrolled the follower list to the bottom");
                    } catch (Exception e) {
                        logger.error("Failed to scrolled the follower list to the bottom");
                        e.printStackTrace();
                    }
                }
                for(int i=0; i<number_of_likes; i++) {
                    if(this.likedFollowerList.size() < numberOFFollowers) {
                        this.getFollower(numberOFFollowers);
                        if(this.numberOfClikingNextTimes >= 0) {
                            this.getBack();
                        }
                    }
                }
                System.out.println(likedFollowerList);
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
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            int randomFollowerIndex = new Random().nextInt(number_of_followers);
            while (this.likedFollowerList.contains(elements.get(randomFollowerIndex).getText())) {
                randomFollowerIndex = new Random().nextInt(elements.size());
            }
            this.likedFollowerList.add(elements.get(randomFollowerIndex).getText());
            javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(randomFollowerIndex));
            logger.info("Moved to picked follower: " + elements.get(randomFollowerIndex).getText());
            TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
            elements.get(randomFollowerIndex).click();
            this.numberOfClikingNextTimes = 0;
            logger.info("Clicked to picked follower");
            TimeUnit.SECONDS.sleep(auxiliary.delayBetween(4, 6));
            this.getPost();
        }catch (Exception e) {
            logger.error("Failed to get follower");
            e.printStackTrace();
        }
    }

    public void getPost() {
        List<WebElement> elements = driver.findElements(InstagramElements.post);
        if(elements.size() > 0) {
            try {
                elements.get(0).click();
                logger.info("Clicked to the latest post of picked follower");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
                this.likePost();
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

    public void likePost() {
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
                    this.likePost();
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
