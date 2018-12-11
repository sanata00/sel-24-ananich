package selenium.lesson5;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class ProductPageHasCorrectData {

    private WebDriver driver;
    private WebDriverWait wait;

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
    public void checkCountries() {

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private ArrayList<String> getTextViaGetText(List<WebElement> items) {
        ArrayList<String> textValues = new ArrayList<>();
        for (WebElement item : items) {
            textValues.add(item.getText());
        }
        return textValues;
    }

    private ArrayList<String> getTextViaCssValue(List<WebElement> items) {
        ArrayList<String> textValues = new ArrayList<>();
        for (WebElement item : items) {
            textValues.add(item.getCssValue("value"));
        }
        return textValues;
    }
}