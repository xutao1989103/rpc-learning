package com.xutao.race.rpc.demo.service;

/**
 * Created by xtao on 15-9-16.
 */
public class RaceException extends RuntimeException {
    private String flag = "race";
    public RaceException(String message){
        super(message);
    }
    public RaceException(Exception e){
        super(e);
    }
    public String getFlag() {
        return flag;
    }
}
