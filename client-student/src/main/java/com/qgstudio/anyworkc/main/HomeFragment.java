package com.qgstudio.anyworkc.main;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.collection.CollectionActivity;
import com.qgstudio.anyworkc.data.model.Organization;
import com.qgstudio.anyworkc.mvp.MVPBaseFragment;
import com.qgstudio.anyworkc.notice.NoticeActivity;
import com.qgstudio.anyworkc.notice.NoticeAdapter;
import com.qgstudio.anyworkc.notice.data.Notice;
import com.qgstudio.anyworkc.websocket.ThreadMode;
import com.qgstudio.anyworkc.websocket.WS;
import com.qgstudio.anyworkc.websocket.WebSocketHolder;
import com.qgstudio.anyworkc.widget.LoadingView;
import com.qgstudio.anyworkc.workout.WorkoutContainerActivity;
import com.qgstudio.anyworkc.workout.WorkoutType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends MVPBaseFragment<HomeContract.HomeView, HomePresenter> implements HomeContract.HomeView {
    @BindView(R.id.btn_my_class)
    TextView btnMyClass;
    @BindView(R.id.toolbar)
    View toolbar;
    @BindView(R.id.tv_online_count)
    TextView tvOnlineCount;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    @BindView(R.id.recycler_view_notice)
    RecyclerView recyclerView;
    NoticeAdapter adapter;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
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

    @Override
    public int getRootId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        if (btnMyClass.getTag() == null) {
            //?????????????????????????????????-1
            btnMyClass.setTag(new Organization(-1));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.sample_blue));
        }
        //???????????????????????????livedata???????????????????????????
        WebSocketHolder.getDefault().onlineCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                tvOnlineCount.setText(integer == null ? "0" : integer.toString());
            }
        });
        //???????????????????????????
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelOffset(resourceId);
        }
        Log.e("gaodu", result + "");
        topView.getLayoutParams().height = result;
        topView.setLayoutParams(topView.getLayoutParams());
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                mPresenter.getNoticeNew();
            }
        });
    }

    @Override
    public void loadData(View view) {

    }

    @OnClick(R.id.btn_my_class)
    public void clickMyClass() {

        //if (((Organization) btnMyClass.getTag()).getOrganizationId() == -1) {
            startActivity(new Intent(getActivity(), NewOrganizationActivity.class));
        //}
    }

    @OnClick(R.id.btn_preview)
    public void clickPreview() {
        WorkoutContainerActivity.start(getActivity(), WorkoutType.PREVIEW, ((Organization) btnMyClass.getTag()).getOrganizationId());

    }

    @OnClick(R.id.btn_exercise)
    public void clickExercise() {
        WorkoutContainerActivity.start(getActivity(), WorkoutType.EXERCISE, ((Organization) btnMyClass.getTag()).getOrganizationId());

    }

    @OnClick(R.id.btn_status)
    public void clickStatus(){
        startActivity(new Intent(requireContext(), StatusActivity.class));
    }

    @OnClick(R.id.btn_exam)
    public void clickExam() {
        WorkoutContainerActivity.start(getActivity(), WorkoutType.EXAM, ((Organization) btnMyClass.getTag()).getOrganizationId());

    }

    @OnClick(R.id.btn_collection)
    public void clickCollection() {
        startActivity(new Intent(getActivity(), CollectionActivity.class));
    }

    /**
     * ???????????????????????????
     */
    @OnClick(R.id.btn_notice_all)
    public void clickNoticeAll() {
        startActivity(new Intent(getActivity(), NoticeActivity.class));
    }

    @OnClick(R.id.tv_notice)
    public void clickNoticeText() {

    }

    @Override
    public void onMyClassGot(Organization organization) {
        btnMyClass.setTag(organization == null ? new Organization(-1) : organization);
        btnMyClass.setText(organization == null ? "??????????????????pick???????????????  >" : organization.getOrganizationName());
        if (btnMyClass.getTag() != null &&
                ((Organization) btnMyClass.getTag()).getOrganizationId() != -1) {
            mPresenter.getNoticeNew();
        }
    }

    @Override
    public void onNoticeGet(List<Notice> notices) {
        if (notices != null) {
            if (adapter == null) {
                adapter = new NoticeAdapter(notices, getActivity());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
                adapter.list.clear();
                adapter.list.addAll(notices);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @WS(threadMode = ThreadMode.MAIN)
    public void onRealtimeNoticeGet(Notice notice) {
        //????????????????????????????????????
        mPresenter.getNoticeNew();
    }

    @Override
    public void onResume() {
        super.onResume();

        //??????????????????????????????????????????
        mPresenter.getJoinOrganization();
        WebSocketHolder.getDefault().register(this);

    }

    public void loading() {
        //?????????????????????????????????????????????????????????item??????????????????loading?????????????????????
        if (adapter == null || adapter.list.isEmpty()) {
            loadingView.load(recyclerView);
        }
    }

    public void loadSuccess() {
        loadingView.loadSuccess(recyclerView);
    }

    public void loadEmpty() {
        loadingView.empty(recyclerView);
    }

    public void loadError() {
        loadingView.error(recyclerView);
    }

    @Override
    public void onStop() {
        super.onStop();
        WebSocketHolder.getDefault().unregister(this);
    }

}
