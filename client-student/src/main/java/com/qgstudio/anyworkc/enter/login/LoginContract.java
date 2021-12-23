package com.qgstudio.anyworkc.enter.login;

import com.qgstudio.anyworkc.data.model.User;
import com.qgstudio.anyworkc.mvp.BasePresenter;
import com.qgstudio.anyworkc.mvp.BaseView;

/**
 *  登录界面的接口
 *  Created by chenyi on 2017/3/28.
 */

public class LoginContract {
    interface View extends BaseView {

        void showError(String errorInfo);

        void showSuccess();

        void showLoad();

        void stopLoad();
    }

    interface  Presenter extends BasePresenter<View> {

        User getUser();

        void login(String account, String password);
    }
}
