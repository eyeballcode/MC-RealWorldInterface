# MC-RealWorldInterface
Control Minecraft with real life

Using this, you can hook up any device to control Minecraft. (Even a toaster if you want)

Working on:

 - [x] World -> Minecraft communication
 - [ ] Minecraft -> World communication
 - [ ] Websockets maybe?
 
 To setup:
 
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
