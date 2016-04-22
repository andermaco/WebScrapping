package com.andermaco.scrapping;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityLayoutTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    /**
     * Validate ViewPager is displayed
     */
    @Test
    public void validateViewPager() {
        onView(withId(R.id.pager)).check(matches(isDisplayed()));
    }

    /**
     * Validate tabs are displayed
     */
    @Test
    public void validateTabs() {
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()));
    }

    /**
     * Click on the RecyclerView item at position 0
     */
    @Test
    public void accessLink () {
        onView(allOf(withId(R.id.recyclerView), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

}

