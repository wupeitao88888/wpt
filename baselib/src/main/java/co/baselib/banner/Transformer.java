package co.baselib.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import co.baselib.banner.transformer.AccordionTransformer;
import co.baselib.banner.transformer.BackgroundToForegroundTransformer;
import co.baselib.banner.transformer.CubeInTransformer;
import co.baselib.banner.transformer.CubeOutTransformer;
import co.baselib.banner.transformer.DefaultTransformer;
import co.baselib.banner.transformer.DepthPageTransformer;
import co.baselib.banner.transformer.FlipHorizontalTransformer;
import co.baselib.banner.transformer.FlipVerticalTransformer;
import co.baselib.banner.transformer.ForegroundToBackgroundTransformer;
import co.baselib.banner.transformer.RotateDownTransformer;
import co.baselib.banner.transformer.RotateUpTransformer;
import co.baselib.banner.transformer.ScaleInOutTransformer;
import co.baselib.banner.transformer.StackTransformer;
import co.baselib.banner.transformer.TabletTransformer;
import co.baselib.banner.transformer.ZoomInTransformer;
import co.baselib.banner.transformer.ZoomOutSlideTransformer;
import co.baselib.banner.transformer.ZoomOutTranformer;

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
