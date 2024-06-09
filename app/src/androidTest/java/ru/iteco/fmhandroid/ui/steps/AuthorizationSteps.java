package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.Helper.checkToast;
import static ru.iteco.fmhandroid.ui.Helper.*;
import static ru.iteco.fmhandroid.ui.Helper.waitElement;
import static ru.iteco.fmhandroid.ui.Helper.waitUntilVisible;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;

import org.hamcrest.core.IsInstanceOf;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class AuthorizationSteps {

    public static ViewInteraction fieldLogin = onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.login_text_input_layout))));
    public static ViewInteraction fieldPassword = onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.password_text_input_layout))));
    public static ViewInteraction signInButton = onView(allOf(withId(R.id.enter_button)));
    public int signInButtonId = R.id.enter_button;

    NavigationSteps mainPage = new NavigationSteps();

    @Step("Авторизация в приложении")
    public void login(String login, String password){
        Allure.step("Вводим логин и пароль: " + login + ", " + password);
        waitElement(signInButtonId);
        fieldLogin.perform(replaceText(login));
        fieldPassword.perform(replaceText(password));
        signInButton.check(matches(isDisplayed())).perform(click());
    }

    @Step("Вход в аккаунт, если не авторизован")
    public void logInIfNotLoggedIn() {
        Allure.step("Вход в аккаунт, если не авторизован с валидными данными");
        if (isLogIn()) {
            login(validLogin, validPassword);
        }
    }

    @Step("Выход авторизованного пользователя")
    public void logOutIfLogged() {
        Allure.step("Выход из аккаунта.");
        if (isLogOut()) {
            mainPage.logOut();
        }
    }

    @Step("Окно с ошибкой при пустых логине и/или пароле")
    public void loginOrPasswordEmpty() {
        Allure.step("Проверяем видимость сообщения об ошибке при пустых логине и/или пароле.");
        waitUntilVisible(checkToast(R.string.empty_login_or_password, true));
    }

    @Step("Окно с ошибкой при неправильных логине и/или пароле.")
    public void loginOrPasswordIsWrong() {
        Allure.step("Проверяем видимость сообщения об ошибке при неправильных логине и/или пароле.");
        waitUntilVisible(checkToast(R.string.wrong_login_or_password, true));
    }
    @Step("Окно с ошибкой 'чтото пошло не так'")
    public void errorPopupWindow () {
        Allure.step("Проверяем видимость сообщения об ошибке с другой формулировкой");
        ViewInteraction imageView = onView(
                allOf(withContentDescription("app background image"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));
    }


    public boolean isLogIn() {
        boolean check =false;
        try {
            waitElement(signInButtonId);
            fieldLogin.check(matches(isDisplayed()));
            check = true;
            return check;
        } catch (NoMatchingViewException e) {
            check = false;
            return check;
        } finally {
            return check;
        }
    }

    public boolean isLogOut() {
        boolean check =false;
        try {
            waitElement(mainPage.LogOutId);
            mainPage.textAuthorization();
            check = true;
            return check;
        } catch (NoMatchingViewException e) {
            check = false;
            return check;
        } finally {
            return check;
        }
    }

}