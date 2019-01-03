package selenium.lesson11.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CheckoutPage extends Page {

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    final String orderedProductsCssSelector = "div#order_confirmation-wrapper td.item";

    @FindBy(css=orderedProductsCssSelector)
    public List<WebElement> orderedProducts;

    @FindBy(css="button[name=remove_cart_item]")
    public WebElement removeProductButton;

    public void removeProduct() {
        int numberOfProducts = orderedProducts.size();
        removeProductButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(
                orderedProductsCssSelector), numberOfProducts - 1));
    }
}
