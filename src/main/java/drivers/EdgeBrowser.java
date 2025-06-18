package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeBrowser implements IBrowser {
    @Override
    public WebDriver getDriver(String driverPath) {
        System.setProperty("webdriver.edge.driver", driverPath);
        return new EdgeDriver();
    }
}
