package selenium.lesson10;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SendViaProxy {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("127.0.0.1:8888");
        options.setCapability("proxy", proxy);
        driver = new ChromeDriver(options);
    }

    @Test
    public void getViaProxy() {
        driver.get(" http://localhost/litecart/admin/");
        driver.get(" http://localhost/litecart/");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}