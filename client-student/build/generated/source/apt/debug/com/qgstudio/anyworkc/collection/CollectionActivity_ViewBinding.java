// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.collection;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.widget.LoadingView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CollectionActivity_ViewBinding implements Unbinder {
  private CollectionActivity target;

  @UiThread
  public CollectionActivity_ViewBinding(CollectionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CollectionActivity_ViewBinding(CollectionActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.btnBack = Utils.findRequiredView(source, R.id.btn_back, "field 'btnBack'");
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycler_view, "field 'recyclerView'", RecyclerView.class);
    target.loadingView = Utils.findRequiredViewAsType(source, R.id.loading_view, "field 'loadingView'", LoadingView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CollectionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.btnBack = null;
    target.recyclerView = null;
    target.loadingView = null;
  }
}
