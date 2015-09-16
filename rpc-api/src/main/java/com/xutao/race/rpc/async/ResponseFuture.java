package com.xutao.race.rpc.async;


import com.xutao.race.rpc.model.RpcResponse;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by xtao on 15-9-16.
 */
public class ResponseFuture {
    public static ThreadLocal<Future<Object>> futureThreadLocal = new ThreadLocal<Future<Object>>();
    public static Object getResponse(long timeout) throws InterruptedException{
        if(null == futureThreadLocal.get()){
            throw new RuntimeException("Thread " + Thread.currentThread() + " have not set the response future");
        }
        try {
            RpcResponse response = (RpcResponse)(futureThreadLocal.get().get(timeout, TimeUnit.SECONDS));
            if(response != null){
                if(response.isError()){
                    return response.getErrorMsg();
                }
                return response.getAppResponse();
            }else {
                throw new InterruptedException("call failed");
            }
        }catch (TimeoutException e){
            throw new RuntimeException("time out");
        }catch (ExecutionException e){
            throw new RuntimeException(e);
        }
    }

    public static void setFuture(Future<Object> future){
        futureThreadLocal.set(future);
    }
}
