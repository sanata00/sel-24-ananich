package selenium.lesson6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

public class AddNewProduct {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void addNewProduct() {
        //Test Data
        String productName = "Car Toy";

        //Login
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Go to Add New Product
        driver.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(2) a")).click();
        driver.findElement(By.cssSelector("td#content a:nth-child(2)")).click();

        //Populate 'General' tab
        driver.findElement(By.cssSelector("div#tab-general tr:nth-of-type(1) label:nth-of-type(1) input")).click();
        driver.findElement(By.cssSelector("input[name=name\\[en\\]]")).sendKeys(productName);
        driver.findElement(By.cssSelector("input[name=code")).sendKeys("12345");
        driver.findElement(By.cssSelector("div#tab-general tr:nth-of-type(4) tr:nth-of-type(2) input")).click();
        new Select(driver.findElement(By.cssSelector("div#tab-general tr:nth-of-type(5) select")))
                .selectByVisibleText("Rubber Ducks");
        driver.findElement(By.cssSelector("div#tab-general tr:nth-of-type(7) tr:nth-of-type(4) input")).click();
        driver.findElement(By.cssSelector("input[name=quantity]")).sendKeys("100");
        new Select(driver.findElement(By.cssSelector("select[name=quantity_unit_id]"))).selectByVisibleText("pcs");
        new Select(driver.findElement(By.cssSelector("select[name=delivery_status_id]")))
                .selectByVisibleText("3-5 days");
        new Select(driver.findElement(By.cssSelector("select[name=sold_out_status_id]")))
                .selectByVisibleText("-- Select --");
        driver.findElement(By.cssSelector("input[name=new_images\\[\\]]"))
                .sendKeys(System.getProperty("user.dir") + "/src/test/resources/images/carToy.jpg");
        driver.findElement(By.cssSelector("input[name=date_valid_from]"))
                .sendKeys("1512" + Keys.TAB + "2018");
        driver.findElement(By.cssSelector("input[name=date_valid_to]"))
                .sendKeys("3112" + Keys.TAB + "2018");

        //Populate 'Information' tab
        driver.findElement(By.cssSelector("a[href*=tab-information]")).click();
        new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]"))).selectByVisibleText("ACME Corp.");
        driver.findElement(By.cssSelector("input[name=keywords]")).sendKeys("car toy boy");
        driver.findElement(By.cssSelector("input[name=short_description\\[en\\]]")).sendKeys("very good car");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("very good car long description");
        driver.findElement(By.cssSelector("input[name=head_title\\[en\\]]")).sendKeys("SUPER CAR");
        driver.findElement(By.cssSelector("input[name=meta_description\\[en\\]]")).sendKeys("meta description");

        //Populate 'Prices' tab
        driver.findElement(By.cssSelector("a[href*=tab-prices]")).click();
        WebElement purchasePriceElement = driver.findElement(By.cssSelector("input[name=purchase_price]"));
        purchasePriceElement.clear();
        purchasePriceElement.sendKeys("15");
        new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]"))).selectByVisibleText("US Dollars");
        driver.findElement(By.cssSelector("input[name=prices\\[USD\\]")).sendKeys("20");
        driver.findElement(By.cssSelector("input[name=prices\\[EUR\\]")).sendKeys("18");
        driver.findElement(By.cssSelector("button[name=save]")).click();

        //Check that the product is created
        driver.findElement(By.linkText(productName));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}