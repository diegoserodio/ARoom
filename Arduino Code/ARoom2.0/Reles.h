class Rele{
  public:
    Rele(int _tela, int _pisca, int _vent, int _luz);
    String screen_status, xmas_status, fan_status, lamp_status;
    void handle(String readString);
    void update();


  private:
    short tela, pisca, ventilador, luz, lamp_input = A5;
    int await_time;
};

Rele::Rele(int _tela, int _pisca, int _vent, int _luz){
  tela = _tela;
  pisca = _pisca;
  ventilador = _vent;
  luz = _luz;
  screen_status = "";
  xmas_status = "";
  fan_status = "";
  lamp_status = "";
  await_time = 0;
  pinMode(tela, OUTPUT);
  pinMode(pisca, OUTPUT);
  pinMode(ventilador, OUTPUT);
  pinMode(luz, OUTPUT);
  pinMode(lamp_input, INPUT);
}

void Rele::handle(String readString){
  if(readString.indexOf("luzOn") >= 0) {
    if(analogRead(lamp_input) <= 450){
      digitalWrite(luz, !digitalRead(luz));
      lamp_status = "Ligado";
    }
  }
  else if(readString.indexOf("luzOff") >= 0) {
    if(analogRead(lamp_input) > 450){
      digitalWrite(luz, !digitalRead(luz));
      lamp_status = "Desligado";
    }
    this->await_time = 500;
  }
  else if(readString.indexOf("ventOn") >= 0) {
    digitalWrite(ventilador, HIGH);
    fan_status = "Ligado";
  }
  else if(readString.indexOf("ventOff") >= 0) {
    digitalWrite(ventilador, LOW);
    fan_status = "Desligado";
  }
  else if(readString.indexOf("natalOn") >= 0) {
    digitalWrite(pisca, HIGH);
    xmas_status = "Ligado";
  }
  else if(readString.indexOf("natalOff") >= 0) {
    digitalWrite(pisca, LOW);
    xmas_status = "Desligado";
  }
  else if(readString.indexOf("telaOn") >= 0) {
    digitalWrite(tela, HIGH);
    screen_status = "Ligado";
  }
  else if(readString.indexOf("telaOff") >= 0) {
    digitalWrite(tela, LOW);
    screen_status = "Desligado";
  }

  //MODOOOOOOOSSSS
}

void Rele::update(){
  if(analogRead(lamp_input) > 450 && await_time == 0) lamp_status = "Ligado";
  else if(analogRead(lamp_input) <= 450 && await_time == 0) lamp_status = "Desligado";
  if(digitalRead(ventilador) == HIGH) fan_status = "Ligado";
  else fan_status = "Desligado";
  if(digitalRead(pisca) == HIGH) xmas_status = "Ligado";
  else xmas_status = "Desligado";
  if(digitalRead(tela) == HIGH) screen_status = "Ligado";
  else screen_status = "Desligado";
  if(await_time > 0)await_time--;
}
