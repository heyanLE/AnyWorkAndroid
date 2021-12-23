package com.qgstudio.anyworkc.exam;


import com.qgstudio.anyworkc.common.PreLoading;
import com.qgstudio.anyworkc.data.model.Question;
import com.qgstudio.anyworkc.data.model.StudentAnswerResult;
import com.qgstudio.anyworkc.mvp.BaseView;

import java.util.List;

public interface ExamView extends BaseView, PreLoading {
    void addQuestions(List<Question> questions);

    void startGradeAty(double socre, List<StudentAnswerResult> analysis);

    void destroySelf();

    void submitDone();

    void saveDone();

}
