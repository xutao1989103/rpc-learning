package com.xutao.race.rpc.api.impl;

import com.xutao.race.rpc.api.RpcProvider;
import com.xutao.race.rpc.handler.server.ServiceEngine;

/**
 * Created by xtao on 15-9-16.
 */
public class RpcProviderImpl extends RpcProvider{
    private Class clazz;
    private Object instance;
    private String version;
    private int timeout;

    private ServiceEngine serviceEngine;

    public RpcProviderImpl(){
        init();
    }

    private void init(){
        serviceEngine = ServiceEngine.instance();
    }

    @Override
    public RpcProvider serviceInterface(Class<?> serviceInterface){
        this.clazz = serviceInterface;
        return this;
    }

    @Override
    public RpcProvider version(String version){
        this.version = version;
        return this;
    }

    @Override
    public RpcProvider impl(Object serviceInstance){
        this.instance = serviceInstance;
        return this;
    }

    @Override
    public RpcProvider timeout(int time){
        this.timeout = time;
        return this;
    }

    @Override
    public RpcProvider serializeType(String serializeType){
        super.serializeType(serializeType);
        return this;
    }

    @Override
    public void publish(){
        serviceEngine.publish(clazz, version, instance);
    }
}
