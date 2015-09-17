package com.xutao.race.rpc.handler.server;

import com.google.common.collect.Maps;
import com.xutao.race.rpc.model.RpcRequest;
import com.xutao.race.rpc.model.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;

/**
 * Created by xtao on 15-9-17.
 */
public class RpcServiceHandler extends ChannelInboundHandlerAdapter {
    private ServiceEngine serviceEngine;
    private Map<String, Object> cachedResult;

    public RpcServiceHandler(ServiceEngine se){
        this.serviceEngine = se;
        cachedResult = Maps.newHashMap();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        RpcRequest request = (RpcRequest)msg;
        RpcResponse response = null;
        if(cachedResult.get(request.toString())!=null){
            response = (RpcResponse) cachedResult.get(request.toString());
        }else {
            response = serviceEngine.call(request);
            cachedResult.put(request.toString(),response);
        }
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
