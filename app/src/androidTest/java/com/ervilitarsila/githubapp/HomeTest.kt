package com.ervilitasila.githubapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.ervilitasila.githubapp.ui.home.HomeFragment
import com.ervilitasila.githubapp.ui.home.UserAdapters
import org.junit.Before
import org.junit.Test

class HomeFragmentTest {

    private lateinit var navHostController: TestNavHostController

    @Before
    fun setup() {
        navHostController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_GitHubApp) {
            HomeFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navHostController.setGraph(R.navigation.main_nav)
                        navHostController.setCurrentDestination(R.id.homeFragment)
                        Navigation.setViewNavController(fragment.requireView(), navHostController)
                    }
                }
            }
        }
    }

    @Test
    fun verifyIfUserListIsDisplayed() {
        onView(withId(R.id.recycler_users)).check(matches(isDisplayed()))
    }

    @Test
    fun testUserListItemClickNavigatesToUserDetailFragment() {
        Thread.sleep(2000)
        onView(withId(R.id.recycler_users))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserAdapters.ViewHolder>(0, click()))
        Thread.sleep(2000)
        assert(navHostController.currentDestination?.id == R.id.datailUserFragment)
    }

    @Test
    fun verifyLogoIsDisplayed() {
        onView(withId(R.id.logo_github)).check(matches(isDisplayed()))
    }
}
