// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.exam;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.widget.ExamPagerView;
import com.qgstudio.anyworkc.widget.LoadingView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ExamActivity_ViewBinding implements Unbinder {
  private ExamActivity target;

  @UiThread
  public ExamActivity_ViewBinding(ExamActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ExamActivity_ViewBinding(ExamActivity target, View source) {
    this.target = target;

    target.mExamPagerView = Utils.findRequiredViewAsType(source, R.id.epv, "field 'mExamPagerView'", ExamPagerView.class);
    target.loadingView = Utils.findRequiredViewAsType(source, R.id.loading_view, "field 'loadingView'", LoadingView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ExamActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mExamPagerView = null;
    target.loadingView = null;
  }
}
