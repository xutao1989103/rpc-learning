package com.xutao.race.rpc.handler.server;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.xutao.race.rpc.context.RpcContext;
import com.xutao.race.rpc.model.RpcRequest;
import com.xutao.race.rpc.model.RpcResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by xtao on 15-9-16.
 */

@Component
@Qualifier("serviceEngine")
public class ServiceEngine {
    private Table<String, String, Object> table = HashBasedTable.create();

    public RpcResponse call(RpcRequest request) {
        RpcResponse response = new RpcResponse();
        Map<String,Object> rpcContext = request.rpcContext;
        if(rpcContext != null){
            for (Map.Entry<String,Object> entry:rpcContext.entrySet()){
                RpcContext.addProp(entry.getKey(), entry.getValue());
            }
        }
        try {
            Object obj = getObjFromTable(request);
            Method method = obj.getClass().getDeclaredMethod(request.methodName,request.params);
            Object result = method.invoke(obj,request.args);
            response.setAppResponse(result);
        } catch (Exception e) {
            response.setErrorMsg(e.getMessage());
            return response;
        }
        return response;
    }

    private Object getObjFromTable(RpcRequest request){
        Object obj = table.get(request.interfaces.getName(),request.version);
        if(obj != null){
            return obj;
        }else {
            throw new RuntimeException("cannot find impl of "+request.interfaces.getName() + " " + request.version);
        }
    }

    public void publish(Class interfaceDefiner, String version, Object impl){
        try {
            validate(interfaceDefiner, version, impl);
            table.put(interfaceDefiner.getName(),version,impl);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void validate(Class interfaceDefiner, String version, Object impl){
        if(interfaceDefiner == null)
            throw new RuntimeException("interface cannot null");
        if(StringUtils.isEmpty(version))
            throw new RuntimeException("version cannot empty");
        if(impl == null)
            throw new RuntimeException("impl cannot null");
        return;
    }

}
