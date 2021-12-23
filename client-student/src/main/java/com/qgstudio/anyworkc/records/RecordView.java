package com.qgstudio.anyworkc.records;

import com.qgstudio.anyworkc.mvp.BaseView;
import com.qgstudio.anyworkc.records.bean.RecordsData;

import java.util.List;

public interface RecordView extends BaseView {

    /**
     * 更新列表
     * @param today
     * @param yesterday
     * @param eariler
     */
    void updateList(List<RecordsData.DataBean> today, List<RecordsData.DataBean> yesterday, List<RecordsData.DataBean> eariler);

    /**
     * 成功
     */
    void showSuccess();

    /**
     * 展示错误信息
     * @param msg 错误信息
     */
    void showError(String msg);

    /**
     * loading
     */
    void showLoading();

    /**
     * 取消loading
     */
    void cancelLoading();

    /**
     * 没有做题记录
     */
    void empty();
}
