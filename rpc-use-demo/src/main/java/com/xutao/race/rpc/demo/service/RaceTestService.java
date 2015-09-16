package com.xutao.race.rpc.demo.service;

import java.util.Map;

/**
 * Created by xtao on 15-9-16.
 */
public interface RaceTestService {
    Map<String, Object> getMap();
    String getString();
    RaceDO getDO();
    boolean longTimeMethod();
    Integer throwException() throws RaceException;
}
