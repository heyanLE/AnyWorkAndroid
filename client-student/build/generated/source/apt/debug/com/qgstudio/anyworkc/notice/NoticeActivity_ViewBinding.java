// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.notice;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NoticeActivity_ViewBinding implements Unbinder {
  private NoticeActivity target;

  @UiThread
  public NoticeActivity_ViewBinding(NoticeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NoticeActivity_ViewBinding(NoticeActivity target, View source) {
    this.target = target;

    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.refresh_layout_notice, "field 'refreshLayout'", SmartRefreshLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycler_view_notice, "field 'recyclerView'", RecyclerView.class);
    target.imageBack = Utils.findRequiredViewAsType(source, R.id.img_back, "field 'imageBack'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NoticeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.refreshLayout = null;
    target.recyclerView = null;
    target.imageBack = null;
  }
}
