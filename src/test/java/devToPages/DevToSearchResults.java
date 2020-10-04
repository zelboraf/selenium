package devToPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DevToSearchResults {
    WebDriver driver;
    WebDriverWait wait;
    public String url = "https://dev.to/search?q=";

    public DevToSearchResults(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }
}
