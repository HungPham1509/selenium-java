package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LikeComments {
    private WebDriver driver;

    private Actions actions;

    private Action action;

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeCommentButton = By.className("_2ic5v");

    private By moreCommentsButton = By.className("glyphsSpriteCircle_add__outline__24__grey_9");

    public void getPost() {
        List<WebElement> elements = driver.findElements(post);
        if(elements.size() > 0) {
            elements.get(0).click();
        }
    }

    public void likeComment() {
        List<WebElement> elements = driver.findElements(svgElement);
        List<WebElement> likeCommentButtons = driver.findElements(likeCommentButton);
        int index = 14;
        // check whether instagram account has igtv or not
        if(elements.get(2).equals("Posts")) {
            index = 15;
        }
        if(likeCommentButtons.size() > 0) {
            List<String> likeStatus = new ArrayList<String>();
            for (int i = index; i < elements.size(); i++) {
                if (elements.get(i).getAttribute("aria-label").equals("Like") || elements.get(i).getAttribute("aria-label").equals("Unlike")) {
                    likeStatus.add(elements.get(i).getAttribute("aria-label"));
                }
            }

            int randomCommentIndex = new Random().nextInt(likeCommentButtons.size());
            actions.moveToElement(likeCommentButtons.get(randomCommentIndex)).perform();

            if (likeStatus.get(randomCommentIndex).equals("Like")) {
                likeCommentButtons.get(randomCommentIndex).click();
            }
        }
    }

    public void moreComments() {
        Boolean button = driver.findElements(moreCommentsButton).size() > 0;
        if(button) {
            try {
                actions.moveToElement(driver.findElements(moreCommentsButton).get(0)).perform();
                TimeUnit.SECONDS.sleep(1);
                driver.findElements(moreCommentsButton).get(0).click();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void closePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        elements.get(elements.size() - 1).click();
    }

    public LikeComments(WebDriver driver) {this.driver = driver; this.actions = new Actions(driver);}
}
