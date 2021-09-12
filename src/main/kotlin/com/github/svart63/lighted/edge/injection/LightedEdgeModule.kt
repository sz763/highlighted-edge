package com.github.svart63.lighted.edge.injection

import com.github.svart63.lighted.edge.*
import com.github.svart63.lighted.edge.impl.*
import com.google.inject.AbstractModule

class LightedEdgeModule : AbstractModule() {
    override fun configure() {
        bind(Config::class.java).to(PropertyConfig::class.java)
        bind(ColorDetector::class.java).toProvider(ColorDetectorFactory::class.java)
        bind(ImageSplitter::class.java).to(SimpleImageSplitter::class.java)
        bind(ScreenshotTaker::class.java).to(RobotScreenshotTaker::class.java)
        bind(CommDevice::class.java).to(JSerialComm::class.java)
        bind(ColorUpdater::class.java).to(SimpleColorUpdater::class.java)
    }

}