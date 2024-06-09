package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.is;

import androidx.test.espresso.ViewInteraction;

import org.hamcrest.Matchers;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class QuotesSteps {


    public static ViewInteraction missionList = onView(allOf(withId(R.id.our_mission_item_list_recycler_view)));
    public static ViewInteraction quoteBody (String text) {
         return onView(Matchers.allOf(withId(R.id.our_mission_item_description_text_view), withText(text)));
    }
    public int missionListId = R.id.our_mission_item_list_recycler_view;
    public static ViewInteraction quoteText = onView(allOf(withId(R.id.our_mission_title_text_view)));


    @Step("Проверка что цитаты разворачиваются")
    public void showHideQuote(int number) {
        Allure.step("Развернуть/свернуть цитату");
        missionList.check(matches(isDisplayed()));
        missionList.perform(actionOnItemAtPosition(number, click()));
    }
    @Step("Сравниваем текст содержания цитаты")
    public void descriptionIsDisplayed(String text) {
        Allure.step("Проверка содержания цитаты");
        quoteBody(text).check(matches(isDisplayed()));
    }
    @Step("Проверяем видимость страницы 'Тематические цитаты'")
    public void checkQuotesPage() {
        Allure.step("Проверяем, что открыт раздел 'Тематические цитаты'");
        quoteText.check(matches(isDisplayed()));
    }

}
