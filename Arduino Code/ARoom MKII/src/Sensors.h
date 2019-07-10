#define temperature_enable 10

class Sensor{
  private:
    int temperature_pin;

  public:
    Sensor(int _temperature_pin){
      temperature_pin = _temperature_pin;
      pinMode(temperature_pin, INPUT);
      pinMode(temperature_enable, INPUT);
    };
    void temperature();
};

void Sensor::temperature(){
  Serial.println(analogRead(temperature_enable));
}
