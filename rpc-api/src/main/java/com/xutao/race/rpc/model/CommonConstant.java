package com.xutao.race.rpc.model;

import io.netty.util.AttributeKey;

/**
 * Created by xtao on 15-9-16.
 */
public class CommonConstant {
    public static AttributeKey<Object> CHANNEL_FUTURE_ATTR_KEY = AttributeKey.valueOf("CallRemoteFuture");
}
