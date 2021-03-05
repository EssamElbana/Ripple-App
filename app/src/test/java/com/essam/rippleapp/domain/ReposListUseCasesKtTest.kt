package com.essam.rippleapp.domain
import com.essam.rippleapp.data.RepositoryImp
import com.essam.rippleapp.data.server_gateway.ServerGateway
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ReposListUseCasesKtTest {
    @Test
    fun `isValidToLoadMoreRepos() with possible visible items less than the total items returns false`() {
        val possibleVisibleItemsOnScreen = 10
        val firstFullyVisibleItemPosition = 0
        val totalItemsInList = 100
        val result = isValidToLoadMoreRepos(
            possibleVisibleItemsOnScreen,
            firstFullyVisibleItemPosition,
            totalItemsInList
        )
        assertEquals(false, result)
    }

    @Test
    fun `isValidToLoadMoreRepos() with possible visible items equal or greater than total items returns true`() {
        val possibleVisibleItemsOnScreen = 10
        val firstFullyVisibleItemPosition = 90
        val totalItemsInList = 100
        val result = isValidToLoadMoreRepos(
            possibleVisibleItemsOnScreen,
            firstFullyVisibleItemPosition,
            totalItemsInList
        )
        assertEquals(true, result)
    }

    @Test
    fun `validateInput() with inputQuery empty returns invalid error message`() {
        val input = ""

        val result = validateInput(input)
        val expected =
            UseCaseResult<Any>(isSuccessful = false, error = "Please input repository name")
        assertEquals(expected, result)
    }


    @Test
    fun `validateInput() with inputQuery not empty returns valid`() {
        val input = "design patterns"

        val result = validateInput(input)
        val expected = UseCaseResult<Any>(isSuccessful = true)
        assertEquals(expected, result)
    }

    @Test
    fun `getRepos() with invalid input returns error no such repo`() {
        val input = "*************************************************"

        val result = getRepos(RepositoryImp(object : ServerGateway {
            override fun getRepos(
                query: String,
                pageNumber: Int,
                pageSize: Int
            ): UseCaseResult<RepoResponse> {
                return UseCaseResult(RepoResponse(0, emptyList()))
            }
        }), input, 1, 100)

        val expected = UseCaseResult<RepoResponse>(RepoResponse(0, emptyList()), error = "there's no such repository!")
        assertEquals(expected, result)
    }



}