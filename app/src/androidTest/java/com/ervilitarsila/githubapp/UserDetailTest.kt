package com.ervilitasila.githubapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ervilitasila.githubapp.ui.userdetail.UserDetailFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDetailFragmentTest {

    private lateinit var navHostController: TestNavHostController

    @Before
    fun setup() {
        navHostController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInContainer<UserDetailFragment>(themeResId = R.style.Theme_GitHubApp) {
            UserDetailFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navHostController.setGraph(R.navigation.main_nav)
                        navHostController.setCurrentDestination(R.id.datailUserFragment)
                        Navigation.setViewNavController(fragment.requireView(), navHostController)
                    }
                }
            }
        }
    }

    @Test
    fun testViewsDisplayed() {
        onView(withId(R.id.user_profile)).check(matches(isDisplayed()))
        onView(withId(R.id.user_name)).check(matches(isDisplayed()))
        onView(withId(R.id.user_login)).check(matches(isDisplayed()))
        onView(withId(R.id.user_location)).check(matches(isDisplayed()))
        onView(withId(R.id.user_company)).check(matches(isDisplayed()))
        onView(withId(R.id.user_followers)).check(matches(isDisplayed()))
        onView(withId(R.id.user_following)).check(matches(isDisplayed()))
    }

    @Test
    fun testBackButtonNavigation() {
        onView(withId(R.id.btn_back)).perform(click())
        assert(navHostController.currentDestination?.id == R.id.homeFragment)
    }

    @Test
    fun testUserDetailsDisplayed() {
        val userName = "Ervili Tarsila"
        val userLogin = "etbs"
        val userLocation = "Manaus - Brazil"
        val userCompany = "Company"
        val userFollowers = "85"
        val userFollowing = "70"

        onView(withId(R.id.user_name)).check(matches(withText(userName)))
        onView(withId(R.id.user_login)).check(matches(withText(userLogin)))
        onView(withId(R.id.user_location)).check(matches(withText(userLocation)))
        onView(withId(R.id.user_company)).check(matches(withText(userCompany)))
        onView(withId(R.id.user_followers)).check(matches(withText(userFollowers)))
        onView(withId(R.id.user_following)).check(matches(withText(userFollowing)))
    }
}
