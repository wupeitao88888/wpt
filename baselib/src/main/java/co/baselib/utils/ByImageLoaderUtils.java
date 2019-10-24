package co.baselib.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import co.baselib.R;


/**
 * 图片显示
 * Created by wupeitao on 15/7/26.
 */
public class ByImageLoaderUtils {


    public static ByImageLoaderUtils pImageLoaderUtils;

    public static ByImageLoaderUtils getInstance() {
        if (pImageLoaderUtils == null) {
            pImageLoaderUtils = new ByImageLoaderUtils();
        }
        return pImageLoaderUtils;
    }


    public void displayIMG(@NonNull Object uri, @NonNull ImageView imageView, @NonNull Context context) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.gray);
        options.error(R.drawable.gray);
        Glide.with(context).load(uri).into(imageView);
    }

    public void displayIMGCenterCorp(@NonNull int uri, @NonNull ImageView imageView, @NonNull Context context) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.gray);
        options.error(R.drawable.gray);
        options.centerCrop();
        Glide.with(context).load(uri).apply(options).into(imageView);
    }
}
