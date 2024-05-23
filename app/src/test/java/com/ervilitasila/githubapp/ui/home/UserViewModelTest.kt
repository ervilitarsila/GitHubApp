package com.ervilitasila.githubapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ervilitasila.githubapp.model.User
import com.ervilitasila.githubapp.network.UserService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.AfterClass
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val service: UserService = mockk(relaxed = true)
    private lateinit var viewModel: UserViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun  setup(){
        Dispatchers.setMain(testDispatcher)
        viewModel = UserViewModel(service)
    }

    @Test
    fun `GIVEN service WHEN call service THEN check if is called once at viewmodel initialization`() {
        coVerify(exactly = 1) { service.getUsers() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN mocked service results WHEN get list of users from view model THEN result as expected`() = runTest {
        val expectedUsers = listOf(
            User(id = 1, login = "user1", avatar_url = "", url = ""),
            User(id = 2, login = "user2", avatar_url = "", url = "")
        )

        coEvery { service.getUsers() } returns expectedUsers

        val observer = mockk<Observer<List<User>>>(relaxed = true)
        viewModel.listUsers.observeForever(observer)

        viewModel.getListUsers()

        verify { observer.onChanged(expectedUsers) }
        assertEquals(expectedUsers, viewModel.listUsers.value)
    }

    @Test
    fun `GIVEN service WHEN getListUsers fails THEN update LiveData with empty list`() = runTest {
        val exception = Exception("Network error")
        coEvery { service.getUsers() } throws exception

        val observer = mockk<Observer<List<User>>>(relaxed = true)

        viewModel.listUsers.removeObserver(observer)
        viewModel.listUsers.observeForever(observer)

        viewModel.getListUsers()

        coVerify { service.getUsers() }
        verify { observer.onChanged(emptyList()) }
        assertEquals(emptyList<User>(), viewModel.listUsers.value)
    }

    companion object {
        @JvmStatic
        @AfterClass
        fun tearDown() {
            unmockkAll()
        }
    }
}