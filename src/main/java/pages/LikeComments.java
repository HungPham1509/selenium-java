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

public class LikeComments {
    private WebDriver driver;

    protected Auxiliary auxiliary = new Auxiliary();

    private Logger logger = Logger.getLogger(LikeComments.class);

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeCommentButton = By.className("_2ic5v");

    private By moreCommentsButton = By.className("glyphsSpriteCircle_add__outline__24__grey_9");

    private By nextArrow = By.className("coreSpriteRightPaginationArrow");

    public void StartLikeComments(int number_of_likes, List<String> excluded_users) {
        List<WebElement> elements = driver.findElements(InstagramElements.post);
        if(elements.size() > 0) {
            try {
                elements.get(0).click();
                logger.info("Clicked to the latest post.");
                Thread.sleep(auxiliary.delayBetween(2500, 3500));
                this.likeComment(number_of_likes);
            }catch (Exception e) {
                logger.error("Failed to get the latest post.");
                e.printStackTrace();
            }
        }
        else {
            logger.warn("No post yet.");
        }
    }

    public void likeComment(int number_of_likes) {
        try {
            List<String> likeStatus = new ArrayList<String>();
            int index = 14;
            List<WebElement> elements = driver.findElements(InstagramElements.svgElement);
            List<WebElement> likeCommentButtons = driver.findElements(InstagramElements.likeCommentButton);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            // check whether instagram account has igtv or not
            if(elements.get(2).getAttribute("aria-label").equals("Posts")) {
                index = 15;
            }
            if(likeCommentButtons.size() > 0) {
                for(int i=0; i<5; i++) {
                    this.moreComments();
                }
                elements = driver.findElements(InstagramElements.svgElement);
                likeCommentButtons = driver.findElements(InstagramElements.likeCommentButton);
                for (int i = index; i < elements.size(); i++) {
                    if (elements.get(i).getAttribute("aria-label").equals("Like") || elements.get(i).getAttribute("aria-label").equals("Unlike")) {
                        likeStatus.add(elements.get(i).getAttribute("aria-label"));
                    }
                }

                for(int j=0; j<number_of_likes; j++) {
                    int randomCommentIndex = new Random().nextInt(likeCommentButtons.size());
                    javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", likeCommentButtons.get(randomCommentIndex));
                    logger.info("Moved to picked comment");
                    TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
                    if (likeStatus.get(randomCommentIndex).equals("Like")) {
                        //likeCommentButtons.get(randomCommentIndex).click();
                        logger.info("Liked picked comment");
                        Thread.sleep(auxiliary.delayBetween(3000, 5000));
                    }
                }
                this.closePost();
            }
            else {
                logger.warn("No comment yet.");
                List<WebElement> arrow = driver.findElements(InstagramElements.nextArrow);
                if(arrow.size() > 0) {
                    arrow.get(0).click();
                    logger.info("Moved to the next post because the previous post had no comment");
                    Thread.sleep(auxiliary.delayBetween(3000, 4000));
                    this.likeComment(number_of_likes);
                }
                else {
                    logger.warn("Out of post");
                    Thread.sleep(auxiliary.delayBetween(3000, 4000));
                    this.closePost();
                }
            }
        }catch (Exception e) {
            logger.error("Failed to like comments");
            e.printStackTrace();
        }
    }

    public void moreComments() {
        try {
            boolean button = driver.findElements(InstagramElements.moreCommentsButton).size() > 0;
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            if(button) {
                javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElements(InstagramElements.moreCommentsButton).get(0));
                Thread.sleep(auxiliary.delayBetween(2500, 3500));
                driver.findElements(InstagramElements.moreCommentsButton).get(0).click();
                logger.info("Clicked to more comments");
                Thread.sleep(auxiliary.delayBetween(3000, 4000));
            }
        }catch (Exception e) {
            logger.error("Failed to load more comments");
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

    public LikeComments(WebDriver driver) {this.driver = driver;}
}
