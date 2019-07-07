#include <Ethernet.h>
#include <FastLED.h>
#include "ServerHandler.h"
#include "Sensors.h"

ServerHandler site;
Sensor sensor(A4);

unsigned long start = millis();
unsigned long startMillis[2];
int intervals[] = {0, 100};

void setup() {
  Serial.begin(115200);
  Ethernet.begin(site.mac, site.ip);
  FastLED.addLeds<LED_TYPE, LED_PIN, COLOR_ORDER>(leds, NUM_LEDS).setCorrection( TypicalLEDStrip );
  FastLED.setBrightness(  BRIGHTNESS );
  currentPalette = RainbowColors_p;
  currentBlending = LINEARBLEND;
  for(int i = 0; i < (sizeof(startMillis)/sizeof(startMillis[0])); i++){
    startMillis[i] = start;
  }
}

void loop() {
  if(millis() - startMillis[0] >= intervals[0]){
    site.show();
    startMillis[0] = millis();
  }
  // if(millis() - startMillis[1] >= intervals[1]){
  //   sensor.temperature();
  //   startMillis[1] = millis();
  // }
}
