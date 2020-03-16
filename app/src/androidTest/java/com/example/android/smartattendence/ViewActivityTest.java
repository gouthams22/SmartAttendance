package com.example.android.smartattendence;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ProgressBar;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.actionWithAssertions;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ViewActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkbutton() throws InterruptedException {
        ActivityScenario.launch(ViewActivity.class);
        Thread.sleep(10000);
        String stringToBetyped = "17150";
        onView(withId(R.id.edit_rollno))
                .perform(typeText(stringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.button_view)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkedittexts() throws InterruptedException {
        ActivityScenario.launch(ViewActivity.class);
        Thread.sleep(10000);
        onView(allOf(withId(R.id.edit_subject), withText("Hello!")));
        onView(allOf(withId(R.id.edit_rollno), withText("Hello!")));
        onView(allOf(withId(R.id.current_subject), withText("Hello!")));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkprogress() throws InterruptedException {
        Thread.sleep(10000);
        ActivityScenario.launch(ViewActivity.class);

/// Replace the drawable with a static color
        onView(isAssignableFrom(ProgressBar.class)).perform(replaceProgressBarDrawable());

        // click on the button that triggers the display of the progress bar
        onView(withId(R.id.button_view)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ViewAction replaceProgressBarDrawable() {
        return actionWithAssertions(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(ProgressBar.class);
            }

            @Override
            public String getDescription() {
                return "replace the ProgressBar drawable";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                // Replace the indeterminate drawable with a static red ColorDrawable
                ProgressBar progressBar = (ProgressBar) view;
                progressBar.setIndeterminateDrawable(new ColorDrawable(0xffff0000));
                uiController.loopMainThreadUntilIdle();
            }
        });
    }
}
