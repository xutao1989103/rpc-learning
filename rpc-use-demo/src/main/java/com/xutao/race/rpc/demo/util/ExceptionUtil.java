package com.xutao.race.rpc.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by xtao on 15-9-16.
 */
public class ExceptionUtil {
    public static String getStackTrace(Throwable throwable) {
        StringWriter buffer = new StringWriter();
        PrintWriter out = new PrintWriter(buffer);
        throwable.printStackTrace(out);
        out.flush();
        return buffer.toString();
    }
}
