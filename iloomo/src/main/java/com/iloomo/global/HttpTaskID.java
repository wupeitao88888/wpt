package com.iloomo.global;

import com.iloomo.global.AppConfig;

/**
 * Created by wupeitao on 2017/4/14.
 */

public class HttpTaskID {
    //注册
    public static final int REGISTER = 1000;
    //登陆
    public static final int LOGIN = 1001;
    //获取验证吗
    public static final int USER_CODE = 1002;
    //找回密码1
    public static final int USER_RESET_PASSWORD = 1003;
    //找回密码2
    public static final int USER_RESET_PASSWORD_TWO = 1004;
    //根据手机号获取密保问题
    public static final int GET_SECURITY_BY_USERNAME = 1005;
    //比较密保是否正确
    public static final int COMPARE_SECURITY = 1006;
    //更改手机号
    public static final int USER_CHANGE_MOBILE = 1007;
    //获取密保问题
    public static final int GET_SECURITY = 1008;
    //用户注册成功后保存密保
    public static final int SAVE_USER_SECURITY = 1009;
    //添加车辆信息
    public static final int USER_CART_SAVE = 1010;

    //发帖
    public static final int USER_POST = 1011;

    //话体列表
    public static final int TOPIC_LIST = 1012;
    //关注话题
    public static final int USER_FOLLOW = 1013;
    //社区主页
    public static final int USER_COMMUNITY = 1014;
    //添加评论
    public static final int ADD_COMMENT = 1015;
    //帖子点赞
    public static final int POST_DIANZAN = 1016;
    //评论点赞
    public static final int COMMENT_DIANZAN = 1017;

    //添加互动问题
    public static final int USER_ANSWER = 1018;
    //收货地址列表
    public static final int USER_ADDRESS_LIST = 1019;
    //获取城市列表
    public static final int GET_CITY_LIST = 1020;
    //添加收获地址
    public static final int ADD_USER_ADDRESS = 1021;
    //删除地址
    public static final int ADDRESS_DEL = 1022;
    //获取地址
    public static final int ADDRESS_SHOW = 1023;
    //修改地址
    public static final int UPDATE_ADDRESS = 1024;
    public static final int SETING_ADDRESS = 1025;
    //用户积分，运费,余额
    public static final int GET_USER_AMOUNT = 1026;
    public static final int APP_ORDER_SAVE = 1027;
    public static final int ADD_FAV = 1028;
    public static final int USER_LOGIN = 1029;

    //阿里支付
    public static final int ALI_PAY = 1030;
    //微信支付
    public static final int WX_PAY = 1031;
    public static final int USER_AMOUNT_PAY = 1032;
    public static final int USER_UPDATE_NAME_BIRTHDAY = 2021;
    public static final int USER_UPDATE_NAME_SEX = 2020;
    public static final int USER_UPDATE_NICKNAME = 2022;

    public static final int USER_REBACK = 1033;
    public static final int THIRD_LOGIN = 1034;
    public static final int USER_BIND_OPEN = 1035;
    public static final int GET_BRAND_LIST = 1036;
    public static final int GET_BRAND_SERISE_LIST = 1037;
    public static final int GET_SERISE_LIST = 1038;
    public static final int SELL_CAR_INFO = 1039;
    public static final int USER_CAR_INFO = 1040;
    public static final int CAR_ASSESS_SAVE = 1041;
    public static final int GET_BRAND_ONE = 1042;
    public static final int FIND_PROPORTION = 1043;
    public static final int BRAND_LIST = 1044;
    public static final int WX_PAY_EWCHARGE = 1045;
    public static final int ALI_PAY_EWCHARGE = 1046;
    public static final int GET_STORE_LIST = 1046;
    public static final int GET_CAR_LIST = 1047;
    public static final int SAO_MA_ORDER = 1048;
    public static final int SAO_MA_WX_PAY = 1049;
    public static final int SAO_MA_ALI_PAY = 1050;
    public static final int USER_CHECK_PAY_PWD = 1051;
    public static final int SAO_MA_AMOUNT = 1052;
    public static final int ORDER_USER_DETAIL = 1053;
    public static final int REMINDER = 1054;
    public static final int CONFIRM_STATUS = 1055;
    public static final int ORDER_PAY = 1056;
    public static final int RETURN_ORDER = 1057;
    public static final int ADD_GOODS_COMMENT = 1058;
    public static final int HISTORY_ORDER = 1059;
    public static final int MY_WASH_PROJECT_LIST = 1060;
    public static final int MY_WASH_PROJECT_DETAIL = 1061;
    public static final int USE_PROJECT = 1062;
    public static final int MY_WASH_PROJECT_REFUND = 1063;
    public static final int ADD_WASH_COMMENT = 1064;
    public static final int MY_ASSESS_LIST = 1065;
    public static final int ASSESS_CANCEL = 1066;
    public static final int USER_SHEN_QING_BAO_XIAN = 1067;
    public static final int REPORT_TOPIC_OR_POST = 1068;
    public static final int POST_DETAIL = 1069;
    public static final int ILLEGEAL_USER_CART_LIST = 1070;
    public static final int ADD_ILLEGAL_USER_CAR = 1071;
    public static final int ILLEGAL_USER_CART_LOG_BY_ILLEGAL_ID = 1072;
    public static final int DELETE_ILLEGAL_USER_CART = 1073;
    public static final int UPDATE_ILLEGAL_USER_CART = 1074;
    public static final int GET_HOT_TOPIC_LIST = 1075;
    public static final int SEARCH_POST = 1076;
    public static final int SEARCH_POST_2 = 2018;
    public static final int SEARCH_TOPIC = 1078;
    public static final int GOOD_KEYWORD_SEARCH_LIST = 1079;
    public static final int CHECK_USER_AUTH = 1080;
    public static final int RESCUE_LIST = 1081;
    public static final int RESCUE_COMMENT = 1082;
    public static final int UPDATE_RESCUE = 1083;
    public static final int SAVE_MAINTAIN_GOODS = 1084;
    public static final int MY_MAINTAIN_LIST = 1085;
    public static final int MY_MAINTAIN_DETAIL = 1086;
    public static final int UP_MAINTAIN_STATUS = 1087;

    public static final int WX_REFUND = 1089;//我的周边——微信申请退款
    public static final int ALI_REFUND = 1090;//我的周边——阿里申请退款
    public static final int AMOUNT_REFUND = 1091;//我的周边——阿里申请退款
    public static final int USER_UN_BIND_OPEN_BY_USER_ID = 1092;//我的周边——阿里申请退款


    public static final int MAIN_STORE_LIST_BY_MODELINFOID = 1099;


    //问答标签列表
    public static final int GET_ANSWER_TOPIC = 2001;
    //问答列表

    //会员积分信息
    public static final int USER_AMOUNT = 2003;

    public static final int FIND_ANSWER_LIST = 2002;
    //用户消费记录
    public static final int USER_RECORD_LIST = 2002;
    //用户发布的帖子
    public static final int MY_SEND_POST = 2003;
    //
    public static final int MY_COMMENT = 2004;
    public static final int MY_TOP_LIST = 2005;
    public static final int POST_TRACK = 2006;

    //我的社区
    public static final int MY_POST = 2007;
    //我的社区 提问
    public static final int MY_ANSWER_LIST = 2008;
    //我的社区 回答
    public static final int MY_REPLAY_LIST = 2009;
    //我的社区 关注
    public static final int USER_FOLLOW_LIST = 2010;
    //我的优惠券我的优惠券
    public static final int USER_COUPON = 2011;
    //我的优惠劵列表
    public static final int USER_COUPON_LIST = 2012;

    public static final int USER_COUPON_NOT_USE = 2020;

    //获取引导广告信息
    public static final int FIND_ADS = 2012;
    //商品列表
    public static final int GOODS_LIST = 2013;
    //分类列表
    public static final int GOOD_CLASS = 2014;
    //商品展示
    public static final int GOOD_SHOW = 2015;
    //活动列表
    public static final int ACT_GOODS_LIST = 2016;
    //活动列表
    public static final int USER_INFO = 2017;
    public static final int ZANTU_USER_CENTER_HOME = 2018;//我的界面

    //修改登陆密码
    public static final int USER_UPDATE_PASSWORD = 3001;
    //设置支付密码
    public static final int SET_USER_PAY_PASSWORD = 3002;
    //修改支付密码
    public static final int UPDATE_PAYMENT_PASSWORD = 3003;
    //用户通过手机号和身份证找回支付密码
    public static final int USER_PAY_PASSWROD_BY_MOBILE_AND_ID_CARD = 3004;
    //验证手机号
    public static final int CHECK_MOBILE_CODE = 3005;
    //获取密保问题
    public static final int GET_SECURITY_BY_USER_ID = 3006;
    //更换用户手机号
    public static final int USER_LOGIN_CHANGE_MOBILE = 3007;
    //会员权益信息
    public static final int USER_LEVEL_INTER = 3008;
    //我的周边页面
    public static final int MY_ORDER_LIST = 3009;
    //我的浏览记录
    public static final int BROWSE_LIST = 3010;
    //为我推荐
    public static final int RECOMMEND_LIST = 3011;
    //申请售后维权
    public static final int ADD_SERVICE_RIGHT = 3012;
    //我的收藏列表
    public static final int FAVORITES_LIST = 3013;
    //热门品牌
    public static final int HOT_BRAND_LIST = 3014;
    //退换货流程
    public static final int RETURN_GOODS_ORDER = 3015;
    //添加运单号
    public static final int ADD_TRANSTION = 3016;
    //违章记录查询
    public static final int VIOLATION_INQUIRY = 3017;
    //洗车列表
    public static final int WASH_STORE_LIST = 3018;
    //洗车店铺详情
    public static final int WASH_STORE_DETAIL = 3019;
    //洗车订单保存
    public static final int PROJECT_SAVE = 3020;
    //洗车订单支付宝支付
    public static final int ALI_PAY_WASH = 3021;
    //洗车订单微信支付
    public static final int WX_PAY_WASH = 3022;
    //洗车订单余额支付
    public static final int WASH_AMOUNT_PAY = 3023;
    //投诉
    public static final int COMPLAINT = 3024;
    //我的服务 我的保险
    public static final int MY_INSURANCE_LIST = 3025;
    //我的服务 保险订单结算信息
    public static final int INSURANCE_DETAIL_BY_ID = 3026;
    //救援服务项列表
    public static final int RESCUE_ITEM = 4001;
    //添加救援
    public static final int ADD_RESCUE = 4002;
    //取消关注话题
    public static final int CANCE_USER_FOLLOW = 4100;
    //我的服务 取消保单
    public static final int INSURANCE_CANCEL = 4101;
    //删除车辆信息
    public static final int DELETE_USER_CART_BY_ID = 4102;
    //修改车辆信息
    public static final int UPDATE_USER_CART_BY_ID = 4103;
    //实名认证
    public static final int USER_AUTH = 4104;
    //判断是否实名认证shou
    public static final int RY_CHECK_USER_AUTH = 4105;
    //身份证识别接口
    public static final int USER_ID_CARD_BY_IMAGE = 4106;
    //保养4s店与通用列表
    public static final int MAINTAIN_LIST = 4200;


    public static final int COMPARE_SECURITY_BY_ID = 4201;
    //首页
    public static final int ZANTU_INDEX = 5000;
    //首页帖子列表
    public static final int ZANTU_POST_INDEX = 5001;
    //  我的服务 保存保险订单
    public static final int INSURANCE_PRICE = 5002;
    //  我的服务 保险订单余额支付
    public static final int AMOUNT_PAY_INSURANCE = 5003;

    //  我的服务 保险订单支付宝支付
    public static final int ALI_PAY_INSURANCE = 5004;

    //  我的服务 保险订单微信支付
    public static final int WX_PAY_INSURANCE = 5005;
    //    查看适用全部店铺
    public static final int GET_SUITAVLE_LIST = 5006;
    public static final int CONVERSION_COUPON = 5007;//优惠劵兑换
    public static final int COUPON_LIST = 5008;//优惠券列表
    public static final int COUPONS_INADEQUATE = 5009;//券不够用提交
    //退换货流程  差额微信支付
    public static final int WX_PAY_DIFF_MONEY = 5100;
    //退换货流程  差额支付宝支付
    public static final int ALI_PAY_DIFF_MONEY = 5101;
    //退换货流程  差额 余额 支付
    public static final int DIFF_AMOUNT = 5102;
    //设置支付密码
    public static final int USER_PAY_PASSWORD_SAVE = 5200;


    //    public static final int WX_REFUND = 5103;
//    //支付宝退款
//    public static final int ALI_REFUND = 5104;
//    //余额退款
//    public static final int AMOUNT_REFUND = 5105;
//
    //消息通知
    public static final int PUSH_NOTICE_MESSAGE_INDEX = 5300;
    //消息中心列表页面
    public static final int PUSH_NOTICE_MESSAGE_LIST = 5301;
    //清空消息列表
    public static final int PUSH_NOTICE_MESSAGE_STATUS = 5302;
    //我的保养其他
    public static final int MY_MAINTAIN_LIST_OTHER = 5303;
    //积分商城
    public static final int INTEGRAL_MALL = 5304;
    public static final int USER_MEMBERSHIP_LIST = 5305;//权益
    public static final int USER_MEMBERSHIP_INFO = 5306;//权益详情


    public static final int CAR_MAINTAIN_COMMENT = 6001;
    //取消救援
    public static final int USERCANCERESCUESERVICE = 6002;
    //帖子列表
    public static final int POST_LIST = 6003;
    //帖子详情
    public static final int POST_DETAILS = 6004;
    //我的社区 我的评论   删除评论
    public static final int DELETE_USER_COMMENT = 6005;
    //保养商品详情
    public static final int MAINTAIN_GOODS_DETAIL = 6006;

    public static final int IS_READ = 6007;
    public static final int USERLEVELINTERESTS = 6008;
    public static final int USER_LICENSE_PLATE_NUMBER_LIST = 6009;
    public static final int USER_ADD_LICENSE_PLATE_NUMBER = 6010;
    public static final int INSURANCE_IMAGE = 6011;
    public static final int USER_MAINTAIN_INFO_LIST = 6011;
    public static final int USER_BIND_OPEN_BY_USER_ID = 6012;
    public static final int GET_STORE_PROVICE_CITY_LIST = 6013;
    public static final int USER_BIND_RECOMMEND_CODE = 6014;
    public static final int LOGOUT = 6015;
    public static final int CHECK_USER_CART = 6016;
    public static final int RESCUESERVICECOMMENT = 6017;
    public static final int USER_UPLOAD_AVATAR = 6018;
    public static final int DEL_USER_PLATE_NUMBER = 6019;
    public static final int UPDATE_READ_STARUS = 6020;
    public static final int FIND_PARKING_LIST = 6021;

    public static final int LICENSE_PLATE_GENERATION_ORDER = 6022;
    public static final int UPDATE_USER_PLATE_NUMBER_TYPE = 6023;
    public static final int PARKING_ORDER_SETTLEMENT = 6024;
    public static final int PARK_ALIPAY_PAY = 6025;
    public static final int PARK_WECHAT_PAY = 6026;
    public static final int PARK_AMOUNT_PAY = 6027;
    public static final int USER_UPLOAD_PIC = 6028;
    public static final int POST_SHARE = 6029;
    public static final int ADD_USER_POST_COLLECTION = 6030;
    public static final int CANCE_USER_POST_COLLECTION = 6031;
    public static final int GET_QIDONGPING = 6032;
    public static final int ACTIVE_H5_TO_APP_PAY = 6033;
    public static final int GET_SHARE_URL_CANTENT = 6034;
    public static final int USER_REPLIES = 6034;
    public static final int GET_APP_ICON = 6035;
    public static final int GET_APP_ICON_2 = 2100;
    public static final int GET_APP_ICON_3 = 2101;
    public static final int GET_APP_ICON_4 = 2102;
    public static final int SID_BY_INFO = 6036;
    public static final int USER_AMOUNT_IMG_LIST = 6037;
    public static final int GET_USER_CONTENT_GOODS = 6038;
    public static final int RY_EXCHANGE_AUTHORITY = 6039;

    public static final int USER_NEW_POST_LIST = 6129;
    public static final int WEEKLY_TITLE_LIST = 6130;
    public static final int GET_ACTIVE_FIXED = 6131;


    public static final int USER_WEEKLY_REMIND = 6131;

    public static final int WEEKLY_DETAILS = 6132;

    public static final int COMMUNITY_ACTIVITIES_LIST = 6133;


    public static final int ZANTUTONGJI = 6134;

    public static final int BAOXIAN_NOTICE = 6135;


    public static final int WEIZHANG_GOT = 6136;
    //保养详情
    public static final int BAOYANG_XQ = 6137;
    //保养再来一单
    public static final int ZAILAI_YIDAN = 6138;
    //用户消息提醒详情
    public static final int USER_NOTICE_INFO = 6139;
    //用户消息提醒设置
    public static final int USER_NOTICE_SETTING = 6140;
    //保养预约根据车型获取店铺
    public static final int GET_STORELIST_BY_MID = 6141;
    //保养预约类别时间列表
    public static final int GET_MAINTENANCE_BOOKING_BY_SID = 6142;
    //添加保养预约订单
    public static final int ADD_MAINTENANCE_BOOKING_ORDER = 6143;
    //保养订单验证预约时间的数量
    public static final int VERIFY_MAINTENANCE_BOOKING_NUMBER = 6144;
    //预约保养订单列表
    public static final int GET_APP_MAINTENANCE_BOOKING_LIST = 6145;
    //预约保养订单详情
    public static final int GET_APP_MAINTENANCE_BOOKING_INFO = 6146;
    //用户取消预约保养订单
    public static final int DEL_USER_APP_MAINTENANCE_BOOKING = 6147;
    //用户修改预约保养订单
    public static final int UPDATE_MAINTENANCE_BOOKING_ORDER = 6148;

    //登陆遇到问题获取验证码
    public static final int USER_CODE_Q = 6149;

    //获取图形验证码
    public static final int GET_IDENTIFYING_CODE = 6150;
    //绑定手机号验证码
    public static final int USER_CODE_B = 6151;


    //用户重置密码->身份证号验证
    public static final int CHECK_DECLARATION_CONFORMITY = 6150;

    //重置密码
    public static final int REGISTER_OR_RESET_PASSWORD_TWO = 6151;
    //个人中心设置默认车
    public static final int SET_VEHICLE_DEFAULTS = 6152;

    //救援服务统一接口
    public static final int RESCUE_NEW_UNIFORM_INTERFACE = 6153;
    //整合消息通知接口
    public static final int INTEGRATED_MESSAGE_NOTIFICATION = 6154;
    //停车楼详情
    public static final int GET_PARKING_INFO = 6155;
    //停车楼我的订单(未调试)
    public static final int USER_PARK_ORDER_LIST = 6156;
    //app首页整合接口
    public static final int ZANTU_INDEX_TWO = 6157;
    //会员中心首页
    public static final int USER_MEMBER_CENTER = 6158;
    //会员礼包列表
    public static final int USER_MEMBER_GIFT_BAG = 6159;
    //我的任务列表
    public static final int USER_TASKS_LIST = 6160;
    //领取任务奖励
    public static final int USER_RECEIVE_AWARD = 6161;
    //领取会员礼包优惠券
    public static final int USER_COUPONS_MEMBERSHIP = 6162;
    //会员等级成长值规则
    public static final int GROWTH_VALUE_RULE_LIST = 6163;
    //社区主页
    public static final int COMMUNITY_HOME_NEWS = 6164;
    //获取用户绑定店铺
    public static final int GET_USER_BIND_STORE = 6165;
    //评分弹框
    public static final int GET_RATINGS_BOUNCED = 6166;

}
