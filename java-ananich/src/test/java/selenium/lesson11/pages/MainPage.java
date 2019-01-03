package selenium.lesson11.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page {

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("http://localhost/litecart/en/");
    }

    @FindBy(css="div#cart a.link")
    public WebElement checkoutLink;

    @FindBy(css="div#box-most-popular a.link:first-child")
    public WebElement firstMostPopularProduct;

}
