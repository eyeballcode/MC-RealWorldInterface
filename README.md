# MC-RealWorldInterface
Control Minecraft with real life

Using this, you can hook up any device to control Minecraft. (Even a toaster if you want)

#Working on:

 - [x] World -> Minecraft communication
 - [ ] Minecraft -> World communication
 - [ ] Websockets maybe?
 - [x] MakeBlock Controller
 - [ ] Java Client for Makeblock
 
#To setup bare minimum:
 
 1. Run `git clone https://github.com/eyeballcode/MC-RealWorldInterface.git; cd MC-RealWorldInterface/`
 2. Run `pwd` and copy the output to the clipboard.
 3. Create a Minecraft launcher profile looking like this: ![](http://i.imgur.com/2KevrJg.png)
    where the game directory is the output of `pwd`. Also, start the Minecraft profile and wait for it to load.
 4. While it is loading, run `cd NodeJS-Server/`, then `npm install -d`. This will install the dependencies for the NodeJS server.
 5. Also run `node base.js`, which starts the server.
 6. When Minecraft finishes loading, open up the world provided. (Excuse my building skills, this is purely a Proof Of Concept.)
 7. Use the command <kbd>F3</kbd> + <kbd>P</kbd> to allow you to switch out of Minecraft without the game pausing.
 8. Open up `http://localhost:8080/demo` in your preferred web browser.
 9. Under `Send to computer`, enter `ServerRoomLock`.
 10. Under `Message`, enter `open`.

#To setup java client:
1. Download the Arduino IDE from https://www.arduino.cc/en/Main/Software.
2. Open up the .ino file provided. Under Tools -> Port, ensure the something like COM3 or /dev/ttyBlah is selected.
3. Hit <kbd>Ctrl</kbd> + <kbd>U</kbd> to upload the file to the MakeBlock
4. If all is well, it should start compiling and uploading. When it is done, it should say "avrdude done."
5. Open up IntelliJ (Sorry eclipse people you're on your own) and load the project.
6. Check that your OS is supported:
   - Windows 32 and 64 Bit
   - Linux 32 and 64 Bit
   - Mac
7. Compile and run the program. When prompted for a serial port, choose the same one as the one used in the Arduino IDE.
(As of now)
8. Move the Joystick up and see if it shows the potentiomenter readings.


#Makeblock setup
- Connect the Potentiometer to Port 8
- Connect the Joystick to Port 7
- Optionally, connect a 7 Segment Display to Port 6


#Notes
- On Linux-based machines, you normally cannot read a serial port. You need root to do so.
- Also, if you get a JVM Native crash when connecting to a serial port, please do help out by downloading RXTX-2.2pre2 and building the natives. I do not have so many machines to build the natives.
