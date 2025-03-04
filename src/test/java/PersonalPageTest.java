import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import api.UserApi;
import api.UserPojo;
import pageObject.Login;
import pageObject.Main;
import pageObject.Personal;
import pageObject.Registration;

public class PersonalPageTest extends BaseTest {
    private Main mainPage;
    private Login loginPage;
    private Registration registerPage;
    private Personal personalPage;
    private UserPojo userPojo;

    @Before
    public void setUp() {
        initWebDriver();
        mainPage = new Main(driver);
        loginPage = new Login(driver);
        registerPage = new Registration(driver);
        personalPage = new Personal(driver);

        //создаем нового пользователя
        String name = "Kirill";
        email = "proverka2@yandex.ru";
        password = "123456";
        userPojo = new UserPojo(email,password,name);
        UserApi.createUser(userPojo);

        //кликаем "Войти в аккаунт"
        mainPage.clickLogin();
        //ждем загрузки страницы логина
        loginPage.waitForLoad();
        //заполняем форму логина
        loginPage.fillLoginForm(email, password);
        loginPage.clickLogin();
        mainPage.waitForLoad();
    }

    @Test
    @DisplayName("Переход по клику на «Личный кабинет».")
    public void checkEnterPersonalAccount() {
        //клик на личный кабинет
        mainPage.clickPersonal();
        //проверяем видимость кнопки профиль
        personalPage.waitForLoad();
        Assert.assertTrue("Вход в личный кабинет не выполнен", personalPage.isProfileButtonVisible());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на «Конструктор»")
    public void checkEnterConstructorByConstructorButton() {
        //клик на личный кабинет
        mainPage.clickPersonal();
        personalPage.waitForLoad();
        //кликаем на Конструктор
        personalPage.clickConstructor();
        //Проверяем видимость кнопки оформить заказ
        mainPage.waitForLoad();
        Assert.assertTrue("Переход в конструктор не произошел", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    public void checkEnterConstructorByLogo() {
        //клик на личный кабинет
        mainPage.clickPersonal();
        personalPage.waitForLoad();
        //кликаем на лого
        personalPage.clickLogo();
        //Проверяем видимость кнопки оформить заказ
        mainPage.waitForLoad();
        Assert.assertTrue("Переход в конструктор не произошел", mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка выхода по кнопке Выйти")
    public void checkLogoutByButtonLogout() {
        //клик на личный кабинет
        mainPage.clickPersonal();
        personalPage.waitForLoad();
        //клик на Выйти
        personalPage.clickLogout();
        //проверяем что перешли на страницу Логина
        loginPage.waitForLoad();
        Assert.assertTrue("Выход не произошел", loginPage.isEnterLabelVisible());
    }
}