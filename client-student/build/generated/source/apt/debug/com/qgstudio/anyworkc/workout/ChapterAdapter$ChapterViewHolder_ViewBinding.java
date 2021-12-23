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

public class ChapterAdapter$ChapterViewHolder_ViewBinding implements Unbinder {
  private ChapterAdapter.ChapterViewHolder target;

  @UiThread
  public ChapterAdapter$ChapterViewHolder_ViewBinding(ChapterAdapter.ChapterViewHolder target,
      View source) {
    this.target = target;

    target.tvChapterTittle = Utils.findRequiredViewAsType(source, R.id.chapter_tittle, "field 'tvChapterTittle'", TextView.class);
    target.card = Utils.findRequiredView(source, R.id.card_view, "field 'card'");
  }

  @Override
  @CallSuper
  public void unbind() {
    ChapterAdapter.ChapterViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvChapterTittle = null;
    target.card = null;
  }
}
