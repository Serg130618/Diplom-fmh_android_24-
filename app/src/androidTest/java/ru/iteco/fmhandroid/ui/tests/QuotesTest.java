package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static ru.iteco.fmhandroid.ui.Helper.waitElement;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.AuthorizationSteps;
import ru.iteco.fmhandroid.ui.steps.NavigationSteps;
import ru.iteco.fmhandroid.ui.steps.QuotesSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class QuotesTest {


    AuthorizationSteps authorizationPage = new AuthorizationSteps();
    NavigationSteps mainPage = new NavigationSteps();
    QuotesSteps quotesSteps = new QuotesSteps();
    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);


    @Before
    public void logIn() {
        authorizationPage.logInIfNotLoggedIn();
        mainPage.transitionToNewsPage();
    }

    @Test
    @DisplayName("Переход на страницу Тематические цитаты с помощью кнопки (бабочка)")
    public void goToQuotesPageTest() {
        mainPage.transitionToQuotesPage();
        quotesSteps.checkQuotesPage();
    }

    @Test
    @DisplayName("Развернуть/свернуть цитату")
    public void checkShowOrHideQuote() {
        String testQuote = "Нет шаблона и стандарта, есть только дух, который живет в разных домах по-разному. Но всегда он добрый, любящий и помогающий.";
        mainPage.transitionToQuotesPage();
        quotesSteps.showHideQuote(1);
        waitElement(quotesSteps.missionListId);
        quotesSteps.descriptionIsDisplayed(testQuote);
        quotesSteps.showHideQuote(1);
    }

}
