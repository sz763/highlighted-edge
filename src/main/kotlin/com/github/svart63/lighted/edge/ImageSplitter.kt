package com.github.svart63.lighted.edge

import java.awt.image.BufferedImage

interface ImageSplitter {
    fun horizontalSplit(frames: Int, input: BufferedImage): List<BufferedImage>
    fun verticalSplit(frames: Int, input: BufferedImage): List<BufferedImage>
}