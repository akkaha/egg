package cc.akkaha.egg.model;

import java.util.HashMap;
import java.util.Map;

public class ApiRes {

    private String code;
    private String msg;
    private Object data;

    public ApiRes() {
        this.code = ApiCode.OK;
        this.msg = ApiMsg.OK;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ApiRes okPageListData(Object data, Integer total) {
        ApiRes res = new ApiRes();
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", data);
        dataMap.put("total", total);
        res.setData(dataMap);
        return res;
    }
}