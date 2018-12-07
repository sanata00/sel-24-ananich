package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class ClickAllSections {

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
    public void clickAllSections() {

        //Login
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Click all sections
        List<WebElement> firstLevelMenuItems = driver.findElements(By.cssSelector("ul#box-apps-menu > li"));
        int numberOfFirstLevelMenuItems = firstLevelMenuItems.size();

        //Click first level menu items in a loop
        for (int i=1; i<(numberOfFirstLevelMenuItems+1); i++) {
            driver.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(" + i + ") > a")).click();
            try {
                assertTrue(isElementPresent(By.cssSelector("h1")));
            } catch (Throwable t) {
                collector.addError(t);
            }

            //Click second level menu items in a loop
            List<WebElement> secondLevelMenuItems = driver.findElements(By.cssSelector("ul.docs > li"));
            int numberOfSecondLevelMenuItems = secondLevelMenuItems.size();
            for (int j=1; j<(numberOfSecondLevelMenuItems+1); j++) {
                driver.findElement(By.cssSelector("ul.docs > li:nth-child(" + j + ") > a")).click();
                try {
                    assertTrue(isElementPresent(By.cssSelector("h1")));
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

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (InvalidSelectorException ex) {
            throw ex;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}