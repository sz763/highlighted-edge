package com.github.svart63.lighted.edge

interface CommApi : AutoCloseable {
    fun init()
}

interface CommWriter : CommApi {
    fun write(date: ByteArray)
}

interface CommReader : CommApi {
    fun read(array: ByteArray): Boolean
}

interface CommDevice : CommWriter, CommReader

