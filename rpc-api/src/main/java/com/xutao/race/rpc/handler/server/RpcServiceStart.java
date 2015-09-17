package com.xutao.race.rpc.handler.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * Created by xtao on 15-9-17.
 */
@Component
@Qualifier("rpcServiceStart")
public class RpcServiceStart {

    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap b;

    @Autowired
    @Qualifier("tcpSocketAddress")
    private InetSocketAddress tcpPort;

    private Channel serverChannel;

    public void start() throws Exception{
        serverChannel = b.bind(tcpPort).sync().channel().closeFuture().sync().channel();
    }
    @PreDestroy
    public void stop() {
        serverChannel.close();
    }
}
