package com.gustavo.architectureapp.pagination

import com.gustavo.architectureapp.utils.pagination.PaginationController
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PaginationControllerTest {

    lateinit var paginationController: PaginationController

    @Nested
    @DisplayName("Given a list of games retrieved")
    inner class UrlControl {

        @BeforeEach
        fun setUp() {
            paginationController = PaginationController()
        }

        @Test
        fun `When set next page url, should get same url`() {
            val expectedUrl = 2

            paginationController.setNextPage(expectedUrl)

            val actualUrl = paginationController.getNextPage()

            assertEquals(expectedUrl, actualUrl)

        }

        @Test
        fun `When a complete url was defined, should returns that has next page `() {
            val expectedReturn = true
            val nextPageUrl = 2

            paginationController.setNextPage(nextPageUrl)

            val actualReturn = paginationController.hasNextPage()

            assertEquals(expectedReturn, actualReturn)
        }

        @Test
        fun `When a empty url was defined, should returns that hasn't next page `() {
            val expectedReturn = false
            val nextPageUrl = 0

            paginationController.setNextPage(nextPageUrl)

            val actualReturn = paginationController.hasNextPage()

            assertEquals(expectedReturn, actualReturn)
        }
    }

}