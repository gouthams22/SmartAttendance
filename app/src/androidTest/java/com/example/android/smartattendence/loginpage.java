package com.example.android.smartattendence;

import android.content.Context;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.TypeTextAction;
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
public class loginpage {

//
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
//            MainActivity.class);
//
//
//
//
//    @Test
//    public void forgetpassword() {
//        String testusername="karthikrao.omega@gmail.com";
//        onView(withId(R.id.username)).perform(typeText(testusername), closeSoftKeyboard());
//
//
//
//        onView(withId(R.id.link_forget)).perform(click());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//
//    @Test
//    public void signup()
//    {
//        onView(withId(R.id.link_signup)).perform(click());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void emailvaild() {
//        String testusername="karthikrao";
//        onView(withId(R.id.username)).perform(typeText(testusername), closeSoftKeyboard());
//
//
//
//        onView(withId(R.id.link_forget)).perform(click());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//
//    }
//    @Test
//    public void loginbutton() {
//        //locate and click on the login button
//
//        String testusername="karthikrao.omega@gmail.com";
//        String testpassword="9063691370010";
//        onView(withId(R.id.username)).perform(typeText(testusername), closeSoftKeyboard());
//        onView(withId(R.id.password)).perform(typeText(testpassword), closeSoftKeyboard());
//        //locate and click on the login button
//        onView(withId(R.id.login_button)).perform(click());
//
//
//
//        //check if the sign up screen is displayed by asserting that the first name edittext is displayed
//
//        // onView(withId(R.id.username)).check(matches(allOf(isDescendantOfA(withId(R.id.welcome_message)), isDisplayed())));
//    }
//



}
