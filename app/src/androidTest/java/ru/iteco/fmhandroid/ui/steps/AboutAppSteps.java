package ru.iteco.fmhandroid.ui.steps;

import static androidx.fragment.app.FragmentManager.TAG;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;

import org.junit.Assert;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.iteco.fmhandroid.R;

public class AboutAppSteps {

    private ViewInteraction version = onView(allOf(withId(R.id.about_version_title_text_view)));
    public static ViewInteraction privacyPolicy = onView(withId(R.id.about_privacy_policy_label_text_view));
    public static ViewInteraction privacyPolicyLink = onView(withId(R.id.about_privacy_policy_value_text_view));
    public static ViewInteraction termsOfUse = onView(withId(R.id.about_terms_of_use_label_text_view));
    public static ViewInteraction termsOfUseLink = onView(withId(R.id.about_terms_of_use_value_text_view));

    @Step("Проверяем существование страницы")
    public void isAboutAppPageExists(String url) {
        Allure.step("Проверяем существование страницы: " + url);
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            int statusCode = response.code();
            assertEquals(200, statusCode);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            Assert.assertTrue(false);
        } finally {
            Espresso.pressBack();
        }
    }

    @Step("Проверяем видимость страницы 'О приложении'")
    public void visibilityAboutAppPage() {
        Allure.step("Проверяем, что открыт раздел 'О приложении'");
        version.check(matches(isDisplayed()));
        privacyPolicy.check(matches(isDisplayed()));
        privacyPolicyLink.check(matches(isDisplayed()));
        termsOfUse.check(matches(isDisplayed()));
        termsOfUseLink.check(matches(isDisplayed()));
    }
    @Step("Проверяем наличие ссылки 'Политика конфиденциальности'")
    public void privacyPolicyLinkAvailability() {
        Allure.step("Проверка наличия ссылки Политика конфиденциальности");
        privacyPolicyLink.check(matches(isClickable()));
    }

    public void clickPrivacyPolicyLink() {
        privacyPolicyLink.perform(click());
    }
    @Step("Проверяем наличие ссылки 'Условия использования'")
    public void termsLinkAvailability() {
        Allure.step("Проверка наличия ссылки Условия использования");
        termsOfUseLink.check(matches(isClickable()));
    }

    public void clickTermsLink() {
        termsOfUseLink.perform(click());
    }



}