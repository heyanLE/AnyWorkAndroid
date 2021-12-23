package com.qgstudio.anyworkc.user;

import com.qgstudio.anyworkc.data.model.User;
import com.qgstudio.anyworkc.mvp.BasePresenter;
import com.qgstudio.anyworkc.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UserContract {
    interface View extends BaseView {
        void showSuccess(User user);
        void showError(String s);
        void setUser(User user);
        void changeImg();
        void showProgressDialog();
        void hidProgressDialog();
    }

    interface Presenter extends BasePresenter<View> {
        void changeInfo(User newUser);
        void changePic(String path);
    }
}
