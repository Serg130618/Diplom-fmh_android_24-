package ru.iteco.fmhandroid.ui.tests;

import static ru.iteco.fmhandroid.ui.Helper.*;
import static ru.iteco.fmhandroid.ui.Helper.generateScreenshotName;
import static ru.iteco.fmhandroid.ui.Helper.waitElement;

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
import ru.iteco.fmhandroid.ui.steps.AuthorizationSteps;
import ru.iteco.fmhandroid.ui.steps.NavigationSteps;


@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@DisplayName("Раздел 'Авторизация'")
public class AuthorizationTest {

    AuthorizationSteps authorizationSteps = new AuthorizationSteps();
    NavigationSteps mainPage = new NavigationSteps();

    @Before
    public void logIn(){
        authorizationSteps.logOutIfLogged();
    }

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule = new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, generateScreenshotName("Failed"));


    @Test
    @DisplayName("Вход с валидными данными")
    public void validLoginAndPasswordTest() {
        authorizationSteps.login(validLogin, validPassword);
        waitElement(mainPage.LogOutId);
        mainPage.textAuthorization();
        }

    @Test
    @DisplayName("Вход с невалидным логином и валидным паролем")
    public void invalidLoginTest() {
        authorizationSteps.login(invalidLogin, validPassword);
        authorizationSteps.loginOrPasswordIsWrong();
    }

    @Test
    @DisplayName("Вход с валидным логином и невалидным паролем")
    public void invalidPasswordTest() {
        authorizationSteps.login(validLogin, invalidPassword);
        authorizationSteps.loginOrPasswordIsWrong();
    }

    @Test
    @DisplayName("Вход с пустыми логином и паролем")
    public void emptyLoginAndPasswordTest() {
        authorizationSteps.login(emptyLogin,emptyPassword);
        authorizationSteps.loginOrPasswordEmpty();
    }

    @Test
    @DisplayName("Вход с невалидными логином и паролем")//
    public void notValidLoginAndPassword() {
        authorizationSteps.login(invalidLogin, invalidPassword);
        authorizationSteps.loginOrPasswordIsWrong();
    }
    @Test
    @DisplayName("Вход с невалидным логином и валидным паролем попытка2")
    public void invalidLoginTest2() {
        authorizationSteps.login(invalidLogin, validPassword);
        authorizationSteps.errorPopupWindow();
    }

    @Test
    @DisplayName("Вход с валидным логином и невалидным паролем попытка2")
    public void invalidPasswordTest2() {
        authorizationSteps.login(validLogin, invalidPassword);
        authorizationSteps.errorPopupWindow();
    }
    @Test
    @DisplayName("Вход с невалидными логином и паролем попытка2")//
    public void notValidLoginAndPasswordTest2() {
        authorizationSteps.login(invalidLogin, invalidPassword);
        authorizationSteps.errorPopupWindow();
    }

}