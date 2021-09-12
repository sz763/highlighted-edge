package com.github.svart63.lighted.edge

import com.github.svart63.lighted.edge.impl.CommonColorDetector
import com.github.svart63.lighted.edge.impl.SimpleColorDetector
import java.util.function.Supplier
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

class ColorDetectorFactory @Inject constructor(private val config: Config) : Provider<ColorDetector> {
    private val detectors = mapOf(
        Pair("simple", Supplier<ColorDetector> { SimpleColorDetector() }),
        Pair("common", Supplier<ColorDetector> { CommonColorDetector() })
    )

    @Singleton
    override fun get(): ColorDetector {
        val strategyName = config.strValue("color.detector")
        val strategySupplier = detectors[strategyName]
        return strategySupplier?.get()
            ?: throw IllegalArgumentException("Undefined strategy '$strategyName', possible values: ${detectors.keys}")
    }
}