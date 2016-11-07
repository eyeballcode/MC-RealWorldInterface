/*
 * Copyright (C) 2016  Eyeballcode
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.mc.rwi;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.util.TooManyListenersException;

public class MCRWI {

    public static void main(String[] args) throws IOException, TooManyListenersException, PortInUseException, UnsupportedCommOperationException {
        SerialPort port = SerialUtil.lookupSerialPort();
    }

    private static int potentioMeter = 0;
    private static int joystickY = 0;
    private static boolean clicking = false;

    static void handleData(String data) {
        if (data.startsWith("PotentioMeter")) {
            potentioMeter = Integer.parseInt(data.substring("PotentioMeter=".length()));
        } else if (data.startsWith("Joystick")) {
            joystickY = Integer.parseInt(data.substring("Joystick=".length()));
        }
        if (joystickY > 5) {
            if (clicking) return;
            handleButtonClick();
            clicking = true;
        } else {
            clicking = false;
        }
    }

    private static void handleButtonClick() {
        System.out.println("Potentiometer reading: " + potentioMeter);
        try {
            String computerName = "Logger", message = "Potentiometer=" + potentioMeter;
            if (potentioMeter >= 0 && potentioMeter <= 20) {
                computerName = "ServerRoomLock";
                message = "toggle";
            } else if (potentioMeter > 150 && potentioMeter < 210) {
                computerName = "LightsControl";
                message = "toggle";
            } else if (potentioMeter > 350 && potentioMeter < 410) {
                computerName = "OutsideDoor";
                message = "toggle";
            }
            HTTPUtil.send(computerName, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
