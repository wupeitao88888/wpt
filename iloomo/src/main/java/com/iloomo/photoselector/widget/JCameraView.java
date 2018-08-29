package com.iloomo.photoselector.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.iloomo.global.AppController;
import com.iloomo.paysdk.R;
import com.iloomo.photoselector.model.OnTakePicListener;
import com.iloomo.utils.AppUtil;
import com.iloomo.utils.BigDecimalUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.PFileUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class JCameraView extends RelativeLayout implements SurfaceHolder.Callback {

    public final String TAG = "JCameraView";

    private Context mContext;
    private VideoView mVideoView;
    private ImageView mImageView;
    private ImageView photoImageView;

    private MediaRecorder mediaRecorder;
    private SurfaceHolder mHolder = null;
    private Camera mCamera;
    private int width;
    private int height;
    private String fileName;

    private int SELECTED_CAMERA = 0;
    private final int CAMERA_POST_POSITION = 0;
    private final int CAMERA_FRONT_POSITION = 1;
    private float screenProp = 0f;

    /*
            构造函数
             */
    public JCameraView(Context context) {
        this(context, null);
    }

    public JCameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("NewApi")
    public JCameraView(final Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;

        SELECTED_CAMERA = CAMERA_POST_POSITION;
        //设置回调
        this.setBackgroundColor(Color.BLACK);
        /*
        初始化Surface
         */
        mVideoView = new VideoView(context);
        LayoutParams videoViewParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        videoViewParam.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//        videoViewParam.setMargins(10,10,10,10);
        mVideoView.setLayoutParams(videoViewParam);
        /*
        初始化CaptureButtom
         */
        LayoutParams btnParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        btnParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        btnParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);


        /*
        初始化结果图片
         */
        photoImageView = new ImageView(context);
        final LayoutParams photoImageViewParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        photoImageView.setLayoutParams(photoImageViewParam);
        photoImageView.setBackgroundColor(0xFF000000);
//        photoImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        photoImageView.setVisibility(INVISIBLE);


        /*
        初始化ImageView
         */
        mImageView = new ImageView(context);
        LayoutParams imageViewParam = new LayoutParams(60, 60);
        imageViewParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        imageViewParam.setMargins(0, 40, 40, 0);
        mImageView.setLayoutParams(imageViewParam);
        mImageView.setImageResource(R.drawable.ic_camera_enhance_black_24dp);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //摄像头切换
//                Toast.makeText(mContext, "mImageView", Toast.LENGTH_SHORT).show();
                if (mCamera != null) {
                    releaseCamera();
                    if (SELECTED_CAMERA == CAMERA_POST_POSITION) {
                        SELECTED_CAMERA = CAMERA_FRONT_POSITION;
                    } else {
                        SELECTED_CAMERA = CAMERA_POST_POSITION;
                    }
                    mCamera = getCamera(SELECTED_CAMERA);
                    setStartPreview(mCamera, mHolder);
                }
            }
        });


        this.addView(mVideoView);
        this.addView(photoImageView);
        this.addView(mImageView);

        mHolder = mVideoView.getHolder();
        mHolder.addCallback(this);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mCamera.autoFocus(new Camera.AutoFocusCallback() {
                        @Override
                        public void onAutoFocus(boolean success, Camera camera) {

                        }
                    });
                } catch (Exception e) {

                }
            }
        });

    }

    /**
     * 设置摄像头切换按钮是否显示
     *
     * @param visible
     */
    public void ischangeVisible(int visible) {
        mImageView.setVisibility(visible);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    //获取Camera
    private Camera getCamera(int position) {
        Camera camera;
        try {
            camera = Camera.open(position);
        } catch (Exception e) {
            camera = null;
            e.printStackTrace();
        }
        return camera;
    }

    public void btnReturn() {
        setStartPreview(mCamera, mHolder);
    }

    private Camera.Parameters mParams;

    //启动相机浏览
    private void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            if (mParams == null) {
                if (mCamera == null) {
                    return;
                }
                mParams = mCamera.getParameters();
            }
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;         // 屏幕宽度（像素）
            int height = dm.heightPixels;       // 屏幕高度（像素）
            float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
            int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
            // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
            int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
            int screenHeight = (int) (height / density);// 屏幕高度(dp)


            final Camera.Parameters parameters = mCamera.getParameters();
            Camera.Size previewSize = CameraParamUtil.getInstance().getPreviewSize(mParams
                    .getSupportedPreviewSizes(), width, screenProp);
            Camera.Size pictureSize = CameraParamUtil.getInstance().getPictureSize(mParams
                    .getSupportedPictureSizes(), height, screenProp);

            parameters.setPreviewSize(previewSize.width, previewSize.height);


            parameters.setPictureSize(pictureSize.width, pictureSize.height);

            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);


            parameters.setPictureFormat(ImageFormat.JPEG);
            parameters.setJpegQuality(100);

            parameters.set("jpeg-quality", 100);//设置照片质量
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            camera.setParameters(parameters);

            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {

        }
    }

    //释放资源
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    //拍照
    public void capture() {
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    mCamera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            Matrix matrix = new Matrix();
                            matrix.setRotate(90);

                            int w = bitmap.getWidth(); // 得到图片的宽，高
                            int h = bitmap.getHeight();

                            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                            DisplayMetrics dm = new DisplayMetrics();
                            wm.getDefaultDisplay().getMetrics(dm);
                            int width = dm.widthPixels;         // 屏幕宽度（像素）
                            int height = dm.heightPixels;       // 屏幕高度（像素）
                            float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
                            int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
                            // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
                            int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
                            int screenHeight = (int) (height / density);// 屏幕高度(dp)


                            int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

                            int retX = w > h ? (w - h) / 4 : 0;// 基于原图，取正方形左上角x坐标
                            int retY = w > h ? 0 : (h - w) / 2;

                            int dis = width - AppUtil.dip2px(getContext(), 52f);

                            int div = (int) BigDecimalUtil.div(dis, 345.0);
                            L.e("屏幕与图片的比例：" + div);
                            int mul = (int) BigDecimalUtil.mul(div, 238.0);

                            L.e("起始点坐标：", AppController.getInstance().dp2px(213) + "____________________" + retY);
                            L.e("图片的宽高：", w + "____________________" + h);
                            L.e("截取的宽高：", AppController.getInstance().dp2px(mul + 53) + "____________________" + wh);


                            double bili = BigDecimalUtil.div(320, 1280);
                            double xZuoBiao = BigDecimalUtil.mul(bili, w);


                            bitmap = Bitmap.createBitmap(bitmap, (int) xZuoBiao, retY, width - (width / 4), wh, matrix,
                                    true);
                            String path = PFileUtils.saveBitmapToSdCard(mContext, bitmap);
                            if (!TextUtils.isEmpty(path)) {
                                if (onTakePicListener != null) {
                                    onTakePicListener.takePic(path);
                                }
                            } else {
                                if (onTakePicListener != null) {
                                    onTakePicListener.filed();
                                }
                            }
                            photoImageView.setVisibility(GONE);
                            mImageView.setVisibility(INVISIBLE);
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float widthSize = mVideoView.getMeasuredWidth();
        float heightSize = mVideoView.getMeasuredHeight();
        if (screenProp == 0) {
            screenProp = heightSize / widthSize;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera, holder);
        Log.i("Camera", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mHolder = holder;
        if (mCamera != null) {
            mCamera.stopPreview();
            setStartPreview(mCamera, holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
        Log.i("Camera", "surfaceDestroyed");
    }

    public void onResume() {
        //默认启动后置摄像头
        mCamera = getCamera(SELECTED_CAMERA);
        setStartPreview(mCamera, mHolder);
    }

    public void onPause() {
        releaseCamera();
    }

    OnTakePicListener onTakePicListener;

    public void setOnTakePicListener(OnTakePicListener onTakePicListener) {
        this.onTakePicListener = onTakePicListener;
    }


}
