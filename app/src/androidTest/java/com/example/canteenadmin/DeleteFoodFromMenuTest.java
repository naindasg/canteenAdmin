package com.example.canteenadmin;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeleteFoodFromMenuTest {

    /*
    The deleteFoodFromMenuTest will only work if the menu is in its original form (i.e. with Pepsi as the last item on the menu).
    If Pepsi is not the last item on the menu, please delete as many meals as necessary until Pepsi is the last item on the menu.
    This is necessary because the deleteFoodFromMenuTest follows a set of instructions and the Espresso
    test is not flexible enough to deviate from these instructions.
     */

    @Rule
    public ActivityTestRule<MenuActivity> mActivityTestRule = new ActivityTestRule<MenuActivity>(MenuActivity.class);

    @Test
    public void deleteFoodFromMenuTest() {


        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.addFood), withText("Add food to the menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("producttodelete"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.ingredients),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.price),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("1.00"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.url),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("n/a"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.saveButton), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton4.perform(click());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.menu_recyclerview)).perform(ViewActions.swipeUp());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.menu_recyclerview)).perform(ViewActions.swipeUp());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.menu_recyclerview)).perform(ViewActions.swipeUp());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.menu_recyclerview)).perform(ViewActions.swipeUp());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.menu_recyclerview)).perform(ViewActions.swipeUp());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.menu_recyclerview),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(25, click()));

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.deleteButton), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        7),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.menu_recyclerview)).perform(ViewActions.swipeUp());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.onView(ViewMatchers.withId(R.id.menu_recyclerview)).perform(ViewActions.swipeUp());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.menu_recyclerview)).perform(ViewActions.swipeUp());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.menu_recyclerview)).perform(ViewActions.swipeUp());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ViewInteraction textView = onView(
                allOf(withId(R.id.foodName), withText("pepsi"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("pepsi")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
