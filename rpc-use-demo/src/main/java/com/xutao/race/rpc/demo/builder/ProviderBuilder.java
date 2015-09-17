package com.xutao.race.rpc.demo.builder;

import com.xutao.race.rpc.api.RpcProvider;
import com.xutao.race.rpc.demo.service.RaceTestService;
import com.xutao.race.rpc.demo.service.RaceTestServiceImpl;
import com.xutao.race.rpc.handler.server.ServiceConfig;
import com.xutao.race.rpc.handler.server.ServiceEngine;
import com.xutao.race.rpc.model.RpcRequest;
import com.xutao.race.rpc.model.RpcResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by xtao on 15-9-16.
 */
public class ProviderBuilder {

    public static void buildProvider(){
        publish();
        callTest();
    }

    private static void callTest(){
        ApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);
        ServiceEngine serviceEngine = (ServiceEngine)context.getBean("serviceEngine");
        RpcRequest request = new RpcRequest();
        request.interfaces = RaceTestService.class;
        request.version = "1.0.0.api";
        request.methodName = "getString";
        request.params = new Class[0];
        request.args = new Object[0];
        RpcResponse response = serviceEngine .call(request);
        System.out.println(response.getAppResponse());
        System.out.println(response.getErrorMsg());
    }

    private static void publish() {
        RpcProvider rpcProvider = null;
        try {
            rpcProvider = (RpcProvider)getProviderImplClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(rpcProvider == null){
            System.out.println("start rpc provider failed");
            System.exit(1);
        }

        rpcProvider.serviceInterface(RaceTestService.class)
                .impl(new RaceTestServiceImpl())
                .version("1.0.0.api")
                .timeout(3000)
                .serializeType("java")
                .publish();

//        try {
//            Thread.sleep(Integer.MAX_VALUE);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private static Class<?> getProviderImplClass(){
        try {
            return Class.forName("com.xutao.race.rpc.api.impl.RpcProviderImpl");
        } catch (ClassNotFoundException e) {
            System.out.println("cannot find the rpc impl class");
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
