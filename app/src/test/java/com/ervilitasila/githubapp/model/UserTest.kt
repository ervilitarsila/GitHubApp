package com.ervilitasila.githubapp.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class UserTest {

    @Test
    fun `GIVEN user object WHEN created THEN verify properties`() {
        val user = User(
            id = 1,
            login = "testUser",
            avatar_url = "https://example.com/user.jpg",
            url = "https://example.com"
        )

        assertEquals(1, user.id)
        assertEquals("testUser", user.login)
        assertEquals("https://example.com/user.jpg", user.avatar_url)
        assertEquals("https://example.com", user.url)
    }

    @Test
    fun `GIVEN three user objects WHEN compared THEN verify equality`() {
        val user1 = User(
            id = 1,
            login = "testUser",
            avatar_url = "https://example.com/user.jpg",
            url = "https://example.com"
        )

        val user2 = User(
            id = 1,
            login = "testUser",
            avatar_url = "https://example.com/user.jpg",
            url = "https://example.com"
        )

        val user3 = User(
            id = 2,
            login = "anotherUser",
            avatar_url = "https://example.com/anotheruser.jpg",
            url = "https://example.com/another"
        )

        assertEquals(user1, user2)
        assertNotEquals(user1, user3)
    }

    @Test
    fun `GIVEN user object WHEN copied THEN verify copy properties`() {
        val user = User(
            id = 1,
            login = "testUser",
            avatar_url = "https://example.com/user.jpg",
            url = "https://example.com"
        )

        val userCopy = user.copy(login = "testUserCopy")

        assertEquals(1, userCopy.id)
        assertEquals("testUserCopy", userCopy.login)
        assertEquals("https://example.com/user.jpg", userCopy.avatar_url)
        assertEquals("https://example.com", userCopy.url)
    }
}
