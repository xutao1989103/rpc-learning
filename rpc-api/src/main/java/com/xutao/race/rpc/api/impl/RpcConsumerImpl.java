package com.xutao.race.rpc.api.impl;

import com.google.common.collect.Maps;
import com.xutao.race.rpc.aop.ConsumerHook;
import com.xutao.race.rpc.api.RpcConsumer;
import com.xutao.race.rpc.async.ResponseCallbackListener;
import com.xutao.race.rpc.model.RpcRequest;
import com.xutao.race.rpc.model.RpcResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Created by xtao on 15-9-16.
 */
public class RpcConsumerImpl extends RpcConsumer {
    private Class clazz;
    private Object instance;
    private String version;
    private int timeout;
    private String serviceIp;
    private ConsumerHook consumerHook;
    private Map<String, ResponseCallbackListener> asynCallBack;
    private Map<String, Boolean> asynMap;

    public RpcConsumerImpl(){
        super();
        asynCallBack = Maps.newHashMap();
        asynMap = Maps.newHashMap();
    }

    @Override
    public RpcConsumer interfaceClass(Class<?> interfaceClass){
        this.clazz = interfaceClass;
        return this;
    }

    @Override
    public RpcConsumer version(String version){
        this.version = version;
        return this;
    }

    @Override
    public RpcConsumer clientTimeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    @Override
    public RpcConsumer hook(ConsumerHook hook){
        this.consumerHook = hook;
        return this;
    }

    @Override
    public Object instance(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{this.clazz}, this);
    }

    @Override
    public void asynCall(String methodName){
        asynMap.put(methodName,true);
    }

    @Override
    public <T extends ResponseCallbackListener> void asynCall(String methodName, T callbackListener){
        asynCallBack.put(methodName, callbackListener);
    }

    @Override
    public void cancelAsyn(String methodName){
        asynMap.remove(methodName);
        asynCallBack.remove(methodName);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        RpcRequest request = new RpcRequest();
        RpcResponse response = new RpcResponse();
        response.setAppResponse("hello");
        return response.getAppResponse();
    }
}
