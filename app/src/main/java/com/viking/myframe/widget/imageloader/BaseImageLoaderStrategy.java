package com.viking.myframe.widget.imageloader;

import android.content.Context;

/**
 * 项目名称: MyFrame-master
 * 创建人: 周正一
 * 创建时间：2017/5/12
 * 策略模式接口
 */

public interface BaseImageLoaderStrategy {
    void loadImage(Context context,ImageLoader img);
}
