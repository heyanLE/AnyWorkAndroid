package com.qgstudio.anyworkc.grade;

import com.qgstudio.anyworkc.data.model.StudentAnswerAnalysis;
import com.qgstudio.anyworkc.mvp.BasePresenter;
import com.qgstudio.anyworkc.mvp.BaseView;

/**
 * Created by chenyi on 2017/7/10.
 */

public interface GradeContract {

    interface View extends BaseView {
        void showSuccess(StudentAnswerAnalysis analysis);
        void showError(String s);
    }

    interface Presenter extends BasePresenter<View> {
        void getDetail(int id);
    }
}
