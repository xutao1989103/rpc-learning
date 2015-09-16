package com.xutao.race.rpc.aop;

import com.xutao.race.rpc.model.RpcRequest;

import java.io.Serializable;

/**
 * Created by xtao on 15-9-16.
 */
public interface ProviderHook extends Serializable {
    public void before(RpcRequest request);
    public void after(RpcRequest request);
}
