// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewBaseDialog_ViewBinding implements Unbinder {
  private NewBaseDialog target;

  @UiThread
  public NewBaseDialog_ViewBinding(NewBaseDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewBaseDialog_ViewBinding(NewBaseDialog target, View source) {
    this.target = target;

    target.titleContainer = Utils.findRequiredViewAsType(source, R.id.title_container, "field 'titleContainer'", FrameLayout.class);
    target.close = Utils.findRequiredViewAsType(source, R.id.close, "field 'close'", ImageView.class);
    target.viewContainer = Utils.findRequiredViewAsType(source, R.id.view_container, "field 'viewContainer'", FrameLayout.class);
    target.confirm = Utils.findRequiredViewAsType(source, R.id.confirm, "field 'confirm'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewBaseDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleContainer = null;
    target.close = null;
    target.viewContainer = null;
    target.confirm = null;
  }
}
