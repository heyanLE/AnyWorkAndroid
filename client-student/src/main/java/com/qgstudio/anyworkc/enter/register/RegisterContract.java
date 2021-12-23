package com.qgstudio.anyworkc.enter.register;

import com.qgstudio.anyworkc.mvp.BasePresenter;
import com.qgstudio.anyworkc.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegisterContract {
    interface View extends BaseView {

        void showError(String errorInfo);

        void showSuccess();

        void showLoad();

        void stopLoad();
    }

    interface  Presenter extends BasePresenter<View> {
        void register(String email, String password, String name, String phone);
    }
}
