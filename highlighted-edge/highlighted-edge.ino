#include <FastLED.h>
#define NUM_LEDS 60
#define LED_PIN 13
#define OFF_TIME 10  
CRGB leds[NUM_LEDS];
void setup() {
  FastLED.addLeds<WS2812B, LED_PIN, GRB>(leds, NUM_LEDS);
  FastLED.setBrightness(50);
  Serial.begin(115200);
  Serial.print("Hello\n");
  for(int i = 0; i< 35; i++) {
    leds[i+9] = CRGB(i*3,i*3,i*3);
  }
  FastLED.show();
}
byte r = 0;
byte g = 0;
byte b = 0;
int index = 0;
int current = 0;
int readVal = 0;
boolean led_state = true;
unsigned long off_timer;

void check_connection() {
  if (led_state) {
    if (millis() - off_timer > (OFF_TIME * 1000)) {
      led_state = false;
      FastLED.clear();
      FastLED.show();
    }
  }
}

void loop() {
  if (!led_state) led_state = true;
  off_timer = millis(); 
//  NUM_LEDS
  for (uint8_t i = 0; i < 35; i++) {
    // читаем данные для каждого цвета
    while (!Serial.available()) check_connection();;
    r = Serial.read();
    while (!Serial.available()) check_connection();;
    g = Serial.read();
    while (!Serial.available()) check_connection();;
    b = Serial.read();
    leds[i+9].r = r;
    leds[i+9].g = g;
    leds[i+9].b = b;
  }
  FastLED.show();
}
