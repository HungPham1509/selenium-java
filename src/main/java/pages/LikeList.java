package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LikeList {
    private WebDriver driver;

    private Actions actions;

    private By post = By.className("_9AhH0");

    private By svgElement = By.className("_8-yf5");

    private By likeButton = By.className("fr66n");

    private By likeList = By.className("_8A5w5");

    private By liker = By.className("MBL3Z");

    private List<String> likedBackList = new ArrayList<>();

    public void StartLikeBack() {
        List<WebElement> elements = driver.findElements(post);
        if(elements.size() > 0) {
            try {
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                elements.get(0).click();
                TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
                for(int i=0; i<3; i++) {
                    this.getLikeList();
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("No post yet");
        }
    }

    public void getLikeList() {
        List<WebElement> elements = driver.findElements(likeList);
        if(elements.size() > 0) {
            int index = 2;
            if(elements.get(0).getText().equals("Edit Profile")) {
                index = 3;
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
                    elements.get(index).click();
                    TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
                    this.getLiker(numberOFLikes);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getLiker(int numberOfLikes) {
        List<WebElement> elements = driver.findElements(liker);
        if(elements.size() > 0) {
           try{
//               int x = numberOfLikes / 11;
//               int mode = numberOfLikes % 11;
//               JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
//               for(int i=0; i<x; i++) {
//                   javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", elements.get(elements.size() - 1));
//                   TimeUnit.SECONDS.sleep(1);
//                   elements = driver.findElements(liker);
//               }
               int randomLikerIndex = new Random().nextInt(elements.size());
               while (this.likedBackList.contains(elements.get(randomLikerIndex).getText())) {
                   randomLikerIndex = new Random().nextInt(elements.size());
               }
               this.likedBackList.add(elements.get(randomLikerIndex).getText());
               actions.moveToElement(elements.get(randomLikerIndex)).perform();
               TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
               elements.get(randomLikerIndex).click();
               TimeUnit.SECONDS.sleep((int)(Math.random()*((5-3)+1))+3);
               this.getPost();
           }catch (InterruptedException e) {
               e.printStackTrace();
           }
        }
    }

    public void getPost() {
        List<WebElement> elements = driver.findElements(post);
        if(elements.size() > 0) {
            try {
                elements.get(0).click();
                TimeUnit.SECONDS.sleep((int)(Math.random()*((3-2)+1))+2);
                this.likePost();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            driver.navigate().back();
        }
    }

    public void closePost() {
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
            try {
                //driver.findElement(likeButton).click();
                TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            this.closePost();
            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
            this.getBack();
            TimeUnit.SECONDS.sleep((int)(Math.random()*((4-2)+1))+2);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getBack() {
        try {
            driver.navigate().back();
            TimeUnit.SECONDS.sleep(1);
            driver.navigate().back();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void backToProfile() {
        driver.navigate().back();
    }

    public LikeList(WebDriver driver) {this.driver = driver; this.actions = new Actions(driver);}
}
