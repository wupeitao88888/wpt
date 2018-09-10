package co.ryit.bean;

import java.io.Serializable;

/**
 * Created by wupeitao on 16/8/14.
 */
public class BaseData implements Serializable {
    private String code_message;
    private String userid;

    public String getCode_message() {
        return code_message;
    }

    public void setCode_message(String code_message) {
        this.code_message = code_message;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
