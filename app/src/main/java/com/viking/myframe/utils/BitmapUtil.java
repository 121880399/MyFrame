package com.viking.myframe.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.viking.myframe.views.TCGlideCircleTransform;

/**
 * 项目名称: MyFrame-master
 * 创建人: 周正一
 * 创建时间：2017/5/16
 */
public class BitmapUtil {


    /**
     * 将原图绘制成圆形
     *
     * @param source the source
     * @param min    the min
     * @return the bitmap
     */
    static public Bitmap createCircleImage(Bitmap source, int min){
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (0 == min){
            min = source.getHeight()>source.getWidth() ? source.getWidth() : source.getHeight();
        }
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        // 创建画布
        Canvas canvas = new Canvas(target);
        // 绘圆
        canvas.drawCircle(min/2, min/2, min/2, paint);
        // 设置交叉模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 绘制图片
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 圆角显示图片
     *
     * @param context 一般为activtiy
     * @param view 图片显示类
     * @param url 图片url
     * @param defResId 默认图 id
     *
     */
    public static void showPicWithUrl(Context context, ImageView view, String url, int defResId) {
        if (context == null || view == null) {
            return;
        }
        try {
            if (TextUtils.isEmpty(url)){
                view.setImageResource(defResId);
            } else {
                RequestManager req = Glide.with(context);
                req.load(url).placeholder(defResId).transform(new TCGlideCircleTransform(context)).into(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }


}
