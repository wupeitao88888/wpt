package com.iloomo.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.iloomo.global.HttpTaskID;
import com.iloomo.model.OnAdapterToastListener;


/**
 * Created by wupeitao on 2017/7/21.
 */

public class CheckInputTypeUtils {


    private Context context;

    public CheckInputTypeUtils(Context context) {
        this.context = context;
    }


    public static CheckInputTypeUtils ryDialogUtils;

    public static CheckInputTypeUtils getInstaces(Context context) {
        if (ryDialogUtils == null) {
            ryDialogUtils = new CheckInputTypeUtils(context);
        }
        return ryDialogUtils;
    }


    public static final int PHONE = 1001;//验证手机
    public static final int PASSWORD = 1002;//验证密码
    public static final int AUTH_CODE = 1003;//验证短信验证码
    public static final int USER_REGISTER = 3001;//验证是否同意用户协议
    public static final int ADDRESS = 1004;//验证地址是否存在
    public static final int DRIVING_LICENSE_POSITIVE = 1005;//验证行驶证正面
    public static final int RESON = 1006;//未选择投诉原因点击提交
    public static final int QUSTION_INFO = 1007;//未选择投诉原因点击提交
    public static final int QUSTION_NONULL_TITLE = 6001;//问题title不能为空
    public static final int QUSTION_NONULL_INFO = 6002;//问题描述不能为空
    public static final int TOPICID_NONULL = 6003;//话题id不能为空
    public static final int COMMIT_CONTENT_NONULL = 6004;//评论内容不能为空
    public static final int WULIUBIANHAO_NONULL = 6005;//点“提交”时未填写物流编号
    public static final int ORDER_ID_NONULL = 6006;//点“提交”时未填写订单编号
    public static final int DRIVING_LICENSE_NEGATIVE = 7000;//验证行驶证反面

    /****
     * 验证输入框里的内容是否合法
     *
     * @param type
     * @param inputString
     */
    public boolean setCheckInput(int type, String inputString, OnAdapterToastListener onAdapterToastListener) {


        switch (type) {
            case ORDER_ID_NONULL:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请填写完整的订单编号");
                    return true;
                }
                return false;
            case WULIUBIANHAO_NONULL:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请填写完整的物流编号");
                    return true;
                }
                return false;
            case COMMIT_CONTENT_NONULL:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("内容不能为空");
                    return true;
                }
                return false;
            case TOPICID_NONULL:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请选择话题标签");
                    return true;
                }
                return false;
            case QUSTION_NONULL_INFO:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("内容不能为空");
                    return true;
                }
                return false;
            case QUSTION_NONULL_TITLE:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("标题不能为空");
                    return true;
                }
                return false;
            case PHONE:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请输入手机号码!");
                    return true;
                }
                if (!FormatCheckUtils.isPhoneLegal(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("手机号码有误 请重新输入!");
                    return true;
                }
                return false;
            case PASSWORD:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请输入密码");
                    return true;
                }

                if (!FormatCheckUtils.isPw(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("登录密码只包含数字、字母，不区分大小写，6-16位字符");
                    return true;
                }
                return false;
            case AUTH_CODE:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请输入验证码");
                    return true;
                }
                if (!FormatCheckUtils.isAuthCode(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("验证码有误");
                    return true;
                }
                return false;
            case ADDRESS:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请选择服务店铺");
                    return true;
                }
                return false;
            case DRIVING_LICENSE_POSITIVE:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请上传被投保人行驶证照片正副本");
                    return true;
                }
                return false;
            case DRIVING_LICENSE_NEGATIVE:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请上传被投保人行驶证照片反副本");
                    return true;
                }
                return false;

            case RESON:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请选择投诉原因");
                    return true;
                }
                return false;
            case QUSTION_INFO:
                if (TextUtils.isEmpty(inputString)) {
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请填写问题描述");
                    return true;
                }
                return false;

        }
        return true;
    }




    /***
     * 验证是否勾选
     *
     * @param type    类型
     * @param checked 是否选中
     * @return
     */

    public boolean isChecked(int type, boolean checked,OnAdapterToastListener onAdapterToastListener) {
        if (!checked) {
            switch (type) {
                case USER_REGISTER:
                    if (onAdapterToastListener != null)
                        onAdapterToastListener.onFiled("请同意赞途用户协议");
                    return true;
            }
        } else {
            return false;
        }
        return true;
    }


    /***
     * 验证密保问题
     *
     * @param question_one   答案一
     * @param question_two   答案二
     * @param question_three 答案三
     * @return
     */
    public boolean setecurityQuestion(String question_one, String question_two, String question_three,OnAdapterToastListener onAdapterToastListener) {
        if (TextUtils.isEmpty(question_one) || TextUtils.isEmpty(question_two) || TextUtils.isEmpty(question_three)) {
            if (onAdapterToastListener != null)
                onAdapterToastListener.onFiled("请填写密保问题!");
            return true;
        }
        if (checkQuestionLength(question_one,onAdapterToastListener)) {
            return true;
        }
        if (checkQuestionLength(question_two,onAdapterToastListener)) {
            return true;
        }
        if (checkQuestionLength(question_three,onAdapterToastListener)) {
            return true;
        }
        return false;
    }

    /****
     * 验证答案字符串长度
     *
     * @param question 答案
     * @return
     */
    private boolean checkQuestionLength(String question,OnAdapterToastListener onAdapterToastListener) {
        if (question.length() <= 0 || question.length() >= 10) {
            if (onAdapterToastListener != null)
                onAdapterToastListener.onFiled("答案字数不超过10个字");
            return true;
        }
        return false;
    }





    /****
     * 检测邮箱是否正确
     *
     * @param email
     * @return
     */
    public boolean checkEmail(String email,OnAdapterToastListener onAdapterToastListener) {
        if (!TextUtils.isEmpty(email)) {
            if (!FormatCheckUtils.isEmail(email)) {
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onFiled("邮箱格式不正确");
                return true;
            }
        }
        return false;
    }






    public static final int SHOP_CAR_NOGOODS = 50001;//是否选择商品
    public static final int SHOP_CAR_DElETE = 50002;//删除成功
    public static final int SHOP_CAR_DElETE_FILD = 50003;//删除失败

    /****
     * 根据code 显示对应的提示
     *
     * @param code
     */
    public void showTextInfo(int code,OnAdapterToastListener onAdapterToastListener) {
        switch (code) {
            case SHOP_CAR_NOGOODS:
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onFiled("请选择商品");
                break;
            case SHOP_CAR_DElETE:
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onFiled("删除成功！");
                break;
            case SHOP_CAR_DElETE_FILD:
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onFiled("删除失败！");
                break;
        }
    }

    /****
     * 检验地址信息是否正确
     *
     * @param username
     * @param moblie
     * @param bg
     * @param address
     * @return
     */
    public boolean checkAddress(String username,
                                String moblie,
                                Bundle bg,
                                String address,
                                OnAdapterToastListener onAdapterToastListener
    ) {

        if (bg == null) {
            if (onAdapterToastListener != null)
                onAdapterToastListener.onFiled("请输入完整的收货信息");
            return true;
        }
        if (TextUtils.isEmpty(username) ||
                TextUtils.isEmpty(moblie) ||
                TextUtils.isEmpty(bg.getString("provinceCode", "")) ||
                TextUtils.isEmpty(bg.getString("cityCode", "")) ||
                TextUtils.isEmpty(bg.getString("districtCode", "")) ||
                TextUtils.isEmpty(address)) {
            if (onAdapterToastListener != null)
                onAdapterToastListener.onFiled("请输入完整的收货信息");
            return true;
        }

        if (!FormatCheckUtils.isPhoneLegal(moblie)) {
            if (onAdapterToastListener != null)
                onAdapterToastListener.onFiled("手机号码有误 请重新输入!");
            return true;
        }

        return false;

    }




}
