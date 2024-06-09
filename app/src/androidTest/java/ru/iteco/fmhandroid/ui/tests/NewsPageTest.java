package ru.iteco.fmhandroid.ui.tests;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.Helper.*;
import static ru.iteco.fmhandroid.ui.Helper.generateScreenshotName;
import static ru.iteco.fmhandroid.ui.Helper.waitElement;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.rules.ScreenshotRule;
import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.Helper;
import ru.iteco.fmhandroid.ui.steps.AuthorizationSteps;
import ru.iteco.fmhandroid.ui.steps.NavigationSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
@DisplayName("Раздел 'Новости'")
public class NewsPageTest {

    AuthorizationSteps authorizationSteps = new AuthorizationSteps();
    NavigationSteps navigationSteps = new NavigationSteps();
    NewsSteps newsSteps = new NewsSteps();

    @Before
    public void logIn() {
        authorizationSteps.logInIfNotLoggedIn();
        navigationSteps.transitionToNewsPage();
    }

    @Rule
    public ActivityScenarioRule<AppActivity> activityScenarioRule = new ActivityScenarioRule<>(AppActivity.class);

    @Rule
    public ScreenshotRule screenshotRule = new ScreenshotRule(ScreenshotRule.Mode.FAILURE, generateScreenshotName("Failed"));


    @Test
    @DisplayName("Проверка отображения страницы новости")
    public void displayNewsPageTest() {
        Allure.step("Проверка что мы находимся на вкладке новостей");
        newsSteps.titleOfNewsBlock.check(matches(isDisplayed()));
        newsSteps.allNewsList.check(matches(isDisplayed()));
    }
    @Test
    @DisplayName("Свернуть/развернуть выбранную новость")
    public void rollupNewsTest() {
        Allure.step("Свернуть/развернуть выбранную новость");
        ViewInteraction recyclerView = newsSteps.getRecyclerViewAndScrollToFirstPosition();
        int heightBeforeClick = newsSteps.getHeightBeforeClick(recyclerView);
        int heightAfterClick = newsSteps.getHeightAfterClick(recyclerView);
        waitElement(newsSteps.newsListId);
        newsSteps.doubleClickFirstItem(recyclerView);
        newsSteps.checkHeightAfterDoubleClick(heightBeforeClick, heightAfterClick);
    }
    @Test
    @DisplayName("Создание новой новости")
    public void createdNewsTest() {
        newsSteps.addNews(category, tittleNews, dateNews, timeNews, descriptionNews);
        newsSteps.editNews(tittleNews);
        newsSteps.checkNews();
    }

    @Test
    @DisplayName("Создание новой новости с пустыми полями")
    public void createdNewsWithEmptyFieldsTest() {
        newsSteps.addNewsWithEmptyFields();
        newsSteps.fieldsDoesNotBeEmpty();
    }

    @Test
    @DisplayName("Создание новой новости с пустой категорией")
    public void createdNewsWithEmptyCategoryTest() {
        newsSteps.addNewsWithEmptyTittle(category, dateNews, timeNews, descriptionNews);
        newsSteps.fieldsDoesNotBeEmpty();
    }

    @Test
    @DisplayName("Редактирование новости: смена заголовка")
    public void changeTittleNewsTest() {
        newsSteps.addNews(categorySalary, tittleNews2, dateNews, timeNews, descriptionNews);
        newsSteps.editNews(tittleNews2);
        newsSteps.changeTittleNews(newTittleNews);
        newsSteps.editNews(newTittleNews);
        newsSteps.checkTittleAfterChange(newTittleNews);
    }

    @Test
    @DisplayName("Удаление новости")
    public void deleteNewsTest() {
        newsSteps.addNews(categoryDay, tittleNews3, dateNews, timeNews, descriptionNews);
        newsSteps.deleteNews(tittleNews3);
        waitElement(newsSteps.newsListId);
        newsSteps.checkNewsDeleted(newsSteps.getItemCount(), tittleNews3);
    }
    @Test
    @DisplayName("Нет новостей, соответствующих критериям поиска")
    public void checkShowNothingToShowScreen() {
        String newsDate = "24.10.1998";
        newsSteps.openFilter();
        newsSteps.isFilterNews();
        newsSteps.enterStartDate(newsDate);
        newsSteps.enterEndDate(newsDate);
        newsSteps.clickFilter();
        newsSteps.titleNewsEmptySearch.check(matches(isDisplayed()));
    }
    @Test
    @DisplayName("Фильтрация по дате на странице новостей")
    public void checkDateInFilter() {
        String newsDate = getCurrentDate();
        newsSteps.openFilter();
        newsSteps.isFilterNews();
        newsSteps.enterStartDate(newsDate);
        newsSteps.enterEndDate(newsDate);
        newsSteps.clickFilter();
        String getDateOfPublication = Helper.Text.getText(NewsSteps.newsData(0));
        assertEquals(newsDate, getDateOfPublication);
    }

    @Test
    @DisplayName("Проверка кнопки сортировки новостей")
    public void checkSortNews() {
        int number = 0;
        String firstNewsTitle = Helper.Text.getText(NewsSteps.newsTitle(number));
        newsSteps.clickSortButton();
        newsSteps.clickSortButton();
        newsSteps.allNewsList.perform(swipeDown());
        String firstNewsTitleAfterSecondSorting = Helper.Text.getText(NewsSteps.newsTitle(number));
        assertEquals(firstNewsTitle, firstNewsTitleAfterSecondSorting);
    }
}