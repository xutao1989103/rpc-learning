package com.xutao.race.rpc.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by xtao on 15-9-16.
 */
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 1723781839600505300L;
    public String version;
    public Class interfaces;
    public String methodName;
    public Class[] params;
    public Object[] args;

    public Map<String,Object> rpcContext;
}
