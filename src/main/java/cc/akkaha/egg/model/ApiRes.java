package cc.akkaha.egg.model;

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
}