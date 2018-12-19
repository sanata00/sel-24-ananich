package selenium.lesson8;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class LinksAreOpenedInNewWindow {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void checkLinks() {

        //Login
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Check that a new window is opened for each link
        driver.findElement(By.cssSelector("td#content > div a")).click();
        List<WebElement> links = driver.findElements(By.cssSelector("td#content > form a[target=_blank]"));
        for (WebElement link : links) {
            checkThatLinkIsOpenedInNewWindow(link);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private void checkThatLinkIsOpenedInNewWindow(WebElement link) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();
        link.click();
        String newWindow = wait.until(anyWindowOtherThan(existingWindows));
        driver.switchTo().window(newWindow);
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    private ExpectedCondition<String> anyWindowOtherThan(Set<String> windows) {
        return input -> {
            Set<String> handles = driver.getWindowHandles();
            handles.removeAll(windows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
    }
}