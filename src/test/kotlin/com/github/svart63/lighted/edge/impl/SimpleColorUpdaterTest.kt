package com.github.svart63.lighted.edge.impl

import com.github.svart63.lighted.edge.ColorDetector
import com.github.svart63.lighted.edge.CommDevice
import com.github.svart63.lighted.edge.Config
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage


internal class SimpleColorUpdaterTest {
    private var config = mockk<Config>()

    private val commDevice = mockk<CommDevice>()

    private val detector = mockk<ColorDetector>()

    @BeforeEach
    internal fun setUp() {
        every { config.intValue(any()) }.returns(0)
        every { detector.getColor(any()) }.answers {
            val image = it.invocation.args.first() as BufferedImage
            image.getRGB(0, 0)
        }
    }

    @Test
    internal fun testPushImageDataToDevice() {
        every { commDevice.write(any()) }.returns(Unit)
        val updater = SimpleColorUpdater(mockk(), detector, mockk(), commDevice, config)
        val array = ByteArray(3)
        val image = BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB)
        fillColor(32, 64, 128, image)
        updater.pushToDevice(array, listOf(image))
        val date: ByteArray = byteArrayOf(32, 64, -128)
        verify { commDevice.write(date) }
    }

    private fun fillColor(r: Int, g: Int, b: Int, image: BufferedImage) {
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                image.setRGB(x, y, r shl 8 or g shl 8 or b)
            }
        }
    }
}