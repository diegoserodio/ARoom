void executeRelay(String command){
  if(command == "luzOn"){
    lampOn();
  }
  else if(command == "luzOff"){
    lampOff();
  }
  
  else if(command == "ventOn"){
    digitalWrite(ventilador, HIGH);
  }
  else if(command == "ventOff"){
    digitalWrite(ventilador, LOW);
  }
  
  else if(command == "natalOn"){
    digitalWrite(natal, HIGH);
  }
  else if(command == "natalOff"){
    digitalWrite(natal, LOW);
  }

  else if(command == "telaOn"){
    digitalWrite(tela, LOW);
  }
  else if(command == "telaOff"){
    digitalWrite(tela, HIGH);
  }
}

void executeMode(String command){
  if(command == "tudoOn"){
    lampOn();
    digitalWrite(ventilador, HIGH);
    digitalWrite(natal, HIGH);
    digitalWrite(tela, LOW);
  }
  else if(command == "tudoOff"){
    lampOff();
    digitalWrite(ventilador, LOW);
    digitalWrite(natal, LOW);
    digitalWrite(tela, HIGH);
    stripEffects = 0;
    stripMusic = 0;
  }
  
  else if(command == "netflixOn"){
    lampOff();
    digitalWrite(natal, LOW);
    digitalWrite(tela, HIGH);
    stripEffects = 0;
    stripMusic = 0;
  }
  else if(command == "netflixOff"){
    digitalWrite(tela, LOW);
  }
  
  else if(command == "festaOn"){
    lampOff();
    digitalWrite(natal, HIGH);
    digitalWrite(tela, HIGH);
    stripPower = true;
    stripEffects = 0;
    stripMusic = 1;
  }
  else if(command == "festaOff"){
    lampOn();
    digitalWrite(natal, LOW);
    digitalWrite(tela, LOW);
    stripMusic = 0;
  }

  else if(command == "comaOn"){
    lampOff();
    digitalWrite(natal, LOW);
  }
  else if(command == "comaOff"){
    lampOn();
  }
}

void statusLamp(){
  light = analogRead(lampada);
  if(light > 240){
    statusLmp = 1;
    lamp = "Ligado";
    lamp2 = "";
  } else{
    statusLmp = 0;
    lamp = "";
    lamp2 = "Desligado";
  }
}

void lampOn(){
  if(statusLmp == 0 && contador <=1){
            digitalWrite(luz, !digitalRead(luz));
            lamp = "Ligado";
            lamp2 = "";
            contador = 3;
           }
}

void lampOff(){
  if(statusLmp == 1 && contador <=1){
            digitalWrite(luz, !digitalRead(luz));
            lamp = "";
            lamp2 = "Desligado";
            contador = 3;
           }
}
