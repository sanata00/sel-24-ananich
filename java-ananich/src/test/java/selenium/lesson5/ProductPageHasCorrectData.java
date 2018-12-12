package selenium.lesson5;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ProductPageHasCorrectData {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
//        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
        driver = new InternetExplorerDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void checkProduct() {

        //Perform checks on a main page
        driver.get("http://localhost/litecart/en/");
        WebElement firstCampaignProduct = driver.findElement(By.cssSelector("div#box-campaigns a.link:first-child"));

        String productNameMainPage = firstCampaignProduct.findElement(By.cssSelector("div.name")).getText();
        System.out.println("Product name main page: " + productNameMainPage);

        WebElement regularPrice = firstCampaignProduct.findElement(By.cssSelector("s.regular-price"));
        int regularPriceMainPage = Integer.parseInt(regularPrice.getText().substring(1));
        System.out.println("Regular price main page: " + regularPriceMainPage);

        WebElement campaignPrice = firstCampaignProduct.findElement(By.cssSelector("strong.campaign-price"));
        int campaignPriceMainPage = Integer.parseInt(campaignPrice.getText().substring(1));
        System.out.println("Campaign price main page: " + campaignPriceMainPage);

        assertTrue("If a regular price crossed.", isTextCrossed(regularPrice));
        assertTrue("If a color is grey for regular price.", isTextGrey(regularPrice));
        assertTrue("If a color is red for campaign price.", isTextRed(campaignPrice));
        assertTrue("If a campaign price is bold.", isTextBold(campaignPrice));
        assertTrue("If campaign price text bigger than regular.",
                isCampaignPriceSizeBigger(regularPrice, campaignPrice));

        //Perform checks on a product page
        firstCampaignProduct.click();
        String productNameProductPage = driver.findElement(By.cssSelector("h1.title")).getText();
        System.out.println("product name: " + productNameProductPage);

        regularPrice = driver.findElement(By.cssSelector("s.regular-price"));
        int regularPriceProductPage = Integer.parseInt(regularPrice.getText().substring(1));
        System.out.println("regular price: " + regularPriceProductPage);

        campaignPrice = driver.findElement(By.cssSelector("strong.campaign-price"));
        int campaignPriceProductPage = Integer.parseInt(campaignPrice.getText().substring(1));
        System.out.println("campaign price: " + campaignPriceProductPage);

        assertEquals(productNameMainPage, productNameProductPage);
        assertEquals(regularPriceMainPage, regularPriceProductPage);
        assertTrue("If a regular price crossed.", isTextCrossed(regularPrice));
        assertTrue("If a color is grey for regular price.", isTextGrey(regularPrice));
        assertTrue("If a color is red for campaign price.", isTextRed(campaignPrice));
        assertTrue("If a campaign price is bold.", isTextBold(campaignPrice));
        assertTrue("If campaign price text bigger than regular.",
                isCampaignPriceSizeBigger(regularPrice, campaignPrice));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private boolean isTextCrossed(WebElement element) {
        String textDecoration = element.getCssValue("text-decoration");
        System.out.println("Text decoration: " + textDecoration);
        if (textDecoration.contains("line-through")) {
            return true;
        } else {
            return false;
        }
    }

    private int[] getRGB(WebElement element) {
        int[] rgb = new int[3];

        String color = element.getCssValue("color");
        System.out.println("Color: " + color);

        int r, g, b;
        int indexOfFirstComma = color.indexOf(',');
        int indexOfSecondComma = color.indexOf(',', indexOfFirstComma+1);
        //It will be -1 for Firefox
        int indexOfThirdComma = color.indexOf(',', indexOfSecondComma+1);

        rgb[0] = Integer.parseInt(color.substring(color.indexOf('(')+1, indexOfFirstComma));
        rgb[1] = Integer.parseInt(color.substring(indexOfFirstComma+2, indexOfSecondComma));
        if (indexOfThirdComma == -1) {
            rgb[2] = Integer.parseInt(color.substring(indexOfSecondComma+2, color.indexOf(')')));
        } else {
            rgb[2] = Integer.parseInt(color.substring(indexOfSecondComma+2, indexOfThirdComma));
        }

        return rgb;
    }

    private boolean isTextGrey(WebElement element) {
        int[] rgb = getRGB(element);
        return (rgb[0] == rgb[1] && rgb[0] == rgb[2]);
    }

    private boolean isTextRed(WebElement element) {
        int[] rgb = getRGB(element);
        return (rgb[0] != 0 && rgb[1] == 0 && rgb[2] == 0);
    }

    private boolean isTextBold(WebElement element) {
        int weight = Integer.parseInt(element.getCssValue("font-weight"));
        //A text is considered bold if its weight is 700+ (https://www.w3.org/wiki/CSS/Properties/font-weight)
        return (weight >= 700);
    }

    private boolean isCampaignPriceSizeBigger(WebElement regularPrice, WebElement campaignPrice) {
        String fontSizeOfRegularPrice = regularPrice.getCssValue("font-size");
        System.out.println("Font size of regular price: " + fontSizeOfRegularPrice);
        String fontSizeOfCampaignPrice = campaignPrice.getCssValue("font-size");
        System.out.println("Font size of campaign price: " + fontSizeOfCampaignPrice);

        double sizeOfRegularPrice = Double.parseDouble(
                fontSizeOfRegularPrice.substring(0, fontSizeOfRegularPrice.indexOf('p')));
        double sizeOfCampaignPrice = Double.parseDouble(
                fontSizeOfCampaignPrice.substring(0, fontSizeOfCampaignPrice.indexOf('p')));

        return (sizeOfCampaignPrice > sizeOfRegularPrice);
    }
}