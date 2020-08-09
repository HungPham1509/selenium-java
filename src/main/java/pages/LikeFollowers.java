package pages;

import base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LikeFollowers {
    private WebDriver driver;

    private Logger logger;

    private Actions actions;

    private By followerList = By.className("-nal3");

    private By follower = By.className("FPmhX");

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeButton = By.className("fr66n");

    private By nextArrow = By.className("coreSpriteRightPaginationArrow");

    private List<String> likedFollowerList = new ArrayList<>();

    public void StartLikeFollowers() {
        List<WebElement> elements = driver.findElements(followerList);
        if(elements.get(1).getText().contains("follower")) {
            try {
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                elements.get(1).click();
                logger.log(Level.INFO, "Clicked follower list");
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                this.getFollower();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void getFollower() {
        List<WebElement> elements = driver.findElements(follower);
        List<WebElement> elements1 = driver.findElements(followerList);
        int numberOFFollowers = 0;
        if(elements1.get(1).getText().contains("follower")) {
            numberOFFollowers = Integer.parseInt(elements1.get(1).getText().split("\\s")[0]);
        }
        if(numberOFFollowers > 0) {
            if(numberOFFollowers > 12) {
                try{
                    int x = numberOFFollowers / 12;
                    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                    for(int i=0; i<x; i++) {
                        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(elements.size() - 1));
                        TimeUnit.SECONDS.sleep(1);
                        elements = driver.findElements(follower);
                    }
                    logger.log(Level.INFO, "Scrolled the follower list to the bottom");
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                int randomFollowerIndex = new Random().nextInt(numberOFFollowers);
                while (this.likedFollowerList.contains(elements.get(randomFollowerIndex).getText())) {
                    randomFollowerIndex = new Random().nextInt(elements.size());
                }
                this.likedFollowerList.add(elements.get(randomFollowerIndex).getText());
                actions.moveToElement(elements.get(randomFollowerIndex)).perform();
                logger.log(Level.INFO, "Moved to picked follower");
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                elements.get(randomFollowerIndex).click();
                logger.log(Level.INFO, "Clicked to picked follower");
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                this.getPost();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            this.close();
        }
    }

    public void getPost() {
        List<WebElement> elements = driver.findElements(post);
        if(elements.size() > 0) {
            try {
                elements.get(0).click();
                logger.log(Level.INFO, "Clicked to the latest post of picked follower");
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                this.likePost();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                logger.log(Level.INFO, "Returned to follower list");
                driver.navigate().back();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        List<WebElement> elements = driver.findElements(svgElement);
        if(elements.get(elements.size() - 1).getAttribute("aria-label").equals("Close")) {
            try {
                elements.get(elements.size() - 1).click();
                logger.log(Level.INFO, "Closed the post");
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void likePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        String liked = elements.get(8).getAttribute("aria-label");
        if(elements.get(2).getAttribute("aria-label").equals("Posts")) {
            liked = elements.get(9).getAttribute("aria-label");
        }
        if(liked.equals("Like")) {
            try {
                driver.findElement(likeButton).click();
                logger.log(Level.INFO, "Liked the post");
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                this.close();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                List<WebElement> arrow = driver.findElements(nextArrow);
                if(arrow.size() > 0) {
                    arrow.get(0).click();
                    logger.log(Level.INFO, "Moved to next post because previous post had been liked");
                    TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                    this.likePost();
                }
                else {
                    this.close();
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public LikeFollowers(WebDriver driver) {this.driver = driver; this.actions = new Actions(driver); this.logger = Logger.getLogger(LikeFollowers.class.getName());}
}
