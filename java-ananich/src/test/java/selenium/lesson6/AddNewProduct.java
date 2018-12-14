package selenium.lesson6;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;
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

        //Login
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Go to Add New Product
        driver.findElement(By.cssSelector("ul#box-apps-menu > li:nth-child(2) a")).click();
        driver.findElement(By.cssSelector("td#content a:nth-child(2)")).click();

        //Populate 'General' tab
        /*driver.findElement(By.cssSelector("div#tab-general tr:nth-of-type(1) label:nth-of-type(1) input")).click();
        driver.findElement(By.cssSelector("input[name=name\\[en\\]]")).sendKeys("Car Toy");
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
                .selectByVisibleText("-- Select --");*/
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        driver.findElement(By.cssSelector("input[name=new_images\\[\\]]"))
                .sendKeys(System.getProperty("user.dir") + "/src/test/resources/images/carToy.jpg");








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