// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.main;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewOrganizationActivity_ViewBinding implements Unbinder {
  private NewOrganizationActivity target;

  private View view2131230917;

  @UiThread
  public NewOrganizationActivity_ViewBinding(NewOrganizationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewOrganizationActivity_ViewBinding(final NewOrganizationActivity target, View source) {
    this.target = target;

    View view;
    target.searching = Utils.findRequiredViewAsType(source, R.id.edi_searching, "field 'searching'", EditText.class);
    target.tvMyOrganization = Utils.findRequiredViewAsType(source, R.id.my_organization, "field 'tvMyOrganization'", TextView.class);
    view = Utils.findRequiredView(source, R.id.img_back, "method 'back'");
    view2131230917 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.back();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    NewOrganizationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.searching = null;
    target.tvMyOrganization = null;

    view2131230917.setOnClickListener(null);
    view2131230917 = null;
  }
}
