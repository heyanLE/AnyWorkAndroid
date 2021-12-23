// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.exam.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FillingAdapter$FillingHolder_ViewBinding implements Unbinder {
  private FillingAdapter.FillingHolder target;

  @UiThread
  public FillingAdapter$FillingHolder_ViewBinding(FillingAdapter.FillingHolder target,
      View source) {
    this.target = target;

    target.tv_filling = Utils.findRequiredViewAsType(source, R.id.tv_filling, "field 'tv_filling'", TextView.class);
    target.edi_filling = Utils.findRequiredViewAsType(source, R.id.edi_filling, "field 'edi_filling'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FillingAdapter.FillingHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_filling = null;
    target.edi_filling = null;
  }
}
