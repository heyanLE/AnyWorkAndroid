package com.qgstudio.anyworkc.notice;

import android.app.NotificationManager;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.qgstudio.anyworkc.App;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.common.DialogManagerActivity;
import com.qgstudio.anyworkc.core.Apis;
import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.notice.data.Notice;
import com.qgstudio.anyworkc.notice.data.NoticeContainer;
import com.qgstudio.anyworkc.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NoticeActivity extends DialogManagerActivity {
    @BindView(R.id.refresh_layout_notice)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_view_notice)
    RecyclerView recyclerView;
    @BindView(R.id.img_back)
    ImageView imageBack;
    NoticeAdapter adapter;
    NoticeApi noticeApi;
    AtomicInteger pageNum = new AtomicInteger(1);
    int lastPage = Integer.MAX_VALUE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.sample_blue));
        }
        ButterKnife.bind(this);
        NotificationManager mNotificationManager = (NotificationManager) App.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoticeAdapter(new ArrayList<Notice>(), this);
        recyclerView.setAdapter(adapter);
        noticeApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(NoticeApi.class);
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                Map map= (Map) buildRequestParam(true);
                if (((int)map.get("pageNum")) > lastPage) {
                    ToastUtil.showToast("???????????????");
                    refreshLayout.finishLoadMore();
                    return;
                }
                noticeApi.getNotice(Apis.getNoticeApi(),buildRequestParam(true))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseResult<NoticeContainer>>() {
                            @Override
                            public void onCompleted() {
                                refreshLayout.finishLoadMore();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                                    return;
                                }
                                refreshLayout.finishLoadMore();
                                ToastUtil.showToast("???????????????");
                            }

                            @Override
                            public void onNext(ResponseResult<NoticeContainer> jsonObjectResponseResult) {
                                if (getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                                    return;
                                }
//                                List<Notice> noticeList = new Gson().fromJson(jsonObjectResponseResult
//                                                .getData()
//                                                .get("list")
//                                        , new TypeToken<List<Notice>>() {
//                                        }.getType());
                                NoticeContainer temp = jsonObjectResponseResult.getData();
                                List<Notice> noticeList = temp.getList();

//                                lastPage = jsonObjectResponseResult
//                                        .getData()
//                                        .get("lastPage").getAsInt();
                                lastPage = temp.getLastPage();

                                if (noticeList.isEmpty()) {
                                    ToastUtil.showToast("???????????????");
                                    refreshLayout.finishLoadMore();
                                } else {
                                    adapter.list.addAll(noticeList);
                                    adapter.notifyDataSetChanged();
                                    refreshLayout.finishLoadMore();
                                }
                            }
                        });
            }

            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {

                noticeApi.getNotice(Apis.getNoticeApi(),buildRequestParam(false))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseResult<NoticeContainer>>() {
                            @Override
                            public void onCompleted() {
                                refreshLayout.finishRefresh(1);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                                    return;
                                }
                                e.printStackTrace();
                                refreshLayout.finishRefresh(1);
                            }

                            @Override
                            public void onNext(ResponseResult<NoticeContainer> jsonObjectResponseResult) {
                                NoticeContainer temp = jsonObjectResponseResult.getData();
                                if (getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                                    return;
                                }
//                                List<Notice> noticeList = new Gson().fromJson(jsonObjectResponseResult
//                                                .getData()
//                                                .get("list")
//                                        , new TypeToken<List<Notice>>() {
//                                        }.getType());
                                List<Notice> noticeList = temp.getList();
                                if (noticeList.isEmpty()) {
                                    ToastUtil.showToast("?????????");
                                    refreshLayout.finishRefresh();
                                } else {
                                    adapter.list.clear();
                                    adapter.list.addAll(noticeList);
                                    adapter.notifyDataSetChanged();
                                    refreshLayout.finishRefresh();
                                }
                            }
                        });
            }
        });
    }

    public Object buildRequestParam(boolean isLoadMore) {
        HashMap info = new HashMap();
        info.put("status", 2);
        info.put("pageSize", 10);
        if (!isLoadMore) {
            pageNum.set(1);
        }
        info.put("pageNum", pageNum.getAndIncrement());
        return info;
    }


}
