package com.xutao.race.rpc.async;

import com.xutao.race.rpc.aop.ConsumerHook;
import com.xutao.race.rpc.model.RpcRequest;
import com.xutao.race.rpc.model.RpcResponse;
import io.netty.channel.Channel;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by xtao on 15-9-16.
 */
public class CallRemoteFuture implements Future {

    private final CountDownLatch latch = new CountDownLatch(1);
    private volatile boolean isDone = false;
    private volatile boolean isCancel = false;
    private Channel useChannel;
    private final AtomicBoolean isProcessed = new AtomicBoolean(false);
    private ConsumerHook consumerHook;
    private RpcRequest request;
    private RpcResponse response;



    public boolean cancel(Throwable cause) {
        if(isProcessed.getAndSet(true)){
            return false;
        }
        isCancel = true;
        if(consumerHook != null){
            consumerHook.after(request);
        }
        latch.countDown();
        return true;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return isCancel;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public RpcResponse get() throws InterruptedException, ExecutionException {
        latch.await();
        return response;
    }

    @Override
    public RpcResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if(!latch.await(timeout, unit)) {
            throw new TimeoutException();
        }
        return response;
    }

    public boolean done(){
        if(isProcessed.getAndSet(true)){
            return false;
        }
        isDone = true;
        if(consumerHook != null){
            consumerHook.after(request);
        }
        latch.countDown();
        return true;
    }

    public Channel getUseChannel() {
        return useChannel;
    }

    public void setUseChannel(Channel useChannel) {
        this.useChannel = useChannel;
    }

    public ConsumerHook getConsumerHook() {

        return consumerHook;
    }

    public void setConsumerHook(ConsumerHook consumerHook) {
        this.consumerHook = consumerHook;
    }

    public RpcRequest getRequest() {
        return request;
    }

    public void setRequest(RpcRequest request) {
        this.request = request;
    }

    public RpcResponse getResponse() {
        return response;
    }

    public void setResponse(RpcResponse response) {
        this.response = response;
    }
}
