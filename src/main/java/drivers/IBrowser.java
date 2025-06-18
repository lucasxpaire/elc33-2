package drivers;

import org.openqa.selenium.WebDriver;

public interface IBrowser {

    WebDriver getDriver(String driverPath);
}
