package com.xutao.race.rpc.handler.client;

import com.xutao.race.rpc.async.CallRemoteFuture;
import com.xutao.race.rpc.model.CommonConstant;
import com.xutao.race.rpc.model.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by xtao on 15-9-18.
 */
@Component
@Qualifier("rpcClientConnects")
public class RpcClientConnects {
    private int port;
    private int minConnect;
    private int maxCOnnect;
    private String host;
    private Bootstrap b;
    private EventLoopGroup workerGroup;
    private ConcurrentLinkedQueue<Channel> connects;
    private RpcClientHandler clientHandler;
    @Autowired
    @Qualifier("serviceIP")
    private String serviceIP;

    private void init(){
        this.port = 8888;
        this.minConnect = 10;
        this.maxCOnnect = 600;
        this.host = "127.0.0.1";

    }

    public RpcClientConnects() {
        init();
        b = null;
        workerGroup = null;
        connects = new ConcurrentLinkedQueue<Channel>();
    }

    public void start() throws Exception{
        int coreCount = (Runtime.getRuntime().availableProcessors());
        workerGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new ObjectEncoder(),
                                new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                new RpcConnectHandler(RpcClientConnects.this)
                        );
                    }
                });

        for(int i=0; i< minConnect; i++){
            callRemoteUseNewChannel(null, null);
        }
    }

    @PreDestroy
    public void closeAll() {
        Exception cause = new Exception("auto closed");
        while (!connects.isEmpty()){
            removeChannel(connects.poll(), cause);
        }
    }

    private void removeChannel(Channel channel, Throwable cause){
        if(channel == null) {
            return;
        }
        CallRemoteFuture f = (CallRemoteFuture)channel.attr(CommonConstant.CHANNEL_FUTURE_ATTR_KEY).get();
        if(f != null){
            f.cancel(cause);
        }
        connects.remove(channel);
    }

    public Channel getConnect() {
        return connects.poll();
    }

    public void putChannel2Pool(Channel channel) {
        if(channel != null){
            connects.offer(channel);
        }
    }

    public CallRemoteFuture callRemoteUsePool(RpcRequest request) {
        CallRemoteFuture future = new CallRemoteFuture();
        Channel c = getConnect();
        while (c == null || !c.isActive()){
            try {
                if(callRemoteUseNewChannel(future, request) == true){
                    return future;
                }
                Thread.sleep(0);
                c = getConnect();
            } catch (InterruptedException e) {
                future.cancel(e);
            }
        }
        c.attr(CommonConstant.CHANNEL_FUTURE_ATTR_KEY).set(future);
        c.writeAndFlush(request).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        future.setUseChannel(c);
        return future;
    }

    public boolean callRemoteUseNewChannel(CallRemoteFuture callRemoteFuture, final RpcRequest rpcRequest) throws InterruptedException{
        final ChannelFuture f = b.connect(serviceIP, port);
        if(null != f){
            if(callRemoteFuture != null) {
                f.channel().attr(CommonConstant.CHANNEL_FUTURE_ATTR_KEY).set(callRemoteFuture);
                callRemoteFuture.setUseChannel(f.channel());
            }else {
                this.putChannel2Pool(f.channel());
            }
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        future.channel().closeFuture().addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) throws Exception {
                                removeChannel(future.channel(), future.cause());
                            }
                        });
                        if(rpcRequest != null){
                            future.channel().writeAndFlush(rpcRequest).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                        }
                    }else {
                        removeChannel(future.channel(),future.cause());
                    }
                }
            });
            return true;
        }
        return false;
    }


}
