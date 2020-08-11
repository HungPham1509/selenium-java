package pages;

import auxiliary.Auxiliary;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LikeComments {
    private WebDriver driver;

    private FileWriter fileWriter;

    private BufferedWriter bufferedWriter;

    protected Auxiliary auxiliary = new Auxiliary();

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
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                elements.get(0).click();
                bufferedWriter.write("Clicked to the latest post");
                bufferedWriter.newLine();
                bufferedWriter.close();
                Thread.sleep(auxiliary.delayBetween(2500, 3500));
                this.likeComment();
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void likeComment() {
        List<WebElement> elements = driver.findElements(svgElement);
        List<WebElement> likeCommentButtons = driver.findElements(likeCommentButton);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        // check whether instagram account has igtv or not
        if(elements.get(2).getAttribute("aria-label").equals("Posts")) {
            this.index = 15;
        }
        if(likeCommentButtons.size() > 0) {
            for(int i=0; i<5; i++) {
                this.moreComments();
            }
            elements = driver.findElements(svgElement);
            likeCommentButtons = driver.findElements(likeCommentButton);
            for (int i = this.index; i < elements.size(); i++) {
                if (elements.get(i).getAttribute("aria-label").equals("Like") || elements.get(i).getAttribute("aria-label").equals("Unlike")) {
                    this.likeStatus.add(elements.get(i).getAttribute("aria-label"));
                }
            }

            for(int j=0; j<3; j++) {
                try {
                    fileWriter = new FileWriter("resources/history.txt", true);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    int randomCommentIndex = new Random().nextInt(likeCommentButtons.size());
                    javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", likeCommentButtons.get(randomCommentIndex));
                    bufferedWriter.write("Moved to picked comment");
                    bufferedWriter.newLine();
                    TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
                    if (this.likeStatus.get(randomCommentIndex).equals("Like")) {
                        likeCommentButtons.get(randomCommentIndex).click();
                        bufferedWriter.write("Liked picked comment");
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                        Thread.sleep(auxiliary.delayBetween(3000, 5000));
                    }
                }catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
            this.closePost();
        }
        else {
            this.closePost();
        }
    }

    public void moreComments() {
        boolean button = driver.findElements(moreCommentsButton).size() > 0;
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        if(button) {
            try {
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElements(moreCommentsButton).get(0));
                Thread.sleep(auxiliary.delayBetween(2500, 3500));
                driver.findElements(moreCommentsButton).get(0).click();
                bufferedWriter.write("Clicked to more comments");
                bufferedWriter.newLine();
                bufferedWriter.close();
                Thread.sleep(auxiliary.delayBetween(3000, 4000));
            }
            catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        if(elements.get(elements.size() - 1).getAttribute("aria-label").equals("Close")) {
            try {
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                elements.get(elements.size() - 1).click();
                bufferedWriter.write("Closed the post");
                bufferedWriter.newLine();
                bufferedWriter.close();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public LikeComments(WebDriver driver) {this.driver = driver;}
}
