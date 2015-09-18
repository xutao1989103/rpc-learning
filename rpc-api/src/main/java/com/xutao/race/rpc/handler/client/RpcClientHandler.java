package com.xutao.race.rpc.handler.client;

import com.xutao.race.rpc.model.RpcRequest;
import com.xutao.race.rpc.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by xtao on 15-9-18.
 */
public class RpcClientHandler extends ChannelInboundHandlerAdapter {
    private RpcRequest request;
    private RpcResponse response;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        response = (RpcResponse) msg;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
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
