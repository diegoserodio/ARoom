#include <SPI.h>
#include <Ethernet.h>
#include <FastLED.h>

#define LED_PIN     34
#define NUM_LEDS    40
#define BRIGHTNESS  64
#define LED_TYPE    WS2811
#define COLOR_ORDER GRB
CRGB leds[NUM_LEDS];
CRGBPalette16 currentPalette;
TBlendType    currentBlending;
extern CRGBPalette16 myRedWhiteBluePalette;
extern const TProgmemPalette16 myRedWhiteBluePalette_p PROGMEM;

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
byte ip[] = { 192, 168, 15, 14 };
EthernetServer server(8090);

////////////////////    RELÊS E PERIFÉRICOS - IO
int tela = 53;
int natal = 49;
int ventilador = 45;
int luz = 41;
int cooler = 36;
int lampada = A5;
int ir = A0;
////////////////////    REFREGERAÇÃO
int estadotemp = 10;
int valortemp = A3;
int tempCounter = 0;
////////////////////    LÂMPADA
String lamp;
String lamp2;
int light;
int statusLmp;
int contador = 0;
////////////////////    FITA DE LED
int vermelho = 0;
int verde = 0;
int azul = 0;
int audio = A2;
int ledIndex = 0;
bool stripPower = false;
bool Red = false;
bool Green = false;
bool Blue = false;
String redReading = "";
String redReading2 = "164";
String greenReading = "";
String greenReading2 = "164";
String blueReading = "";
String blueReading2 = "164";
int redValue = 64;
int greenValue = 64;
int blueValue = 64;
String text = "";
int stripMusic = 0;
int stripEffects = 0;
////////////////////    ETHERNET SERVER
String readString = String(30);
String statusRele;
String statusRele2;
unsigned int data;

void setup() {
 Ethernet.begin(mac, ip);
 Serial.begin(9600);
 ////////////////////    DEFINE IO
 pinMode(tela, OUTPUT);
 pinMode(natal, OUTPUT);
 pinMode(ventilador, OUTPUT);
 pinMode(luz, OUTPUT);
 pinMode(audio, INPUT);
 pinMode(lampada, INPUT);
 pinMode(ir, INPUT);
 pinMode(estadotemp, OUTPUT);
 pinMode(valortemp, INPUT);
 pinMode(cooler, OUTPUT);
 ////////////////////    SETAR SAÍDAS
 digitalWrite(estadotemp, HIGH);
 digitalWrite(tela, LOW);
 ////////////////////    DELAY DE SEGURANÇA
 delay(2000);
 ////////////////////    INICIALIZA FITA DE LED
 FastLED.addLeds<LED_TYPE, LED_PIN, COLOR_ORDER>(leds, NUM_LEDS).setCorrection( TypicalLEDStrip );
 FastLED.setBrightness(  BRIGHTNESS );   
 currentPalette = RainbowColors_p;
 currentBlending = LINEARBLEND;
}

void loop() {
 EthernetClient client = server.available();
 refrigeration(analogRead(valortemp));
 statusLamp();
 play(redValue, blueValue, greenValue);
 if(client)
 {
  if(contador <=1){
    statusLamp();  
  }else{
    contador--;
  }

  while(client.connected())
  {
    if(client.available())
    {
       char c = client.read();

       if(readString.length() < 30) {
          readString += (c);
          text = String(c);
       }    
       
       if(c == '\n')
       {
         ////////////////////    RELÊS
         if(readString.indexOf("luzOn") >= 0) {
           executeRelay("luzOn");
         }
         if(readString.indexOf("luzOff") >= 0) {
           executeRelay("luzOff");
         }
         
         if(readString.indexOf("ventOn") >= 0) {
           executeRelay("ventOn");
         }
         if(readString.indexOf("ventOff") >= 0) {
           executeRelay("ventOff");
         }
         
         if(readString.indexOf("natalOn") >= 0) {
           executeRelay("natalOn");
         }
         if(readString.indexOf("natalOff") >= 0) {
           executeRelay("natalOff");
         }

         if(readString.indexOf("telaOn") >= 0) {
           executeRelay("telaOn");
         }
         if(readString.indexOf("telaOff") >= 0) {
           executeRelay("telaOff");
         }

         ////////////////////    MODOS
         if(readString.indexOf("tudoOn") >= 0) {
           executeMode("tudoOn");
         }

         if(readString.indexOf("tudoOff") >= 0) {
           executeMode("tudoOff");
         }

         if(readString.indexOf("netflixOn") >= 0) {
           executeMode("netflixOn");
         }

         if(readString.indexOf("netflixOff") >= 0) {
           executeMode("netflixOff");
         }

         if(readString.indexOf("festaOn") >= 0) {
           executeMode("festaOn");
         }

         if(readString.indexOf("festaOff") >= 0) {
           executeMode("festaOff");
         }

         if(readString.indexOf("comaOn") >= 0) {
           executeMode("comaOn");
         }
         
         if(readString.indexOf("comaOff") >= 0) {
           executeMode("comaOff");
         }


         ////////////////////    FITA
         if(readString.indexOf("strip") >= 0) {
            stripPower = !stripPower;
         }
         
         if(readString.indexOf("red:") >= 0) {
            redReading = readString;
            redReading2 = redReading.substring(9, 12);
            redValue = redReading2.toInt();
            redValue = redValue - 100;
         }
         if(readString.indexOf("green:") >= 0) {
            greenReading = readString;
            greenReading2 = greenReading.substring(11, 14);
            greenValue = greenReading2.toInt();
            greenValue = greenValue - 100;
         }
         if(readString.indexOf("blue:") >= 0) {
            blueReading = readString;
            blueReading2 = blueReading.substring(10, 13);
            blueValue = blueReading2.toInt();
            blueValue = blueValue - 100;
         }

         if(readString.indexOf("stripOff") >= 0) {
            stripPower = false;
         }
         
         if(readString.indexOf("effectminus") >= 0) {
            stripEffects = stripEffects - 1;
            if(stripEffects <= 0){
              stripEffects = 0;
            }
            stripMusic = 0;       
         }

         if(readString.indexOf("effectplus") >= 0) {
            stripEffects = stripEffects + 1;
            if(stripEffects >= 3){
              stripEffects = 3;
            }
            stripMusic = 0;      
         }

         if(readString.indexOf("music") >= 0) {
            stripEffects = 0;
            if(stripMusic<3){
               stripMusic = stripMusic+1;
            }else{
               stripMusic = 0;
            }
         }
 
          // Cabeçalho http padrão
          client.println("HTTP/1.1 200 OK");
          client.println("Access-Control-Allow-Origin: *");
          client.println("Content-Type: text/html");
          client.println();
          
          client.println("<!doctype html>");
          client.println("<html>");
          client.println("<head>");
          client.println("<meta name=\"viewport\" content=\"width=320\">");
          client.println("<meta name=\"viewport\" content=\"width=device-width\">");
          client.println("<meta charset=\"utf-8\" />");
          client.println("<title>Central</title>");
          client.println("<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">");
          client.println("<meta http-equiv=\"refresh\" content=\"5, URL=http://192.168.15.14:8090\">");
          client.println("</head>");
          client.println("<body>");
          client.println("<center>");
          
          client.println("<font size=\"5\" face=\"verdana\" color=\"red\">Painel de Controle</font>");
          
          client.println("<td> <form action=\"luzOn\" method=\"get\">");
          client.println("<button id=\"lampOn\" type=submit style=\"width:200px;\">Lampada - "+lamp+"</button>");
          client.println("</form> <br /></td>");
          
          client.println("<td> <form action=\"luzOff\" method=\"get\">");
          client.println("<button id=\"lampOff\" type=submit style=\"width:200px;\">Lampada - "+lamp2+"</button>");
          client.println("</form> <br /></td>");
          
          if(digitalRead(ventilador)) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "";
          }
          client.println("<td> <form action=\"ventOn\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Ventilador - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          if(digitalRead(ventilador)) {
           statusRele2 = "" ;
          } else {
            statusRele2 = "Desligado";
          }
          client.println("<td> <form action=\"ventOff\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Ventilador - "+statusRele2+"</button>");
          client.println("</form> <br /></td>");
          
          if(digitalRead(natal)) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "";
          }
          client.println("<td> <form action=\"natalOn\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Pisca - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          if(digitalRead(natal)) {
           statusRele2 = "" ;
          } else {
            statusRele2 = "Desligado";
          }
          client.println("<td> <form action=\"natalOff\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Pisca - "+statusRele2+"</button>");
          client.println("</form> <br /></td>");

          if(digitalRead(tela)) {
           statusRele = "Desligado" ;
          } else {
            statusRele = "";
          }
          client.println("<td> <form action=\"telaOn\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Tela - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          if(digitalRead(tela)) {
           statusRele2 = "" ;
          } else {
            statusRele2 = "Ligado";
          }
          client.println("<td> <form action=\"telaOff\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Tela - "+statusRele2+"</button>");
          client.println("</form> <br /></td>");

          if(stripPower == true) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "Desligado";
          }
          client.println("<td> <form action=\"strip\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Fita - "+statusRele+"</button>");
          client.println("</form> <br /></td>");

          if(Red == true) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "Desligado";
          }
          client.println("<td> <form action=\"red\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Red - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          if(Green == true) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "Desligado";
          }
          client.println("<td> <form action=\"green\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Green - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          if(Blue == true) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "Desligado";
          }
          client.println("<td> <form action=\"blue\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Blue - "+statusRele+"</button>");
          client.println("</form> <br /></td>");

          if(stripEffects == 0) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "Desligado";
          }
          client.println("<td> <form action=\"police\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">NAEffect - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          if(stripEffects == 1) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "Desligado";
          }
          client.println("<td> <form action=\"strobo\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Efeito 1 - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          if(stripEffects == 2) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "Desligado";
          }
          client.println("<td> <form action=\"cascade\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Efeito 2 - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          if(stripEffects == 3) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "Desligado";
          }
          client.println("<td> <form action=\"cascade\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Efeito 3 - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          if(stripMusic != 0) {
           statusRele = "Ligado" ;
          } else {
            statusRele = "Desligado";
          }
          client.println("<td> <form action=\"music\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Music - "+statusRele+"</button>");
          client.println("</form> <br /></td>");
          
          client.println("<div id=\"tempValue\">Temperatura atual (Offset: 100): "+String(analogRead(valortemp)+100)+"</div>");
          client.println("<div id=\"coolerState\">Cooler: "+String(digitalRead(cooler))+"</div>");
          client.println("<div id=\"rgbValue\">RGB (Offset: 100): "+redReading2+" "+greenReading2+" "+blueReading2+" "+"</div>");
          client.println("<div id=\"musicValue\">Music (Offset: 100): "+String(ledIndex+100)+"</div>");
          
          client.println("</center>");
          client.println("</body>");
          client.println("</html>");
          
          
         
         readString = "";
         
         client.stop();
       }
     }
  }
 }
}
