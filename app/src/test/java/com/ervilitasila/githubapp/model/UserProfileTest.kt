package com.ervilitasila.githubapp.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class UserProfileTest {

    @Test
    fun `GIVEN userProfile object WHEN created THEN verify properties`() {
        val repositories = mutableListOf<Repository>(
            Repository(1, "Repo1", "Kotlin", 10, 5, "public", "First repo"),
            Repository(2, "Repo2", "Java", 20, 10, "private", "Second repo")
        )
        val userProfile = UserProfile(
            login = "testUser",
            avatar_url = "https://example.com/user.jpg",
            repos_url = "https://example.com/repos",
            name = "Test User",
            company = "Test Company",
            location = "Test Location",
            followers = 100,
            following = 50,
            repositories = repositories
        )

        assertEquals("testUser", userProfile.login)
        assertEquals("https://example.com/user.jpg", userProfile.avatar_url)
        assertEquals("https://example.com/repos", userProfile.repos_url)
        assertEquals("Test User", userProfile.name)
        assertEquals("Test Company", userProfile.company)
        assertEquals("Test Location", userProfile.location)
        assertEquals(100, userProfile.followers)
        assertEquals(50, userProfile.following)
        assertEquals(repositories, userProfile.repositories)
    }

    @Test
    fun `GIVEN two userProfile objects WHEN compared THEN verify equality`() {
        val repositories1 = mutableListOf<Repository>(
            Repository(1, "Repo1", "Kotlin", 10, 5, "public", "First repo"),
            Repository(2, "Repo2", "Java", 20, 10, "private", "Second repo")
        )
        val userProfile1 = UserProfile(
            login = "testUser",
            avatar_url = "https://example.com/user.jpg",
            repos_url = "https://example.com/repos",
            name = "Test User",
            company = "Test Company",
            location = "Test Location",
            followers = 100,
            following = 50,
            repositories = repositories1
        )

        val userProfile2 = UserProfile(
            login = "testUser",
            avatar_url = "https://example.com/user.jpg",
            repos_url = "https://example.com/repos",
            name = "Test User",
            company = "Test Company",
            location = "Test Location",
            followers = 100,
            following = 50,
            repositories = repositories1
        )

        val repositories2 = mutableListOf<Repository>(
            Repository(3, "Repo3", "Python", 30, 15, "public", "Third repo")
        )
        val userProfile3 = UserProfile(
            login = "anotherUser",
            avatar_url = "https://example.com/anotheruser.jpg",
            repos_url = "https://example.com/anotherrepos",
            name = "Another User",
            company = "Another Company",
            location = "Another Location",
            followers = 200,
            following = 150,
            repositories = repositories2
        )

        assertEquals(userProfile1, userProfile2)
        assertNotEquals(userProfile1, userProfile3)
    }

    @Test
    fun `GIVEN userProfile object WHEN copied THEN verify copy properties`() {
        val repositories = mutableListOf<Repository>(
            Repository(1, "Repo1", "Kotlin", 10, 5, "public", "First repo"),
            Repository(2, "Repo2", "Java", 20, 10, "private", "Second repo")
        )
        val userProfile = UserProfile(
            login = "testUser",
            avatar_url = "https://example.com/user.jpg",
            repos_url = "https://example.com/repos",
            name = "Test User",
            company = "Test Company",
            location = "Test Location",
            followers = 100,
            following = 50,
            repositories = repositories
        )

        val userProfileCopy = userProfile.copy(name = "Test User Copy")

        assertEquals("testUser", userProfileCopy.login)
        assertEquals("https://example.com/user.jpg", userProfileCopy.avatar_url)
        assertEquals("https://example.com/repos", userProfileCopy.repos_url)
        assertEquals("Test User Copy", userProfileCopy.name)
        assertEquals("Test Company", userProfileCopy.company)
        assertEquals("Test Location", userProfileCopy.location)
        assertEquals(100, userProfileCopy.followers)
        assertEquals(50, userProfileCopy.following)
        assertEquals(repositories, userProfileCopy.repositories)
    }
}
