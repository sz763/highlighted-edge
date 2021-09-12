package com.github.svart63.lighted.edge.impl

import com.github.svart63.lighted.edge.ColorDetector
import java.awt.image.BufferedImage
import javax.inject.Singleton
import kotlin.math.abs


@Singleton
class CommonColorDetector : ColorDetector {
    override fun getColor(image: BufferedImage): Int {
        val width: Int = image.width
        val height: Int = image.height
        var r: Int
        var g: Int
        var b: Int
        val model = image.colorModel
        val raster = image.raster
        val map: MutableMap<Int, Int> = mutableMapOf()
        var rgb = 0
        for (x in 0 until width) {
            for (y in 0 until height) {
                r = model.getRed(raster.getDataElements(x, y, null))
                g = model.getGreen(raster.getDataElements(x, y, null))
                b = model.getBlue(raster.getDataElements(x, y, null))
                rgb = r shl 8 or g shl 8 or b
                if (!isGray(r, g, b)) {
                    var counter = map.computeIfAbsent(rgb) { 0 }
                    counter++
                    map[rgb] = counter
                }
            }
        }
        return if (map.isNotEmpty()) {
            getMostCommonColour(map)
        } else {
            rgb
        }
    }

    private fun getMostCommonColour(map: Map<Int, Int>): Int {
        val list = map.entries.toList()
        list.sortedBy { it.value }
        return list.last().key
    }

    private fun isGray(r: Int, g: Int, b: Int): Boolean {
        val rgDiff = r - g
        val rbDiff = r - b
        val tolerance = 10
        return !(abs(rgDiff) > tolerance || abs(rbDiff) > tolerance)
    }
}