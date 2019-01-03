package selenium.lesson11.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage extends Page {

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public final String sizeSelectCssSelector = "select[name=options\\[Size\\]";

    @FindBy(css=sizeSelectCssSelector)
    public WebElement sizeSelector;

    @FindBy(css="button[name=add_cart_product]")
    public WebElement addProductButton;

    public boolean isSizeSelectorVisible() {
        return driver.findElements(By.cssSelector(sizeSelectCssSelector)).size() > 0;
    }

    public void selectSize(String size) {
        new Select(sizeSelector).selectByVisibleText(size);
    }

    public void addProduct() {
        HeaderPanelPage headerPanelPage = new HeaderPanelPage(driver);
        int expectedQuantity = Integer.parseInt(headerPanelPage.quantityOfItems.getText()) + 1;
        addProductButton.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.cssSelector(headerPanelPage.quantityOfItemsCssSelector), String.valueOf(expectedQuantity)));
    }

}
