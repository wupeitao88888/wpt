package com.iloomo.alipay;

import com.iloomo.bean.BaseModel;

/**
 * 支付宝实体类
 *
 * @author Lvfl
 *         created at 2016/10/13 15:55
 */
public class AlipayBean extends BaseModel {

    private String body;
    private String total_fee;
    private String ali_notify_url;
    private String PUBLIC;
    private String PRIVATE;
    private String seller_id;
    private String partner;
    private String out_trade_no;
    private String subject;

    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getAli_notify_url() {
        return ali_notify_url;
    }

    public void setAli_notify_url(String ali_notify_url) {
        this.ali_notify_url = ali_notify_url;
    }

    public String getPUBLIC() {
        return PUBLIC;
    }

    public void setPUBLIC(String PUBLIC) {
        this.PUBLIC = PUBLIC;
    }

    public String getPRIVATE() {
        return PRIVATE;
    }

    public void setPRIVATE(String PRIVATE) {
        this.PRIVATE = PRIVATE;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
