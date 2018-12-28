package selenium.lesson10;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class NoMessagesInBrowserLog {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void checkLog() {
        int expectedSizeOfLogs = getSizeOfLogs();

        //Login
        driver.get(" http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Get number of products from footer (used in the loop)
        String categoriesProducts = driver.findElement(By.cssSelector("table.dataTable tr.footer > td")).getText();
        int numberOfProducts = Integer.parseInt(categoriesProducts.substring(categoriesProducts.lastIndexOf(':')+2));

        //For each product (not a category) open a link, check logs and return back
        for (int i = 0; i<numberOfProducts; i++) {
            List<WebElement> productsList = driver.findElements(By.cssSelector("table.dataTable tr.row td:nth-child(3) a"));
            int sizeOfProductsList = productsList.size();
            String productName = productsList.get(sizeOfProductsList - 5 + i).getText();
            productsList.get(sizeOfProductsList - 5 + i).click();
            expectedSizeOfLogs = checkChangeInLogs(expectedSizeOfLogs, productName);
            driver.navigate().back();
        }

        //Print all entities from logs if exist
        if (expectedSizeOfLogs > 0) {
            driver.manage().logs().get(LogType.BROWSER).forEach(l -> System.out.println(l));
            assertEquals(expectedSizeOfLogs, getSizeOfLogs());
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private int getSizeOfLogs() {
        return driver.manage().logs().get("browser").getAll().size();
    }

    private int checkChangeInLogs(int expectedNumberOfEntries, String productName) {
        int sizeOfLogs = getSizeOfLogs();
        if (sizeOfLogs > expectedNumberOfEntries) {
            System.out.println("There are new messages after opening product " + productName + "." +
                " Total size: " + sizeOfLogs);
        }
        return sizeOfLogs;
    }
}