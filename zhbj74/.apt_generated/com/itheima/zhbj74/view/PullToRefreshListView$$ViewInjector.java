// Generated code from Butter Knife. Do not modify!
package com.itheima.zhbj74.view;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PullToRefreshListView$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.zhbj74.view.PullToRefreshListView target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968608, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2130968619, "field 'tvTime'");
    target.tvTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2130968618, "field 'ivArrow'");
    target.ivArrow = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2130968587, "field 'pbProgress'");
    target.pbProgress = (android.widget.ProgressBar) view;
  }

  public static void reset(com.itheima.zhbj74.view.PullToRefreshListView target) {
    target.tvTitle = null;
    target.tvTime = null;
    target.ivArrow = null;
    target.pbProgress = null;
  }
}
