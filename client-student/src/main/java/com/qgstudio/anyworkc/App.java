package com.qgstudio.anyworkc;

import android.content.Context;

import com.qgstudio.anyworkc.data.model.User;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by Yason on 2017/4/14.
 *
 *
 * 更改： 更换tinker插件，引入bugly的tinker-support
 */

public class App extends TinkerApplication {

    private static Context context;

    private static App app;

    public App() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.qgstudio.anyworkc.AppLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    public static App getInstance() {
        return app;
    }

    /**
     * 用于获得全局上下文
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        app = this;

 /*       if (BuildConfig.TINKER_ENABLE) {

            // 我们可以从这里获得Tinker加载过程的信息
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

            // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true);

            // 每隔3个小时去访问后台时候有更新,通过handler实现轮训的效果
            new FetchPatchHandler().fetchPatchWithInterval(3);
        }*/


//        //获取应用签名
//        try {
//            PackageInfo info =  getPackageManager().getPackageInfo(getPackageName(),PackageManager.GET_SIGNATURES);
//            System.out.println(info.signatures[0].toCharsString());
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }


        //startService(new Intent(this, AppService.class));
        //Log.d("checkcheckchekc", Apis.initLib());
/*
        OkHttpClient client = new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.COMPATIBLE_TLS,
                        ConnectionSpec.CLEARTEXT)).build();*/
    }

    private User user;

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
