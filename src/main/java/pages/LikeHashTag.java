package pages;

import auxiliary.Auxiliary;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LikeHashTag {
    private WebDriver driver;

    private Actions actions;

    private FileWriter fileWriter;

    private BufferedWriter bufferedWriter;

    protected Auxiliary auxiliary = new Auxiliary();

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeButton = By.className("fr66n");

    private By recentlyPosts = By.className("yQ0j1");

    private By searchInput = By.className("XTCLo");

    private By nextArrow = By.className("coreSpriteRightPaginationArrow");

    public void findHashTag(String tag) {
        List<WebElement> elements = driver.findElements(searchInput);
        System.out.println(elements.size());
//        try {
//            fileWriter = new FileWriter("resources/history.txt", true);
//            bufferedWriter = new BufferedWriter(fileWriter);
//            driver.get("https://www.instagram.com/explore/tags/" + tag);
//            bufferedWriter.write("Moved to target hashtag " + tag);
//            bufferedWriter.newLine();
//            bufferedWriter.close();
//            TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
//        }
//        catch (InterruptedException | IOException e) {
//            e.printStackTrace();
//        }
    }

    public void getRecentlyPost() {
        try {
            if(driver.findElements(recentlyPosts).size() > 0) {
                actions.moveToElement(driver.findElement(recentlyPosts)).perform();
                TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
                List<WebElement> posts = driver.findElements(post);
                posts.get(9).click();
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void likePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        String liked = elements.get(5).getAttribute("aria-label");
        if(liked.equals("Like")) {
            driver.findElement(likeButton).click();
        }
    }

    public void nextPost() {
        if(driver.findElement(nextArrow) != null) {
            driver.findElement(nextArrow).click();
        }
    }
    public void closePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        if(elements.get(elements.size() - 1).equals("Close")) {
            elements.get(elements.size() - 1).click();
        }
    }

    public LikeHashTag(WebDriver driver) {this.driver = driver; this.actions = new Actions(driver);}
}
