package com.viking.myframe.widget.imageloader;

import android.content.Context;

/**
 * 项目名称: MyFrame-master
 * 创建人: 周正一
 * 创建时间：2017/5/12
 * 图片加载门面类
 */

public class ImageLoaderUtil {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    private static ImageLoaderUtil mInstance;
    private BaseImageLoaderStrategy mStrategy;

    private ImageLoaderUtil(){
        mStrategy=new GlideImageLoaderStrategy();
    }

    public static ImageLoaderUtil getInstantce(){
        if(mInstance==null){
            synchronized (ImageLoaderUtil.class){
                if(mInstance==null){
                    mInstance=new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public void loadImage(Context context,ImageLoader img){
        mStrategy.loadImage(context,img);
    }

    public void setImageLoadStrategy(BaseImageLoaderStrategy strategy){
        mStrategy=strategy;
    }
}
