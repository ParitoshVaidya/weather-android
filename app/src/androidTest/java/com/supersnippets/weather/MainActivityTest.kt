package com.supersnippets.weather

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_weather_data_visibility() {
        onView(withId(R.id.includeLayoutTemp))
            .check(matches(ViewMatchers.isCompletelyDisplayed()))

        onView(withId(R.id.recyclerView))
            .check(matches(ViewMatchers.isCompletelyDisplayed()))
    }
}