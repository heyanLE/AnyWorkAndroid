// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.workout;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChapterAdapter$CatalogViewHolder_ViewBinding implements Unbinder {
  private ChapterAdapter.CatalogViewHolder target;

  @UiThread
  public ChapterAdapter$CatalogViewHolder_ViewBinding(ChapterAdapter.CatalogViewHolder target,
      View source) {
    this.target = target;

    target.tvCatalogTittle = Utils.findRequiredViewAsType(source, R.id.catalog_tittle, "field 'tvCatalogTittle'", TextView.class);
    target.tvCatalogTime = Utils.findRequiredViewAsType(source, R.id.catalog_time, "field 'tvCatalogTime'", TextView.class);
    target.stateTab = Utils.findRequiredViewAsType(source, R.id.tv_workout_state_tab, "field 'stateTab'", TextView.class);
    target.card = Utils.findRequiredView(source, R.id.card, "field 'card'");
    target.btnRank = Utils.findRequiredView(source, R.id.btn_rank, "field 'btnRank'");
  }

  @Override
  @CallSuper
  public void unbind() {
    ChapterAdapter.CatalogViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvCatalogTittle = null;
    target.tvCatalogTime = null;
    target.stateTab = null;
    target.card = null;
    target.btnRank = null;
  }
}
