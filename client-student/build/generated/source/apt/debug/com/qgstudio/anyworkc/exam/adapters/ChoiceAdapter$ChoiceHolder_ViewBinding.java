// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.exam.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChoiceAdapter$ChoiceHolder_ViewBinding implements Unbinder {
  private ChoiceAdapter.ChoiceHolder target;

  @UiThread
  public ChoiceAdapter$ChoiceHolder_ViewBinding(ChoiceAdapter.ChoiceHolder target, View source) {
    this.target = target;

    target.tv_choice = Utils.findRequiredViewAsType(source, R.id.tv_choice, "field 'tv_choice'", TextView.class);
    target.tv_content = Utils.findRequiredViewAsType(source, R.id.tv_content, "field 'tv_content'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChoiceAdapter.ChoiceHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_choice = null;
    target.tv_content = null;
  }
}
