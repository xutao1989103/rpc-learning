package com.xutao.race.rpc.demo.builder;

import com.xutao.race.rpc.api.RpcConsumer;
import com.xutao.race.rpc.async.ResponseFuture;
import com.xutao.race.rpc.context.RpcContext;
import com.xutao.race.rpc.demo.service.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by xtao on 15-9-16.
 */
public class ConsumerBuilder {
    private static RpcConsumer consumer;
    private static RaceTestService apiService;

    static {
        try {
            consumer = (RpcConsumer) getConsumerImplClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(consumer == null){
            System.out.println("Start rpc consumer failed");
            System.exit(1);
        }
        apiService = (RaceTestService) consumer
                .interfaceClass(RaceTestService.class)
                .version("1.0.0.api")
                .clientTimeout(3000)
                .hook(new RaceConsumerHook()).instance();

    }

    public boolean pressureTest() {
        try {
            RaceDO result = apiService.getDO();
            if (result == null){
                System.out.println("failed");

                return false;
            }

//            System.out.println(result.equals(new RaceDO()));
        } catch (Throwable t) {
            return false;
        }
        return true;
    }

    @Test
    public void testNormalApiCall() {
        Assert.assertNotNull(apiService.getMap());
        Assert.assertEquals("this is a rpc framework", apiService.getString());
        Assert.assertEquals(new RaceDO(), apiService.getDO());
    }

    @Test
    public void testNormalSpringCall() {
        Assert.assertNotNull(apiService.getMap());
        Assert.assertEquals("this is a rpc framework", apiService.getString());
        Assert.assertEquals(new RaceDO(), apiService.getDO());
    }

    /**
     * test timeout property,at init this service,we set the client timeout 3000ms
     * so we should break up before Provider finish running(Provider will sleep 5000ms beyond clientTimeout)
     */
    @Test
    public void testTimeoutCall() {
        long beginTime = System.currentTimeMillis();
        try {
            boolean result = apiService.longTimeMethod();
        } catch (Exception e) {
            long period = System.currentTimeMillis() - beginTime;
            Assert.assertTrue(period < 3100);
        }
    }

    /**
     * when provider throws an exception when process the request,
     * the rpc framework must pass the exception to the consumer
     */
    @Test
    public void testCatchException() {
        try {
            Integer result = apiService.throwException();
        } catch (RaceException e) {
            Assert.assertEquals("race", e.getFlag());
//            e.printStackTrace();
        }
    }


    @Test
    public void testFutureCall() {
        consumer.asynCall("getString");
        String nullValue = apiService.getString();
        Assert.assertEquals(null, nullValue);

        try {
            String result = (String) ResponseFuture.getResponse(3000);
            Assert.assertEquals("this is a rpc framework", result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.cancelAsyn("getString");
        }
    }

    /**
     * set the method {@code getDO} of {@link com.xutao.race.rpc.demo.service.RaceTestService} asynchronous
     * and pass a listener to framework,when response is back
     * we want our listener be called by framework
     */
    @Test
    public void testCallback() {
        RaceServiceListener listener = new RaceServiceListener();
        consumer.asynCall("getDO", listener);
        RaceDO nullDo = apiService.getDO();
        Assert.assertEquals(null, nullDo);
        try {
            RaceDO resultDo = (RaceDO) listener.getResponse();
        } catch (InterruptedException e) {
        }finally {
            consumer.cancelAsyn("getDO");
        }
//        RaceServiceListener listener = new RaceServiceListener();
//        consumer.asynCall("throwException", listener);
//        Integer nullDo = apiService.throwException();
//        Assert.assertEquals(null, nullDo);
//        try {
//            Integer resultDo = (Integer) listener.getResponse();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }finally {
//            consumer.cancelAsyn("throwException");
//        }
    }

    /**
     * use {@link com.xutao.race.rpc.context.RpcContext} to pass a key-value structure to Provider
     * {@function getMap()} will pass this context to Consumer
     */
    @Test
    public void testRpcContext() {
        RpcContext.addProp("context", "please pass me!");
        Map<String, Object> resultMap = apiService.getMap();
        Assert.assertTrue(resultMap.containsKey("context"));
        Assert.assertTrue(resultMap.containsValue("please pass me!"));
    }

    /**
     * I can run a hook before I try to call a remote service.
     */
    @Test
    public void testConsumerHook() {
        Map<String, Object> resultMap = apiService.getMap();
        Assert.assertTrue(resultMap.containsKey("hook key"));
        Assert.assertTrue(resultMap.containsValue("this is pass by hook"));
    }

    private static Class<?> getConsumerImplClass(){
        try {
            return Class.forName("com.xutao.race.rpc.api.impl.RpcConsumerImpl");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot found the class which must exist and override all RpcProvider's methods");
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
