// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.exam;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.widget.QuestionView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QuestionFragment_ViewBinding implements Unbinder {
  private QuestionFragment target;

  @UiThread
  public QuestionFragment_ViewBinding(QuestionFragment target, View source) {
    this.target = target;

    target.questionView = Utils.findRequiredViewAsType(source, R.id.question_view, "field 'questionView'", QuestionView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    QuestionFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.questionView = null;
  }
}
