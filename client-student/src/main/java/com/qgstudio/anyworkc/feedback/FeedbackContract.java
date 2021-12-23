package com.qgstudio.anyworkc.feedback;

import com.qgstudio.anyworkc.feedback.data.FeedBack;
import com.qgstudio.anyworkc.mvp.BasePresenter;
import com.qgstudio.anyworkc.mvp.BaseView;

import rx.Subscription;

public class FeedbackContract {
    interface View extends BaseView {

        void showError(String errorInfo);

        void showSuccess();

        void showLoad();

        void stopLoad();

        void updateUploadProgress(long length, long hasWrited);
    }

    interface Presenter extends BasePresenter<View> {
        Subscription uploadFeedback(FeedBack feedBack, String imagePath);
    }
}
