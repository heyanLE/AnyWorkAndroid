// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.enter.register;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterFragment_ViewBinding implements Unbinder {
  private RegisterFragment target;

  private View view2131231019;

  private View view2131230795;

  private View view2131230953;

  @UiThread
  public RegisterFragment_ViewBinding(final RegisterFragment target, View source) {
    this.target = target;

    View view;
    target.studentID = Utils.findRequiredViewAsType(source, R.id.student_id, "field 'studentID'", EditText.class);
    target.email = Utils.findRequiredViewAsType(source, R.id.email, "field 'email'", EditText.class);
    target.phone = Utils.findRequiredViewAsType(source, R.id.phone, "field 'phone'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.password, "field 'password'", EditText.class);
    target.password2 = Utils.findRequiredViewAsType(source, R.id.password2, "field 'password2'", EditText.class);
    view = Utils.findRequiredView(source, R.id.register, "field 'btnRegister' and method 'register'");
    target.btnRegister = Utils.castView(view, R.id.register, "field 'btnRegister'", Button.class);
    view2131231019 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.register();
      }
    });
    view = Utils.findRequiredView(source, R.id.cancel, "method 'cancel'");
    view2131230795 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cancel();
      }
    });
    view = Utils.findRequiredView(source, R.id.meed_question_register, "method 'clickMeedQuestion'");
    view2131230953 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickMeedQuestion();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.studentID = null;
    target.email = null;
    target.phone = null;
    target.password = null;
    target.password2 = null;
    target.btnRegister = null;

    view2131231019.setOnClickListener(null);
    view2131231019 = null;
    view2131230795.setOnClickListener(null);
    view2131230795 = null;
    view2131230953.setOnClickListener(null);
    view2131230953 = null;
  }
}
