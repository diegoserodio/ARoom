int novaSoma = -1;
void play(int r, int b, int g)
{ 
  int a1 = analogRead(audio);
  int a2 = analogRead(audio);
  int a3 = analogRead(audio);
  int b1 = analogRead(audio);
  int b2 = analogRead(audio);
  int b3 = analogRead(audio);
  int soma = r+g+b;
  music(a1, a2, a3, b1, b2, b3);
  if(stripPower == true){
    if(stripMusic == 1){
      musicEffect1();
      novaSoma = -1;
    }
    if(stripMusic == 2){
      musicEffect2();
    }
    if(stripMusic == 3){
      musicEffect3();
    }
    if(stripEffects == 1){
      for( int i = 0; i < NUM_LEDS; i++) {
        leds[i].setRGB( 128, 0, 0);
      }
      FastLED.show();
      novaSoma = -1;
    }
    if(stripEffects == 2){
      for( int i = 0; i < NUM_LEDS; i++) {
        leds[i].setRGB( 0, 128, 0);
      }
      FastLED.show();
    }
    if(stripEffects == 3){
      for( int i = 0; i < NUM_LEDS; i++) {
        leds[i].setRGB( 0, 0, 128);
      }
      FastLED.show();
    }
    if(stripEffects == 0 && stripMusic == 0){
      if(soma!=novaSoma){
        for( int i = 0; i < NUM_LEDS; i++) {
        leds[i].setRGB( r, b, g);
      }
      FastLED.show();
      novaSoma = soma; 
      }
    }
  }else{
      novaSoma = -1;
      FastLED.clear();
      FastLED.show();
  }
}

void music(int n1, int n2, int n3, int m1, int m2, int m3){
  int dataOld = (n1+n2+n3)/3;
  int dataNew = (m1+m2+m3)/3;
  int dataDifference = dataNew-dataOld;
  int index = map(dataDifference, 0, 100, 0 , NUM_LEDS);
  if(index < 0){
    index = index*-1;
  }
  if(index >= NUM_LEDS){
    index = NUM_LEDS;
  }
  ledIndex = index;
}

void musicEffect1(){
  for( int i = 0; i < NUM_LEDS/2; i++) {
          int left = (NUM_LEDS/2)-i-1;
          int right = (NUM_LEDS/2)+i;
          int dist = NUM_LEDS/2 - left;
          if(i==0){
            vermelho = random(100);
            verde = random(100);
            azul = random(100);
          }
          if(ledIndex <= 15){
            if(dist <= ledIndex){
              if(dist <= 1){
                leds[left].setRGB( vermelho, 0, 0);
                leds[right].setRGB( vermelho, 0, 0);  
              }
              else if(dist <= 5){
                leds[left].setRGB( vermelho, verde, 0);
                leds[right].setRGB( vermelho, verde, 0);  
              }
              else if(dist > 5){
                leds[left].setRGB( vermelho, verde, azul);
                leds[right].setRGB( vermelho, verde, azul);  
              }  
            }else{
              leds[left] = CRGB::Black;
              leds[right] = CRGB::Black;
            }
          }else{
            if(dist <= ledIndex){
              leds[left].setRGB( vermelho, verde, azul);
              leds[right].setRGB( vermelho, verde, azul); 
            }else{
              leds[left] = CRGB::Black;
              leds[right] = CRGB::Black;
            }
          }
          FastLED.show();
          FastLED.delay(1);
      }
}

void musicEffect2(){
  for( int i = 0; i < NUM_LEDS/2; i++) {
          int left = i;
          int right = NUM_LEDS-i-1;
          if(i==0){
            vermelho = random(100);
            verde = random(100);
            azul = random(100);
          }
          if(ledIndex <= 15){
            if(left <= ledIndex){
              if(left <= 1){
                leds[left].setRGB( vermelho, 0, 0);
                leds[right].setRGB( vermelho, 0, 0);  
              }
              else if(left <= 5){
                leds[left].setRGB( vermelho, verde, 0);
                leds[right].setRGB( vermelho, verde, 0);  
              }
              else if(left > 5){
                leds[left].setRGB( vermelho, verde, azul);
                leds[right].setRGB( vermelho, verde, azul);  
              }  
            }else{
              leds[left] = CRGB::Black;
              leds[right] = CRGB::Black;
            }
          }else{
            if(left <= ledIndex){
              leds[left].setRGB( vermelho, verde, azul);
              leds[right].setRGB( vermelho, verde, azul); 
            }else{
              leds[left] = CRGB::Black;
              leds[right] = CRGB::Black;
            }
          }
          FastLED.show();
          FastLED.delay(1);
      }
}

void musicEffect3(){
  for( int i = 0; i < NUM_LEDS; i++) {
          if(i==0){
            vermelho = random(100);
            verde = random(100);
            azul = random(100);
          }
          if(ledIndex <= 15){
            if(i <= ledIndex){
              if(i <= 1){
                leds[i].setRGB( vermelho, 0, 0);
              }
              else if(i <= 5){
                leds[i].setRGB( vermelho, verde, 0);
              }
              else if(i > 5){
                leds[i].setRGB( vermelho, verde, azul); 
              }  
            }else{
              leds[i] = CRGB::Black;
            }
          }else{
            if(i <= ledIndex){
              leds[i].setRGB( vermelho, verde, azul);
            }else{
              leds[i] = CRGB::Black;
            }
          }
          FastLED.show();
          FastLED.delay(1);
      }
}
