package com.xutao.race.rpc.handler.client;

import com.xutao.race.rpc.async.ResponseCallbackListener;
import com.xutao.race.rpc.model.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.Callable;

/**
 * Created by xtao on 15-9-18.
 */
public class RpcClientStart implements Callable<Object>{

    private int port = 8888;
    private String host = "127.0.0.1";
    private RpcRequest rpcRequest;
    private Bootstrap b;
    private ResponseCallbackListener responseCallbackListener;
    private RpcClientHandler handler;
    private ChannelFuture channelFuture;
    private EventLoopGroup workerGroup;

    public RpcClientStart() {
        rpcRequest = null;
        b = null;
        responseCallbackListener = null;
        handler = null;
        channelFuture = null;
        workerGroup = null;
    }

    public RpcClientStart(String host,int port){
        rpcRequest = null;
        b = null;
        responseCallbackListener = null;
        handler = null;
        channelFuture = null;
        workerGroup = null;
        this.host = host;
        this.port = port;

    }

    public void start() throws Exception {
        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    handler);
                        }
                    });
            channelFuture = b.connect(host, port).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public Object call() throws Exception {
        Object obj = null;
        this.handler = new RpcClientHandler();
        this.handler.setRequest(rpcRequest);
        try {
            this.start();
            obj = this.handler.getResponse();
        }catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }
}
