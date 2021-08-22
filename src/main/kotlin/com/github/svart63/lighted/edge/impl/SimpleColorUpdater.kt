package com.github.svart63.lighted.edge.impl

import com.github.svart63.lighted.edge.*
import com.google.inject.Inject
import org.slf4j.LoggerFactory
import java.awt.image.BufferedImage
import javax.inject.Singleton

@Singleton
class SimpleColorUpdater @Inject constructor(
    private val imageSplitter: ImageSplitter,
    private val colorDetector: ColorDetector,
    private val screenshotTaker: ScreenshotTaker,
    private val commApi: CommDevice,
    private val config: Config
) : ColorUpdater {
    private val log = LoggerFactory.getLogger(javaClass)
    private lateinit var thread: Thread
    private val edgeHeight = config.intValue("edge.height")
    private val edgeHorizontal = config.intValue("edge.horizontal.leds")
    private val edgeVertical = config.intValue("edge.vertical.leds")
    private val refreshRate = config.intValue("refresh.rate").toLong()

    override fun start() {
        log.info("Start updating led's color")
        thread = Thread {
            val writeBuffer = ByteArray(3)
            val list = ArrayList<BufferedImage>(edgeVertical + edgeHorizontal)
            while (!Thread.interrupted()) {
                val right = screenshotTaker.right(edgeHeight)
                val rightImage = imageSplitter.verticalSplit(edgeVertical, right)
                val top = screenshotTaker.top(edgeHeight)
                val topImage = imageSplitter.horizontalSplit(edgeHorizontal, top)
                val left = screenshotTaker.left(edgeHeight)
                val leftImage = imageSplitter.verticalSplit(edgeVertical, left)
                list.addAll(leftImage)
                list.addAll(topImage)
                list.addAll(rightImage)
                pushToDevice(writeBuffer, list)
                list.clear()
                delay()
            }
        }
        thread.start()
    }

    private fun delay() {
        try {
            Thread.sleep(refreshRate)
        } catch (e: InterruptedException) {
            log.debug("Application has been stopped. Updater has stopped the work.")
        }
    }

    private fun pushToDevice(writeBuffer: ByteArray, images: List<BufferedImage>) {
        val map: ByteArray = images.flatMap { bufferedImage ->
            val color = colorDetector.getColor(bufferedImage)
            toByteArray(writeBuffer, color).asList()
        }.reversed().toByteArray()
        commApi.write(map)
    }

    override fun stop() {
        thread.interrupt()
    }

    internal fun toByteArray(buffer: ByteArray, color: Int): ByteArray {
        buffer[2] = (color shr 16).toByte()
        buffer[1] = (color shr 8).toByte()
        buffer[0] = (color shr 0).toByte()
        return buffer
    }
}