// Generated code from Butter Knife. Do not modify!
package com.qgstudio.anyworkc.paper;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.qgstudio.anyworkc.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PaperActivity_ViewBinding implements Unbinder {
  private PaperActivity target;

  private View view2131230779;

  @UiThread
  public PaperActivity_ViewBinding(PaperActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PaperActivity_ViewBinding(final PaperActivity target, View source) {
    this.target = target;

    View view;
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mTabLayout = Utils.findRequiredViewAsType(source, R.id.tab, "field 'mTabLayout'", TabLayout.class);
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.pager, "field 'mViewPager'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.btn_back, "method 'back'");
    view2131230779 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.back();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PaperActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mTabLayout = null;
    target.mViewPager = null;

    view2131230779.setOnClickListener(null);
    view2131230779 = null;
  }
}
