package com.xutao.race.rpc.api.impl;

import com.xutao.race.rpc.api.RpcProvider;
import com.xutao.race.rpc.handler.server.RpcServiceStart;
import com.xutao.race.rpc.handler.server.ServiceConfig;
import com.xutao.race.rpc.handler.server.ServiceEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by xtao on 15-9-16.
 */
public class RpcProviderImpl extends RpcProvider{
    private Class clazz;
    private Object instance;
    private String version;
    private int timeout;

    private ServiceEngine serviceEngine;
    private RpcServiceStart rpcServiceStart;

    public RpcProviderImpl(){
        init();
    }

    private void init(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);
        serviceEngine = (ServiceEngine)context.getBean("serviceEngine");
        rpcServiceStart = (RpcServiceStart)context.getBean("rpcServiceStart");
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
        try {
            rpcServiceStart.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
