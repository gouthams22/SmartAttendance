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

}