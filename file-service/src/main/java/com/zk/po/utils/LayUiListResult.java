package com.zk.po.utils;

import java.util.ArrayList;
import java.util.List;

public class LayUiListResult<T> {
    private Integer code;
    private String msg;
    private Integer count;
    private Integer page,limit;

    public LayUiListResult() {
    }

    public LayUiListResult(Integer code, String msg, Integer count, Integer page, Integer limit) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.page = page;
        this.limit = limit;
    }

    private List<T> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        int pageNo=(page-1)*limit;   //每页的起始索引

        int pageSize=10; // 每页记录数

        List<T> list=new ArrayList<T>();

        Integer sum = data.size(); //记录总数

        if (pageNo+pageSize > sum) {
            list = data.subList(pageNo,sum );
        }else {
            list = data.subList(pageNo,pageNo+pageSize);
        }
        this.data = list;
    }
}
