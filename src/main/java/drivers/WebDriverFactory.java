package drivers;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {

    public static WebDriver getWebDriver(String browserName, String driverPath) {
        IBrowser browser = switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeBrowser();
            case "edge" -> new EdgeBrowser();
            default -> throw new IllegalArgumentException("Falha: Navegador não suportado " + browserName);
        };

        return browser.getDriver(driverPath);
    }
}