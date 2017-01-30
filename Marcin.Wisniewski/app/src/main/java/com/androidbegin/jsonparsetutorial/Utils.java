package com.androidbegin.jsonparsetutorial;

import java.io.InputStream;
import java.io.OutputStream;

class Utils {
    private static final int BUFFER_SIZE = 1024;
    private static final int OFFSET_START = 0;
    private static final int BREAK_COUNT = -1;

    static void CopyStream(InputStream is, OutputStream os) {
        try {
            byte[] bytes = new byte[BUFFER_SIZE];
            for (; ; ) {
                int count = is.read(bytes, OFFSET_START, BUFFER_SIZE);
                if (count == BREAK_COUNT)
                    break;
                os.write(bytes, OFFSET_START, count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}