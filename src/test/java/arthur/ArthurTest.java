package arthur;

import drivers.WebDriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArthurTest {
    private WebDriver driver;

    @BeforeEach
    void inicializaSelenium() {
        String BROWSER_TO_USE = "edge";
        String DRIVER_PATH = "src/driver/msedgedriver.exe";
        String BASE_URL = "https://www.student.uni-stuttgart.de/";

        System.out.println("Iniciando navegador...");
        driver = WebDriverFactory.getWebDriver(BROWSER_TO_USE, DRIVER_PATH);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(BASE_URL);
    }

    @AfterEach
    void finalizaSelenium() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Nested
    @DisplayName("Testes de Elementos Não-Leandro - Versão Completa")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ElementosSemLeandroTests {

        @Test
        @DisplayName("4. Testar botão de busca (presença, visibilidade, texto)")
        @Order(1)
        void testeBotaoBusca() {
        WebElement botaoBusca = driver.findElement(By.cssSelector("button[aria-controls='search-box']"));


            assertNotNull(botaoBusca, "Botão de busca não encontrado.");
            assertTrue(botaoBusca.isDisplayed(), "Botão de busca não está visível.");

            String textoBotao = botaoBusca.getText().trim();
            assertTrue(!textoBotao.isEmpty(), "Botão de busca não tem texto.");
            System.out.println("Texto do botão de busca: " + textoBotao);

            // Testar funcionalidade: ao clicar, campo de busca aparece
            botaoBusca.click();
            WebElement campoBusca = driver.findElement(By.cssSelector("input[type='search']"));
            assertTrue(campoBusca.isDisplayed(), "Campo de busca não apareceu após clicar no botão.");
        }

        @Test
        @DisplayName("9. Testar link no conteúdo (presença, visibilidade, href, texto)")
        @Order(2)
        void testeLinkConteudo() {
            List<WebElement> links = driver.findElements(By.cssSelector("main a[href]"));

            assertFalse(links.isEmpty(), "Nenhum link encontrado no conteúdo principal.");

            WebElement primeiroLink = links.get(0);
            assertTrue(primeiroLink.isDisplayed(), "Primeiro link do conteúdo não está visível.");
            assertTrue(primeiroLink.getAttribute("href").startsWith("http"), "O href do link não é válido.");
            assertFalse(primeiroLink.getText().isEmpty(), "Link não possui texto visível.");

            System.out.println("Link verificado: " + primeiroLink.getText() + " -> " + primeiroLink.getAttribute("href"));
        }

        @Test
        @DisplayName("11. Testar imagem secundária (visível, src, alt)")
        @Order(3)
        void testeImagemSecundaria() {
            List<WebElement> imagens = driver.findElements(By.cssSelector("img"));

            assertFalse(imagens.isEmpty(), "Nenhuma imagem encontrada na página.");
            WebElement imagem = imagens.get(4); // Pega uma imagem que não seja a logo (supondo)

            assertTrue(imagem.isDisplayed(), "Imagem secundária não está visível.");

            String src = imagem.getAttribute("src");
            assertNotNull(src, "Imagem não possui atributo src.");
            assertTrue(src.startsWith("https://"), "src da imagem não é uma URL válida.");

            String alt = imagem.getAttribute("alt");
            assertNotNull(alt, "Imagem não possui atributo alt.");
            System.out.println("Imagem secundária -> src: " + src + ", alt: " + alt);
        }

        @Test
@DisplayName("15. Testar link no menu lateral (visibilidade, texto, href, ordem)")
@Order(4)
void testeMenuLateralLink() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Abre o menu lateral
    WebElement menuButton = driver.findElement(By.cssSelector("button.lines-button")); 
    menuButton.click();

    // Espera o menu expandir (esperando que o <ul> esteja visível)
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("level-0-0")));

    List<WebElement> menuLinks = driver.findElements(By.cssSelector("#level-0-0 li a[href]"));
    assertFalse(menuLinks.isEmpty(), "Nenhum link de navegação lateral encontrado.");

    // Encontrar o primeiro link realmente visível
    WebElement primeiroLinkVisivel = null;
    for (WebElement link : menuLinks) {
        if (link.isDisplayed()) {
            primeiroLinkVisivel = link;
            break;
        }
    }

    assertNotNull(primeiroLinkVisivel, "Nenhum link visível encontrado no menu lateral.");

    assertFalse(primeiroLinkVisivel.getText().isEmpty(), "Link do menu não possui texto.");
    assertTrue(primeiroLinkVisivel.getAttribute("href").startsWith("https://"), "href do link é inválido.");

    System.out.println("Primeiro link visível do menu lateral: " + primeiroLinkVisivel.getText());

    // Testar ordem: verificar pelo menos os dois primeiros visíveis
    List<WebElement> linksVisiveis = menuLinks.stream().filter(WebElement::isDisplayed).toList();
    if (linksVisiveis.size() >= 2) {
        WebElement segundoLink = linksVisiveis.get(1);
        assertNotEquals(linksVisiveis.get(0).getText(), segundoLink.getText(), "Dois primeiros links visíveis são iguais.");
    }
}


        @Test
        @DisplayName("14. Testar parágrafo específico com texto institucional")
        @Order(5)
        void testeParagrafoEspecifico() {
            // Procura o primeiro <p> dentro de um bloco principal
            WebElement paragrafo = driver.findElement(By.cssSelector("main p"));

            assertNotNull(paragrafo, "Parágrafo específico não encontrado.");
            assertTrue(paragrafo.isDisplayed(), "Parágrafo não está visível.");

            String texto = paragrafo.getText().trim();
            assertFalse(texto.isEmpty(), "Texto do parágrafo está vazio.");

            // Verifica se contém palavras-chave esperadas (ajuste conforme o site)
            assertTrue(texto.length() > 20, "Texto do parágrafo é muito curto para ser informativo.");
            System.out.println("Texto do parágrafo: " + texto);
        }

    }
}