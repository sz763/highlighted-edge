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
        val chunks = sourceWidth / frameSize
        for (index in 0 until chunks) {
            val x = frameSize * index
            if (index == chunks - 1) {
                frameList.add(input.getSubimage(x, 0, sourceWidth - x, sourceHeight))
            } else {
                frameList.add(input.getSubimage(x, 0, frameSize, sourceHeight))
            }
        }
        return frameList
    }

    override fun verticalSplit(frames: Int, input: BufferedImage): List<BufferedImage> {
        val sourceWidth = input.width
        val sourceHeight = input.width
        val frameSize: Int = sourceWidth / frames
        val frameList = mutableListOf<BufferedImage>()
        for (index in 0..sourceHeight / frameSize) {
            val x = frameSize * index
            frameList.add(input.getSubimage(0, x, frameSize, sourceWidth))
        }
        return frameList
    }
}