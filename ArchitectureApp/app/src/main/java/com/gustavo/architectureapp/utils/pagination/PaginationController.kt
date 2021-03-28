package com.gustavo.architectureapp.utils.pagination

class PaginationController {

    private var nextPage = 0

    fun setNextPage(page: Int) {
        nextPage = page
    }

    fun getNextPage() = nextPage

    fun hasNextPage() = nextPage > 0

}