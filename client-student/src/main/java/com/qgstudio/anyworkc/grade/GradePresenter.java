package com.qgstudio.anyworkc.grade;

import android.content.Context;
import android.util.Log;

import com.qgstudio.anyworkc.App;
import com.qgstudio.anyworkc.core.Apis;
import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.data.model.Question;
import com.qgstudio.anyworkc.data.model.StudentAnswerAnalysis;
import com.qgstudio.anyworkc.mvp.BasePresenterImpl;
import com.qgstudio.anyworkc.utils.GsonUtil;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyi on 2017/7/10.
 */

public class GradePresenter extends BasePresenterImpl<GradeContract.View> implements GradeContract.Presenter {

    @Override
    public void detachView() {
        mView = new GradeContract.View() {


            @Override
            public void showSuccess(StudentAnswerAnalysis analysis) {

            }

            @Override
            public void showError(String s) {

            }

            @Override
            public Context getContext() {
                return null;
            }
        };
    }

    private GradeApi gradeApi;

    @Override
    public void getDetail(int id) {
        if (gradeApi == null) {
            gradeApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(GradeApi.class);
        }
        Map info = new HashMap<>();
        info.put("questionId", id+"");
        info.put("userId", App.getInstance().getUser().getUserId());
        gradeApi.changeInfo(Apis.changeInfoApi(),info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<StudentAnswerAnalysis>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showError("网络连接错误");
                    }

                    @Override
                    public void onNext(ResponseResult<StudentAnswerAnalysis> result) {
                        assert result != null;

                        if (result.getState() == 1) {
                            Question question = result.getData().getQuestion();
                            mView.showSuccess(result.getData());
                            Log.i("TAG", "onNext: " + GsonUtil.GsonString(question));
                        } else {
                            mView.showError(result.getStateInfo());
                        }
                    }
                });
    }
}
