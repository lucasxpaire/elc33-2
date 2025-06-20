package leandro;

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

public class LeandroTest {
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
	class TesteDeDiferentesElementos {

		@Test
		@DisplayName("5. Teste de Imagem Principal (img)")
		@Order(1)
		void testeImagemLogoUniversidade() {
			// Tenta localizar a imagem da logo pelo alt exato
			WebElement logo = driver.findElement(By.cssSelector(
					"img[alt='Logo: Universität Stuttgart - zur Startseite']"));

			// Verificações de segurança
			assertNotNull(logo, "Logo da universidade não foi encontrado.");
			assertTrue(logo.isDisplayed(),
					"Logo da universidade não está visível.");
//
			String src = logo.getAttribute("src");
			assertNotNull(src, "Imagem da logo não possui atributo 'src'.");
			assertTrue(src.contains("logo"),
					"O 'src' da imagem não parece correto: " + src);
			assertEquals("Logo: Universität Stuttgart - zur Startseite",
					logo.getAttribute("alt"),
					"O atributo 'alt' da imagem não está correto.");

			System.out.println(
					"Imagem da logo validada com sucesso: src = " + src);
		}

		@Test
		@DisplayName("2. Teste de Presença e Conteúdo do Cabeçalho Principal (h1/h2) ")
		@Order(2)
		void testeH1() {
			// Tenta localizar a imagem da logo pelo alt exato
			WebElement h1 = driver
					.findElement(By.className("intro-teaser__headline"));

			// Verificações de segurança
			assertNotNull(h1, "Título não foi encontrado.");
			assertTrue(h1.isDisplayed(), "O título não está visível.");
			String textoH1 = h1.getText().trim();
			assertFalse(textoH1.isEmpty(), "O conteúdo do h1 está vazio.");
			System.out.println("Título principal encontrado: " + textoH1);

		}

		@Test
		@DisplayName("8. Teste de Lista de Navegação Principal (ul/li)")
		@Order(3)
		void testeUlLi() {
			// Encontra a <ul> com a classe esperada
			WebElement ul = driver
					.findElement(By.className("linklist_standard"));
			assertNotNull(ul,
					"Elemento <ul> com a classe 'linklist_standard' não foi encontrado.");
			assertTrue(ul.isDisplayed(), "O <ul> não está visível.");

			// Busca os <li> dentro da <ul>
			List<WebElement> itens = ul.findElements(By.tagName("li"));
			assertFalse(itens.isEmpty(),
					"Nenhum item <li> encontrado dentro da <ul>.");

			// Verifica se cada <li> tem um link com texto
			for (WebElement li : itens) {
				WebElement link = li.findElement(By.tagName("a"));
				assertNotNull(link, "Item da lista não contém um link.");
				String texto = link.getText().trim();
				assertFalse(texto.isEmpty(), "Texto do link está vazio.");
				System.out.println("Item do menu: " + texto);
			}
		}

		@Test
		@DisplayName("12. Teste de um Campo de Busca Interno (input de texto)")
		@Order(4)
		void testeInput() {
			// Encontra o campo de input com múltiplas classes
			WebElement input = driver
					.findElement(By.cssSelector(".text-input.form-control"));

			assertNotNull(input, "Campo de input não foi encontrado.");
			assertTrue(input.isDisplayed(), "Campo de input não está visível.");
			assertEquals("text", input.getAttribute("type"),
					"O tipo do input não é 'text'.");

			// Testa se é possível digitar
			String textoBusca = "Computer Science";
			input.sendKeys(textoBusca);
			assertEquals(textoBusca, input.getAttribute("value"),
					"O valor digitado não corresponde.");

			System.out.println(
					"Campo de busca testado com sucesso. Valor digitado: "
							+ textoBusca);
		}

		@Test
		@DisplayName("7. Teste do botão hambúrguer (menu responsivo)")
		@Order(5)
		void testeBotaoHamburguer() {
			WebElement botaoHamburguer = driver
					.findElement(By.cssSelector(".lines-button"));

			assertNotNull(botaoHamburguer,
					"Botão hambúrguer não foi encontrado.");
			assertTrue(botaoHamburguer.isDisplayed(),
					"Botão hambúrguer não está visível.");

			// Clica no botão hambúrguer
			botaoHamburguer.click();
			System.out.println("Botão hambúrguer clicado.");

			// Aguarda o menu expandido aparecer (ajuste esse seletor se
			// necessário)
			WebDriverWait wait = new WebDriverWait(driver,
					Duration.ofSeconds(10));
			WebElement menuLateral = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("level-0-0") // ou outro
																	// ID/classe
																	// do menu
																	// aberto
					));

			assertTrue(menuLateral.isDisplayed(),
					"Menu lateral não apareceu após clicar no botão.");

			System.out.println("Menu lateral foi aberto com sucesso.");
		}

	}
}
