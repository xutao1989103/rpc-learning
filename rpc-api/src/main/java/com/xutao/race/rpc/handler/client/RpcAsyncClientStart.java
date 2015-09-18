package com.xutao.race.rpc.handler.client;

import com.xutao.race.rpc.aop.ConsumerHook;
import com.xutao.race.rpc.async.ResponseCallbackListener;
import com.xutao.race.rpc.model.RpcRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by xtao on 15-9-18.
 */
@Component
@Scope("prototype")
@Qualifier("rpcAsyncClientStart")
public class RpcAsyncClientStart implements Runnable {

    private ResponseCallbackListener responseCallbackListener;
    private RpcRequest request;
    private ConsumerHook consumerHook;
    private int timeout;

    @Override
    public void run() {

    }
}
