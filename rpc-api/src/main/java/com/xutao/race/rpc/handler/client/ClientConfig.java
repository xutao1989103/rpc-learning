package com.xutao.race.rpc.handler.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Created by xtao on 15-9-18.
 */
@Configuration
@ComponentScan("com.xutao.race.rpc.handler.client")
public class ClientConfig {
    @Bean(name = "serviceIP")
    public String serviceIP() {
        String brokerIp = System.getProperty("SIP");
        if(StringUtils.isEmpty(brokerIp)){
            return "127.0.0.1";
        }else {
            return brokerIp;
        }
    }
}
