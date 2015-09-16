package com.xutao.race.rpc.model;

import java.io.Serializable;

/**
 * Created by xtao on 15-9-16.
 */
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 8717323226728102092L;

    public String errorMsg;

    public Object appResponse;
}
