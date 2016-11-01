#include <Wire.h>
#include <SoftwareSerial.h>
#include <Makeblock.h>

MePotentiometer meter(PORT_8);

void setup() {
  Serial.begin(2400);
}

void loop() {
  print("PotentioMeter", meter.read());  
}

void print(const char* name, int val) {
  Serial.print(name);
  Serial.print("=");
  Serial.println(val);
}
