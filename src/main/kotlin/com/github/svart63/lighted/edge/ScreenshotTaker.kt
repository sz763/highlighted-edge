package com.github.svart63.lighted.edge

import java.awt.image.BufferedImage

interface ScreenshotTaker {
    fun top(height: Int): BufferedImage
}