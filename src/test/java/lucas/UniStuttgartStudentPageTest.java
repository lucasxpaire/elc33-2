package lucas;

import drivers.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniStuttgartStudentPageTest {
    private WebDriver driver;

    @BeforeEach
    void setup() {
        String BROWSER_TO_USE = "edge";
        String DRIVER_PATH = "src/driver/msedgedriver.exe";
        String BASE_URL = "https://www.student.uni-stuttgart.de/";

        System.out.println("Configurando o teste: Iniciando navegador " + BROWSER_TO_USE);
        driver = WebDriverFactory.getWebDriver(BROWSER_TO_USE, DRIVER_PATH);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);
        System.out.println("Navegou para: " + BASE_URL);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            System.out.println("Encerrando navegador.");
            driver.quit();
        }
    }

    @Test
    void testPageTitle() {
        System.out.println("Verificando o título da página...");
        String expectedTitle = "Für Studierende | Universität Stuttgart";
        assertEquals(expectedTitle, driver.getTitle(), "O título da página está incorreto.");
        System.out.println("Título da página verificado: OK.");
    }
}
