// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.exam.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AskingAdapter$AskingHolder_ViewBinding implements Unbinder {
  private AskingAdapter.AskingHolder target;

  @UiThread
  public AskingAdapter$AskingHolder_ViewBinding(AskingAdapter.AskingHolder target, View source) {
    this.target = target;

    target.edi_asking = Utils.findRequiredViewAsType(source, R.id.edi_asking, "field 'edi_asking'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AskingAdapter.AskingHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edi_asking = null;
  }
}
