import io.restassured.response.Response;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import api.UserApi;
import Constants.ConstantData;
import api.UserPojo;

public class BaseTest {
    WebDriver driver;
    String email;
    String password;

    public void initWebDriver() {
        String browser = System.getProperty("browser", "yandex").toLowerCase();
        ChromeOptions options = new ChromeOptions();
        addCommonArguments(options);

        switch (browser) {
            case "chrome":
                configureChromeDriver();
                break;
            case "yandex":
                configureYandexDriver(options);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver = new ChromeDriver(options);
        driver.get(Constants.ConstantData.BURGER_URL);
    }

    private void configureChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "C://Users//79161//WebDriver//bin//chromedriver-win64//chromedriver.exe");
    }

    private void configureYandexDriver(ChromeOptions options) {
        System.setProperty("webdriver.chrome.driver", "C://Users//79161//YandexDriver-25.2.1-stable//yandexdriver.exe");
        options.setBinary("C://Users/79161//AppData//Local//Yandex//YandexBrowser//Application//browser.exe");
    }

    private void addCommonArguments(ChromeOptions options) {
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--start-maximized");
        options.addArguments("--enable-automation");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--incognito");
    }

    @After
    public void tearDown() {
        String accessToken = UserApi.loginUser(new UserPojo(email, password)).then().extract().path("accessToken");
        if (accessToken != null) {
            UserApi.deleteUser(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
