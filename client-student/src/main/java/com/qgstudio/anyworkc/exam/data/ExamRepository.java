package com.qgstudio.anyworkc.exam.data;

import android.content.Context;

import com.qgstudio.anyworkc.core.Apis;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.data.RetrofitSubscriber;
import com.qgstudio.anyworkc.data.model.Question;
import com.qgstudio.anyworkc.data.model.StudentAnswerAnalysis;
import com.qgstudio.anyworkc.data.model.StudentAnswerResult;
import com.qgstudio.anyworkc.data.model.StudentPaper;
import com.qgstudio.anyworkc.data.model.StudentTestResult;
import com.qgstudio.anyworkc.exam.ExamActivity;
import com.qgstudio.anyworkc.exam.ExamView;
import com.qgstudio.anyworkc.exam.adapters.AnswerBuffer;
import com.qgstudio.anyworkc.mvp.BasePresenterImpl;
import com.qgstudio.anyworkc.utils.GsonUtil;
import com.qgstudio.anyworkc.utils.LogUtil;
import com.qgstudio.anyworkc.workout.data.Testpaper;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Yason 2017/4/14.
 */

public class ExamRepository extends BasePresenterImpl<ExamView> implements ExamPresenter {

    public static final String TAG = "ExamRepository";

    private ExamApi mExamApi;

    public ExamRepository() {
        Retrofit retrofit = RetrofitClient.RETROFIT_CLIENT.getRetrofit();
        mExamApi = retrofit.create(ExamApi.class);
    }

    private ExamActivity getExamActivity() {
        return (ExamActivity) mView;
    }

    public void getTestpaper(int textpaperId, int state) {
        Map map = new HashMap<>();
        map.put("testpaperId", textpaperId + "");
        if (state == Testpaper.STATUS_UNFINISHED) {
            map.put("choice", 1);
        } else {
            map.put("choice", 0);
        }
        if (mView != null) {
            getExamActivity().loading();
        }
        mExamApi.getTestpaper(Apis.getTestpaperApi(), map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<List<Question>>() {
                    @Override
                    protected void onSuccess(List<Question> data) {
                        LogUtil.d(TAG, "[getTestpaper] " + "onSuccess -> " + data);
                        if (mView != null) {
                            getExamActivity().loadSuccess();
                            mView.addQuestions(data);
                        }
                    }

                    @Override
                    protected void onFailure(String info) {
                        if (mView != null) {
                            getExamActivity().loadError();
                        }
                        LogUtil.d(TAG, "[getTestpaper] " + "onFailure -> " + info);
                        handleGetPaperContentFailure();
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d(TAG, "[getTestpaper] " + "onMistake -> " + t.getMessage());
                        if (mView == null) {
                            return;
                        }
                        getExamActivity().loadError();
                        if (t instanceof ConnectException) {
                            mView.showToast("????????????????????????");
                            mView.destroySelf();
                            return;
                        }

                        handleGetPaperContentFailure();
                    }
                });
    }

    public void submitTestPaper(StudentPaper studentPaper) {
        LogUtil.d(TAG, "[submitTestPaper] " + "studentPaper -> " + GsonUtil.GsonString(studentPaper));
        if (mView != null) {
            mView.showLoading();
        }
        mExamApi.submitTestpaper(Apis.submitTestpaperApi(), studentPaper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<StudentTestResult>() {
                    @Override
                    protected void onSuccess(StudentTestResult data) {
                        if (mView != null) {
                            mView.hideLoading();
                        }
                        LogUtil.d(TAG, "[submitTestPaper] " + "onSuccess -> " + data);
                        AnswerBuffer.getInstance().clear();//???????????????
                        if (data == null) {
                            //??????????????????
                            mView.saveDone();
                            return;
                        }
                        double socre = data.getSocre();
                        List<StudentAnswerResult> results = new ArrayList<>();

                        List<StudentAnswerAnalysis> analysis = data.getStudentAnswerAnalysis();
                        for (StudentAnswerAnalysis analysi : analysis) {
                            results.add(new StudentAnswerResult(analysi));
                        }
                        mView.startGradeAty(socre, results);
                    }

                    @Override
                    protected void onFailure(String info) {
                        if (mView != null) {
                            mView.hideLoading();
                        }
                        LogUtil.d(TAG, "[submitTestPaper] " + "onFailure -> " + info);
                        mView.showToast("??????????????????");
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d(TAG, "[submitTestPaper] " + "onMistake -> " + t);
                        if (mView != null) {
                            mView.hideLoading();
                        }
                        if (t instanceof ConnectException) {
                            mView.showToast("????????????????????????");
                            return;
                        }

                        mView.showToast("??????????????????");
                    }
                });
    }

    private void handleGetPaperContentFailure() {
        mView.showToast("????????????????????????");
        mView.destroySelf();
    }

    @Override
    public void detachView() {
        super.detachView();
        mView = new ExamView() {
            @Override
            public void addQuestions(List<Question> questions) {

            }

            @Override
            public void startGradeAty(double socre, List<StudentAnswerResult> analysis) {

            }

            @Override
            public void destroySelf() {

            }

            @Override
            public void submitDone() {

            }

            @Override
            public void saveDone() {

            }

            @Override
            public Context getContext() {
                return null;
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }

            @Override
            public void showToast(String s) {

            }
        };
    }
}
