package com.qgstudio.anyworkc.main;

import com.qgstudio.anyworkc.core.Apis;
import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.data.RetrofitClient;
import com.qgstudio.anyworkc.data.RetrofitSubscriber;
import com.qgstudio.anyworkc.data.model.Organization;
import com.qgstudio.anyworkc.main.data.OrganizationApi;
import com.qgstudio.anyworkc.mvp.BasePresenterImpl;
import com.qgstudio.anyworkc.notice.NoticeApi;
import com.qgstudio.anyworkc.notice.data.NoticeContainer;
import com.qgstudio.anyworkc.utils.LogUtil;

import java.util.HashMap;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenter extends BasePresenterImpl<HomeContract.HomeView> implements HomeContract.HomePresenter {
    private OrganizationApi mOrganizationApi;
    private NoticeApi mNoticeApi;
    public static final String TAG = "HomePresenter";

    public HomePresenter() {
        Retrofit retrofit = RetrofitClient.RETROFIT_CLIENT.getRetrofit();
        mOrganizationApi = retrofit.create(OrganizationApi.class);
        mNoticeApi = retrofit.create(NoticeApi.class);
    }

    private HomeFragment getHomeFragment() {
        return (HomeFragment) mView;
    }

    @Override
    public void attachView(HomeContract.HomeView view) {
        mView = view;
    }

    @Override
    public void getJoinOrganization() {
        prepareLoading();
        mOrganizationApi.getJoinOrganization(Apis.getJoinOrganizationApi())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<List<Organization>>() {
                    @Override
                    protected void onSuccess(List<Organization> data) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onSuccess -> " + data);
                        //如果用户已经加入组织，则把组织号查入数据库，避免多余的网络请求
                        afterLoading();
                        if (mView != null) {
                            mView.onMyClassGot(data.isEmpty() ? null : data.get(0));
                        }
                    }

                    @Override
                    protected void onFailure(String info) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onFailure -> " + info);

                        afterLoading();
                        if (mView != null) {
                            mView.showToast("获取信息失败");
                        }
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d2(TAG, "getJoinOrganization", "onMistake -> " + t.getMessage());

                        afterLoading();
                        if (mView != null) {
                            mView.showToast("获取信息失败");
                        }

                    }
                });
    }

    @Override
    public void getNoticeNew() {
        if (mView != null) {
            getHomeFragment().loading();
        }
        mNoticeApi.getNotice(Apis.getNoticeApi(), buildRequestParam())
                .subscribeOn(Schedulers.io())
                .observeOn((AndroidSchedulers.mainThread()))
                .subscribe(new Observer<ResponseResult<NoticeContainer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (mView != null) {
                            getHomeFragment().loadError();
                        }
                    }

                    @Override
                    public void onNext(ResponseResult<NoticeContainer> result) {


                        if (mView != null) {
                            if (result.getData() == null || result.getData().getList() == null || result.getData().getList().isEmpty()) {
                                getHomeFragment().loadEmpty();
                            } else {
                                getHomeFragment().loadSuccess();
                                mView.onNoticeGet(result.getData().getList());
                            }

                        }
                    }
                });

    }

    private Object buildRequestParam() {
        HashMap info = new HashMap();
        info.put("status", 2);
        info.put("pageSize", 5);
        info.put("pageNum", 1);
        return info;
    }

    private void afterLoading() {
        if (mView != null) {
            mView.hideLoading();
        }
    }

    private void prepareLoading() {
        if (mView != null) {
            mView.showLoading();
        }
    }
}
