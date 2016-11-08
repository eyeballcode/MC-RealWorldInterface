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
  int potentiometer = meter.read();
  Serial.println(potentiometer);
  int mode = 0;
  if (potentiometer >= 0 && potentiometer <= 100) {
    print("Mode", 0);
    mode = 0;
  } else if (potentiometer > 100 && potentiometer <= 250) {
    print("Mode", 1);
    mode = 1;
  } else if (potentiometer > 250 && potentiometer <= 400) {
    print("Mode", 2);
    mode = 2;
  } else mode = 99;
  print("Joystick", -(joystick.readY() + ZERO_ERROR));
  disp.display(mode);
}

void print(const char* name, int val) {
  Serial.print(name);
  Serial.print("=");
  Serial.println(val);
}
