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

    static String[][] data = {
            {"ServerRoomLock", "toggle"},
            {"LightsControl", "toggle"},
            {"OutsideDoor", "toggle"},
    };

    public static void main(String[] args) throws IOException, TooManyListenersException, PortInUseException, UnsupportedCommOperationException {
        SerialPort port = SerialUtil.lookupSerialPort();
    }

    private static int mode = 0;
    private static int joystickY = 0;
    private static boolean clicking = false;
    private static int temperature = 0;

    static void handleData(String data) {
        if (data.startsWith("Mode")) {
            mode = Integer.parseInt(data.substring("Mode=".length()));
        } else if (data.startsWith("Joystick")) {
            joystickY = Integer.parseInt(data.substring("Joystick=".length()));
        } else if (data.startsWith("TempLogger")) {
            temperature = Integer.parseInt(data.substring("TempLogger=".length()));
            try {
                HTTPUtil.send("TempLogger", data);
            } catch (IOException e) {
            }
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
        System.out.println("Mode: " + mode);
        try {
            if (mode == 99) return;
            String computerName = data[mode][0];
            String message = data[mode][1];
            HTTPUtil.send(computerName, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
