package com.github.svart63.lighted.edge.impl

import com.github.svart63.lighted.edge.ColorDetector
import com.github.svart63.lighted.edge.ColorUpdater
import com.github.svart63.lighted.edge.CommDevice
import com.github.svart63.lighted.edge.Config
import com.github.svart63.lighted.edge.ImageSplitter
import com.github.svart63.lighted.edge.ScreenshotTaker
import com.google.inject.Inject
import org.slf4j.LoggerFactory
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
    private val refreshRate = config.intValue("refresh.rate").toLong()

    override fun start() {
        log.info("Start updating led's color")
        thread = Thread {
            while (!Thread.interrupted()) {
                val image = screenshotTaker.top(edgeHeight)
                val list = imageSplitter.horizontalSplit(edgeHorizontal, image)
                val writeBuffer = ByteArray(3)
                val map: ByteArray = list.flatMap { bufferedImage ->
                    val color = colorDetector.getColor(bufferedImage)
                    toByteArray(writeBuffer, color).asList()
                }.reversed().toByteArray()
                commApi.write(map)
                try {
                    Thread.sleep(refreshRate)
                } catch (e: InterruptedException) {
                    log.debug("Application has been stopped. Updater has stopped the work.")
                }
            }
        }
        thread.start()
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