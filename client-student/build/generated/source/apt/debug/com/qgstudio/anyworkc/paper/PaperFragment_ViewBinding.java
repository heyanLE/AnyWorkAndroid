// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.paper;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PaperFragment_ViewBinding implements Unbinder {
  private PaperFragment target;

  @UiThread
  public PaperFragment_ViewBinding(PaperFragment target, View source) {
    this.target = target;

    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.recycler_paper, "field 'mRecyclerView'", RecyclerView.class);
    target.mSwipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.refresh, "field 'mSwipeRefreshLayout'", SwipeRefreshLayout.class);
    target.mBg = Utils.findRequiredViewAsType(source, R.id.bg, "field 'mBg'", RelativeLayout.class);
    target.mImg = Utils.findRequiredViewAsType(source, R.id.img, "field 'mImg'", ImageView.class);
    target.mTv = Utils.findRequiredViewAsType(source, R.id.tv, "field 'mTv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PaperFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRecyclerView = null;
    target.mSwipeRefreshLayout = null;
    target.mBg = null;
    target.mImg = null;
    target.mTv = null;
  }
}
