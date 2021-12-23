// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.paper;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PaperAdapter$Holder_ViewBinding implements Unbinder {
  private PaperAdapter.Holder target;

  @UiThread
  public PaperAdapter$Holder_ViewBinding(PaperAdapter.Holder target, View source) {
    this.target = target;

    target.civ = Utils.findRequiredViewAsType(source, R.id.civ_paper, "field 'civ'", CircleImageView.class);
    target.tv_title = Utils.findRequiredViewAsType(source, R.id.textView5, "field 'tv_title'", TextView.class);
    target.tv_chapter = Utils.findRequiredViewAsType(source, R.id.textView6, "field 'tv_chapter'", TextView.class);
    target.tv_type = Utils.findRequiredViewAsType(source, R.id.textView7, "field 'tv_type'", TextView.class);
    target.tv_date = Utils.findRequiredViewAsType(source, R.id.textView8, "field 'tv_date'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PaperAdapter.Holder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.civ = null;
    target.tv_title = null;
    target.tv_chapter = null;
    target.tv_type = null;
    target.tv_date = null;
  }
}
