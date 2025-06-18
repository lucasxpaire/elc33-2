package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeBrowser implements IBrowser {
    @Override
    public WebDriver getDriver(String driverPath) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        return new ChromeDriver();
    }
}
