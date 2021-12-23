// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.notice;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NoticeAdapter$ViewHolder_ViewBinding implements Unbinder {
  private NoticeAdapter.ViewHolder target;

  @UiThread
  public NoticeAdapter$ViewHolder_ViewBinding(NoticeAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tvNtContent = Utils.findRequiredViewAsType(source, R.id.tv_content_item_notice, "field 'tvNtContent'", TextView.class);
    target.tvNtName = Utils.findRequiredViewAsType(source, R.id.tv_name_item_notice, "field 'tvNtName'", TextView.class);
    target.tvNtTime = Utils.findRequiredViewAsType(source, R.id.tv_time_item_notice, "field 'tvNtTime'", TextView.class);
    target.mgNtRead = Utils.findRequiredViewAsType(source, R.id.image_view_item_notice, "field 'mgNtRead'", ImageView.class);
    target.clClickItem = Utils.findRequiredViewAsType(source, R.id.item_click, "field 'clClickItem'", ConstraintLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NoticeAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvNtContent = null;
    target.tvNtName = null;
    target.tvNtTime = null;
    target.mgNtRead = null;
    target.clClickItem = null;
  }
}
