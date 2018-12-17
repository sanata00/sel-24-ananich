package selenium.lesson7;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class AddProductsToCheckout {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void checkProduct() {

        driver.get("http://localhost/litecart/en/");

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private boolean isTextCrossed(WebElement element) {
        String textDecoration = element.getCssValue("text-decoration");
        System.out.println("Text decoration: " + textDecoration);
        if (textDecoration.contains("line-through")) {
            return true;
        } else {
            return false;
        }
    }
}