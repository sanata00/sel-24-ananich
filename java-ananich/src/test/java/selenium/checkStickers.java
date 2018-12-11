package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class checkStickers {

    private WebDriver driver;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void checkStickers() {

        driver.get("http://localhost/litecart/en/");

        //Click all categories
        List<WebElement> categories = driver.findElements(By.cssSelector("nav.content > ul > li"));
        int numberOfCategories = categories.size();

        //Click categories in a loop
        for (int i=1; i<(numberOfCategories+1); i++) {
            driver.findElement(By.cssSelector("nav.content > ul > li:nth-child(" + i + ") a")).click();

            //Check that each item has only one sticker
            List<WebElement> items = driver.findElements(By.cssSelector("li.product"));
            for(WebElement item : items) {
                List<WebElement> stickers = item.findElements(By.cssSelector("div.sticker"));
                try {
                    assertEquals(1, stickers.size());
                } catch (Throwable t) {
                    collector.addError(t);
                }
            }
        }
}

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}