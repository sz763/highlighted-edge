package com.github.svart63.lighted.edge.impl

import com.github.svart63.lighted.edge.ImageSplitter
import java.awt.image.BufferedImage
import javax.inject.Singleton

@Singleton
class SimpleImageSplitter : ImageSplitter {

    override fun horizontalSplit(frames: Int, input: BufferedImage): List<BufferedImage> {
        val sourceWidth = input.width
        val sourceHeight = input.height
        val frameSize: Int = sourceWidth / frames
        val frameList = mutableListOf<BufferedImage>()
        for (index in 0 until frames) {
            val x = frameSize * index
            if (index == frames - 1) {
                frameList.add(input.getSubimage(x, 0, sourceWidth - x, sourceHeight))
            } else {
                frameList.add(input.getSubimage(x, 0, frameSize, sourceHeight))
            }
        }
        return frameList
    }

    override fun verticalSplit(frames: Int, input: BufferedImage): List<BufferedImage> {
        val sourceHeight = input.height
        val sourceWidth = input.width
        val frameSize: Int = sourceHeight / frames
        val frameList = mutableListOf<BufferedImage>()
        for (index in 0 until frames) {
            val y = frameSize * index
            if (index == frames - 1) {
                frameList.add(input.getSubimage(0, y, sourceWidth, sourceHeight - y))
            } else {
                frameList.add(input.getSubimage(0, y, sourceWidth, frameSize))
            }
        }
        return frameList
    }
}