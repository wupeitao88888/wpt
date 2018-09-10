package co.baselib.model;

/****
 * 适配器中显示toast回调接口
 */
public interface OnAdapterToastListener {
    void onSuccess(String msg);

    void onFiled(String msgl);
}
