package com.example.android.smartattendence;
import android.content.Context;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class mainmenu {

 /*   @Rule
    public ActivityTestRule<MenuActivity> mActivityRule = new ActivityTestRule<>(
            MenuActivity.class);
    int i=1;



    @Test
    public void markattendancebutton()
    {


        onView(withId(R.id.button_mark)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void p_updateattendancebutton()
    {


        onView(withId(R.id.button_update)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void signoutbutton()
    {


        onView(withId(R.id.sign_out)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
*/
    /*
    @Test
    public void viewattendancebutton()
    {


        onView(withId(R.id.button_update)).perform(click());
        try {
            Thread.sle+ep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void studentdetailbutton()
    {


        onView(withId(R.id.button_update)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    } */

}
