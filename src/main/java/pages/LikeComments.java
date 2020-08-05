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

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeCommentButton = By.className("_2ic5v");

    private By moreCommentsButton = By.className("glyphsSpriteCircle_add__outline__24__grey_9");

    private int index = 14;

    private List<String> likeStatus = new ArrayList<String>();

    public void StartLikeComments() {
        List<WebElement> elements = driver.findElements(post);
        if(elements.size() > 0) {
            try {
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                elements.get(0).click();
                TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
                this.likeComment();
                this.closePost();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("No post yet");
        }
    }

    public void likeComment() {
        List<WebElement> elements = driver.findElements(svgElement);
        List<WebElement> likeCommentButtons = driver.findElements(likeCommentButton);
        // check whether instagram account has igtv or not
        if(elements.get(2).equals("Posts")) {
            this.index = 15;
        }
        if(likeCommentButtons.size() > 0) {
            for(int j=0; j<3; j++) {
                for (int i = this.index; i < elements.size(); i++) {
                    if (elements.get(i).getAttribute("aria-label").equals("Like") || elements.get(i).getAttribute("aria-label").equals("Unlike")) {
                        this.likeStatus.add(elements.get(i).getAttribute("aria-label"));
                    }
                }
                this.index = elements.size();
                try {
                    int randomCommentIndex = new Random().nextInt(likeCommentButtons.size());
                    actions.moveToElement(likeCommentButtons.get(randomCommentIndex)).perform();
                    TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
                    if (this.likeStatus.get(randomCommentIndex).equals("Like")) {
                        likeCommentButtons.get(randomCommentIndex).click();
                        TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                    }
                    this.moreComments();
                    likeCommentButtons = driver.findElements(likeCommentButton);
                    elements = driver.findElements(svgElement);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            this.closePost();
        }
    }

    public void moreComments() {
        Boolean button = driver.findElements(moreCommentsButton).size() > 0;
        if(button) {
            try {
                actions.moveToElement(driver.findElements(moreCommentsButton).get(0)).perform();
                TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
                driver.findElements(moreCommentsButton).get(0).click();
                TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("No more comments");
        }
    }

    public void closePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        if(elements.get(elements.size() - 1).getAttribute("aria-label").equals("Close")) {
            elements.get(elements.size() - 1).click();
        }
    }

    public LikeComments(WebDriver driver) {this.driver = driver; this.actions = new Actions(driver);}
}
