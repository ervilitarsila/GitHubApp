package com.ervilitasila.githubapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ervilitasila.githubapp.model.Repository
import com.ervilitasila.githubapp.model.UserProfile
import com.ervilitasila.githubapp.network.UserService
import com.ervilitasila.githubapp.ui.userdetail.UserDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.AfterClass
import org.junit.Rule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class UserDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var userService: UserService
    private lateinit var viewModel: UserDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        userService = mockk()
        viewModel = UserDetailViewModel(userService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getDetailUserSelected_loginUser_detailUserProfile() = runTest {
        val userProfile = UserProfile(
            login = "testUser",
            avatar_url = "https://example.com/user.jpg",
            repos_url = "https://example.com/repos",
            name = "Test User",
            company = "Test Company",
            location = "Test Location",
            followers = 100,
            following = 50,
            repositories = mutableListOf()
        )

        coEvery { userService.getUser("testUser") } returns userProfile

        val latch = CountDownLatch(1)
        val observer = Observer<UserProfile?> {
            if (it == userProfile) {
                latch.countDown()
            }
        }

        viewModel.userSelected.observeForever(observer)
        viewModel.getDetailUserSelected("testUser")

        if (!latch.await(2, TimeUnit.SECONDS)) {
            viewModel.userSelected.removeObserver(observer)
            throw AssertionError("LiveData value was not set within the timeout")
        }

        val result = viewModel.userSelected.value
        assertEquals(userProfile, result)
        viewModel.userSelected.removeObserver(observer)
    }

    @Test
    fun getDetailUserSelected_loginUser_returnsNull() = runTest {
        coEvery { userService.getUser("testUser") } throws Exception("Network error")

        viewModel.getDetailUserSelected("testUser")

        val result = viewModel.userSelected.value
        assertEquals(null, result)
    }


    @Test
    fun getDetailUserSelectedRepositories_userName_returnsListOfRepositories() = runTest {
        val userName = "testUser"
        val repositories = listOf(
            Repository(id = 1, name = "repo1", language = "Kotlin", forks = 10, watchers = 5, visibility = "public", description = "Repo 1 description"),
            Repository(id = 2, name = "repo2", language = "Java", forks = 20, watchers = 15, visibility = "public", description = "Repo 2 description")
        )

        coEvery { userService.getRepositories(userName) } returns repositories

        val latch = CountDownLatch(1)
        val observer = Observer<List<Repository>> {
            if (it == repositories) {
                latch.countDown()
            }
        }

        viewModel.listUserRepositories.observeForever(observer)
        viewModel.getDetailUserSelectedRepositories(userName)

        if (!latch.await(2, TimeUnit.SECONDS)) {
            viewModel.listUserRepositories.removeObserver(observer)
            throw AssertionError("LiveData value was not set within the timeout")
        }

        val result = viewModel.listUserRepositories.value
        assertEquals(repositories, result)
        viewModel.listUserRepositories.removeObserver(observer)
    }

    @Test
    fun getDetailUserSelectedRepositories_networkError_returnsEmptyList() = runTest {
        val userName = "testUser"

        coEvery { userService.getRepositories(userName) } throws Exception("Network error")

        val latch = CountDownLatch(1)
        val observer = Observer<List<Repository>> {
            if (it.isEmpty()) {
                latch.countDown()
            }
        }

        viewModel.listUserRepositories.observeForever(observer)
        viewModel.getDetailUserSelectedRepositories(userName)

        if (!latch.await(2, TimeUnit.SECONDS)) {
            viewModel.listUserRepositories.removeObserver(observer)
            throw AssertionError("LiveData value was not set within the timeout")
        }

        val result = viewModel.listUserRepositories.value
        assertEquals(emptyList<Repository>(), result)
        viewModel.listUserRepositories.removeObserver(observer)
    }
}
