package devToPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DevToSinglepodcastPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(className = "record-wrapper")
    public WebElement playArea;

    public DevToSinglepodcastPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void playPodcast() {
        playArea.click();
    }
}
