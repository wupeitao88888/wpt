package co.wpt.login.result;



/**
 * Created by shaohui on 2016/12/3.
 */

public class WeiboToken extends BaseToken {

    private String refreshToken;

    private String phoneNum;


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
