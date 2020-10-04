import devToPages.DevToMainPage;
import devToPages.DevToSearchResults;
import devToPages.DevToSinglePostPage;
import devToPages.DevToWeekPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class BaseTest {
    WebDriver driver;
    WebDriverWait wait;

    public void highlightElement(WebDriver driver, WebElement element){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: green; border: 3px solid blue;');", element);
    }
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","D:\\bajzel\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
    }
    @Test
    public void selectFirstPostFromWeek(){
        DevToMainPage devToMainPage = new DevToMainPage(driver, wait);
        DevToWeekPage devToWeekPage = devToMainPage.goToWeekPage();

        String firstPostLink = devToWeekPage.firstPostOnWeek.getAttribute("href");
        String firstPostText = devToWeekPage.firstPostOnWeek.getText();

        DevToSinglePostPage devToSinglePostPage = devToWeekPage.selectFirstPost();
        wait.until(ExpectedConditions.urlToBe(firstPostLink));

        String postTitleText = devToSinglePostPage.postTitle.getText();
        String currentUrl = driver.getCurrentUrl();

        assertEquals("Urls aren't the same", currentUrl, firstPostLink);
        assertEquals("Titles aren't the same", postTitleText, firstPostText);


//        wait.until(ExpectedConditions.urlToBe("https://dev.to/top/week")); //zanim zaczniesz szukać elementu, poczekaj aż url będzie miał wartość https://dev.to/top/week
//        WebElement firstPostOnWeek = driver.findElement(By.cssSelector("h2.crayons-story__title > a"));
//        String firstPostOnWeekText = firstPostOnWeek.getText();
//        String firstPostLink = firstPostOnWeek.getAttribute("href");
//        firstPostOnWeek.click();
//        wait.until(ExpectedConditions.urlToBe(firstPostLink));
//        WebElement postTitle = driver.findElement(By.cssSelector("div.crayons-article__header__meta > h1"));
//        String postUrl = driver.getCurrentUrl();
//        String postTitleText = postTitle.getText();
//        assertEquals("Urls aren't the same", postUrl, firstPostLink);
//        assertEquals("Titles aren't the same",postTitleText, firstPostOnWeekText);
    }
    @Test
    public void searchBarTesting() {
        DevToMainPage devToMainPage = new DevToMainPage(driver, wait);
        String searchText = "testing";
        DevToSearchResults devToSearchResults = devToMainPage.search(searchText);

        String searchingUrlWithText = devToSearchResults.url + searchText;
        wait.until(ExpectedConditions.urlToBe(searchingUrlWithText));

        ArrayList<String> postTitleList = devToSearchResults.getTopThreePostTitles();
        for (int i = 0; i < 3; i++) {
            String postTileText = postTitleList.get(i).toLowerCase();
            assertTrue(postTileText.contains(searchText));
        }

//        WebElement searchBox = driver.findElement(By.id("nav-search"));
//        String searchText = "testing";
//        String searchUrl = "https://dev.to/search?q=";
//
//        searchBox.sendKeys(searchText + Keys.ENTER);
//        wait.until(ExpectedConditions.urlToBe(searchUrl + searchText));
//
//        List<WebElement> postTilesList = driver.findElements(By.cssSelector("h2.crayons-story__title a"));
//        for (int i = 0; i < 3; i++) {
//            String message = String.format("Header %d doesn't contain text %s", i, searchText);
//            Assert.assertTrue(message, postTilesList.get(i).getText().toLowerCase().contains(searchText));
//        }
    }
    @Test
    public void findJavaInNavBar(){
        WebElement java = driver.findElement(By.cssSelector("div#default-sidebar-element-java > a"));
        highlightElement(driver, java);
        java.click();
    }
    @Test
    public void playFourthPodcast(){
        WebElement podcasts = driver.findElement(By.cssSelector("a[href=\"/pod\"]"));
        highlightElement(driver, podcasts);
        podcasts.click();

        wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));
        List<WebElement> podcastList = driver.findElements(By.cssSelector("h3"));
        podcastList.get(3).click();

        wait.until(ExpectedConditions.urlContains("stackpodcast"));
        WebElement playArea = driver.findElement(By.className("record-wrapper"));
        playArea.click();

        WebElement initializing = driver.findElement(By.className("status-message"));
        wait.until(ExpectedConditions.invisibilityOf(initializing));
        String playAreaClassAtribute = playArea.getAttribute("class");
        boolean isPlaying = playAreaClassAtribute.contains("playing");

        assertTrue("Podcast is not playing", isPlaying);
    }
    @After
    public void cleanUp() {
//        driver.quit();
    }
}