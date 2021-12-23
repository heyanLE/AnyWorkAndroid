// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseDialog_ViewBinding implements Unbinder {
  private BaseDialog target;

  @UiThread
  public BaseDialog_ViewBinding(BaseDialog target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BaseDialog_ViewBinding(BaseDialog target, View source) {
    this.target = target;

    target.titleContainer = Utils.findRequiredViewAsType(source, R.id.title_container, "field 'titleContainer'", FrameLayout.class);
    target.close = Utils.findRequiredViewAsType(source, R.id.close, "field 'close'", ImageView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.title_text, "field 'title'", TextView.class);
    target.viewContainer = Utils.findRequiredViewAsType(source, R.id.view_container, "field 'viewContainer'", FrameLayout.class);
    target.confirm = Utils.findRequiredViewAsType(source, R.id.confirm, "field 'confirm'", Button.class);
    target.cancel = Utils.findRequiredViewAsType(source, R.id.cancel, "field 'cancel'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleContainer = null;
    target.close = null;
    target.title = null;
    target.viewContainer = null;
    target.confirm = null;
    target.cancel = null;
  }
}
