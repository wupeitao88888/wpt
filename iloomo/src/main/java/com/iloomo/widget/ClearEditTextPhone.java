package com.iloomo.widget;

import android.content.Context;
import android.text.Editable;
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

import com.iloomo.paysdk.R;


/**
 * Created by wupeitao on 2017/8/4.
 */

public class ClearEditTextPhone extends RelativeLayout {


    private EditText re_edit;
    private RelativeLayout password_icon;
    private RelativeLayout rl_password_hidden;
    private ImageView iv_password_hidden;
    private RelativeLayout input_icon_re;
    private ImageView input_icon;

    private boolean isHidden = true;
    private int maxlength = 500;

    private boolean delete_icon = false;

    public ClearEditTextPhone(Context context) {
        super(context);
        init(context);
    }

    public ClearEditTextPhone(Context context, AttributeSet attrs) {
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


    TextWatcher mTextWatcher = new TextWatcher() {
        private CharSequence temp;

        private int editStart;
        private int editEnd;

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!delete_icon) {
                if (s.length() > 0) {
                    password_icon.setVisibility(View.VISIBLE);
                } else {
                    password_icon.setVisibility(View.GONE);
                }
            }

//            lcFeedbackResidueIdea.setText(Integer.valueOf(this.temp.length()) + "/" + maxlength);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.temp = s;

        }

        public void afterTextChanged(Editable s) {


            editStart = re_edit.getSelectionStart();
            editEnd = re_edit.getSelectionEnd();
            if (temp.length() > maxlength) {
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                re_edit.setText(s);
                re_edit.setSelection(tempSelection);
            }
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

    /***
     * 设置输入类型
     *
     * @param type
     */
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

}
