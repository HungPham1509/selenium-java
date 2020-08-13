package pages;

import auxiliary.Auxiliary;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LikeList {
    private WebDriver driver;

    protected Auxiliary auxiliary = new Auxiliary();

    private Logger logger = Logger.getLogger(LikeList.class);

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeButton = By.className("fr66n");

    private By likeList = By.className("_8A5w5");

    private By liker = By.className("MBL3Z");

    private By nextArrow = By.className("coreSpriteRightPaginationArrow");

    private List<String> likedBackList = new ArrayList<>();

    private List<String> allLikers = new ArrayList<>();

    private int numberOfClikingNextTimes = 0;

    private boolean stopLike = false;

    public void StartLikeBack() {
        List<WebElement> elements = driver.findElements(post);
        if(elements.size() > 0) {
            try {
                elements.get(0).click();
                logger.info("Clicked to the latest post");
                Thread.sleep(auxiliary.delayBetween(2500, 3500));
                for(int i=0; i<3; i++) {
                    if(!stopLike) {
                        this.getLikeList();
                    }
                }
            }catch (Exception e) {
                logger.error("Failed to get the latest post.");
                e.printStackTrace();
            }
        }
        else {
            logger.warn("No post yet.");
        }
    }

    public void getLikeList() {
        try {
            List<WebElement> elements = driver.findElements(likeList);
            if(elements.size() > 0) {
                int index = 2;
                if(elements.get(2).getText().equals("")) {
                    index = 3;
                }
                if (!elements.get(0).getText().equals("Edit Profile") && !elements.get(2).getText().equals("")) {
                    index = 1;
                }

                String likeListText = elements.get(index).getText().split("\\s")[1];
                if(likeListText.equals("others") || likeListText.equals("like") || likeListText.equals("likes")) {
                    int numberOFLikes = 0;
                    if(likeListText.equals("others")) {
                        numberOFLikes = Integer.parseInt(elements.get(index).getText().split("\\s")[0]) + 1;
                    }
                    else {
                        numberOFLikes = Integer.parseInt(elements.get(index).getText().split("\\s")[0]);
                    }
                    if(this.likedBackList.size() < numberOFLikes) {
                        elements.get(index).click();
                        logger.info("Clicked to the like list of the post");
                        Thread.sleep(auxiliary.delayBetween(2000, 4000));
                        this.getLiker(numberOFLikes);
                    }
                }
                else {
                    logger.warn("No one has liked this post yet.");
                    List<WebElement> arrow = driver.findElements(nextArrow);
                    if(arrow.size() > 0) {
                        arrow.get(0).click();
                        logger.info("Moved to the next post because the previous post hadn't been liked");
                        Thread.sleep(auxiliary.delayBetween(3000, 4000));
                        this.getLikeList();
                    }
                    else {
                        logger.warn("Out of post");
                        this.stopLike = true;
                        Thread.sleep(auxiliary.delayBetween(3000, 4000));
                    }
                }
            }
        }catch (Exception e) {
            logger.error("Couldn't get the like list");
            e.printStackTrace();
        }
    }

    public void getLiker(int numberOfLikes) {
        try {
            List<WebElement> elements = driver.findElements(liker);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            List<String> likersInOneScroll = new ArrayList<>();
            if(elements.size() > 0) {
                for (WebElement element : elements) {
                    if (!this.allLikers.contains(element.getText())) {
                        this.allLikers.add(element.getText());
                    }
                }
                if(numberOfLikes > 11) {
                    int x = numberOfLikes / 11 + 2;
                    for (int i = 0; i < x; i++) {
                        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(elements.size() - 1));
                        Thread.sleep(auxiliary.delayBetween(800, 1500));
                        elements = driver.findElements(liker);
                        for (WebElement element : elements) {
                            if (!this.allLikers.contains(element.getText())) {
                                this.allLikers.add(element.getText());
                            }
                        }
                    }
                }
                int randomLikerIndex = new Random().nextInt(this.allLikers.size());
                while (this.likedBackList.contains(this.allLikers.get(randomLikerIndex))) {
                    randomLikerIndex = new Random().nextInt(this.allLikers.size());
                }

                this.likedBackList.add(this.allLikers.get(randomLikerIndex));

                for(WebElement element: elements) {
                    if(!likersInOneScroll.contains(element.getText())) {
                        likersInOneScroll.add(element.getText());
                    }
                }
                while (!likersInOneScroll.contains(this.allLikers.get(randomLikerIndex))) {
                    javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(0));
                    Thread.sleep(auxiliary.delayBetween(800, 1500));
                    elements = driver.findElements(liker);
                    for(WebElement element: elements) {
                        if(!likersInOneScroll.contains(element.getText())) {
                            likersInOneScroll.add(element.getText());
                        }
                    }
                }
                WebElement pickedOne = elements.get(0);
                for(WebElement element: elements) {
                    if(element.getText().equals(this.allLikers.get(randomLikerIndex))) {
                        pickedOne = element;
                    }
                }
                Thread.sleep(auxiliary.delayBetween(2000, 3000));
                pickedOne.click();
                logger.info("Moved to " + this.allLikers.get(randomLikerIndex) + "'s profile");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
                this.getPost();
            }
        }catch (Exception e) {
            logger.error("Couldn't get the liker");
            e.printStackTrace();
        }
    }

    public void getPost() {
        try {
            this.numberOfClikingNextTimes = 0;
            List<WebElement> elements = driver.findElements(post);
            if(elements.size() > 0) {
                elements.get(0).click();
                logger.info("Clicked to the latest post of picked liker");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
                this.likePost();
            }
            else {
                logger.warn("The liker has no post yet.");
                driver.navigate().back();
                logger.info("Returned to my latest post");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }
        }catch (Exception e) {
            logger.error("Couldn't get the post");
            e.printStackTrace();
        }
    }

    public void closePost() {
        List<WebElement> elements = driver.findElements(svgElement);
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

    public void likePost() {
        try {
            List<WebElement> elements = driver.findElements(svgElement);
            String liked = elements.get(8).getAttribute("aria-label");
            if(elements.get(2).getAttribute("aria-label").equals("Posts")) {
                liked = elements.get(9).getAttribute("aria-label");
            }
            if(liked.equals("Like")) {
                //driver.findElement(likeButton).click();
                logger.info("Liked the post.");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
                this.closePost();
                this.getBack();
            }
            else if(liked.equals("Unlike")){
                List<WebElement> arrow = driver.findElements(nextArrow);
                if(arrow.size() > 0) {
                    arrow.get(0).click();
                    this.numberOfClikingNextTimes += 1;
                    logger.info("Moved to the next post because the previous post had been liked");
                    Thread.sleep(auxiliary.delayBetween(3000, 4000));
                    this.likePost();
                }
                else {
                    logger.warn("Out of post");
                    Thread.sleep(auxiliary.delayBetween(3000, 4000));
                    this.closePost();
                    this.getBack();
                }
            }
            else {
                logger.warn("Couldn't find the like button");
            }
        }catch (Exception e) {
            logger.error("Failed to like the post.");
            e.printStackTrace();
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
            logger.info("Returned to the lastest post");
            TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
        }catch (Exception e) {
            logger.error("Failed to return the latest post.");
            e.printStackTrace();
        }
    }

    public LikeList(WebDriver driver) {this.driver = driver;}
}
