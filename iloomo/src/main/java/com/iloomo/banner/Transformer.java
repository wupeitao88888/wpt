package com.iloomo.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.iloomo.banner.transformer.AccordionTransformer;
import com.iloomo.banner.transformer.BackgroundToForegroundTransformer;
import com.iloomo.banner.transformer.CubeInTransformer;
import com.iloomo.banner.transformer.CubeOutTransformer;
import com.iloomo.banner.transformer.DefaultTransformer;
import com.iloomo.banner.transformer.DepthPageTransformer;
import com.iloomo.banner.transformer.FlipHorizontalTransformer;
import com.iloomo.banner.transformer.FlipVerticalTransformer;
import com.iloomo.banner.transformer.ForegroundToBackgroundTransformer;
import com.iloomo.banner.transformer.RotateDownTransformer;
import com.iloomo.banner.transformer.RotateUpTransformer;
import com.iloomo.banner.transformer.ScaleInOutTransformer;
import com.iloomo.banner.transformer.StackTransformer;
import com.iloomo.banner.transformer.TabletTransformer;
import com.iloomo.banner.transformer.ZoomInTransformer;
import com.iloomo.banner.transformer.ZoomOutSlideTransformer;
import com.iloomo.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
