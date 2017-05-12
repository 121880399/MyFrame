package com.viking.myframe.widget.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.viking.myframe.utils.NetUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * 项目名称: MyFrame-master
 * 创建人: 周正一
 * 创建时间：2017/5/12
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    /**
     *  @author zhou
     *  @time 2017/5/12  15:51
     *  @return
     *  加载图片
     */
    @Override
    public void loadImage(Context context, ImageLoader img) {
        //得到当前使用的是wifi加载策略
        int strategy=img.getWifiStrategy();
        //如果是只在WIFI开启的状态下才加载图片
        if(strategy==ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI){
            //判断当前网络状态是否是wifi
            if(NetUtil.isWifi(context)){
                loadNormal(context,img);
            }else{
                loadCache(context,img);
            }
        }else{
            //如果加载策略不是只在wifi状态下才加载，那么直接加载图片
            loadNormal(context,img);
        }
    }

    /**
     * load image with Glide
     */
    private void loadNormal(Context ctx, ImageLoader img) {
        Glide.with(ctx).load(img.getUrl()).placeholder(img.getPlaceHolder()).into(img.getImgView());
    }

    /**
     *load cache image with Glide
     */
    private void loadCache(Context ctx, ImageLoader img) {
        Glide.with(ctx).using(new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                return new DataFetcher<InputStream>() {
                    @Override
                    public InputStream loadData(Priority priority) throws Exception {
                        throw new IOException();
                    }

                    @Override
                    public void cleanup() {

                    }

                    @Override
                    public String getId() {
                        return model;
                    }

                    @Override
                    public void cancel() {

                    }
                };
            }
        }).load(img.getUrl()).placeholder(img.getPlaceHolder()).diskCacheStrategy(DiskCacheStrategy.ALL).into(img.getImgView());
    }
}
