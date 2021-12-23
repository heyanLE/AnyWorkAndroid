package com.qgstudio.anyworkc.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qgstudio.anyworkc.App;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.data.ApiStores;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by chenyi on 2017/7/12.
 */

public class GlideUtil {

    public static void setPicture(ImageView img, String path) {
        Glide.with(App.getInstance())
                .load(path)
                .error(R.drawable.ic_icon)
                .fitCenter()
                .crossFade(500)
                .into(img);
    }

    public static void setPictureWithOutCache(ImageView img, int id, int def) {

        Glide.with(App.getInstance())
                .load(ApiStores.API_DEFAULT_URL + "/picture/user/" + id + ".jpg")
                .placeholder(R.drawable.icon_head)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(def == -1 ? R.drawable.ic_icon : def)
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(App.getInstance()))
                .into(img);
    }

    public static void setPictureWithOutCache(ImageView img, String url, int def) {
        Glide.with(App.getInstance())
                .load(ApiStores.API_DEFAULT_URL + url)
                .placeholder(def)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(def == -1 ? R.drawable.ic_icon : def)
                .fitCenter()
                .crossFade(200)
                .bitmapTransform(new CropCircleTransformation(App.getInstance()))
                .into(img);
    }

}
