package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class AllSongsPage extends BasePage{

    public AllSongsPage(WebDriver givenDriver){
        super(givenDriver);
    }

    //Elements

    By firstSongInAllSongs = By.cssSelector(".all-songs tr.song-item:nth-child(1)");

    By playBtnInContextMenu = By.cssSelector("li.playback");

    By playlistElementToClick = By.cssSelector(".playlist:nth-child(3)");

    By playlistInputField = By.cssSelector(".playlist playlist editing");

    //Methods

    public WebElement firstSongInAllSongs (){
        return findElementUsingByLocator(firstSongInAllSongs);
    }

    public void contextClickFirstSong() throws InterruptedException{
        WebElement firstSongInTheList = wait.until(ExpectedConditions
                .visibilityOfElementLocated(firstSongInAllSongs));
        actions.contextClick(firstSongInTheList).perform();
    }

    public void choosePlayOption() throws InterruptedException{
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(playBtnInContextMenu)).click();
    }

    public void doubleClickPlaylist() throws InterruptedException{
        WebElement playlistElement = wait.until(ExpectedConditions.visibilityOfElementLocated(playlistElementToClick));
        actions.doubleClick(playlistElement).perform();
    }


    public void enterNewPlaylistName(String newPlaylistName){
      wait.until(ExpectedConditions.visibilityOfElementLocated(playlistInputField)).sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.BACK_SPACE));
        //Clear does not work since element has an attribute of required.
      wait.until(ExpectedConditions.visibilityOfElementLocated(playlistInputField)).sendKeys(newPlaylistName);
      wait.until(ExpectedConditions.visibilityOfElementLocated(playlistInputField)).sendKeys(Keys.ENTER);
    }
}
