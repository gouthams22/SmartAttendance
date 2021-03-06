package com.example.android.smartattendence;

import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class MainActivityTest {
    @Test
    public void period_check() {
        int input = 15;
        int output;
        int expected = 6;
        MainMark mainMark = new MainMark();
        output = mainMark.getPeriod(input);
        assertEquals(expected, output);
    }

    @Test
    public void userCheck() {
        MainMark mainMark = new MainMark();
        FirebaseUser firebaseUser = mainMark.firebaseUser;
        assertNull(firebaseUser);
    }

    @Test
    public void login_empty() throws Exception {
        MainActivity mainActivity = new MainActivity();
        String username = "";
        String password = "";
        boolean output;
        output = mainActivity.checkEmpty(username);
        assertTrue(output);
        output = mainActivity.checkEmpty(password);
        assertTrue(output);

        username = "Hello";
        password = "everyone";
        output = mainActivity.checkEmpty(username);
        assertFalse(output);
        output = mainActivity.checkEmpty(password);
        assertFalse(output);
    }

    @Test
    public void register_validate() throws Exception {
        RegisterActivity registerActivity = new RegisterActivity();
        String username = "abcdefgh";
        String password = "1234567";
        boolean output;
        boolean expectedUser = true;
        boolean expectedPass = true;
        output = registerActivity.checkEmail(username);
        assertEquals(expectedUser, output);
        output = registerActivity.checkPassword(password);
        assertEquals(expectedPass, output);

        username = "abc@ef.gh";
        password = "12345678";
        expectedUser = false;
        expectedPass = false;
        output = registerActivity.checkEmail(username);
        assertEquals(expectedUser, output);
        output = registerActivity.checkPassword(password);
        assertEquals(expectedPass, output);
    }

    @Test
    public void date_check() {
        int input = 15;
        String output;
        String expected = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR);
        ;
        MainMark mainMark = new MainMark();
        output = mainMark.getCurrentDate();
        assertEquals(expected, output);
    }

    @Test
    public void update_empty() throws Exception {
        MainUpdate mainUpdate = new MainUpdate();
        String date = "";
        String period = "";
        boolean output;
        boolean expectedUser = true;
        boolean expectedPass = true;
        output = mainUpdate.validateUpdate(date);
        assertEquals(expectedUser, output);
        output = mainUpdate.validateUpdate(period);
        assertEquals(expectedPass, output);

        date = "21/2/2020";
        period = "5";
        expectedUser = false;
        expectedPass = false;
        output = mainUpdate.validateUpdate(date);
        assertEquals(expectedUser, output);
        output = mainUpdate.validateUpdate(period);
        assertEquals(expectedPass, output);
    }
}