package com.xutao.race.rpc.demo.test;

import com.xutao.race.rpc.demo.builder.ConsumerBuilder;
import com.xutao.race.rpc.demo.util.ExceptionUtil;

import java.io.OutputStream;
import java.lang.reflect.Method;

/**
 * Created by xtao on 15-9-17.
 */
public class ConsumerFunctionalTest extends ConsumerTest {
    public static void main(String[] args) {
        try {
            OutputStream out = getFunctionalOutputStream();
            ConsumerBuilder consumerBuilder = new ConsumerBuilder();
            Method[] methods = consumerBuilder.getClass().getDeclaredMethods();
            for(Method method: methods){
                if(method.getName().startsWith("test")){
                    StringBuilder sb = new StringBuilder();
                    sb.append(method.getName()).append(": ");
                    try {
                        method.invoke(consumerBuilder, null);
                        sb.append("pass").append("\r\n");
                    }catch (Exception e){
                        sb.append(ExceptionUtil.getStackTrace(e)).append("\r\n");
                    }
                    System.out.println(sb.toString());
                    out.write(sb.toString().getBytes());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
