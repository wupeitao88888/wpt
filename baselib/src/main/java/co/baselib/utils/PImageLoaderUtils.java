package co.baselib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import co.baselib.R;


/**
 * UIL 工具类
 * Created by sky on 15/7/26.
 */
public class PImageLoaderUtils {


    public static PImageLoaderUtils pImageLoaderUtils;

    public static PImageLoaderUtils getInstance() {
        if (pImageLoaderUtils == null) {
            pImageLoaderUtils = new PImageLoaderUtils();
        }
        return pImageLoaderUtils;
    }



    public void displayIMGLocal(String uri, ImageView imageView, Context context) {
        try {
            RequestOptions options = new RequestOptions();
            options.override(100, 100);
            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }


    }

    public void displayIMGLocalFile(File file, ImageView imageView, Context context) {
        try {
            Glide.with(context).load(file).into(imageView);
        } catch (Exception e) {

        }


    }

    public void displayIMGNoDefaultNoCenterGroupBg(String uri, ImageView imageView, Context context) {
        try {
            RequestOptions options = new RequestOptions();

            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }


    }

    public void displayIMGNoDefaultBg(String uri, ImageView imageView, Context context) {
        try {
            RequestOptions options = new RequestOptions();
            options.centerCrop();

            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }


    }



    public void displaycenterCrop(String uri, ImageView imageView, Context context) {
        try {


            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);
            options.centerCrop();
            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }
    }


    public void displayIMGONBG(String uri, ImageView imageView, Context context) {
        try {

            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);

            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }
    }

    public void displayIMGNoCenterGroup(@Nullable Object model, ImageView imageView, Context context) {
//        imageView.setBackgroundResource(R.drawable.small_image);
        try {
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);

            Glide.with(context).load(model).apply(options).into(imageView);

        } catch (Exception e) {

        }


    }

    public void displayIMGTransparency(String uri, ImageView imageView, Context context) {
//        imageView.setBackgroundResource(R.drawable.small_image);
        try {
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);
            options.centerCrop();

            Glide.with(context).load(uri).apply(options).into(imageView);
        } catch (Exception e) {

        }


    }


    public void displayIMGNoCenterGroupTransparency(String uri, ImageView imageView, Context context) {
//        imageView.setBackgroundResource(R.drawable.small_image);
        try {

            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);

            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }
    }


    public void displayIMG(Drawable uri, ImageView imageView, Context context) {
        try {

            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);
            Glide.with(context).load(uri).into(imageView);

        } catch (Exception e) {

        }


    }

    public void displayIMG(int uri, ImageView imageView, Context context) {
        try {

            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);
            Glide.with(context).load(uri).into(imageView);

        } catch (Exception e) {

        }


    }

    public void displayIMGLocal(int uri, ImageView imageView, Context context) {
        try {

            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.gray);
            options.error(R.drawable.gray);
            options.centerCrop();
            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }
    }

    public void displayfaceIMG(Bitmap uri, ImageView imageView, Context context) {
        try {

            RequestOptions options = new RequestOptions();
            options.error(R.drawable.gray);
            Glide.with(context).load(uri).apply(options).into(imageView);

        } catch (Exception e) {

        }
    }








}
