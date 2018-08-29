package com.iloomo.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iloomo.model.OnTextChangeListener;
import com.iloomo.paysdk.R;
import com.iloomo.utils.FormatCheckUtils;
import com.iloomo.utils.L;


/**
 * Created by wupeitao on 2017/8/4.
 */

public class ClearEditText extends RelativeLayout {


    private EditText re_edit;
    private RelativeLayout password_icon;
    private RelativeLayout rl_password_hidden;
    private ImageView iv_password_hidden;
    private RelativeLayout input_icon_re;
    private ImageView input_icon;

    private boolean isHidden = true;
    private int maxlength = 500;

    private boolean delete_icon = false;

    public ClearEditText(Context context) {
        super(context);
        init(context);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_clear_editview, null);
        re_edit = (EditText) inflate.findViewById(R.id.re_edit);

        password_icon = (RelativeLayout) inflate.findViewById(R.id.password_icon);


        rl_password_hidden = (RelativeLayout) inflate.findViewById(R.id.rl_password_hidden);
        iv_password_hidden = (ImageView) inflate.findViewById(R.id.iv_password_hidden);

        input_icon_re = (RelativeLayout) inflate.findViewById(R.id.input_icon_re);
        input_icon = (ImageView) inflate.findViewById(R.id.input_icon);

        re_edit.addTextChangedListener(mTextWatcher);

        rl_password_hidden.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    //设置EditText文本为可见的
                    re_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_password_hidden.setImageResource(R.drawable.show);

                } else {
                    //设置EditText文本为隐藏的
                    re_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_password_hidden.setImageResource(R.drawable.blank);
                }
                isHidden = !isHidden;
            }
        });
        password_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteListenr != null)
                    onDeleteListenr.onDelete();
                re_edit.setText("");
            }
        });
        re_edit.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
                if (re_edit.getText().length() > 0)
                    password_icon.setVisibility(View.VISIBLE);
                else {
                    password_icon.setVisibility(View.GONE);
                }
//                } else {
//                    if (re_edit.getText().length() > 0)
//                        password_icon.setVisibility(View.VISIBLE);
//                    else {
//                        password_icon.setVisibility(View.GONE);
//                    }
//                }
            }
        });

        addView(inflate);
    }

    private boolean isAdd;
    private int selection = 0;
    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        private int editStart;
        private int editEnd;
        // 特殊下标位置
        private static final int PHONE_INDEX_1 = 1;
        private static final int PHONE_INDEX_2 = 2;
        private static final int PHONE_INDEX_3 = 3;
        private static final int PHONE_INDEX_4 = 4;
        private static final int PHONE_INDEX_6 = 6;
        private static final int PHONE_INDEX_7 = 7;
        private static final int PHONE_INDEX_8 = 8;
        private static final int PHONE_INDEX_9 = 9;
        private static final int PHONE_INDEX_14 = 14;
        private static final int PHONE_INDEX_15 = 15;

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!delete_icon) {
                if (s.length() > 0) {
                    password_icon.setVisibility(View.VISIBLE);
                } else {
                    password_icon.setVisibility(View.GONE);
                }
            }

            if (type == InputType.TYPE_CLASS_NUMBER && phone) {
                //手机号输入格式
//                if (s == null || s.length() == 0)
//                    return;
//                StringBuffer stringBuffer = new StringBuffer();
//                String replace = s.toString().replace(" ", "");
//                selection=re_edit.getSelectionStart();
//                if (replace.length() > 3) {
//                    stringBuffer.append(replace.substring(0, 3));
//                    stringBuffer.append(" ");
//                    if (replace.length() > 7) {
//                        stringBuffer.append(replace.substring(3, 7));
//                        stringBuffer.append(" ");
//                        stringBuffer.append(replace.substring(7, replace.length()));
//                    } else {
//                        stringBuffer.append(replace.substring(3, replace.length()));
//                    }
//                } else {
//                    stringBuffer.append(replace);
//                }
//                if (!stringBuffer.toString().equals(s.toString())) {
//                    re_edit.setText(stringBuffer.toString());
//                    re_edit.setSelection(stringBuffer.length());
//                }

                if (s == null || s.length() == 0) {
                    return;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != PHONE_INDEX_3 && i != PHONE_INDEX_8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == PHONE_INDEX_4 || sb.length() == PHONE_INDEX_9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }

                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    try {
                        if (sb.charAt(start) == ' ') {
                            if (before == 0) {
                                index++;
                            } else {
                                index--;
                            }
                        } else {
                            if (before == 1) {
                                index--;
                            }
                        }
                    } catch (Exception e) {

                    }


                    re_edit.setText(sb.toString());
                    if (sb.toString().length() == maxlength) {
                        try {
                            re_edit.setSelection(maxlength);
                        } catch (Exception e) {
                            re_edit.setSelection(sb.toString().length());
                        }
                    } else {
                        try {
                            re_edit.setSelection(index);
                        } catch (Exception e) {
                            re_edit.setSelection(sb.toString().length());
                        }
                    }
                }
            } else if (type == InputType.TYPE_CLASS_NUMBER && idCard) {
                //身份证格式
//                if (s == null || s.length() == 0)
//                    return;
//                StringBuffer stringBuffer = new StringBuffer();
//                String replace = s.toString().replace(" ", "");
//
//                if (replace.length() > 6) {
//                    stringBuffer.append(replace.substring(0, 6));
//                    stringBuffer.append(" ");
//                    if (replace.length() > 14) {
//                        stringBuffer.append(replace.substring(6, 14));
//                        stringBuffer.append(" ");
//                        stringBuffer.append(replace.substring(14, replace.length()));
//                    } else {
//                        stringBuffer.append(replace.substring(6, replace.length()));
//                    }
//                } else {
//                    stringBuffer.append(replace);
//                }
//                if (!stringBuffer.toString().equals(s.toString())) {
//                    re_edit.setText(stringBuffer.toString());
//                    re_edit.setSelection(stringBuffer.length());
//                }


                if (s == null || s.length() == 0) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != PHONE_INDEX_6 && i != PHONE_INDEX_14 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == PHONE_INDEX_7 || sb.length() == PHONE_INDEX_15) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    try {
                        if (sb.charAt(start) == ' ') {
                            if (before == 0) {
                                index++;
                            } else {
                                index--;
                            }
                        } else {
                            if (before == 1) {
                                index--;
                            }
                        }
                    } catch (Exception e) {

                    }
                    re_edit.setText(sb.toString());
                    if (sb.toString().length() == maxlength) {
                        try {
                            re_edit.setSelection(maxlength);
                        } catch (Exception e) {
                            re_edit.setSelection(sb.toString().length());
                        }
                    } else {
                        try {
                            re_edit.setSelection(index);
                        } catch (Exception e) {
                            re_edit.setSelection(sb.toString().length());
                        }
                    }
                }


            } else if (InputType.TYPE_CLASS_TEXT == type && carnumber) {
                //车牌号格式

                if (s == null || s.length() == 0) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != PHONE_INDEX_1 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if (sb.length() == PHONE_INDEX_2 && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }

                    re_edit.setText(sb.toString());
                    re_edit.setSelection(index);
                }


            }

            /***
             * 验证自动响应
             */
            if (s.length() == 6 && isListener) {
                if (onTextChangeListener != null) {
                    onTextChangeListener.onChange(s.toString());
                }
            }


//            lcFeedbackResidueIdea.setText(Integer.valueOf(this.temp.length()) + "/" + maxlength);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.temp = s;
            if (after == 1) {//增加
                isAdd = true;
            } else {
                isAdd = false;
            }
        }

        public void afterTextChanged(Editable s) {


        }
    };

    public void setHinit(String hinit) {
        re_edit.setHint(hinit);
    }

    public void setHintColor(int color) {
        re_edit.setHintTextColor(color);
    }


    public void setText(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        re_edit.setText(content);
    }

    private int type;

    private boolean phone = false;
    private boolean idCard = false;
    private boolean carnumber = false;

    /***
     * 设置输入类型 --身份证
     *
     * @param type
     */
    public void setCarNumberType(int type, boolean carnumber) {
        this.carnumber = carnumber;
        if (carnumber) {
            this.type = type;
        }
        re_edit.setInputType(type);
    }

    /***
     * 设置输入类型 --身份证
     *
     * @param type
     */
    public void setInputIdCardType(int type, boolean idCard) {
        this.idCard = idCard;
        if (idCard) {
            this.type = type;
        }
        re_edit.setInputType(type);
    }

    /***
     * 设置输入类型  --手机号
     *
     * @param type
     */
    public void setInputType(int type, boolean phone) {
        this.phone = phone;
        if (phone) {
            this.type = type;
        }
        re_edit.setInputType(type);
    }

    public void setInputType(int type) {
        re_edit.setInputType(type);
    }

    /***
     * 隐藏后边的眼
     *
     * @param visibility
     */
    public void setLookVisibility(int visibility) {
        rl_password_hidden.setVisibility(visibility);
    }

    /***
     * 获取字体内容
     *
     * @return
     */
    public String getText() {
        if (type == InputType.TYPE_CLASS_NUMBER) {
            return re_edit.getText().toString().trim().replace(" ", "");
        }
        if (carnumber) {
            return re_edit.getText().toString().trim().replace(" ", "");
        }
        return re_edit.getText().toString().trim();
    }

    /***
     * 获取原版数据
     *
     * @return
     */
    public String getTextV2() {
        return re_edit.getText().toString().trim();
    }

    /**
     * 隐藏前边的图表
     *
     * @param visibility
     */
    public void setInputIconVisibility(int visibility) {
        input_icon_re.setVisibility(visibility);
    }

    /***
     * 设置前边图标的图片
     *
     * @param drawable
     */
    public void setInputIconSrc(int drawable) {
        input_icon.setImageResource(drawable);
    }

    /****
     * 设置字符的最大长度
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        this.maxlength = maxLength;
        re_edit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    /***
     * 设置行数
     *
     * @param maxLines
     */
    public void setMaxLines(int maxLines) {
        re_edit.setMaxLines(maxLines);
    }

    /***
     * 隐藏输入框里里的内容
     */
    public void setHidden() {
        re_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    /***
     * 获取编辑器
     *
     * @return
     */
    public EditText getEditText() {
        return re_edit;
    }

    /***
     * 删除按钮是否隐藏
     *
     * @param visibility
     */
    public void setDeleteVisibility(int visibility) {
        if (visibility == View.GONE) {
            delete_icon = true;
        }
        password_icon.setVisibility(visibility);
    }

    /***
     * 是否获取焦点
     *
     * @param isFocusable
     */
    public void setFocusable(boolean isFocusable) {
        re_edit.setFocusable(isFocusable);
    }

    /**
     * 清除输入框里的内容
     */
    public void setClearText() {
        re_edit.setText("");
    }

    private OnDeleteListenr onDeleteListenr;

    /**
     * 删除数据
     *
     * @param onDeleteListenr
     */
    public void setOnDeleteListenr(OnDeleteListenr onDeleteListenr) {
        this.onDeleteListenr = onDeleteListenr;
    }

    public interface OnDeleteListenr {
        void onDelete();
    }

    public OnTextChangeListener onTextChangeListener;

    /***
     * 设置监听字符串变化
     * @param onTextChangeListener
     */
    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    boolean isListener = false;

    /***
     * 设置是否监听字符串变化
     * @param isListener
     */
    public void isListener(boolean isListener) {
        this.isListener = isListener;
    }


}
