package com.xutao.race.rpc.handler.client;

import com.xutao.race.rpc.aop.ConsumerHook;
import com.xutao.race.rpc.async.CallRemoteFuture;
import com.xutao.race.rpc.async.ResponseCallbackListener;
import com.xutao.race.rpc.model.RpcRequest;
import com.xutao.race.rpc.model.RpcResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    @Autowired
    @Qualifier("rpcClientConnects")
    private RpcClientConnects rpcClientConnects;

    @Override
    public void run() {
        CallRemoteFuture future = rpcClientConnects.callRemoteUsePool(request);
        future.setRequest(request);
        future.setConsumerHook(consumerHook);
        try {
            RpcResponse response = future.get(timeout, TimeUnit.SECONDS);
            if(response!=null){
                boolean isErr = (response.getErrorMsg() == null ?false:true);
                if(isErr){
                    if(response.getAppResponse() == null){
                        responseCallbackListener.onException(new Exception(response.getErrorMsg()));
                    }else {
                        responseCallbackListener.onException((Exception)response.getAppResponse());
                    }
                }else {
                    responseCallbackListener.onResponse(response.getAppResponse());
                }
            }else {
                responseCallbackListener.onException(new Exception("call failed"));
            }
        } catch (InterruptedException e) {
            responseCallbackListener.onException(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            responseCallbackListener.onTimeout();
        }
    }
}
