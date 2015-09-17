package com.xutao.race.rpc.demo.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by xtao on 15-9-17.
 */
public class ConsumerTest {
    protected static OutputStream getFunctionalOutputStream() throws FileNotFoundException{
        File file = new File("function.log");
        FileOutputStream out = new FileOutputStream(file);
        return out;
    }
}
