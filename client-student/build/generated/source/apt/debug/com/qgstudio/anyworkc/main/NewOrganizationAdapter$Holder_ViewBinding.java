// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewOrganizationAdapter$Holder_ViewBinding implements Unbinder {
  private NewOrganizationAdapter.Holder target;

  @UiThread
  public NewOrganizationAdapter$Holder_ViewBinding(NewOrganizationAdapter.Holder target,
      View source) {
    this.target = target;

    target.tv_name = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tv_name'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewOrganizationAdapter.Holder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tv_name = null;
  }
}
