package com.xutao.race.rpc.demo.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xtao on 15-9-16.
 */
public class RaceDO implements Serializable {
    private static final long serialVersionUID = -1422176180715898631L;
    private int num;
    private String str;
    private List<String> list;
    private RaceChildrenDO child;

    public RaceDO() {
        num = 3;
        str = "rpc";

        list = new ArrayList<String>();
        list.add("rpc-list");

        child = new RaceChildrenDO();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RaceDO other = (RaceDO) obj;

        if(num != other.getNum())
            return false;

        if (str == null) {
            if (other.getStr()!= null)
                return false;
        } else{
            if(other.getStr() == null)
                return false;
            else if (!str.equals(other.getStr()))
                return false;
        }

        if (list == null) {
            if (other.getList() != null)
                return false;
        } else{
            if(other.getList() == null)
                return false;
            else if (!list.equals(other.getList()))
                return false;
        }

        if (child == null) {
            if (other.getChild() != null)
                return false;
        } else {
            if(other.getChild() == null)
                return false;
            else if(!child.equals(other.getChild()))
                return false;
        }

        return true;
    }

    public int getNum() {
        return num;
    }

    public String getStr() {
        return str;
    }

    public List<String> getList() {
        return list;
    }

    public RaceChildrenDO getChild() {
        return child;
    }
    public static void main(String[] args){
        RaceDO raceDO = new RaceDO();
        System.out.println(raceDO.equals(new RaceDO()));
    }
}
