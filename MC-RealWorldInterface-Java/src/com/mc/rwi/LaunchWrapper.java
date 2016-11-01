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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class LaunchWrapper {

    public static void main(String[] args) throws IOException, InterruptedException {
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        String arch = System.getProperty("sun.arch.data.model");

        if (arch.equals("64")) arch = "x86_64";
        else if (arch.equals("32")) arch = "i368";

        File myJAR = new File(LaunchWrapper.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = "natives" + File.separator + OSUtil.getOS().name().toLowerCase() + File.separator + arch;
        String fullPath = null;
        if (myJAR.isFile()) {
            JarFile jar = new JarFile(myJAR);

            ZipEntry nativeFile = jar.getEntry(path.replaceAll("\\" + File.separator, "/") + "/" + OSUtil.getOS().getNativePrefix() + "rxtxSerial." + OSUtil.getOS().getNativeExtension());

            File dest = new File(tmpDir, OSUtil.getOS().getNativePrefix() + "rxtxSerial." + OSUtil.getOS().getNativeExtension());
            InputStream inputStream = jar.getInputStream(nativeFile);
            FileOutputStream outputStream = new FileOutputStream(dest);
            for (int b = inputStream.read(); b != -1; b = inputStream.read())
                outputStream.write(b);
            outputStream.close();
            inputStream.close();
            fullPath = tmpDir.getAbsolutePath();
        } else {
            File nativesLoc = new File(myJAR, path);
            fullPath = nativesLoc.getAbsolutePath();
        }

        ArrayList<String> arg = new ArrayList<>();
        arg.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java");

        arg.add("-Djava.library.path=" + fullPath);
        arg.add("-cp");
        arg.add(myJAR.getAbsolutePath() + File.pathSeparator + System.getProperty("java.class.path"));
        arg.add("com.mc.rwi.MCRWI");

        ProcessBuilder pb = new ProcessBuilder(arg);
        pb.inheritIO();
        Process p = pb.start();
        p.waitFor();
        System.exit(p.exitValue());
    }

}
