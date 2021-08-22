package com.github.svart63.lighted.edge.impl

import com.fazecast.jSerialComm.SerialPort
import com.github.svart63.lighted.edge.CommDevice
import com.github.svart63.lighted.edge.Config
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JSerialComm @Inject constructor(config: Config) : CommDevice {
    private var portName = config.strValue("comm.device.name")
    private var baudRate = config.intValue("comm.device.baudRate")
    private val log = LoggerFactory.getLogger(javaClass)

    private lateinit var port: SerialPort

    @Inject
    override fun init() {
        log.info("Init comm port: ${this.portName}, baudRate: ${this.baudRate}")
        this.port = SerialPort.getCommPort(this.portName)!!
        this.port.baudRate = this.baudRate
        this.port.openPort()
        log.info("Comm port is open: ${port.isOpen}")
    }

    override fun close() {
        log.info("Close com port: ${this.portName}")
        this.port.closePort()
    }

    override fun write(date: ByteArray) {
        port.writeBytes(date, date.size.toLong())
    }

    override fun read(array: ByteArray): Boolean {
        return port.readBytes(array, array.size.toLong()) > 0
    }

}