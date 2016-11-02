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

import gnu.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TooManyListenersException;

public class SerialUtil implements SerialPortEventListener {


    private static BufferedReader input;
    /**
     * The output stream to the port
     */
    private static OutputStream output;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 2400;

    public static SerialPort lookupSerialPort() throws IOException, PortInUseException, UnsupportedCommOperationException, TooManyListenersException {
        CommPortIdentifier portId = null;
        @SuppressWarnings("unchecked")
        ArrayList<CommPortIdentifier> ports = Collections.list(CommPortIdentifier.getPortIdentifiers());
        if (ports.size() == 0) {
            System.out.println("No serial ports detected! Cannot continue loading!");
            return null;
        }
        System.out.println("Please select the port your Arduino is on: ");
        for (CommPortIdentifier port : ports) {
            System.out.println(" - " + port.getName());
        }

        String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
        for (CommPortIdentifier port : ports) {
            if (port.getName().equalsIgnoreCase(name)) {
                portId = port;
            }
        }
        if (portId == null) {
            System.out.println("That's not a valid serial port.");
            System.exit(1);
        }
        SerialPort port = (SerialPort) portId.open("MCRWI-Java", TIME_OUT);
        port.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        input = new BufferedReader(new InputStreamReader(port.getInputStream()));
        output = port.getOutputStream();

        port.addEventListener(new SerialUtil());
        port.notifyOnDataAvailable(true);
        return port;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String data = input.readLine();
                MCRWI.handleData(data);
            } catch (IOException e) {
            }
        }
    }
}
