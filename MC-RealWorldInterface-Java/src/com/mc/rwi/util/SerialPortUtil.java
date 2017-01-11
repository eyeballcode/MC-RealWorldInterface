package com.mc.rwi.util;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.util.ArrayList;
import java.util.Collections;

public class SerialPortUtil {

    public static SerialPort loadSerialPort(CommPortIdentifier identifier) throws PortInUseException {
        SerialPort port = (SerialPort) identifier.open("MCRWI-Java", 2000);
        if (port == null) {
            throw new RuntimeException("Timed out");
        } else {
            return port;
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<CommPortIdentifier> getAllSerialPorts() {
        return Collections.list(CommPortIdentifier.getPortIdentifiers());
    }

    public static CommPortIdentifier findSerialPortByName(String name) {
        ArrayList<CommPortIdentifier> commPortIdentifiers = getAllSerialPorts();
        for (CommPortIdentifier identifier : commPortIdentifiers) {
            if (identifier.getName().equalsIgnoreCase(name)) return identifier;
        }
        return null;
    }
}
