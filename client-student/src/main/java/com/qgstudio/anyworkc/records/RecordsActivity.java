package com.qgstudio.anyworkc.records;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.data.model.StudentAnswerAnalysis;
import com.qgstudio.anyworkc.data.model.StudentAnswerResult;
import com.qgstudio.anyworkc.data.model.StudentTestResult;
import com.qgstudio.anyworkc.exam.ExamActivity;
import com.qgstudio.anyworkc.grade.GradeActivity;
import com.qgstudio.anyworkc.mvp.MVPBaseActivity;
import com.qgstudio.anyworkc.records.bean.RecordsData;
import com.qgstudio.anyworkc.utils.GsonUtil;
import com.qgstudio.anyworkc.utils.ToastUtil;
import com.qgstudio.anyworkc.widget.LoadingView;
import com.qgstudio.anyworkc.widget.ToolBar;
import com.qgstudio.anyworkc.workout.ChapterAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RecordsActivity extends MVPBaseActivity<RecordView, RecordPresent> implements RecordView{

    private static final String TAG = "RecordsActivity";

    private List<RecordsData.DataBean> today = new ArrayList<>();

    private List<RecordsData.DataBean> yesterday = new ArrayList<>();

    private List<RecordsData.DataBean> eariler = new ArrayList<>();

    private RecordsAdapter adapter;

    private RecyclerView rv;

    private LinearLayoutManager manager;

    private LoadingView loadingView;

    private ToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // TODO 更改状态栏颜色
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#4287FF"));
        }

        rv = findViewById(R.id.rv_records);
        loadingView = findViewById(R.id.loading_view);
        loadingView.changeBackgroundColor(Color.parseColor("#F8F8F8"));
        adapter = new RecordsAdapter(today, yesterday, eariler);
        manager = new LinearLayoutManager(this);
        toolBar = findViewById(R.id.tb_record);
        toolBar.setListener(new ToolBar.OnBackListener() {
            @Override
            public void onBack() {
                finish();
            }
        });

        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);


        adapter.setL(new RecordsAdapter.onItemClickListener() {
            @Override
            public void onClick(RecordsData.DataBean dataBean) {
                if (dataBean.getStatus() == 1) {
                    Map<String, Integer> info = new HashMap<>();
                    info.put("testpaperId", dataBean.getTestpaperId());
                    RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(ChapterAdapter.CheckApi.class)
                            .checkTheTestFinish(info)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseResult<StudentTestResult>>() {
                                @Override
                                public void onCompleted() {
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                    Log.d("ChapterAdapter", e.getMessage());
                                    ToastUtil.showToast("网络连接错误");
                                }

                                @Override
                                public void onNext(ResponseResult<StudentTestResult> result) {
                                    double socre = result.getData().getSocre();
                                    List<StudentAnswerResult> results = new ArrayList<>();

                                    List<StudentAnswerAnalysis> analysis = result.getData().getStudentAnswerAnalysis();
                                    for (StudentAnswerAnalysis analysi : analysis) {
                                        results.add(new StudentAnswerResult(analysi));
                                    }
                                    GradeActivity.start(RecordsActivity.this, socre, GsonUtil.GsonString(results), dataBean.getTestpaperTitle());

                                }
                            });
                }else {
                    ExamActivity.start(RecordsActivity.this,dataBean.getTestpaperId(),dataBean.getTestpaperType(),dataBean.getTestpaperTitle(),dataBean.getStatus());
                }
            }
        });

        // 失败重连
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                mPresenter.getRecords();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getRecords();
    }

    @Override
    public void updateList(List<RecordsData.DataBean> today, List<RecordsData.DataBean> yesterday, List<RecordsData.DataBean> eariler) {
        this.today.clear();
        this.yesterday.clear();
        this.eariler.clear();
        this.today.addAll(today);
        this.yesterday.addAll(yesterday);
        this.eariler.addAll(eariler);
        Log.d(TAG, "today:" + this.today.size());
        Log.d(TAG, "yesterday:" + this.yesterday.size());
        Log.d(TAG, "earily:" + this.eariler.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showError(String msg) {
        loadingView.error(null);
    }

    @Override
    public void showLoading() {
        loadingView.load(null);
    }

    @Override
    public void cancelLoading() {
        loadingView.loadSuccess(null);
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, RecordsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void empty() {
        Log.d(TAG, "empty");
        loadingView.empty(null);
    }
}