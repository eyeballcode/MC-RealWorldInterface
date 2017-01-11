package online.eyeball.minecraft;

import com.mc.rwi.MinecraftInterface;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            MinecraftInterface minecraftInterface = new MinecraftInterface();
            minecraftInterface.addButtonPushHandler(new ButtonPushEventHandler());
            minecraftInterface.loadSerialPort();
            minecraftInterface.startListening();
        } catch (IOException | PortInUseException | UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
    }
}
