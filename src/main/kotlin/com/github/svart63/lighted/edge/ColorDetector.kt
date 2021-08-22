package com.github.svart63.lighted.edge

import java.awt.image.BufferedImage

interface ColorDetector {
    fun getColor(image: BufferedImage): Int
}