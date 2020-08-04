package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class LikeList {
    private WebDriver driver;

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeButton = By.className("fr66n");

    private By likeList = By.className("yWX7d");

    private By liker = By.className("FPmhX");

    public void getPost() {
        List<WebElement> elements = driver.findElements(post);
        if(elements.size() > 0) {
            elements.get(0).click();
        }
    }

    public void getLikeList() {
        List<WebElement> elements = driver.findElements(likeList);
        if(elements.size() > 1) {
            if(elements.get(1).equals("Follow")) {
                elements.get(3).click();
            }
            else {
                elements.get(2).click();
            }
        }
    }

    public void getLiker() {
        List<WebElement> elements = driver.findElements(liker);
        if(elements.size() > 1) {
            elements.get(1).click();
        }
    }

    public void closePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        if(elements.get(elements.size() - 1).equals("Close")) {
            elements.get(elements.size() - 1).click();
        }
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

    public void backToProfile() {
        driver.navigate().back();
    }

    public LikeList(WebDriver driver) {this.driver = driver;}
}
