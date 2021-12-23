package com.qgstudio.anyworkc.paper.data;

import com.qgstudio.anyworkc.paper.PaperFragView;
import com.qgstudio.anyworkc.mvp.BasePresenter;

/**
 * @author Yason 2017/8/13.
 */

public interface PaperPresenter extends BasePresenter<PaperFragView>  {
    void getExaminationPaper(int organizationId);
    void getPracticePaper(int organizationId);
}
