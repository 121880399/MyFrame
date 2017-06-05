package com.viking.myframe.views;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.viking.myframe.utils.BitmapUtil;

/**
 * Glide图像裁剪
 */
public class TCGlideCircleTransform extends BitmapTransformation {
    public TCGlideCircleTransform(Context context){
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return BitmapUtil.createCircleImage(toTransform, 0);
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
