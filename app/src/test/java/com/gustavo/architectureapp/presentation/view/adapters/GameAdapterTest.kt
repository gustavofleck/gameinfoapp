package com.gustavo.architectureapp.presentation.view.adapters

import androidx.recyclerview.widget.RecyclerView
import com.gustavo.architectureapp.utils.createMappedGameListResponse
import com.gustavo.architectureapp.utils.image.ImageLoader
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GameAdapterTest {

    private val imageLoaderMock = mockk<ImageLoader>(relaxed = true)

    private val recyclerAdapter = spyk<RecyclerView>()

    private lateinit var adapter: GameAdapter

    @BeforeEach
    fun setUp() {
        adapter = GameAdapter(imageLoaderMock)
    }

    @Test
    fun `Given a game list retrieved, when add items, should append this items`() {
        val retrievedList = createMappedGameListResponse()

        every { recyclerAdapter.adapter?.notifyItemRangeInserted(any(), any()) }

        adapter.addNewGameList(retrievedList)

        assertEquals(retrievedList.size, adapter.itemCount)
    }

}