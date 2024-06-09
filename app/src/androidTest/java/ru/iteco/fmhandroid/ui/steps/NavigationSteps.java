package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.Helper.waitElement;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class NavigationSteps {

    public static ViewInteraction mainMenuButton = onView(allOf(withId(R.id.main_menu_image_button)));
    public static ViewInteraction newsOfMenu = onView(withText(R.string.news));
    public static ViewInteraction mainOfMenu = onView(withText("Main"));
    private ViewInteraction authorizationButton = onView(allOf(withId(R.id.authorization_image_button)));
    private ViewInteraction quotesButton = onView(allOf(withId(R.id.our_mission_image_button)));
    public static ViewInteraction aboutOfMenu = onView(withText(R.string.about));
    public static ViewInteraction allNewsButton = onView(allOf(withId(R.id.all_news_text_view)));
    private final int mainMenuButtonId = R.id.main_menu_image_button;
    private int allNewsButtonId = R.id.all_news_text_view;
    public static ViewInteraction allNewsRollUpButton = onView(withId(R.id.all_news_text_view));
    public int LogOutId = R.id.authorization_image_button;
    public static ViewInteraction expandNewsFeedButton = onView(withId(R.id.expand_material_button));
    private int quotesButtonID = R.id.our_mission_image_button;

    @Step("Выход из аккаунта.")
    public void logOut() {
        Allure.step("Выходим из аккаунта, если авторизованы.");
        onView(withId(LogOutId)).perform(click());
        onView(withId(android.R.id.title)).check(matches(isDisplayed()));
        onView(withId(android.R.id.title)).perform(click());
    }
    @Step("Проверка кнопки основного меню")
    public void clickMainMenu () {
        Allure.step("Клик по основному меню");
        mainMenuButton.check(matches(isDisplayed()));
        mainMenuButton.perform(click());
    }

    @Step("Переход в раздел 'Новости'")
    public void transitionToNewsPage() {
        Allure.step("Кликнуть 'Главное меню', выбрать раздел 'Новости'");
        waitElement(mainMenuButtonId);
        mainMenuButton.perform(click());
        newsOfMenu.check(matches(isDisplayed()));
        newsOfMenu.perform(click());
    }

    @Step("Переход в раздел 'Новости' через кнопку 'Все новости'")
    public void ExpandAllNews() {
        Allure.step("Кликнуть 'все новости' на Главной странице");
        waitElement(allNewsButtonId);
        allNewsButton.perform(click());
    }

    @Step("Переход в раздел 'О приложении'")
    public void transitionToAboutPage() {
        Allure.step("Кликнуть 'Главное меню', выбрать раздел 'О приложении'");
        waitElement(mainMenuButtonId);
        mainMenuButton.perform(click());
        aboutOfMenu.check(matches(isDisplayed()));
        aboutOfMenu.perform(click());
    }

    @Step("Переход в раздел 'Тематические цитаты'")
    public void transitionToQuotesPage() {
        Allure.step("Клик по кнопке (бабочка)");
        waitElement(quotesButtonID);
        quotesButton.perform(click());
    }

    @Step("Проверка видимости кнопки выхода из аккаунта.")
    public void textAuthorization() {
        Allure.step("Проверка что залогинились");
        authorizationButton.check(matches(isDisplayed()));
    }
    @Step("Проверка видимости ленты новостей.")
    public void feedNews() {
        Allure.step("Кликнуть галочку ленты новостей на главной странице");
        expandNewsFeedButton.check(matches(isDisplayed()));
        expandNewsFeedButton.perform(click());
    }

}