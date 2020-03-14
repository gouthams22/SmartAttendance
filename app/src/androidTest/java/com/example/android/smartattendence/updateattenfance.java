package com.example.android.smartattendence;

import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

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
public class updateattenfance {
/*
    @Rule
    public ActivityTestRule<MainUpdate> mActivityRule = new ActivityTestRule<>(
            MainUpdate.class);






    @Test
    public void datepicker() throws InterruptedException {
        String testdate="4/2/2020";

        onView(withId(R.id.edit_date)).perform(typeText(testdate), closeSoftKeyboard());








    }
    @Test
    public void details() throws InterruptedException {
        String testdate="4/2/2020";
        String pno="1";

        onView(withId(R.id.edit_date)).perform(typeText(testdate), closeSoftKeyboard());
        onView(withId(R.id.edit_period)).perform(typeText(pno), closeSoftKeyboard());

        onView(withId(R.id.button_detail)).perform(click());

    }


    @Test
    public void updatekbutton() throws InterruptedException {
        String  testdate="4/2/2020";
        String pno="1";

        onView(withId(R.id.edit_date)).perform(typeText(testdate), closeSoftKeyboard());
        onView(withId(R.id.edit_period)).perform(typeText(pno), closeSoftKeyboard());

        onView(withId(R.id.button_detail)).perform(click());
        onView(withId(R.id.update_button)).perform(click());
    }


*/


}
