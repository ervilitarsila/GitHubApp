package com.ervilitasila.githubapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ervilitasila.githubapp.model.Repository
import com.ervilitasila.githubapp.model.UserProfile
import com.ervilitasila.githubapp.ui.userdetail.UserDetailFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDetailFragmentTest {

    private lateinit var navController: TestNavHostController
    private lateinit var user: UserProfile

    @Before
    fun setup() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInContainer<UserDetailFragment>(themeResId = R.style.Theme_GitHubApp) {
            UserDetailFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.main_nav)
                        navController.setCurrentDestination(R.id.datailUserFragment)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        val mockRepositories = listOf(
            Repository(1, "Repo1", "Kotlin", 10, 100, "public", "Description 1"),
            Repository(2, "Repo2", "Java", 20, 200, "private", "Description 2"),
            Repository(3, "Repo3", "Python", 30, 300, "public", "Description 3"),
            Repository(4, "Repo4", "JavaScript", 40, 400, "public", "Description 4"),
            Repository(5, "Repo5", "Ruby", 50, 500, "private", "Description 5"),
            Repository(6, "Repo6", "Swift", 60, 600, "public", "Description 6"),
            Repository(7, "Repo7", "Go", 70, 700, "public", "Description 7"),
            Repository(8, "Repo8", "C#", 80, 800, "public", "Description 8"),
            Repository(9, "Repo9", "C++", 90, 900, "private", "Description 9"),
            Repository(10, "Repo10", "PHP", 100, 1000, "public", "Description 10")
        )

        user = UserProfile(
            "etbs",
            "https://avatars.githubusercontent.com/u/1?v=4",
            "www.google.com",
            "Ervili Tarsila",
            "Company",
            "Manaus - AM",
            700,
            200,
            mockRepositories.toMutableList()
        )
        launchFragmentInContainer<UserDetailFragment>(themeResId = R.style.Theme_GitHubApp) {
            UserDetailFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.main_nav)
                        navController.setCurrentDestination(R.id.datailUserFragment)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                        fragment.userDetailViewModel.setUser(user)
                        fragment.userDetailViewModel.setListUserRepositories(mockRepositories)
                    }
                }
            }
        }
    }

    @Test
    fun testUserDetailsDisplayed() {
        onView(withId(R.id.user_name)).check(matches(withText(user.name)))
        onView(withId(R.id.user_login)).check(matches(withText(user.login)))
        onView(withId(R.id.user_location)).check(matches(withText(user.location)))
        onView(withId(R.id.user_company)).check(matches(withText(user.company)))
        onView(withId(R.id.user_followers)).check(matches(withText(user.followers.toString())))
        onView(withId(R.id.user_following)).check(matches(withText(user.following.toString())))
    }

    @Test
    fun testRepositoriesDisplayed() {
        user.repositories.forEachIndexed { index, repository ->
            onView(withId(R.id.recycler_repositories))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(index))
            onView(withText(repository.name)).check(matches(isDisplayed()))
            onView(withText(repository.language)).check(matches(isDisplayed()))
            onView(withText(repository.description)).check(matches(isDisplayed()))
        }
    }
}
