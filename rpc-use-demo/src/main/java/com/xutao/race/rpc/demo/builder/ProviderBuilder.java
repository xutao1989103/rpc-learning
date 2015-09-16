package com.xutao.race.rpc.demo.builder;

import com.xutao.race.rpc.api.RpcProvider;
import com.xutao.race.rpc.demo.service.RaceTestService;
import com.xutao.race.rpc.demo.service.RaceTestServiceImpl;

/**
 * Created by xtao on 15-9-16.
 */
public class ProviderBuilder {

    public static void buildProvider(){
        publish();
    }

    private static void publish() {
        RpcProvider rpcProvider = null;
        try {
            rpcProvider = (RpcProvider)getProviderImplClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(rpcProvider == null){
            System.out.println("start rpc provider failed");
            System.exit(1);
        }

        rpcProvider.serviceInterface(RaceTestService.class)
                .impl(new RaceTestServiceImpl())
                .version("1.0.0.api")
                .timeout(3000)
                .serializeType("java")
                .publish();
    }

    private static Class<?> getProviderImplClass(){
        try {
            return Class.forName("com.xutao.race.rpc.api.impl.RpcProviderImpl");
        } catch (ClassNotFoundException e) {
            System.out.println("cannot find the rpc impl class");
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
