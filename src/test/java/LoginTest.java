import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import api.UserApi;
import api.UserPojo;
import pageObject.Login;
import pageObject.Main;
import pageObject.Registration;
import pageObject.ResetPassword;

public class LoginTest extends BaseTest {

    private Main mainPage;
    private Login loginPage;
    private Registration registerPage;
    private UserPojo userPojo;

    @Before
    public void setUp() {
        initWebDriver();
        mainPage = new Main(driver);
        loginPage = new Login(driver);
        registerPage = new Registration(driver);

        //создаем нового пользователя
        String name = "Kirill";
        email = "proverka@yandex.ru";
        password = "123456";
        userPojo = new UserPojo(email,password,name);
        UserApi.createUser(userPojo);
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной странице")
    public void checkLoginOnMainPage() {
        //клик на кнопку войти
        mainPage.clickLogin();
        //заполняем форму логина
        loginPage.waitForLoad();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        //Проверяем что появилась кнопка оформить заказ
        mainPage.waitForLoad();
        Assert.assertTrue("Кнопка оформить заказ не появилась", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void checkLoginByPersonalButton() {
        //клик на кнопку личный кабинет
        mainPage.clickPersonal();
        //заполняем форму логина
        loginPage.waitForLoad();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        //Проверяем что появилась кнопка оформить заказ
        mainPage.waitForLoad();
        Assert.assertTrue("Кнопка оформить заказ не появилась", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void checkLoginOnRegistrationPage() {
        mainPage.clickLogin();
        //ждем загрузки страницы логина
        loginPage.waitForLoad();
        //кликаем на Зарегистроваться
        loginPage.clickRegister();
        //ждем загрузки страницы регистрации
        registerPage.waitForLoad();
        //клик на кнопку Войти
        registerPage.clickLogin();
        //заполняем форму логина
        loginPage.waitForLoad();
        loginPage. fillLoginForm(email, password);
        loginPage.clickLogin();
        //Проверяем что появилась кнопка оформить заказ
        mainPage.waitForLoad();
        Assert.assertTrue("Кнопка оформить заказ не появилась", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void checkLoginOnResetPasswordPage() {
        ResetPassword resetPasswordPage = new ResetPassword(driver);

        mainPage.clickLogin();
        loginPage.waitForLoad();
        loginPage.clickReset();
        //Кликаем на Войти
        resetPasswordPage.waitForLoad();
        resetPasswordPage.clickLogin();
        //ждем загрузки страницы регистрации
        loginPage.waitForLoad();
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        //Проверяем что появилась кнопка оформить заказ
        mainPage.waitForLoad();
        Assert.assertTrue("Кнопка оформить заказ не появилась", mainPage.isOrderButtonVisible());
    }
}