package com.xutao.race.rpc.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by xtao on 15-9-18.
 */
public class RpcConnectHandler extends ChannelInboundHandlerAdapter {
    private RpcClientConnects rpcClientConnects;

    public RpcConnectHandler(RpcClientConnects rpcClientConnects) {
        this.rpcClientConnects = rpcClientConnects;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

    }
}
