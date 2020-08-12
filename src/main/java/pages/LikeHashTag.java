package pages;

import auxiliary.Auxiliary;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LikeHashTag {
    private WebDriver driver;

    private FileWriter fileWriter;

    private BufferedWriter bufferedWriter;

    protected Auxiliary auxiliary = new Auxiliary();

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
        WebElement search = driver.findElement(searchInput);
        try {
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
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                for(int i=0; i<results.size(); i++) {
                    if(names.get(i).getText().equals(tag)) {
                        results.get(i).click();
                    }
                }
                bufferedWriter.write("Moved to target hashtag " + tag);
                bufferedWriter.newLine();
                bufferedWriter.close();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
                this.getPost(postType);
                this.closePost();
            }
        }
        catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void getPost(String postType) {
        List<WebElement> elements = driver.findElements(post);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        this.numberOfLikes = 0;
        if(elements.size() > 0) {
            try {
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                if (postType.equals("Top")) {
                    elements.get(0).click();
                    bufferedWriter.write("Clicked to the first top post");
                }
                if (postType.equals("Recent")) {
                    javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(recentlyPosts));
                    Thread.sleep(auxiliary.delayBetween(2000, 3000));
                    elements.get(9).click();
                    bufferedWriter.write("Clicked to the first recently post");
                }
                bufferedWriter.newLine();
                bufferedWriter.close();
                Thread.sleep(auxiliary.delayBetween(2500, 3500));
                this.likePost();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void likePost() {
        List<WebElement> elements = driver.findElements(svgElement);
        String liked = elements.get(5).getAttribute("aria-label");
        try {
            fileWriter = new FileWriter("resources/history.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            if(liked.equals("Like")) {
                    //driver.findElement(likeButton).click();
                    numberOfLikes +=1;
                    bufferedWriter.write("Liked the post");
                    bufferedWriter.newLine();
                    TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }
            List<WebElement> arrow = driver.findElements(nextArrow);
            if(arrow.size() > 0 && this.numberOfLikes < 2) {
                arrow.get(0).click();
                bufferedWriter.write("Moved to next post because previous post had been liked");
                bufferedWriter.newLine();
                bufferedWriter.close();
                Thread.sleep(auxiliary.delayBetween(3000, 4000));
                this.likePost();
            }
            else {
                this.closePost();
            }
        }catch (InterruptedException | IOException e) {
            e.printStackTrace();
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

    public LikeHashTag(WebDriver driver) {this.driver = driver;}
}
