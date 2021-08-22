package com.github.svart63.lighted.edge.impl

import com.github.svart63.lighted.edge.ScreenshotTaker
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.image.BufferedImage
import javax.inject.Singleton

@Singleton
class RobotScreenshotTaker : ScreenshotTaker {
    private val robot = Robot()
    private val screenDimension = Toolkit.getDefaultToolkit().screenSize
    private val height = screenDimension.height
    private val width = screenDimension.width

    override fun top(height: Int): BufferedImage = robot.createScreenCapture(Rectangle(0, 0, width, height))
}