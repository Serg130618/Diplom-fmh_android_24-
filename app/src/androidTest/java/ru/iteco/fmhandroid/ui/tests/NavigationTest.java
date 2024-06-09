package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.Helper.generateScreenshotName;
import static ru.iteco.fmhandroid.ui.steps.NavigationSteps.*;
import static ru.iteco.fmhandroid.ui.steps.QuotesSteps.quoteText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.AboutAppSteps;
import ru.iteco.fmhandroid.ui.steps.AuthorizationSteps;
import ru.iteco.fmhandroid.ui.steps.NavigationSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.QuotesSteps;


@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@DisplayName("Навигация по приложению")
public class NavigationTest {

    AboutAppSteps aboutAppSteps = new AboutAppSteps();
    AuthorizationSteps authorizationSteps = new AuthorizationSteps();
    NavigationSteps navigationSteps = new NavigationSteps();
    NewsSteps newsSteps = new NewsSteps();
    QuotesSteps quotesSteps = new QuotesSteps();



    @Before
    public void logIn(){
        authorizationSteps.logInIfNotLoggedIn();
    }

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule = new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, generateScreenshotName("Failed"));


    @Test
    @DisplayName("Проверка списка вкладок кнопки меню")
    public void menuElementsShouldBeVisible() {
        navigationSteps.clickMainMenu();
        mainOfMenu.check(matches(isDisplayed()));
        newsOfMenu.check(matches(isDisplayed()));
        aboutOfMenu.check(matches(isDisplayed()));
    }

    @Test
    @DisplayName("Переход через 'Главное меню' в раздел 'Новости'")
    public void transitionToNewsPageTest() {
        navigationSteps.transitionToNewsPage();
        newsSteps.checkNewsPage();
    }

    @Test
    @DisplayName("Переход в раздел 'Новости' из раздела 'Главная' через кнопку 'Все новости'")
    public void transitionToNewsByAllNewsButtonTest() {
        navigationSteps.ExpandAllNews();
        newsSteps.checkNewsPage();
    }

    @Test
    @DisplayName("Переход через 'Главное меню' в раздел 'О приложении'")
    public void transitionToAboutAppPageTest() {
        navigationSteps.transitionToAboutPage();
        aboutAppSteps.visibilityAboutAppPage();
    }
    @Test
    @DisplayName("Переход на страницу 'Тематические цитаты'")
    public void transitionToQuotes() {
        navigationSteps.transitionToQuotesPage();
        quoteText.check(matches(isDisplayed()));
    }
    @Test
    @DisplayName("Кликабельность кнопки развернуть/свернуть список новостей")
    public void clickShowOrHideNewsBlock()  {
        navigationSteps.feedNews();
        allNewsRollUpButton.check(matches(not(isDisplayed())));
        navigationSteps.feedNews();
        allNewsRollUpButton.check(matches(isDisplayed()));
    }


    @Test
    @DisplayName("Выход авторизованного пользователя")
    public void checkLogOut() {
        authorizationSteps.logOutIfLogged();
    }



}