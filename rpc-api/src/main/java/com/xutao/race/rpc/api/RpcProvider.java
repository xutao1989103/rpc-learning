package com.xutao.race.rpc.api;

/**
 * Created by xtao on 15-9-16.
 */
public class RpcProvider {
    public RpcProvider() {}

    private void init(){

    }

    public RpcProvider serviceInterface(Class<?> serviceInterface){
        return this;
    }

    public RpcProvider version(String version){
        return this;
    }

    public RpcProvider impl(Object serviceInstance){
        return this;
    }

    public RpcProvider timeout(int time){
        return this;
    }

    public RpcProvider serializeType(String serializeType){
        return this;
    }

    public void publish(){

    }
}
