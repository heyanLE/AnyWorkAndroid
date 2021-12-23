package com.qgstudio.anyworkc.paper;


import com.qgstudio.anyworkc.common.PreLoading;
import com.qgstudio.anyworkc.workout.data.Testpaper;
import com.qgstudio.anyworkc.mvp.BaseView;

import java.util.List;

public interface PaperFragView extends BaseView, PreLoading {
    void addPracticePapers(List<Testpaper> testpapers);
    void addExaminationPapers(List<Testpaper> testpapers);
    void showImageError();
    void hideImageError();
    void showImageBlank();
//    void hideImageBlank();
}
