// Generated code from Butter Knife. Do not modify!
package com.itheima.zhbj74.base.impl.menu;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TabDetailPager$ViewHolder$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.zhbj74.base.impl.menu.TabDetailPager.ViewHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968608, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2130968612, "field 'tvDate'");
    target.tvDate = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2130968611, "field 'ivIcon'");
    target.ivIcon = (android.widget.ImageView) view;
  }

  public static void reset(com.itheima.zhbj74.base.impl.menu.TabDetailPager.ViewHolder target) {
    target.tvTitle = null;
    target.tvDate = null;
    target.ivIcon = null;
  }
}
