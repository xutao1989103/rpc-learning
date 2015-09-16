package com.xutao.race.rpc.async;

/**
 * Created by xtao on 15-9-16.
 */
public interface ResponseCallbackListener {
    void onResponse(Object response);
    void onTimeout();
    void onException(Exception e);
}
