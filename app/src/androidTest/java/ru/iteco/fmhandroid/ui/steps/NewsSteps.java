package ru.iteco.fmhandroid.ui.steps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static ru.iteco.fmhandroid.ui.Helper.checkToast;
import static ru.iteco.fmhandroid.ui.Helper.clickChildViewWithId;
import static ru.iteco.fmhandroid.ui.Helper.*;
import static ru.iteco.fmhandroid.ui.Helper.getRecyclerViewItemCount;
import static ru.iteco.fmhandroid.ui.Helper.getTextFromNews;
import static ru.iteco.fmhandroid.ui.Helper.waitElement;
import static ru.iteco.fmhandroid.ui.Helper.waitUntilVisible;
import static ru.iteco.fmhandroid.ui.Helper.withIndex;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.Helper;

public class NewsSteps {
    public static ViewInteraction sortNewsButton = onView(withId(R.id.sort_news_material_button));
    public static ViewInteraction editNewsButton = onView(withId(R.id.edit_news_material_button));
    public static ViewInteraction filterNewsButton = onView(withId(R.id.filter_news_material_button));
    public static ViewInteraction titleOfFilterPage = onView(withId(R.id.filter_news_title_text_view));
    public static ViewInteraction startDateField = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
    public static ViewInteraction endDateField = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
    public static ViewInteraction filterButton = onView(withId(R.id.filter_button));
    public static ViewInteraction titleOfNewsBlock = onView(withText("News"));
    public static ViewInteraction blockOfNews = onView(withId(R.id.news_list_recycler_view));
    public static ViewInteraction allNewsList = onView(withId(R.id.all_news_cards_block_constraint_layout));
    public static ViewInteraction addNewsButton = onView(withId(R.id.add_news_image_view));
    public static ViewInteraction categoryField = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    public static ViewInteraction tittleField = onView(withId(R.id.news_item_title_text_input_edit_text));
    public static ViewInteraction dateField = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    public static ViewInteraction timeField = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    public static ViewInteraction descriptionField = onView(withId(R.id.news_item_description_text_input_edit_text));
    public static ViewInteraction saveButton = onView(withId(R.id.save_button));
    public static ViewInteraction titleNewsEmptySearch = onView(withId(R.id.empty_news_list_text_view));
    public static ViewInteraction confirmDeleteNewsButton = onView(allOf(withId(android.R.id.button1)));
    public static int confirmDeleteNewsButtonId = android.R.id.button1;
    public int newsListId = R.id.news_list_recycler_view;
    public static ViewInteraction newsTitle (int number) {
        return onView(withIndex(withId(R.id.news_item_title_text_view), number));
    }
    public static ViewInteraction newsData (int number) {
        return onView(withIndex(withId(R.id.news_item_date_text_view), number));
    }

    @Step("Редактирование новости")
    public void clickEditButton() {
        Allure.step("Клинуть кнопку редактирования новости (Карандаш)");
        editNewsButton.check(matches(isDisplayed()));
        editNewsButton.perform(click());
    }

    @Step("Проверяем видимость страницы 'Новости'")
    public void checkNewsPage() {
        Allure.step("Проверка что мы находимся на вкладке 'Новости'");
        filterNewsButton.check(matches(isDisplayed()));
    }
    @Step("Создание новой новости")
    public void addNews(String category, String tittle, String date, String time, String description) {
        Allure.step("Клик на кнопку '+'. Заполняем поля: категория, заголовок, дата, время, описание.");
        clickEditButton();
        addNewsButton.perform(click());
        categoryField.perform(replaceText(category));
        tittleField.perform(replaceText(tittle));
        dateField.perform(replaceText(date));
        timeField.perform(replaceText(time));
        descriptionField.perform(replaceText(description));
        saveButton.perform(click());
    }

    @Step("Поиск новости по заголовку")
    public void findNewsWithTittle(String tittle) {
        Allure.step("Ищем новость в списке по заголовку " + tittle);
        waitElement(newsListId);
        onView(withId(newsListId))
                .check(matches(isDisplayed()))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(allOf(withText(tittle)))));
        onView(withId(newsListId))
                .check(matches(isDisplayed()));
    }

    @Step("Выбираем новость для редактирования")
    public void editNews(String tittle) {
        Allure.step("Выбираем новость - " +tittle + " для редактирования");
        findNewsWithTittle(tittle);
        onView(allOf(withId(R.id.news_item_material_card_view), hasDescendant(withText(tittle))))
                .perform(clickChildViewWithId(R.id.edit_news_item_image_view));
    }
    @Step("Проверяем, что все поля новости соответствуют заданным при ее создании")
    public void checkNews() {
        Allure.step("Проверяем, что все поля новости соответствуют заданным");
        onView(withText(tittleNews)).check(matches(isDisplayed()));
        onView(withText(dateNews)).check(matches(isDisplayed()));
        onView(withText(timeNews)).check(matches(isDisplayed()));
        onView(withText(descriptionNews)).check(matches(isDisplayed()));
    }
    @Step("Выбираем кнопку фильтрования новостей")
    public void openFilter() {
        Allure.step("Открыть окно фильтра");
        filterNewsButton.check(matches(isDisplayed()));
        filterNewsButton.perform(click());
    }
    @Step("Проверяем видимость вкладки фильтрации новостей")
    public void isFilterNews () {
        Allure.step("Проверка что мы находимся на вкладке 'Filter news' ");
        titleOfFilterPage.check(matches(isDisplayed()));
    }
    @Step("Выбираем начальную дату")
    public void enterStartDate (String startDate) {
        Allure.step("Заполнить поле начальная дата");
        startDateField.perform(replaceText(startDate));
    }
    @Step("Выбираем конечную дату")
    public void enterEndDate (String endDate) {
        Allure.step("Заполнить поле конечная дата");
        endDateField.perform(replaceText(endDate));
    }
    @Step("Выбор кнопки фильтр")
    public void clickFilter () {
        Allure.step("Кликнуть кнопку фильтр");
        filterButton.perform(click());
    }
    @Step("Выбор кнопки сортироки")
    public void clickSortButton() {
        Allure.step("Кликнуть кнопку сортироки");
        sortNewsButton.check(matches(isDisplayed()));
        sortNewsButton.perform(click());
    }
    @Step("Выбор кнопки прокрутки новостей")
    public void scrollNews(int i) {
        onView(withId(newsListId))
                .perform(scrollToPosition(i))
                .perform(actionOnItemAtPosition(i, scrollTo()))
                .check(matches(isDisplayed()));
    }
    public int findNews (String FindText, int number){
        int numberOfSwipe = 0;
        boolean massen = true;
        while (massen){
            String firstNewsTitleAfterSecondSorting = Helper.Text.getText(newsTitle(number));
            if (FindText.equals(firstNewsTitleAfterSecondSorting)){
                massen = false;
            }
            else if (number == 3)
            {
                number = 0;
                numberOfSwipe++;
                blockOfNews.perform(swipeUp());
            }else if (numberOfSwipe == 10){
                massen = false;
            }else {
                number++;
            }
        }
        return number;
    }



    @Step("Проверка заголовка после изменения")
    public void checkTittleAfterChange(String tittle) {
        Allure.step("Проверяем, что заголовок изменен");
        onView(withText(tittle)).check(matches(isDisplayed()));
    }

    @Step("Смена заголовка новости")
    public void changeTittleNews(String newTittle) {
        Allure.step("Меняем заголовок новости на " + newTittle);
        tittleField.perform(replaceText(newTittle));
        saveButton.perform(click());
        waitElement(newsListId);
    }

    @Step("Удаление новости")
    public void deleteNews(String tittle) {
        Allure.step("Удаляем выбранную новость - " + tittle);
        findNewsWithTittle(tittle);
        onView(allOf(withId(R.id.news_item_material_card_view), hasDescendant(withText(tittle))))
                .perform(clickChildViewWithId(R.id.delete_news_item_image_view));
        confirmDelete();
    }

    @Step("Проверка, что новость удалена")
    public void checkNewsDeleted(int itemCount, String tittle) {
        Allure.step("Проверяем, что новость удалена");
        for (int i = 0; i < itemCount; i++) {
            scrollNews(i);
            String actualTittle = getTextFromNews(R.id.news_item_title_text_view, i);
            assertNotEquals(tittle, actualTittle);
        }
    }

    @Step("Подтверждение удаления")
    public void confirmDelete() {
        Allure.step("Подтверждаем удаление новости нажимая ОК");
        waitElement(confirmDeleteNewsButtonId);
        confirmDeleteNewsButton.perform(click());
    }

    @Step("Создание новости с пустыми полями")
    public void addNewsWithEmptyFields() {
        Allure.step("Клик на кнопку '+'. Оставить все поля пустыми, нажать кнопку 'Сохранить'");
        clickEditButton();
        addNewsButton.perform(click());
        saveButton.perform(click());
    }

    @Step("Создание новости с пустым полем заголовка")
    public void addNewsWithEmptyTittle(String category, String date, String time, String description) {
        Allure.step("Клик на кнопку '+'. Заполнить поля, кроме заголовка: категория, дата, время, описание. Нажать кнопку 'Сохранить''");
        clickEditButton();
        addNewsButton.perform(click());
        categoryField.perform(replaceText(category));
        dateField.perform(replaceText(date));
        timeField.perform(replaceText(time));
        descriptionField.perform(replaceText(description));
        saveButton.perform(click());
    }

    @Step("Проверка видимости сообщения об ошибке при создании новости с пустыми полями.")
    public void fieldsDoesNotBeEmpty() {
        Allure.step("Проверяем видимость сообщения об ошибке при создании новости с пустыми полями.");
        waitUntilVisible(checkToast(R.string.empty_fields, true));
    }

    public ViewInteraction getRecyclerViewAndScrollToFirstPosition() {       //Получаем recyclerView для раздела Новости и прокручиваем до его первой позиции
        ViewInteraction recyclerView = onView(withId(R.id.news_list_recycler_view));
        return recyclerView;
    }

    public int getHeightBeforeClick(ViewInteraction recyclerView) {      //Получаем высоту первого элемента списка до клика
        int[] heightBeforeClick = {0};
        recyclerView.perform(new Helper.GetHeightAfterClickViewAction(heightBeforeClick));
        return heightBeforeClick[0];
    }

    public void doubleClickFirstItem(ViewInteraction recyclerView) {     //Кликаем дважды на первом элементе списка, чтобы элемент развернулся и свернулся
        recyclerView.perform(actionOnItemAtPosition(0, doubleClick()));
    }

    public int getHeightAfterClick(ViewInteraction recyclerView) {       //Получаем высоту первого элемента списка после клика
        int[] heightAfterClick = {0};
        recyclerView.perform(new Helper.GetHeightAfterClickViewAction(heightAfterClick));
        return heightAfterClick[0];
    }

    public void checkHeightAfterDoubleClick(int heightBeforeClick, int heightAfterClick) {       //Проверяем, что высота первого элемента списка осталась той же после двойного клика
        assertEquals(heightBeforeClick, heightAfterClick);
    }

    public int getItemCount() {      //Получаем количество элементов в списке новостей
        int itemCount = getRecyclerViewItemCount(newsListId);
        return itemCount;
    }


}