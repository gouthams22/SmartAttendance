package com.example.android.smartattendence;
import android.content.Context;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
@RunWith(AndroidJUnit4.class)
public class markattendance {

    @Rule
    public ActivityTestRule<MainMark> mActivityRule = new ActivityTestRule<>(
            MainMark.class);



    @Test
    public void dropdowndeptselction() throws InterruptedException {

        Thread.sleep(10000);
        onView(withId(R.id.spinner1)).perform(click());
        onData(anything()).atPosition(2).perform(click());

        }


    /*@Test
    public void checkbox() throws InterruptedException {
        Thread.sleep(10000);
        onView(withId(R.id.spinner1)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        Thread.sleep(10000);


        }
*/
    @Test
    public void markbutton() throws InterruptedException {
        Thread.sleep(10000);
        onView(withId(R.id.spinner1)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.mark_button)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }





}
