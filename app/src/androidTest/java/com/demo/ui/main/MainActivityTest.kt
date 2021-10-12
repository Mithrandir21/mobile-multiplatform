package com.demo.ui.main

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.demo.R
import com.demo.testing.idling.CountingIdler
import com.demo.ui.albums.list.AlbumAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.hamcrest.core.StringContains.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var idler: CountingIdler

    @Before
    fun registerIdlers() {
        hiltAndroidRule.inject()
        IdlingRegistry.getInstance().register(idler.getIdler())
    }

    @After
    fun unregisterIdlers() {
        IdlingRegistry.getInstance().unregister(idler.getIdler())
    }

    @Test
    fun openFirstItem() {
        onView(withId(R.id.albumList))
            .perform(RecyclerViewActions.actionOnItem<AlbumAdapter.RecyclerViewHolder>(hasDescendant(withText("Album ID: 1")), click()))

        onView(withId(R.id.detailsAlbumIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("Album ID: 1")))

        onView(withId(R.id.detailsAlbumUserIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("User ID: 1")))

        onView(withId(R.id.detailsAlbumNameLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("Album Name:"))))

        onView(withId(R.id.detailsAlbumNotFound))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun openFirstSecond() {
        onView(withId(R.id.albumList))
            .perform(RecyclerViewActions.actionOnItem<AlbumAdapter.RecyclerViewHolder>(hasDescendant(withText("Album ID: 2")), click()))

        onView(withId(R.id.detailsAlbumIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("Album ID: 2")))

        onView(withId(R.id.detailsAlbumUserIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("User ID: 1")))

        onView(withId(R.id.detailsAlbumNameLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("Album Name:"))))

        onView(withId(R.id.detailsAlbumNotFound))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun openFirstIntermediaryStep() {
        onView(withId(R.id.albumList))
            .perform(RecyclerViewActions.actionOnItem<AlbumAdapter.RecyclerViewHolder>(hasDescendant(withText("Album ID: 5")), click()))

        onView(withId(R.id.intermediaryStepLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("Intermediary Feature Step")))

        onView(withId(R.id.intermediaryStepButton))
            .check(matches(isDisplayed()))
            .check(matches(withText("Next")))
            .perform(click())

        onView(withId(R.id.detailsAlbumIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("Album ID: 5")))

        onView(withId(R.id.detailsAlbumUserIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("User ID: 1")))

        onView(withId(R.id.detailsAlbumNameLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("Album Name:"))))

        onView(withId(R.id.detailsAlbumNotFound))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun openFirstItemAndBackToList() {
        onView(withId(R.id.albumList))
            .perform(RecyclerViewActions.actionOnItem<AlbumAdapter.RecyclerViewHolder>(hasDescendant(withText("Album ID: 1")), click()))

        onView(withId(R.id.detailsAlbumIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("Album ID: 1")))

        onView(withId(R.id.detailsAlbumUserIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("User ID: 1")))

        onView(withId(R.id.detailsAlbumNameLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("Album Name:"))))

        onView(withId(R.id.detailsAlbumNotFound))
            .check(matches(not(isDisplayed())))

        pressBack()

        onView(withId(R.id.albumList))
            .check(matches(isDisplayed()))
    }

    @Test
    fun openFirstItemAndRotate() {
        onView(withId(R.id.albumList))
            .perform(RecyclerViewActions.actionOnItem<AlbumAdapter.RecyclerViewHolder>(hasDescendant(withText("Album ID: 1")), click()))

        onView(withId(R.id.detailsAlbumIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("Album ID: 1")))

        onView(withId(R.id.detailsAlbumUserIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("User ID: 1")))

        onView(withId(R.id.detailsAlbumNameLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("Album Name:"))))

        onView(withId(R.id.detailsAlbumNotFound))
            .check(matches(not(isDisplayed())))

        pressBack()

        activityRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.albumList))
            .perform(RecyclerViewActions.actionOnItem<AlbumAdapter.RecyclerViewHolder>(hasDescendant(withText("Album ID: 1")), click()))

        onView(withId(R.id.detailsAlbumIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("Album ID: 1")))

        onView(withId(R.id.detailsAlbumUserIdLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText("User ID: 1")))

        onView(withId(R.id.detailsAlbumNameLabel))
            .check(matches(isDisplayed()))
            .check(matches(withText(containsString("Album Name:"))))

        onView(withId(R.id.detailsAlbumNotFound))
            .check(matches(not(isDisplayed())))
    }
}