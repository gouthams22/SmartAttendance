package com.example.android.smartattendence;

import com.google.firebase.auth.FirebaseUser;

import org.junit.Test;

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
        boolean expectedUser = true;
        boolean expectedPass = true;
        output = mainActivity.checkEmpty(username);
        assertEquals(expectedUser, output);
        output = mainActivity.checkEmpty(password);
        assertEquals(expectedPass, output);

        username = "Hello";
        password = "everyone";
        expectedUser = false;
        expectedPass = false;
        output = mainActivity.checkEmpty(username);
        assertEquals(expectedUser, output);
        output = mainActivity.checkEmpty(password);
        assertEquals(expectedPass, output);
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
        String expected = "19/2/2020";
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

        date = "19/2/2020";
        period = "5";
        expectedUser = false;
        expectedPass = false;
        output = mainUpdate.validateUpdate(date);
        assertEquals(expectedUser, output);
        output = mainUpdate.validateUpdate(period);
        assertEquals(expectedPass, output);
    }
}