// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrganizationAdapter$Holder_ViewBinding implements Unbinder {
  private OrganizationAdapter.Holder target;

  @UiThread
  public OrganizationAdapter$Holder_ViewBinding(OrganizationAdapter.Holder target, View source) {
    this.target = target;

    target.civ = Utils.findRequiredViewAsType(source, R.id.civ_organization, "field 'civ'", CircleImageView.class);
    target.tv_name = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tv_name'", TextView.class);
    target.tv_teacher = Utils.findRequiredViewAsType(source, R.id.tv_teacher, "field 'tv_teacher'", TextView.class);
    target.tv_description = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'tv_description'", TextView.class);
    target.tv_status = Utils.findRequiredViewAsType(source, R.id.tv_status, "field 'tv_status'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrganizationAdapter.Holder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.civ = null;
    target.tv_name = null;
    target.tv_teacher = null;
    target.tv_description = null;
    target.tv_status = null;
  }
}
