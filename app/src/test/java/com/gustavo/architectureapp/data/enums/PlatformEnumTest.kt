package com.gustavo.architectureapp.data.enums

import com.gustavo.architectureapp.data.enums.PlatformEnum.*
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class PlatformEnumTest {

    @Test
    fun `Should return 1 from XBOX_ONE`() {
        assertEquals(1, XBOX_ONE.platformId)
    }

    @Test
    fun `Should return 186 from XBOX_SERIES`() {
        assertEquals(186, XBOX_SERIES.platformId)
    }

    @Test
    fun `Should return 18 from PS4`() {
        assertEquals(18, PS4.platformId)
    }

    @Test
    fun `Should return 187 from PS5`() {
        assertEquals(187, PS5.platformId)
    }

    @Test
    fun `Should return 7 from NINTENDO_SWITCH`() {
        assertEquals(7, NINTENDO_SWITCH.platformId)
    }

    @Test
    fun `Should return 4 from PC`() {
        assertEquals(4, PC.platformId)
    }

}