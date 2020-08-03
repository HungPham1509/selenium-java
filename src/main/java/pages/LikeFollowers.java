package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LikeFollowers {
    private WebDriver driver;

    private By followerList = By.className("-nal3");

    private By follower = By.className("FPmhX");

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeButton = By.className("fr66n");

    public void getFollowerList() {
        List<WebElement> elements = driver.findElements(followerList);
        elements.get(1).click();
    }

    public void getFollower() {
        List<WebElement> elements = driver.findElements(follower);
        elements.get(0).click();
    }

    public void getPost() {
        List<WebElement> elements = driver.findElements(post);
        if(elements.size() > 0) {
            elements.get(0).click();
        }
    }

    public void close() {
        List<WebElement> elements = driver.findElements(svgElement);
        elements.get(elements.size() - 1).click();
    }

    public void likePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        String liked = elements.get(8).getAttribute("aria-label");
        if(elements.get(2).equals("Posts")) {
            liked = elements.get(9).getAttribute("aria-label");
        }
        if(liked.equals("Like")) {
            driver.findElement(likeButton).click();
        }
    }

    public void getBack() {
        driver.navigate().back();
        driver.navigate().back();
    }


    public LikeFollowers(WebDriver driver) {this.driver = driver;}
}
