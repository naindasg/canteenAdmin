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
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddFoodToMenuTest {

    /**
     * MAKE SURE TO DELETE PEPSI FROM THE MENU MANUALLY, AS THIS TEST WILL BE ADDING PEPSI.
     * IF YOU DO NOT DELETE PEPSI ...
     * THEN ANOTHER PEPSI WILL BE ADDED, DUE TO THE FACT THAT THE MEALID IS PRE-POPULATED BY +1 every time a food item is added
     */

    @Rule
    public ActivityTestRule<MenuActivity> mActivityTestRule = new ActivityTestRule<>(MenuActivity.class);

    @Test
    public void addFoodTest() {

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.addFood), withText("Add food to the menu"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.name),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Pepsi max cherry"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.description),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Ice cold drink"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.ingredients),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("N/A"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.price),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("1.50"), closeSoftKeyboard());


        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.url),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJV6Qvt4bMXRSR79AS2aojutsn2Jlnig6-6A&usqp=CAU"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.saveButton), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.goBackFromAdd), withText("Go back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                8),
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


        ViewInteraction textView = onView(
                allOf(withId(R.id.foodName), withText("Pepsi max cherry"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Pepsi max cherry")));
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
