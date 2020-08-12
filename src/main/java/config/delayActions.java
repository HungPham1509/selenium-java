package config;

import auxiliary.Auxiliary;

import java.util.concurrent.TimeUnit;

public class delayActions {
    private Auxiliary auxiliary = new Auxiliary();
    public void delayAfterAccessLoginPage () throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }

    public void delayAfterTypeACharacter () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(100, 300));
    }

    public void delayAfterFillInUsernameField () throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }

    public void delayAfterFillInPasswordField () throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }

    public void delayAfterClickLoginButton () throws InterruptedException {
        TimeUnit.SECONDS.sleep(auxiliary.delayBetween(5, 8));
    }

    public void delayAfterAccessProfile () throws InterruptedException {
        TimeUnit.SECONDS.sleep(auxiliary.delayBetween(4, 6));
    }

    public void delayAfterClickFollowerList () throws InterruptedException {
        TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
    }

    public void delayAfterScrollToBottom () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(800, 1500));
    }

    public void delayAfterScrollToTarget () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(2000, 3000));
    }

    public void delayAfterClickToTarget () throws InterruptedException {
        TimeUnit.SECONDS.sleep(auxiliary.delayBetween(4, 6));
    }

    public void delayAfterClickToLatestPost () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(2500, 3500));
    }

    public void delayAfterReturnToFollowerList () throws InterruptedException {
        TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
    }

    public void delayAfterCloseThePost () throws InterruptedException {
        TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
    }

    public void delayAfterLikePost () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(3000, 4500));
    }

    public void delayAfterClickToLikeList () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(2000, 4000));
    }

    public void delayAfterScrollLikeListOneTime () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(800, 1500));
    }

    public void delayClosePost () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(3000, 4000));
    }

    public void delayAfterClickBack () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(2000, 3000));
    }

    public void delayAfterFinishLikeOneTarget () throws InterruptedException {
        TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
    }

    public void delayAfterMoveToComment () throws InterruptedException {
        TimeUnit.SECONDS.sleep(auxiliary.delayBetween(2, 4));
    }

    public void delayAfterLikeComment () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(3000, 5000));
    }

    public void delayAfterScrollToMoreComment () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(2500, 3500));
    }

    public void delayAfterClickMoreComment () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(3000, 4000));
    }

    public void delayAfterSearchHashtag () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(2000, 3000));
    }

    public void delayAfterMoveToHashtag () throws InterruptedException {
        TimeUnit.SECONDS.sleep(auxiliary.delayBetween(3, 5));
    }

    public void delayAfterFinishLikeOneHashtag () throws InterruptedException {
        Thread.sleep(auxiliary.delayBetween(2500, 3500));
    }
}
