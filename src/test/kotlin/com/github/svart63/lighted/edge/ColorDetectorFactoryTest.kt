package com.github.svart63.lighted.edge

import com.github.svart63.lighted.edge.impl.CommonColorDetector
import com.github.svart63.lighted.edge.impl.SimpleColorDetector
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


internal class ColorDetectorFactoryTest {
    private val config = mockkClass(Config::class)

    @Test
    internal fun testCreateSimpleStrategy() {
        every { config.strValue("color.detector") }.returns("simple")
        val factory = ColorDetectorFactory(config)
        val detector = factory.get()
        assertTrue(detector is SimpleColorDetector)
    }

    @Test
    internal fun testCreateCommonStrategy() {
        every { config.strValue("color.detector") }.returns("common")
        val factory = ColorDetectorFactory(config)
        val detector = factory.get()
        assertTrue(detector is CommonColorDetector)
    }

    @Test
    internal fun testFactoryThrowsExceptionIfNotDefinedType() {
        every { config.strValue("color.detector") }.returns("not_defined")
        val factory = ColorDetectorFactory(config)
        assertThrows<IllegalArgumentException> { factory.get() }
    }
}