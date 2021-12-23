// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.workout;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import com.qgstudio.anyworkc.widget.LoadingView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WorkoutCatalogFragment_ViewBinding implements Unbinder {
  private WorkoutCatalogFragment target;

  private View view2131230779;

  private View view2131230789;

  @UiThread
  public WorkoutCatalogFragment_ViewBinding(final WorkoutCatalogFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.workout_frag_toolbar, "field 'toolbar'", Toolbar.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recycler_view_workout, "field 'recyclerView'", RecyclerView.class);
    target.loadingView = Utils.findRequiredViewAsType(source, R.id.loading_view, "field 'loadingView'", LoadingView.class);
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'clickBack'");
    view2131230779 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickBack();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_search, "method 'clickSearch'");
    view2131230789 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickSearch();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    WorkoutCatalogFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.recyclerView = null;
    target.loadingView = null;

    view2131230779.setOnClickListener(null);
    view2131230779 = null;
    view2131230789.setOnClickListener(null);
    view2131230789 = null;
  }
}
