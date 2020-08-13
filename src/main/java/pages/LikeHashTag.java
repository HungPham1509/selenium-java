package pages;

import auxiliary.Auxiliary;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
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

    private int numberOfLikes = 0;

    public void findHashTag(String tag, String postType) {
        try {
            WebElement search = driver.findElement(searchInput);
            for (int i=0; i<tag.length(); i++) {
                char c = tag.charAt(i);
                String s = String.valueOf(c);
                int delayTime = auxiliary.delayBetween(300, 500);
                search.sendKeys(s);
                Thread.sleep(delayTime);
            }
            Thread.sleep(auxiliary.delayBetween(2000, 3000));
            List<WebElement> results = driver.findElements(searchResult);
            List<WebElement> names = driver.findElements(searhName);
            if(results.size() > 0) {
                for(int i=0; i<results.size(); i++) {
                    if(names.get(i).getText().equals(tag)) {
                        results.get(i).click();
                    }
                }
                logger.info("Moved to target hashtag: " + tag);
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
                this.getPost(postType);
            }
            else {
                search.clear();
                logger.warn("Couldn't find hashtag.");
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }
        }
        catch (Exception e) {
            logger.error("Failed to find hashtag.");
            e.printStackTrace();
        }
    }

    public void getPost(String postType) {
        try {
            List<WebElement> elements = driver.findElements(post);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            this.numberOfLikes = 0;
            if(elements.size() > 0) {
                if (postType.equals("Top")) {
                    elements.get(0).click();
                    logger.info("Clicked to the first top post");
                }
                if (postType.equals("Recent")) {
                    javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(recentlyPosts));
                    Thread.sleep(auxiliary.delayBetween(2000, 3000));
                    elements.get(9).click();
                    logger.info("Clicked to the first recently post");
                }
                Thread.sleep(auxiliary.delayBetween(2500, 3500));
                this.likePost();
            }
            else {
                logger.warn("No post yet");
            }
        }catch (Exception e) {
            logger.error("Failed to get post.");
            e.printStackTrace();
        }
    }

    public void likePost() {
        try {
            List<WebElement> elements = driver.findElements(svgElement);
            String liked = elements.get(5).getAttribute("aria-label");
            if(liked.equals("Like")) {
                    //driver.findElement(likeButton).click();
                    numberOfLikes +=1;
                    logger.info("Liked the post.");
                    TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }
            List<WebElement> arrow = driver.findElements(nextArrow);
            if(arrow.size() > 0 && this.numberOfLikes < 3) {
                arrow.get(0).click();
                logger.info("Moved to the next post because the previous post had been liked");
                Thread.sleep(auxiliary.delayBetween(3000, 4000));
                this.likePost();
            }
            else {
                logger.warn("Out of post");
                this.closePost();
            }
        }catch (Exception e) {
            logger.error("Failed to like the post");
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

    public LikeHashTag(WebDriver driver) {this.driver = driver;}
}
