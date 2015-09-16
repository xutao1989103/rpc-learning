package com.xutao.race.rpc.model;

import java.io.Serializable;

/**
 * Created by xtao on 15-9-16.
 */
public class Method implements Serializable {
    private static final long serialVersionUID = -7639460433537068717L;

    private String methodName;
    private Class[] params;
    public Method(String name, Class<?>[] paramterTypes){
        this.methodName = name;
        this.params = paramterTypes;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParams() {
        return params;
    }

    public void setParams(Class[] params) {
        this.params = params;
    }
}
