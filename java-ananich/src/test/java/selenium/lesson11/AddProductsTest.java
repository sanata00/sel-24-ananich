package selenium.lesson11;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Set;

public class AddProductsTest extends TestBase {

    @Test
    public void addProducts() {
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

    public void deleteAllProductsFromCheckout() {
        List<WebElement> elements = driver.findElements(By.cssSelector("div#order_confirmation-wrapper td.item"));
    }
}
