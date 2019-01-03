package selenium.lesson11.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HeaderPanelPage extends Page {

    public HeaderPanelPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public final String quantityOfItemsCssSelector = "span.quantity";

    @FindBy(css="div#logotype-wrapper > a")
    public WebElement logoLink;

    @FindBy(css=quantityOfItemsCssSelector)
    public WebElement quantityOfItems;
}
