package com.ervilitasila.githubapp.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class RepositoryTest {

    @Test
    fun `GIVEN repository object WHEN created THEN verify properties`() {
        val repository = Repository(
            id = 1,
            name = "TestRepo",
            language = "Kotlin",
            forks = 10,
            watchers = 5,
            visibility = "public",
            description = "A test repository"
        )

        assertEquals(1, repository.id)
        assertEquals("TestRepo", repository.name)
        assertEquals("Kotlin", repository.language)
        assertEquals(10, repository.forks)
        assertEquals(5, repository.watchers)
        assertEquals("public", repository.visibility)
        assertEquals("A test repository", repository.description)
    }

    @Test
    fun `GIVEN two repository objects WHEN compared THEN verify equality`() {
        val repository1 = Repository(
            id = 1,
            name = "TestRepo",
            language = "Kotlin",
            forks = 10,
            watchers = 5,
            visibility = "public",
            description = "A test repository"
        )

        val repository2 = Repository(
            id = 1,
            name = "TestRepo",
            language = "Kotlin",
            forks = 10,
            watchers = 5,
            visibility = "public",
            description = "A test repository"
        )

        val repository3 = Repository(
            id = 2,
            name = "AnotherRepo",
            language = "Java",
            forks = 20,
            watchers = 15,
            visibility = "private",
            description = "Another test repository"
        )

        assertEquals(repository1, repository2)
        assertNotEquals(repository1, repository3)
    }

    @Test
    fun `GIVEN repository object WHEN copied THEN verify copy properties`() {
        val repository = Repository(
            id = 1,
            name = "TestRepo",
            language = "Kotlin",
            forks = 10,
            watchers = 5,
            visibility = "public",
            description = "A test repository"
        )

        val repositoryCopy = repository.copy(name = "TestRepoCopy")

        assertEquals(1, repositoryCopy.id)
        assertEquals("TestRepoCopy", repositoryCopy.name)
        assertEquals("Kotlin", repositoryCopy.language)
        assertEquals(10, repositoryCopy.forks)
        assertEquals(5, repositoryCopy.watchers)
        assertEquals("public", repositoryCopy.visibility)
        assertEquals("A test repository", repositoryCopy.description)
    }
}
