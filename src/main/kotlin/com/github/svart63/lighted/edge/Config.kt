package com.github.svart63.lighted.edge

interface Config {
    fun strValue(name: String): String
    fun intValue(name: String): Int
    fun booleanValue(name: String): Boolean
}