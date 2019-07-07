#define LED_PIN     34
#define unfiltered_input A3
#define filtered_input A2
#define NUM_LEDS    40
#define BRIGHTNESS  64
#define LED_TYPE    WS2811
#define COLOR_ORDER GRB
CRGB leds[NUM_LEDS];
CRGBPalette16 currentPalette;
TBlendType    currentBlending;
extern CRGBPalette16 myRedWhiteBluePalette;
extern const TProgmemPalette16 myRedWhiteBluePalette_p PROGMEM;

int filtered_sum = 0;
int count = 0;
int strip[NUM_LEDS];

int normalize(int value, int min, int max){
  if(value >= max) return max;
  else if(value <= min) return min;
  return value;
}

void stripDisplay(byte r, byte g, byte b){
  for( int i = 0; i < NUM_LEDS; i++) {
    leds[i].setRGB( r, b, g);
  }
  FastLED.show();
}

void stripMusic(){
  int r = 0, g = 0, b = 0, level;
  int unfiltered = map(pow(pow(analogRead(unfiltered_input)-484, 2), 0.5), 250, 400, 0, NUM_LEDS);
  int bass = map(pow(pow(analogRead(filtered_input)-484, 2), 0.5), 250, 500, 0, NUM_LEDS);
  filtered_sum += unfiltered;
  if(count != 0){
    level = normalize(filtered_sum/count, 0, NUM_LEDS);
    bass = normalize(bass, 0, NUM_LEDS);
    r = random(map(bass, 0 , NUM_LEDS, 0, 255));
    for( int i = 0; i < NUM_LEDS; i++) {
      if(strip[i] != 1)leds[i].setRGB(r, g, b);
    }
  }
  if(count >= 15){
    r = random(map(level, 0 , NUM_LEDS, 255, 0));
    g = random(map(level, 0 , NUM_LEDS, 255, 0));
    b = random(map(level, 0 , NUM_LEDS, 255, 0));
    for( int i = 0; i < NUM_LEDS/2; i++) {
      int left = (NUM_LEDS/2)-i-1;
      int right = (NUM_LEDS/2)+i;
      if(i < level){
        leds[left].setRGB(r, g, b);
        leds[right].setRGB(r, g, b);
        strip[left] = 1;
        strip[right] = 1;
      }else{
        strip[left] = 0;
        strip[right] = 0;
      }
    }
    filtered_sum = 0;
    count = 0;
  }else{
    count++;
  }
  FastLED.show();
}

void stripClear(){
  FastLED.clear();
  FastLED.show();
}

class LedStrip{
  public:
    LedStrip(byte _red, byte _green, byte _blue);
    void handle(String readString);
    void update();
    byte red, green, blue;
    String strip_status, music_status;
};

LedStrip::LedStrip(byte _red, byte _green, byte _blue){
  pinMode(unfiltered_input, INPUT);
  pinMode(filtered_input, INPUT);
  red = _red;
  green = _green;
  blue = _blue;
  strip_status = "Desligado";
  music_status = "Desligado";
}

void LedStrip::handle(String readString){
  if(readString.indexOf("strip") >= 0) {
    if(strip_status == "Ligado") strip_status = "Desligado";
    else strip_status = "Ligado";
  }
  else if(readString.indexOf("music") >= 0) {
    if(music_status == "Ligado") music_status = "Desligado";
    else music_status = "Ligado";
  }
  else if(readString.indexOf("red:") >= 0) {
     red = readString.substring(9, 12).toInt()-100;
  }
  else if(readString.indexOf("green:") >= 0) {
     green = readString.substring(11, 14).toInt()-100;
  }
  else if(readString.indexOf("blue:") >= 0) {
     blue = readString.substring(10, 13).toInt()-100;
  }
}

void LedStrip::update(){
  if(strip_status == "Ligado"){
    if(music_status == "Ligado") stripMusic();
    else stripDisplay(red, green, blue);
  }else{
    stripClear();
  }
}
