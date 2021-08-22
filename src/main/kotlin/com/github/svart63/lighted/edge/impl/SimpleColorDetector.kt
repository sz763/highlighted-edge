package com.github.svart63.lighted.edge.impl

import com.github.svart63.lighted.edge.ColorDetector
import java.awt.image.BufferedImage
import javax.inject.Singleton

@Singleton
class SimpleColorDetector : ColorDetector {
    override fun getColor(image: BufferedImage): Int {
        val width: Int = image.width
        val height: Int = image.height
        var r = 0
        var g = 0
        var b = 0
        val model = image.colorModel
        val raster = image.raster
        for (x in 0 until width) {
            for (y in 0 until height) {
                r += model.getRed(raster.getDataElements(x, y, null))
                g += model.getGreen(raster.getDataElements(x, y, null))
                b += model.getBlue(raster.getDataElements(x, y, null))
            }
        }
        val pixels = height * width
        val red = r / pixels
        val green = g / pixels
        val blue = b / pixels
        return red shl 8 or green shl 8 or blue
    }

}