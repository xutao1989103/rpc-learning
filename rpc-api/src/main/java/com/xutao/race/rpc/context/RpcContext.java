package com.xutao.race.rpc.context;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * Created by xtao on 15-9-16.
 */
public class RpcContext {
    public static Map<String,Object> props = Maps.newHashMap();

    public static void addProp(String key, Object value){
        props.put(key,value);
    }

    public static Object getProp(String key) {
        return props.get(key);
    }

    public static Map<String,Object> getProps() {
        return Collections.unmodifiableMap(props);
    }
}
