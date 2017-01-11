package com.mc.rwi;

import gnu.io.CommPortIdentifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.mc.rwi.util.SerialPortUtil.getAllSerialPorts;
import static java.lang.Integer.parseInt;

public class Startup {

    private static String readLine() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    private static void printSerialPorts(ArrayList<CommPortIdentifier> commPortIdentifiers) {
        for (int i = 0; i < commPortIdentifiers.size(); i++) {
            CommPortIdentifier commPortIdentifier = commPortIdentifiers.get(i);
            System.out.println(i + ": " + commPortIdentifier.getName());
        }
    }

    public static CommPortIdentifier getSerialPortIdentifier() throws IOException {
        ArrayList<CommPortIdentifier> commPorts = getAllSerialPorts();
        printSerialPorts(commPorts);
        int portID = readSerialPortID();
        return commPorts.get(portID);
    }

    private static int readSerialPortID() throws IOException {
        String line = readLine();
        return parseInt(line);
    }
}
