package selenium.lesson6;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class NewUserRegistration {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void registerNewUser() {

        //Login
        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.cssSelector("form[name=login_form] a")).click();

        //Input register data and submit
        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys("Pavel");
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys("Ananich");
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys("Washington Street 10");
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys("99501");
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys("Anchourage");


        Select country = new Select(driver.findElement(By.cssSelector("select[name=country_code]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        https://developer.mozilla.org/en-US/docs/Web/API/HTMLSelectElement/labels
        js.executeScript("var opts = arguments[0].selectedOptions;" +
                "for (var i=0; i<opts.length; i++) {if (opts[i].text == 'Uzbekistan') {arguments[0].selectedIndex[i]}};" +
                "arguments[0].dispatchEvent(new Event('change'))", country);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}