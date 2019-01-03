package selenium.lesson11.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import selenium.lesson11.pages.CheckoutPage;
import selenium.lesson11.pages.HeaderPanelPage;
import selenium.lesson11.pages.MainPage;
import selenium.lesson11.pages.ProductPage;

import java.util.concurrent.TimeUnit;

public class Application {

    private WebDriver driver;

    private MainPage mainPage;
    private CheckoutPage checkoutPage;
    private HeaderPanelPage headerPanelPage;
    private ProductPage productPage;

    public Application() {
        driver = new ChromeDriver();

        mainPage = new MainPage(driver);
        checkoutPage = new CheckoutPage(driver);
        headerPanelPage = new HeaderPanelPage(driver);
        productPage = new ProductPage(driver);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void openMainPage() {
        mainPage.open();
    }

    public void deleteAllProductsFromCheckout() {
        mainPage.checkoutLink.click();
        for(int i = checkoutPage.orderedProducts.size(); i>0; i--) {
            checkoutPage.removeProduct();
        }
        headerPanelPage.logoLink.click();
    }

    public void addProductsToCheckout(int numberOfProducts) {
        for (int i = 1; i<(numberOfProducts+1); i++) {
            mainPage.firstMostPopularProduct.click();
            //Some products contain mandatory select Size
            if (productPage.isSizeSelectorVisible()) {
                productPage.selectSize("Small");
            }
            productPage.addProduct();
            headerPanelPage.logoLink.click();
        }

    }

    public void quit() {
        driver.quit();
    }
}
