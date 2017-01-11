package com.mc.rwi;

import com.mc.rwi.event.ButtonPushEvent;
import com.mc.rwi.event.ButtonPushHandler;
import com.mc.rwi.event.KeyPairValue;
import com.mc.rwi.util.SerialPortUtil;
import gnu.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TooManyListenersException;

import static java.lang.Integer.parseInt;

public class MinecraftInterface implements SerialPortEventListener {

    private SerialPort serialPort;
    private BufferedReader inputStream;
    private ArrayList<ButtonPushHandler> buttonPushHandlers = new ArrayList<>();
    private boolean isListening = false;

    private int lastJoystickReading = 0;
    private int lastMode = 0;
    private long lastTriggerTime = System.currentTimeMillis();

    private static final int MESSAGE_START_CONSTANT = 0;

    public MinecraftInterface() {
    }

    public void loadSerialPort() throws IOException, PortInUseException, UnsupportedCommOperationException {
        loadSerialPort(null);
    }

    public void loadSerialPort(String name) throws IOException, PortInUseException, UnsupportedCommOperationException {
        SerialPort serialPort = null;
        CommPortIdentifier identifier;
        if (name == null) {
            identifier = Startup.getSerialPortIdentifier();

        } else {
            identifier = SerialPortUtil.findSerialPortByName(name);
        }
        serialPort = SerialPortUtil.loadSerialPort(identifier);
        serialPort.setSerialPortParams(2400,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        serialPort.notifyOnDataAvailable(true);
        this.serialPort = serialPort;
    }

    public void addButtonPushHandler(ButtonPushHandler handler) {
        buttonPushHandlers.add(handler);
    }

    public void removeButtonPushHandler(ButtonPushHandler handler) {
        buttonPushHandlers.remove(handler);
    }

    public void startListening() throws IOException {
        if (isListening) throw new RuntimeException("Already listening");
        inputStream = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        try {
            serialPort.addEventListener(this);
            isListening = true;
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }

    public void stopListening() {
        if (!isListening) throw new RuntimeException("Not listening yet!");
        serialPort.removeEventListener();
        isListening = false;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String line = inputStream.readLine();
                if ((int) line.toCharArray()[0] != MESSAGE_START_CONSTANT)
                    return; // Didn't start reading from start
                line = line.substring(1);
                KeyPairValue readData = createKeyPair(line);
                if (readData == null) {
                    System.out.println("Invalid message!!");
                } else {
                    if (readData.getName().equals("Joystick")) {
                        int reading = parseInt(readData.getValue());
                        if (reading == lastJoystickReading || Math.abs(reading - lastJoystickReading) < 20) {
                            lastJoystickReading = reading;
                            return;
                        }
                        lastJoystickReading = reading;
                        if (reading == 488) {
                            ButtonPushEvent event = new ButtonPushEvent(lastMode);
                            for (ButtonPushHandler handler : buttonPushHandlers)
                                handler.onButtonPush(event);
                        }
                    } else if (readData.getName().equals("Mode")) {
                        lastMode = parseInt(readData.getValue());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private KeyPairValue createKeyPair(String line) {
        String[] parts = line.split("=");
        if (parts.length < 2) return null;
        String name = parts[0];
        String value = parts[1];
        return new KeyPairValue(name, value);
    }
}
