// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.grade;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.widget.QuestionView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AnalysisFragment_ViewBinding implements Unbinder {
  private AnalysisFragment target;

  @UiThread
  public AnalysisFragment_ViewBinding(AnalysisFragment target, View source) {
    this.target = target;

    target.btnBack = Utils.findRequiredView(source, R.id.toolbar, "field 'btnBack'");
    target.questionView = Utils.findRequiredViewAsType(source, R.id.question_view, "field 'questionView'", QuestionView.class);
    target.btnCollect = Utils.findRequiredView(source, R.id.btn_collect, "field 'btnCollect'");
  }

  @Override
  @CallSuper
  public void unbind() {
    AnalysisFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnBack = null;
    target.questionView = null;
    target.btnCollect = null;
  }
}
