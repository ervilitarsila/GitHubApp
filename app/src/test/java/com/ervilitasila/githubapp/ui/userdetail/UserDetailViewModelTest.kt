package com.ervilitasila.githubapp.ui.userdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ervilitasila.githubapp.model.Repository
import com.ervilitasila.githubapp.model.UserProfile
import com.ervilitasila.githubapp.network.UserService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val service: UserService = mockk(relaxed = true)
    private lateinit var viewModel: UserDetailViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UserDetailViewModel(service)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN service WHEN getDetailUserSelected fails THEN update LiveData with null`() = runTest {
        val userName = "testUser"
        val exception = Exception("Network error")

        coEvery { service.getUser(userName) } throws exception

        val observer = mockk<Observer<UserProfile?>>(relaxed = true)
        viewModel.userSelected.observeForever(observer)

        viewModel.getDetailUserSelected(userName)

        coVerify { service.getUser(userName) }
        coVerify { observer.onChanged(null) }
        assertNull(viewModel.userSelected.value)

        viewModel.userSelected.removeObserver(observer)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN service WHEN getDetailUserSelectedRepositories fails THEN update LiveData with empty list`() = runTest {
        val userName = "testUser"
        val exception = Exception("Network error")

        coEvery { service.getRepositories(userName) } throws exception

        val observer = mockk<Observer<List<Repository>>>(relaxed = true)
        viewModel.listUserRepositories.observeForever(observer)

        viewModel.getDetailUserSelectedRepositories(userName)

        coVerify { service.getRepositories(userName) }
        coVerify { observer.onChanged(emptyList()) }
        assertEquals(emptyList<Repository>(), viewModel.listUserRepositories.value)

        viewModel.listUserRepositories.removeObserver(observer)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN service WHEN getDetailUserSelected is called THEN check if getUser is called`() = runTest {
        val userName = "testUser"
        val expectedUser = UserProfile(
            login = "testUser",
            avatar_url = "",
            repos_url = "",
            name = "",
            company = "",
            location = "",
            followers = 0,
            following = 0,
            repositories = mutableListOf()
        )

        coEvery { service.getUser(userName) } returns expectedUser

        val observer = mockk<Observer<UserProfile?>>(relaxed = true)
        viewModel.userSelected.observeForever(observer)

        viewModel.getDetailUserSelected(userName)

        coVerify { service.getUser(userName) }
        coVerify { observer.onChanged(expectedUser) }
        assertEquals(expectedUser, viewModel.userSelected.value)

        viewModel.userSelected.removeObserver(observer)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN service WHEN getDetailUserSelectedRepositories is called THEN check if getRepositories is called`() = runTest {
        val userName = "testUser"
        val expectedRepositories = listOf(
            Repository(id = 1, name = "repo1", language = "Kotlin", forks = 10, watchers = 5, visibility = "public", description = "Repo 1 description"),
            Repository(id = 2, name = "repo2", language = "Java", forks = 20, watchers = 15, visibility = "public", description = "Repo 2 description")
        )

        coEvery { service.getRepositories(userName) } returns expectedRepositories

        val observer = mockk<Observer<List<Repository>>>(relaxed = true)
        viewModel.listUserRepositories.observeForever(observer)

        viewModel.getDetailUserSelectedRepositories(userName)

        coVerify { service.getRepositories(userName) }
        verify { observer.onChanged(expectedRepositories) }
        assertEquals(expectedRepositories, viewModel.listUserRepositories.value)

        viewModel.listUserRepositories.removeObserver(observer)
    }

    companion object {
        @JvmStatic
        @AfterClass
        fun tearDownAll() {
            unmockkAll()
        }
    }
}
