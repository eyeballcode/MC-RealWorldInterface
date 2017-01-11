#include <Wire.h>
#include <SoftwareSerial.h>
#include "MeOrion.h"

#define ZERO_ERROR 2
#define MESSAGE_START_CONSTANT 0

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
  int mode = 0;
  if (potentiometer >= 0 && potentiometer <= 100) {
    mode = 0;
  } else if (potentiometer > 100 && potentiometer <= 250) {
    mode = 1;
  } else if (potentiometer > 250 && potentiometer <= 400) {
    mode = 2;
  } else if (potentiometer > 400 && potentiometer <= 550) {
    mode = 3;
  } else mode = 99;
 
  print("Mode", mode);
  print("Joystick", -(joystick.readY() + ZERO_ERROR));
  disp.display(mode);
}

void print(const char* name, int val) {
  Serial.write(MESSAGE_START_CONSTANT);
  Serial.print(name);
  Serial.print("=");
  Serial.println(val);
}
