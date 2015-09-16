package com.xutao.race.rpc.api;

import com.xutao.race.rpc.aop.ConsumerHook;
import com.xutao.race.rpc.async.ResponseCallbackListener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xtao on 15-9-16.
 */
public class RpcConsumer implements InvocationHandler {

    private Class<?> interfaceClazz;

    public RpcConsumer(){

    }

    private void init(){

    }

    public RpcConsumer interfaceClass(Class<?> interfaceClazz){
        this.interfaceClazz = interfaceClazz;
        return this;
    }

    public RpcConsumer version(String version){
        return this;
    }

    public RpcConsumer clientTimeout(int clientTimeout){
        return this;
    }

    public RpcConsumer hook(ConsumerHook hook){
        return this;
    }

    public Object instance() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{this.interfaceClazz},this);
    }

    public void asynCall(String methodName) {
        asynCall(methodName, null);
    }

    public <T extends ResponseCallbackListener> void asynCall(String methodName, T callbackListener){

    }

    public void cancelAsyn(String methodName){

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
