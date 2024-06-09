package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.Helper.*;
import static ru.iteco.fmhandroid.ui.Helper.generateScreenshotName;

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

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@DisplayName("Раздел 'О приложении'")
public class AboutAppTest {

    AuthorizationSteps authorizationPage = new AuthorizationSteps();
    AboutAppSteps aboutAppSteps = new AboutAppSteps();
    NavigationSteps mainPage = new NavigationSteps();

    @Before
    public void logIn (){
        authorizationPage.logInIfNotLoggedIn();
        mainPage.transitionToAboutPage();
    }

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule = new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, generateScreenshotName("Failed"));

    @Test
    @DisplayName("Отображение страницы 'О приложении'")
    public void displayingAboutAppPage() {
        aboutAppSteps.visibilityAboutAppPage();

    }

    @Test
    @DisplayName("Проверка наличия ссылок Политики и Условий")
    public void linksAvailability() {
        aboutAppSteps.privacyPolicyLinkAvailability();
        aboutAppSteps.termsLinkAvailability();
    }

    @Test
    @DisplayName("Переход по ссылке на страницу с текстом Политики конфиденциальности")
    public void clickingLinkUrlPrivacyPolicyTest() {
        aboutAppSteps.isAboutAppPageExists(urlPrivacyPolicy);
    }

    @Test
    @DisplayName("Переход по ссылке на страницу с текстом Пользовательского соглашения")
    public void clickingLinkUrlTermsOfUseTest() {
        aboutAppSteps.isAboutAppPageExists(urlTermsOfUse);
    }

}