package com.qgstudio.anyworkc.records;

import android.util.Log;

import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.mvp.BasePresenterImpl;
import com.qgstudio.anyworkc.records.bean.RecordsData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecordPresent extends BasePresenterImpl<RecordView> {

    private static final String TAG = "RecordPresent";

    public void getRecords() {
        mView.showLoading();
        RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(RecordService.class)
                .getNotice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecordsData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError("网络异常");
                    }
                    @Override
                    public void onNext(RecordsData recordsData) {
                        String todayTime = getToday();
                        String yesterdayTime = getYesterDay();

                        if (recordsData.getState() == 1) {
                            List<RecordsData.DataBean> datas = recordsData.getData();
                            List<RecordsData.DataBean> today = new ArrayList<>();
                            List<RecordsData.DataBean> yesterday = new ArrayList<>();
                            List<RecordsData.DataBean> earily = new ArrayList<>();
                            for (RecordsData.DataBean temp : datas) {
                                Log.d(TAG, temp.toString());
                                if (temp.getEndTime() == null) {
                                    continue;
                                }
                                String endTime = temp.getEndTime().substring(0, 10).replaceAll("-", ".");
                                if (endTime.equals(todayTime)) {
                                    today.add(temp);
                                }else if (endTime.equals(yesterdayTime)){
                                    yesterday.add(temp);
                                }else {
                                    earily.add(temp);
                                }
                            }
                            if (today.size() == 0 && yesterday.size() == 0 && earily.size() == 0) {
                                mView.empty();
                            }else {
                                mView.updateList(today, yesterday, earily);
                                mView.cancelLoading();
                            }
                        }else {
                            mView.showError(recordsData.getStateInfo());
                        }
                    }
                });
    }

    private String getYesterDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy.MM.dd").format(time);
    }

    private String getToday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy.MM.dd").format(time);
    }
}
