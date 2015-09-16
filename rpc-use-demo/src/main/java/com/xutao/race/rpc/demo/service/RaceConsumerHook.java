package com.xutao.race.rpc.demo.service;

import com.xutao.race.rpc.aop.ConsumerHook;
import com.xutao.race.rpc.context.RpcContext;
import com.xutao.race.rpc.model.RpcRequest;

/**
 * Created by xtao on 15-9-16.
 */
public class RaceConsumerHook implements ConsumerHook {
    @Override
    public void before(RpcRequest request) {
        RpcContext.addProp("hook key", "this is pass by hook");
    }

    @Override
    public void after(RpcRequest request) {
        System.out.println("I have finished Rpc calling");
    }
}
