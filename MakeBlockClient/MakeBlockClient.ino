#include <Wire.h>
#include <SoftwareSerial.h>
#include <Makeblock.h>

MePotentiometer meter(PORT_8);
MeJoystick joystick(PORT_6);

void setup() {
  Serial.begin(2400);
}

void loop() {
  print("PotentioMeter", meter.read());  
  print("Joystick", abs(980 - joystick.readY()));
}

void print(const char* name, int val) {
  Serial.print(name);
  Serial.print("=");
  Serial.println(val);
}
