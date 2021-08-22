package com.github.svart63.lighted.edge.injection

import com.github.svart63.lighted.edge.ColorDetector
import com.github.svart63.lighted.edge.ColorUpdater
import com.github.svart63.lighted.edge.CommDevice
import com.github.svart63.lighted.edge.Config
import com.github.svart63.lighted.edge.ImageSplitter
import com.github.svart63.lighted.edge.ScreenshotTaker
import com.github.svart63.lighted.edge.impl.JSerialComm
import com.github.svart63.lighted.edge.impl.PropertyConfig
import com.github.svart63.lighted.edge.impl.RobotScreenshotTaker
import com.github.svart63.lighted.edge.impl.SimpleColorDetector
import com.github.svart63.lighted.edge.impl.SimpleColorUpdater
import com.github.svart63.lighted.edge.impl.SimpleImageSplitter
import com.google.inject.AbstractModule

class LightedEdgeModule : AbstractModule() {
    override fun configure() {
        bind(Config::class.java).to(PropertyConfig::class.java)
        bind(ColorDetector::class.java).to(SimpleColorDetector::class.java)
        bind(ImageSplitter::class.java).to(SimpleImageSplitter::class.java)
        bind(ScreenshotTaker::class.java).to(RobotScreenshotTaker::class.java)
        bind(CommDevice::class.java).to(JSerialComm::class.java)
        bind(ColorUpdater::class.java).to(SimpleColorUpdater::class.java)
    }

}