// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.user;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserActivity_ViewBinding implements Unbinder {
  private UserActivity target;

  private View view2131230891;

  private View view2131230794;

  private View view2131230844;

  @UiThread
  public UserActivity_ViewBinding(UserActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserActivity_ViewBinding(final UserActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.head_pic, "field 'pic' and method 'changePic'");
    target.pic = Utils.castView(view, R.id.head_pic, "field 'pic'", CircleImageView.class);
    view2131230891 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.changePic();
      }
    });
    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", TextView.class);
    target.studentId = Utils.findRequiredViewAsType(source, R.id.student_id, "field 'studentId'", TextView.class);
    target.email = Utils.findRequiredViewAsType(source, R.id.email, "field 'email'", EditText.class);
    target.phone = Utils.findRequiredViewAsType(source, R.id.phone, "field 'phone'", EditText.class);
    view = Utils.findRequiredView(source, R.id.button_save, "field 'edit' and method 'save'");
    target.edit = Utils.castView(view, R.id.button_save, "field 'edit'", Button.class);
    view2131230794 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.save();
      }
    });
    view = Utils.findRequiredView(source, R.id.edit, "method 'edit'");
    view2131230844 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.edit();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UserActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.pic = null;
    target.name = null;
    target.studentId = null;
    target.email = null;
    target.phone = null;
    target.edit = null;

    view2131230891.setOnClickListener(null);
    view2131230891 = null;
    view2131230794.setOnClickListener(null);
    view2131230794 = null;
    view2131230844.setOnClickListener(null);
    view2131230844 = null;
  }
}
