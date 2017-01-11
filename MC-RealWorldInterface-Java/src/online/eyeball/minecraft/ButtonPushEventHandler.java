package online.eyeball.minecraft;

import com.mc.rwi.event.ButtonPushEvent;
import com.mc.rwi.event.ButtonPushHandler;

import java.io.IOException;

public class ButtonPushEventHandler implements ButtonPushHandler {

    private final static String[][] modes = {
            {"ServerRoomLock", "toggle"},
            {"LightsControl", "toggle"},
            {"OutsideDoor", "toggle"},
            {"TempRoomDoor", "toggle"}
    };

    @Override
    public void onButtonPush(ButtonPushEvent event) {
        String[] modeData = modes[event.getMode()];
        try {
            HTTPUtil.send(modeData[0], modeData[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
