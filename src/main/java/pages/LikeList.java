package pages;

import auxiliary.Auxiliary;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LikeList {
    private WebDriver driver;

    private Logger logger;

    private Actions actions;

    private FileWriter fileWriter;

    private BufferedWriter bufferedWriter;

    protected Auxiliary auxiliary = new Auxiliary();

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeButton = By.className("fr66n");

    private By likeList = By.className("_8A5w5");

    private By liker = By.className("MBL3Z");

    private List<String> likedBackList = new ArrayList<>();

    private List<String> likers = new ArrayList<>();

    public void StartLikeBack() {
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
                for(int i=0; i<3; i++) {
                    this.getLikeList();
                }
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getLikeList() {
        List<WebElement> elements = driver.findElements(likeList);
        if(elements.size() > 0) {
            for (WebElement element : elements) {
                System.out.println(element.getText());
            }
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

                try {
                    fileWriter = new FileWriter("resources/history.txt", true);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    elements.get(index).click();
                    bufferedWriter.write("Clicked to like list of the post");
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                    Thread.sleep(auxiliary.delayBetween(2000, 4000));
                    this.getLiker(numberOFLikes);
                }catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getLiker(int numberOfLikes) {
        List<WebElement> elements = driver.findElements(liker);
        if(elements.size() > 0) {
            for (WebElement element : elements) {
                if (!this.likers.contains(element.getText())) {
                    this.likers.add(element.getText());
                }
            }
            if(numberOfLikes > 11) {
                try {
                    int x = numberOfLikes / 11 + 2;
                    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                    for (int i = 0; i < x; i++) {
                        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(elements.size() - 1));
                        Thread.sleep(auxiliary.delayBetween(800, 1500));
                        elements = driver.findElements(liker);
                        for (WebElement element : elements) {
                            if (!this.likers.contains(element.getText())) {
                                this.likers.add(element.getText());
                            }
                        }
                    }
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                int randomLikerIndex = new Random().nextInt(this.likers.size());
                while (this.likedBackList.contains(elements.get(randomLikerIndex).getText())) {
                    randomLikerIndex = new Random().nextInt(this.likers.size());
                    if(this.likedBackList.size() == numberOfLikes) {
                        break;
                    }
                }
                this.likedBackList.add(elements.get(randomLikerIndex).getText());
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
                driver.get("https://www.instagram.com/" + this.likers.get(randomLikerIndex));
                bufferedWriter.write("Moved to " + this.likers.get(randomLikerIndex) + "'s profile");
                bufferedWriter.newLine();
                bufferedWriter.close();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
                this.getPost();
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getPost() {
        List<WebElement> elements = driver.findElements(post);
        if(elements.size() > 0) {
            try {
                fileWriter = new FileWriter("resources/history.txt", true);
                bufferedWriter = new BufferedWriter(fileWriter);
                elements.get(0).click();
                bufferedWriter.write("Clicked to the latest post of picked liker");
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
                bufferedWriter.write("Returned to the post");
                bufferedWriter.newLine();
                bufferedWriter.close();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }catch (InterruptedException | IOException e) {
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
                //driver.findElement(likeButton).click();
                bufferedWriter.write("Liked the post");
                bufferedWriter.newLine();
                bufferedWriter.close();
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.closePost();
        this.getBack();
    }

    public void getBack() {
        try {
            fileWriter = new FileWriter("resources/history.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            driver.navigate().back();
            TimeUnit.SECONDS.sleep(1);
            driver.navigate().back();
            bufferedWriter.write("Returned to the lastest post");
            bufferedWriter.newLine();
            bufferedWriter.close();
            TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
        }catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void backToProfile() {
        try {
            fileWriter = new FileWriter("resources/history.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
            driver.navigate().back();
            bufferedWriter.write("Returned to profile");
            bufferedWriter.newLine();
            bufferedWriter.close();
            Thread.sleep(auxiliary.delayBetween(2500, 3500));
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }

    public LikeList(WebDriver driver) {this.driver = driver; this.actions = new Actions(driver); this.logger = Logger.getLogger(LikeList.class.getName());}
}
