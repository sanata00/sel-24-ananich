package selenium.lesson6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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

        driver.get("http://localhost/litecart/en/");
        driver.findElement(By.cssSelector("form[name=login_form] a")).click();

        //Input register data and submit
        String email = generateRandomEmail();
        String password = "MyNewPassword01!";

        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys("Pavel");
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys("Ananich");
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys("Washington Street 10");
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys("99501");
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys("Anchourage");

        //JS implementation of country selection
        /*Select country = new Select(driver.findElement(By.cssSelector("select[name=country_code]")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "var options = arguments[0].options;" +
                "for (var i=0; i<options.length; i++) {" +
                    "if (options.item(i).text == 'United States') {" +
                        "arguments[0].selectedIndex = i;" +
                        "arguments[0].dispatchEvent(new Event('change'));" +
                        "break;" +
                    "}" +
                "}", country);*/

        //Only user interactions to select a country
        driver.findElement(By.cssSelector("span.select2")).click();
        driver.findElement(By.cssSelector("input.select2-search__field"))
                .sendKeys("United States" + Keys.ENTER);

        Select zoneCode = new Select(driver.findElement(By.cssSelector("select[name=zone_code]")));
        zoneCode.selectByVisibleText("Alaska");
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+1800800123");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys(password);

        driver.findElement(By.cssSelector("button[name=create_account]")).click();

        //relogin
        driver.findElement(By.cssSelector("div#box-account")).findElement(By.linkText("Logout")).click();
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[name=login]")).click();
        driver.findElement(By.cssSelector("div#box-account")).findElement(By.linkText("Logout")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private String generateRandomEmail() {
        String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
        int stringLength = 10;

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(stringLength);

        while (buffer.length() < stringLength) {
            int index = (int) (random.nextFloat() * chars.length());
            buffer.append(chars.charAt(index));
        }

        return buffer.toString() + "@mail.com";
    }
}