package selenium.lesson5;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertTrue;

public class CheckCountriesZones {

    private WebDriver driver;
    private WebDriverWait wait;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void checkCountries() {
        int countryColumn = 5;
        int zonesColumn = 6;
        int zoneNameColumn = 3;

        //Login
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Get all country names and check the order
        List<WebElement> countryNames = driver.findElements(
                By.cssSelector("form[name=countries_form] tr.row > td:nth-of-type(" + countryColumn + ")"));
        System.out.println(countryNames.size() + " countries are received for processing.");

        try {
            assertTrue("Sorting of countries is invalid, refer to the message above.",
                    isAlphabeticalSortingCorrect(getTextViaGetText(countryNames)));
        } catch (Throwable t) {
            collector.addError(t);
        }

        //Check sorting of zones for countries that have multiple of them
        List<WebElement> rows = driver.findElements(
                By.cssSelector("form[name=countries_form] tr.row"));

        for(WebElement row : rows) {
            String numberOfZones = row.findElement(By.cssSelector("td:nth-of-type(" + zonesColumn + ")")).getText();
            if (Integer.parseInt(numberOfZones) != 0) {
                System.out.println("More than 0 zones are discovered: " + numberOfZones + ".");
                String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
                row.findElement(By.cssSelector("td:nth-of-type(" + countryColumn + ") a"))
                        .sendKeys(selectLinkOpenInNewTab);

                //Open a link in a new tab
                ArrayList<String> tabs = new ArrayList (driver.getWindowHandles());
                driver.switchTo().window(tabs.get(1));

                //Check sorting
                List<WebElement> zoneNames = driver.findElements(
                        By.cssSelector("table#table-zones tr:not(.header) > td:nth-of-type(" + zoneNameColumn + ") " +
                                "input[type=hidden]"));
                try {
                    assertTrue("Sorting of zones is invalid, refer to the message above.",
                            isAlphabeticalSortingCorrect(getTextViaCssValue(zoneNames)));
                } catch (Throwable t) {
                    collector.addError(t);
                }

                //Close the tab and return to the country list
                driver.close();
                driver.switchTo().window(tabs.get(0));
            }
        }
    }

    @Test
    public void checkZones() {
        int countryNameColumn = 3;
        int zoneColumn = 3;

        //Login
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Check sorting of zones for each country
        List<WebElement> links = driver.findElements(
                By.cssSelector("form[name=geo_zones_form] tr.row td:nth-of-type(" + countryNameColumn + ") a"));

        //Open country zones in a new tab, perform checks and close it
        String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
        for(WebElement link : links) {
            link.sendKeys(selectLinkOpenInNewTab);
            ArrayList<String> tabs = new ArrayList (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

            List<WebElement> selectedOptions = driver.findElements(
                    By.cssSelector("table#table-zones tr:not(.header) > td:nth-of-type(" + zoneColumn + ") " +
                            "option[selected=selected]"));
            try {
                assertTrue("Sorting of zones is invalid, refer to the message above.",
                        isAlphabeticalSortingCorrect(getTextViaGetText(selectedOptions)));
            } catch (Throwable t) {
                collector.addError(t);
            }

            driver.close();
            driver.switchTo().window(tabs.get(0));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private ArrayList<String> getTextViaGetText(List<WebElement> items) {
        ArrayList<String> textValues = new ArrayList<>();
        for (WebElement item : items) {
            textValues.add(item.getText());
        }
        return textValues;
    }

    private ArrayList<String> getTextViaCssValue(List<WebElement> items) {
        ArrayList<String> textValues = new ArrayList<>();
        for (WebElement item : items) {
            textValues.add(item.getCssValue("value"));
        }
        return textValues;
    }

    private boolean isAlphabeticalSortingCorrect(ArrayList<String> items) {
        String previousItemText = "";
        String currentItemText = "";
        boolean isSortingCorrect = true;

        for(String item : items) {
            currentItemText = item;

            //Executed during the first loop
            if (previousItemText.equals("")) {
                previousItemText = currentItemText;
                continue;
            }

            //DEBUG
            //System.out.println("Current item: " + currentItemText);
            //System.out.println("Previous item: " + previousItemText);

            //Compare chars
            for (int i=0; i<currentItemText.length(); i++) {
                if (currentItemText.charAt(i) > previousItemText.charAt(i)) {
                    break;
                } else if (currentItemText.charAt(i) < previousItemText.charAt(i)) {
                    System.out.println("Invalid sorting. Previous item: " + previousItemText +
                            ", next item: " + currentItemText + ".");
                    isSortingCorrect = false;
                    break;
                }

                //Check length of country names (break if prev is shorter and have the same start chars,
                //break with error if it is longer and have the same start chars
                if (previousItemText.length() == (i+1)) {
                    break;
                } else if (i == (currentItemText.length()-1) && (previousItemText.length() > currentItemText.length())) {
                    System.out.println("Invalid sorting. Previous item: " + previousItemText +
                            ", next item: " + currentItemText + ".");
                    isSortingCorrect = false;
                    break;
                }
            }
            previousItemText = currentItemText;
        }
        return isSortingCorrect;
    }
}