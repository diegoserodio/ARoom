void refrigeration(int c_temp){
  int currentTemp = analogRead(valortemp);
  if(c_temp-currentTemp >= 10){
    tempCounter++;
  }else{
    tempCounter--;
  }
  if(tempCounter > 50){
    tempCounter = 50;
  }
  else if(tempCounter < 0){
    tempCounter = 0;
  }
  if(tempCounter >= 20){
    digitalWrite(cooler, HIGH);
  }else{
    digitalWrite(cooler, LOW);
  }
}

