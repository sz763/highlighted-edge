#include <FastLED.h>
#define NUM_LEDS 73
#define LED_PIN 13
#define OFF_TIME 10  
CRGB leds[NUM_LEDS];

byte r,g,b = 0;

void setup() {
  FastLED.addLeds<WS2812B, LED_PIN, GRB>(leds, NUM_LEDS);
  FastLED.setBrightness(50);
  Serial.begin(115200);
  for(int i = 0; i< NUM_LEDS; i++) {
    leds[i] = CRGB(127,127,127);
  }
  FastLED.show();
}

void loop() {
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i].r = readNext();
    leds[i].g = readNext();
    leds[i].b = readNext();
  }
  FastLED.show();
}

byte readNext() {
  while (!Serial.available());
  return Serial.read();
}
