import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageObject.Login;
import pageObject.Main;
import pageObject.Registration;


public class RegistrationTest extends BaseTest {
    Main mainPage;
    Login loginPage;
    Registration registerPage;
    @Before
    public void setUp() {
        initWebDriver();
        mainPage = new Main(driver);
        loginPage = new Login(driver);
        registerPage = new Registration(driver);

        //кликаем "Войти в аккаунт"
        mainPage.clickLogin();
        //ждем загрузки страницы логина
        loginPage.waitForLoad();
        //кликаем на Зарегистроваться
        loginPage.clickRegister();
        //ждем загрузки страницы регистрации
        registerPage.waitForLoad();
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    public void checkRegisterNewUser() {
        String name = "Kirill";
        email = "proverka3@yandex.ru";
        password = "123456";
        registerPage.fillRegistrationForm(name, email, password);
        registerPage.clickRegister();
        loginPage.waitForLoad();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        mainPage.waitForLoad();
        Assert.assertTrue("Регистрация не произошла", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка ввода некорректного пароля")
    public void checkErrorWrongPassword() {
        //заполняем форму регистрации
        String name = "Kirill";
        email = "proverka3@yandex.ru";
        password = "12345";
        registerPage.fillRegistrationForm(name, email, password);
        registerPage.clickRegister();
        //проверяем что появилась надпись о некорректном пароле
        Assert.assertTrue("Ошибка о некорректном пароле не появилась",
                registerPage.isIncorrectPasswordLabelVisible());
    }
}