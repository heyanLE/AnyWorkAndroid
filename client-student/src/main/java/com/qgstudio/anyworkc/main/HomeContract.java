package com.qgstudio.anyworkc.main;

import com.qgstudio.anyworkc.common.PreLoading;
import com.qgstudio.anyworkc.data.model.Organization;
import com.qgstudio.anyworkc.mvp.BasePresenter;
import com.qgstudio.anyworkc.mvp.BaseView;
import com.qgstudio.anyworkc.notice.data.Notice;

import java.util.List;

public class HomeContract {
    interface HomeView extends BaseView, PreLoading {
        public void onMyClassGot(Organization organization);

        public void onNoticeGet(List<Notice> notices);

    }

    interface HomePresenter extends BasePresenter<HomeView> {
        public void getJoinOrganization();

        public void getNoticeNew();
//        public void postReadNew(String id);
    }
}
