// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.user;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChangePasswordActivity_ViewBinding implements Unbinder {
  private ChangePasswordActivity target;

  @UiThread
  public ChangePasswordActivity_ViewBinding(ChangePasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChangePasswordActivity_ViewBinding(ChangePasswordActivity target, View source) {
    this.target = target;

    target.etOldPw = Utils.findRequiredViewAsType(source, R.id.editText_old, "field 'etOldPw'", EditText.class);
    target.etNewPw = Utils.findRequiredViewAsType(source, R.id.editText_new, "field 'etNewPw'", EditText.class);
    target.etAgainPw = Utils.findRequiredViewAsType(source, R.id.editText_again, "field 'etAgainPw'", EditText.class);
    target.btSave = Utils.findRequiredViewAsType(source, R.id.button_save, "field 'btSave'", Button.class);
    target.IgCancel = Utils.findRequiredViewAsType(source, R.id.image_cancel, "field 'IgCancel'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChangePasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etOldPw = null;
    target.etNewPw = null;
    target.etAgainPw = null;
    target.btSave = null;
    target.IgCancel = null;
  }
}
