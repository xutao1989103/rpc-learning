package com.xutao.race.rpc.model;

import java.io.Serializable;

/**
 * Created by xtao on 15-9-16.
 */
public class RpcResponse implements Serializable {

    private static final long serialVersionUID = 8717323226728102092L;

    private String errorMsg;

    private Object appResponse;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getAppResponse() {
        return appResponse;
    }

    public void setAppResponse(Object appResponse) {
        this.appResponse = appResponse;
    }
    public boolean isError(){
        return errorMsg == null ? false:true;
    }
}
