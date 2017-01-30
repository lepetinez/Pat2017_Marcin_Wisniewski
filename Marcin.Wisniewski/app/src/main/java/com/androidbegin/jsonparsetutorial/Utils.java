package com.androidbegin.jsonparsetutorial;

import java.io.InputStream;
import java.io.OutputStream;

class Utils {
    private static final int BUFFER_SIZE = 1024;

    static void CopyStream(InputStream is, OutputStream os) {
        try {
            byte[] bytes = new byte[BUFFER_SIZE];
            for (; ; ) {
                int count = is.read(bytes, 0, BUFFER_SIZE);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}