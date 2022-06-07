# highlighted-edge
Implementation of Ambilight functionality for PC.


![](https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Ambilight-1.jpg/640px-Ambilight-1.jpg)

## Workflow
Take screenshot of edge -> split to regions -> detect common color of region (each region maps to LED on strip) -> send color information to `arduino` thru `COM` port -> set LED color on digital LED strip.

## Configuration
|Name|Default|Description|
|----|-------|-------|
|refresh.rate|10|Low refresh rate can affect CPU, hight refresh rate will produce lag between picture on screen and LED strip color. I use around 100 on Raspberry Pi 4|
|edge.horizontal.leds|35|Count of LED's on strip by horizontal (don't forget set same value in arduino code)|
|edge.vertical.leds|19|Count of LED's on strip by vertical (don't forget set same value in arduino code)|
|edge.height|60|The region hight|
|edge.width|60|The region width|
|comm.device.name|COM3|Plugged in arduino device name. `COM3` or any other value for Windows. `/dev/ttyUSB0` or any other value for for Linux|
|comm.device.baudRate|115200|Bits per second that will be send to arduino (this value should be same in arduino)|
|color.detector|common|Detector strategy, there are two implementations: `simple`, `common`|
