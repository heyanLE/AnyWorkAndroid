// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.enter.login;

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

public class LoginFragment_ViewBinding implements Unbinder {
  private LoginFragment target;

  private View view2131231055;

  private View view2131230792;

  private View view2131230952;

  private View view2131230882;

  @UiThread
  public LoginFragment_ViewBinding(final LoginFragment target, View source) {
    this.target = target;

    View view;
    target.account = Utils.findRequiredViewAsType(source, R.id.account, "field 'account'", EditText.class);
    target.password = Utils.findRequiredViewAsType(source, R.id.password, "field 'password'", EditText.class);
    view = Utils.findRequiredView(source, R.id.sign_in, "field 'btnLogin' and method 'login'");
    target.btnLogin = Utils.castView(view, R.id.sign_in, "field 'btnLogin'", Button.class);
    view2131231055 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.login();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_to_register, "method 'toRegister'");
    view2131230792 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.toRegister();
      }
    });
    view = Utils.findRequiredView(source, R.id.meed_question, "method 'clickMeedQuestion'");
    view2131230952 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickMeedQuestion();
      }
    });
    view = Utils.findRequiredView(source, R.id.forget_password, "method 'clickForgetPassword'");
    view2131230882 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickForgetPassword();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.account = null;
    target.password = null;
    target.btnLogin = null;

    view2131231055.setOnClickListener(null);
    view2131231055 = null;
    view2131230792.setOnClickListener(null);
    view2131230792 = null;
    view2131230952.setOnClickListener(null);
    view2131230952 = null;
    view2131230882.setOnClickListener(null);
    view2131230882 = null;
  }
}
