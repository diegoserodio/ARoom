#include "Reles.h"
#include "LedStrip.h"

EthernetServer server(8090);
Rele rele(53, 49, 45, 41);
LedStrip led(128, 128, 128);

String readString = String(30);

void displaySite(){
  EthernetClient client = server.available();
  if(client){
     if(client.available()){
        char c = client.read();
        if(readString.length() < 30){
           readString += (c);
        }
        if(c == '\n'){
           rele.handle(readString);
           led.handle(readString);
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
           //client.println("<meta http-equiv=\"refresh\" content=\"5, URL=http://192.168.15.14:8090\">");
           client.println("</head>");
           client.println("<body>");

           client.println("Lampada - "+rele.lamp_status+"<br/>");
           client.println("Ventilador - "+rele.fan_status+"<br/>");
           client.println("Pisca - "+rele.xmas_status+"<br/>");
           client.println("Tela - "+rele.screen_status+"<br/>");
           client.println("Fita - "+led.strip_status+"<br/>");
           client.println("Red - Desligado <br/>");
           client.println("Green - Desligado <br/>");
           client.println("Blue - Desligado <br/>");
           client.println("NAEffect - Desligado <br/>");
           client.println("Efeito 1 - Desligado <br/>");
           client.println("Efeito 2 - Desligado <br/>");
           client.println("Efeito 3 - Desligado <br/>");
           client.println("Music - "+led.music_status+"<br/>");
           client.println("<div id=\"tempValue\">Temperatura atual (Offset: 100): 80</div>");
           client.println("<div id=\"coolerState\">Cooler: Ligado</div>");
           client.println("<div id=\"rgbValue\">RGB (Offset: 100): "+String(led.red+100)+" "+String(led.green+100)+" "+String(led.blue+100)+"</div>");
           client.println("<div id=\"musicValue\">Music (Offset: 100): 80</div>");

           client.println("</body>");
           client.println("</html>");

          readString = "";
          client.stop();
        }
      }
  }
  rele.update();
  led.update();
}

class ServerHandler{
  public:
  byte mac[6] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED }, ip[4] = { 192, 168, 15, 14 };
  void show();
};

void ServerHandler::show(){
  displaySite();
}
