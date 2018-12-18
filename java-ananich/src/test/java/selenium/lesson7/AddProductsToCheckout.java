package selenium.lesson7;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class AddProductsToCheckout {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void addProductsToCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.get("http://localhost/litecart/en/");

        //Add three products
        for(int i = 1; i<4; i++) {
            driver.findElement(By.cssSelector("div#box-most-popular a.link:first-child")).click();
            //Some products contain mandatory select Size
            if (driver.findElements(By.cssSelector("select[name=options\\[Size\\]")).size() > 0) {
               new Select(driver.findElement(By.cssSelector("select[name=options\\[Size\\]"))).selectByVisibleText("Small");
            }
            driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("span.quantity"), String.valueOf(i)));
            driver.findElement(By.cssSelector("div#logotype-wrapper > a")).click();
        }

        //Delete all products from checkout
        driver.findElement(By.cssSelector("div#cart a.link")).click();
        for(int i = 3; i>0; i--) {
            driver.findElement(By.cssSelector("button[name=remove_cart_item]")).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("div#order_confirmation-wrapper td.item"), i-1));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}