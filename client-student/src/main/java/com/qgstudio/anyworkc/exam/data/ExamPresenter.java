package com.qgstudio.anyworkc.exam.data;

import com.qgstudio.anyworkc.data.model.StudentPaper;
import com.qgstudio.anyworkc.exam.ExamView;
import com.qgstudio.anyworkc.mvp.BasePresenter;

/**
 * Created by Yason on 2017/8/13.
 */

public interface ExamPresenter extends BasePresenter<ExamView> {
    void getTestpaper(int textpaperId,int state);
    void submitTestPaper(StudentPaper studentPaper);
}
