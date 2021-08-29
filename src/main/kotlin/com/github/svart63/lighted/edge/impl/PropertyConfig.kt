package com.github.svart63.lighted.edge.impl

import com.github.svart63.lighted.edge.Config
import java.io.FileInputStream
import java.util.Properties
import javax.inject.Singleton

@Singleton
class PropertyConfig(private val configFile: String = "./config.properties") : Config {
    private val properties: Properties = Properties()

    init {
        val inStream = FileInputStream(configFile)
        inStream.use(properties::load)
    }

    override fun strValue(name: String): String {
        return properties.getProperty(name)
            ?: throw IllegalStateException("Property '${name}' is not defined in '$configFile'")
    }

    override fun intValue(name: String): Int {
        return strValue(name).trim().toInt()
    }

    override fun booleanValue(name: String): Boolean {
        return strValue(name).trim().toBoolean()
    }

}