package com.qgstudio.anyworkc.utils;

import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.data.model.User;

import java.util.concurrent.ScheduledExecutorService;

import retrofit2.Retrofit;
import retrofit2.http.GET;
import rx.Observable;


/**
 * @author Yason
 * @since 2017/9/23
 */
@Deprecated
public class SessionMaintainUtil {

    private static AccessApi accessApi;
    private static ScheduledExecutorService executors;

    private SessionMaintainUtil(){}

    static{
        Retrofit retrofit = RetrofitClient.RETROFIT_CLIENT.getRetrofit();
        accessApi = retrofit.create(AccessApi.class);
    }

    public static void start () {
//        executors = Executors.newScheduledThreadPool(1);
//        executors.scheduleAtFixedRate(new AccessTask(), 5, 5, TimeUnit.SECONDS);
    }

    public static void stop(){
        if (executors != null && !executors.isTerminated()) {
            executors.shutdownNow();
        }
    }


    private interface AccessApi{
        @GET("user/myinfo")
        Observable<ResponseResult<User>> access();
    }

    private static class AccessTask implements Runnable {
        @Override
        public void run() {
            accessApi.access()
                    .subscribe();
        }
    }

}
