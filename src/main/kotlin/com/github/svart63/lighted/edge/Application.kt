package com.github.svart63.lighted.edge

import com.github.svart63.lighted.edge.injection.LightedEdgeModule
import com.google.inject.Guice
import java.util.Scanner
import kotlin.system.exitProcess

fun main() {
    val injector = Guice.createInjector(LightedEdgeModule())
    val binding = injector.getInstance(ColorUpdater::class.java)
    binding.start()
    val scanner = Scanner(System.`in`)
    scanner.nextLine()
    binding.stop()
    exitProcess(0)
}