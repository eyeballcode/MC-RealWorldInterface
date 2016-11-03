#include <Wire.h>
#include <SoftwareSerial.h>
#include "MeOrion.h"

#define ZERO_ERROR 2

MePotentiometer meter(PORT_8);
MeJoystick joystick(PORT_7);
Me7SegmentDisplay disp(PORT_6);

void setup() {
  Serial.begin(2400);
  disp.set();
  disp.init();
}

void loop() {
  print("PotentioMeter", meter.read());  
  print("Joystick", -(joystick.readY() + ZERO_ERROR));
  disp.display(meter.read());
}

void print(const char* name, int val) {
  Serial.print(name);
  Serial.print("=");
  Serial.println(val);
}
