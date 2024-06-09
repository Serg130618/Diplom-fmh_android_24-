package ru.iteco.fmhandroid.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ui.Helper.RecyclerViewMatcher.withRecyclerView;

import android.content.res.Resources;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.Root;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;
import ru.iteco.fmhandroid.R;

public class Helper {
    private static final LocalDateTime date = LocalDateTime.now();
    private static final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("hh:mm");
    public static final String validLogin = "login2";
    public static final String validPassword = "password2";
    public static final String invalidLogin = "login13";
    public static final String invalidPassword = "password13";
    public static final String emptyLogin = "";
    public static final String emptyPassword = "";
    public static final String urlPrivacyPolicy = "https://vhospice.org/#/privacy-policy/";
    public static final String urlTermsOfUse = "https://vhospice.org/#/terms-of-use";
    public static final String category = "Объявление";
    public static final String categoryDay = "День рождения";
    public static final String categorySalary = "Зарплата";
    public static final String tittleNews = "Tittle_1";
    public static final String tittleNews2 = "Tittle_2";
    public static final String tittleNews3 = "Tittle_3";
    public static final String newTittleNews = "NewTittle_1";
    public static final String dateNews = formatterDate.format(date);
    public static final String timeNews = formatterTime.format(date);
    public static final String descriptionNews = "Какая-то новость";


    public static ViewAction waitDisplayed(final int viewId, final long millis) {       //Выполнение действия по ожиданию отображения определенного идентификатора представления.
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> has been displayed during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> matchId = withId(viewId);
                final Matcher<View> matchDisplayed = isDisplayed();

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (matchId.matches(child) && matchDisplayed.matches(child)) {
                            return;
                        }
                    }
                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }


    public static void waitElement(int id) {
        onView(isRoot()).perform(waitDisplayed(id, 10000));
    }


    public static void waitUntilVisible(View view) {
        final CountDownLatch latch = new CountDownLatch(1);
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (view.isShown()) {
                    latch.countDown();
                }
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDate() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(currentDate);
    }

    public static class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    return true;
                }
            }
            return false;
        }
    }

    public static ViewInteraction emptyToast(int id) {
        return onView(withText(id)).inRoot(new Helper.ToastMatcher());
    }

    public static ViewInteraction checkToast(int id, boolean visible) {
        if (visible) {
            emptyToast(id).check(matches(isDisplayed()));
        } else {
            emptyToast(id).check(matches(not(isDisplayed())));
        }
        return null;
    }

    public static void waitUntilVisible(ViewInteraction inRoot) {
    }


    public static class RecyclerViewMatcher {
        private final int recyclerViewId;

        public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
            return new RecyclerViewMatcher(recyclerViewId);
        }

        public RecyclerViewMatcher(int recyclerViewId) {
            this.recyclerViewId = recyclerViewId;
        }

        public Matcher<View> atPosition(final int position) {
            return atPositionOnView(position, -1);
        }

        public Matcher<View> atPositionOnView(final int position, final int targetViewId) {

            return new TypeSafeMatcher<View>() {
                Resources resources = null;
                View childView;

                public void describeTo(Description description) {
                    String idDescription = Integer.toString(recyclerViewId);
                    if (this.resources != null) {
                        try {
                            idDescription = this.resources.getResourceName(recyclerViewId);
                        } catch (Resources.NotFoundException var4) {
                            idDescription = String.format("%s (resource not found)", recyclerViewId);
                        }
                    }

                    description.appendText("with id: " + idDescription);
                }

                public boolean matchesSafely(View view) {

                    this.resources = view.getResources();

                    if (childView == null) {
                        RecyclerView recyclerView = view.getRootView().findViewById(recyclerViewId);
                        if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                            if (viewHolder != null) {
                                childView = viewHolder.itemView;
                            }
                        } else {
                            return false;
                        }
                    }

                    if (targetViewId == -1) {
                        return view == childView;
                    } else {
                        View targetView = childView.findViewById(targetViewId);
                        return view == targetView;
                    }
                }
            };
        }
    }
    public static class Text {

        public static String getText(ViewInteraction matcher) {
            final String[] text = new String[1];
            ViewAction viewAction = new ViewAction() {

                @Override
                public Matcher<View> getConstraints() {
                    return isAssignableFrom(TextView.class);
                }

                @Override
                public String getDescription() {
                    return "Text of the view";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    TextView textView = (TextView) view;
                    text[0] = textView.getText().toString();
                }
            };

            matcher.perform(viewAction);

            return text[0];
        }
    }
        public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
            return new TypeSafeMatcher<View>() {
                int currentIndex = 0;

                @Override
                public void describeTo(Description description) {
                    description.appendText("with index: ");
                    description.appendValue(index);
                    matcher.describeTo(description);
                }

                @Override
                public boolean matchesSafely(View view) {
                    return matcher.matches(view) && currentIndex++ == index;
                }
            };
        }

    public static int getRecyclerViewItemCount(@IdRes int recyclerViewId) {
        final int[] count = new int[1];
        onView(allOf(withId(recyclerViewId), isDisplayed()))
                .check((view, noViewFoundException) -> {
                    if (view instanceof RecyclerView) {
                        RecyclerView recyclerView = (RecyclerView) view;
                        RecyclerView.Adapter adapter = recyclerView.getAdapter();
                        if (adapter != null) {
                            count[0] = adapter.getItemCount();
                        }
                    }
                });
        return count[0];
    }

    public static String getTextFromNews(int fieldId, int position) {
        final String[] itemDateText = new String[1];
        onView(withRecyclerView(R.id.news_list_recycler_view).atPosition(position))
                .check((view, noViewFoundException) -> {
                    if (noViewFoundException != null) {
                        throw noViewFoundException;
                    }
                    TextView dateTextView = view.findViewById(fieldId);
                    itemDateText[0] = dateTextView.getText().toString();
                });
        return itemDateText[0];
    }

    public static class GetHeightAfterClickViewAction implements ViewAction {

        private int[] heightAfterClick;

        public GetHeightAfterClickViewAction(int[] heightAfterClick) {
            this.heightAfterClick = heightAfterClick;
        }

        @Override
        public Matcher<View> getConstraints() {
            return isAssignableFrom(RecyclerView.class);
        }

        @Override
        public String getDescription() {
            return "Get height after click";
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            View firstItem = recyclerView.getChildAt(0);
            if (firstItem != null) {
                heightAfterClick[0] = firstItem.getHeight();
            }
        }
    }

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(ViewGroup.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "click child view with id " + id;
            }

            @Override
            public void perform(UiController uiController, View view) {
                View childView = view.findViewById(id);
                childView.performClick();
            }
        };
    }

    public static String generateScreenshotName(String testName) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        return testName + "_" + timestamp + ".png";
    }

}