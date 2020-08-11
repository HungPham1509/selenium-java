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

public class LikeFollowers {
    private WebDriver driver;

    private FileWriter fileWriter;

    private BufferedWriter bufferedWriter;

    protected Auxiliary auxiliary = new Auxiliary();

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
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                elements.get(1).click();
                bufferedWriter.write("Clicked follower list");
                bufferedWriter.newLine();
                bufferedWriter.close();
                int delayTime = auxiliary.delayBetween(2, 4);
                TimeUnit.SECONDS.sleep(delayTime);
                this.getFollower();
            }catch (InterruptedException | IOException e) {
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
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            if(numberOFFollowers > 12) {
                try{
                    int x = numberOFFollowers / 12;
                    fileWriter = new FileWriter("resources/history.txt", true);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    for(int i=0; i<x; i++) {
                        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(elements.size() - 1));
                        Thread.sleep(auxiliary.delayBetween(800, 1500));
                        elements = driver.findElements(follower);
                    }
                    bufferedWriter.write("Scrolled the follower list to the bottom");
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                }catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                int randomFollowerIndex = new Random().nextInt(numberOFFollowers);
                while (this.likedFollowerList.contains(elements.get(randomFollowerIndex).getText())) {
                    randomFollowerIndex = new Random().nextInt(elements.size());
                }
                this.likedFollowerList.add(elements.get(randomFollowerIndex).getText());
                //actions.moveToElement(elements.get(randomFollowerIndex)).perform();
                javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(randomFollowerIndex));
                bufferedWriter.write("Moved to picked follower");
                bufferedWriter.newLine();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
                elements.get(randomFollowerIndex).click();
                bufferedWriter.write("Clicked to picked follower");
                bufferedWriter.newLine();
                bufferedWriter.close();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(4, 6));
                this.getPost();
            }catch (InterruptedException | IOException e) {
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
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                elements.get(0).click();
                bufferedWriter.write("Clicked to the latest post of picked follower");
                bufferedWriter.newLine();
                bufferedWriter.close();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
                this.likePost();
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                driver.navigate().back();
                bufferedWriter.write("Returned to follower list");
                bufferedWriter.newLine();
                bufferedWriter.close();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
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

    public void likePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        String liked = elements.get(8).getAttribute("aria-label");
        if(elements.get(2).getAttribute("aria-label").equals("Posts")) {
            liked = elements.get(9).getAttribute("aria-label");
        }
        if(liked.equals("Like")) {
            try {
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                driver.findElement(likeButton).click();
                bufferedWriter.write("Liked the post");
                bufferedWriter.newLine();
                bufferedWriter.close();
                Thread.sleep(auxiliary.delayBetween(3000, 4500));
                this.close();
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                List<WebElement> arrow = driver.findElements(nextArrow);
                if(arrow.size() > 0) {
                    arrow.get(0).click();
                    bufferedWriter.write("Moved to next post because previous post had been liked");
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    Thread.sleep(auxiliary.delayBetween(3000, 4000));
                    this.likePost();
                }
                else {
                    this.close();
                }
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public LikeFollowers(WebDriver driver) {this.driver = driver;}
}
