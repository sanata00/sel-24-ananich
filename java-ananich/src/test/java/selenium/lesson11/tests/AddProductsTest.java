package selenium.lesson11.tests;

import org.junit.Test;

public class AddProductsTest extends TestBase {

    @Test
    public void addProducts() {
        app.openMainPage();
        app.deleteAllProductsFromCheckout();
        app.addProductsToCheckout(3);
        app.deleteAllProductsFromCheckout();
    }
}
