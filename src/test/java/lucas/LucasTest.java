package lucas;

import drivers.WebDriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LucasTest {
    private WebDriver driver;

    @BeforeEach
    void inicializaSelenium() {
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
    void finalizaSelenium() {
        if (driver != null) {
            System.out.println("Encerrando navegador.");
            driver.quit();
        }
    }

    @Nested
    @DisplayName("Testes da Página Inicial e Elementos Base")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class PaginaInicialTests {
        @Test
        @DisplayName("1. Verificar o título principal da página (Componente 1)")
        @Order(1)
        void teste1_tituloDePagina() {
            System.out.println("Verificando o título da página...");
            String expectedTitle = "Für Studierende | Universität Stuttgart";
            assertEquals(expectedTitle, driver.getTitle(), "O título da página está incorreto.");
            System.out.println("Título da página verificado: OK.");
        }
    }

    @Nested
    @DisplayName("Testes de Links de Navegação e Conteúdo")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class LinksENavegacaoTests {
        @Test
        @DisplayName("3. Verificar link 'Alle Studiengänge' e seu atributo HREF (Componente 3)")
        @Order(1)
        void teste3_LinkAlleStudiengaenge() {
            System.out.println("Verificando link 'Alle Studiengänge'...");
            WebElement link = driver.findElement(By.linkText("Alle Studiengänge"));
            assertNotNull(link, "Link 'Alle Studiengänge' não encontrado.");
            assertTrue(link.isDisplayed(), "Link 'Alle Studiengänge' não está visível.");
            assertEquals("https://www.student.uni-stuttgart.de/studiengang/", link.getAttribute("href"), "URL do link 'Alle Studiengänge' está incorreta.");
            System.out.println("Link 'Alle Studiengänge' visivel: OK.");
        }

        @Test
        @DisplayName("6. Teste de Links de Mídia Social - Facebook (Componente 6)")
        @Order(2)
        void teste6_LinkFacebookSocialMedia() {
            System.out.println("Verificando link do Facebook no rodapé...");
            WebElement fbLink = driver.findElement(By.cssSelector("a.is-facebook"));
            assertNotNull(fbLink, "Link do Facebook não encontrado.");
            assertTrue(fbLink.isDisplayed(), "Link do Facebook não está visível.");
            assertEquals("https://www.facebook.com/Universitaet.Stuttgart", fbLink.getAttribute("href"), "URL do Facebook está incorreta.");
            System.out.println("Link do Facebook verificado: OK.");
        }

        @Test
        @DisplayName("10. Teste de Ícone/Link do Instagram (Componente 10)")
        @Order(3)
        void teste10_LinkInstagramSocialMedia() {
            System.out.println("Verificando link do Instagram no rodapé...");
            WebElement instagramLink = driver.findElement(By.cssSelector("a.is-instagram"));
            assertNotNull(instagramLink, "Link do Instagram não encontrado.");
            assertTrue(instagramLink.isDisplayed(), "Link do Instagram não está visível.");
            assertEquals("https://www.instagram.com/unistuttgart/", instagramLink.getAttribute("href"), "URL do Instagram está incorreta.");
            System.out.println("Link do Instagram verificado: OK.");
        }
    }

    @Nested
    @DisplayName("Testes de Títulos de Seção")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TitulosDeSecaoTests {

        @Test
        @DisplayName("13. Teste de Título de Seção 'Veranstaltungen für Studierende' (Componente 13)")
        @Order(1)
        void teste13_tituloSecaoEventos() {
            System.out.println("Verificando o título da seção 'Veranstaltungen für Studierende'...");
            WebElement sectionHeader = driver.findElement(By.cssSelector("h2.stream-teaser__component-headline"));
            assertNotNull(sectionHeader, "Título da seção 'Veranstaltungen für Studierende' não encontrado.");
            assertTrue(sectionHeader.isDisplayed(), "Título da seção 'Veranstaltungen für Studierende' não está visível.");
            assertEquals("Veranstaltungen für Studierende", sectionHeader.getText().trim(), "Texto do título da seção está incorreto.");
            System.out.println("Título da seção 'Veranstaltungen für Studierende' verificado: OK.");
        }
    }
}