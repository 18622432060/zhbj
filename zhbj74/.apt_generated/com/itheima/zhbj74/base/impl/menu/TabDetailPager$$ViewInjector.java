// Generated code from Butter Knife. Do not modify!
package com.itheima.zhbj74.base.impl.menu;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TabDetailPager$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.zhbj74.base.impl.menu.TabDetailPager target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968609, "field 'mIndicator'");
    target.mIndicator = (com.viewpagerindicator.CirclePageIndicator) view;
    view = finder.findRequiredView(source, 2130968608, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2130968606, "field 'mViewPager'");
    target.mViewPager = (com.itheima.zhbj74.view.TopNewsViewPager) view;
  }

  public static void reset(com.itheima.zhbj74.base.impl.menu.TabDetailPager target) {
    target.mIndicator = null;
    target.tvTitle = null;
    target.mViewPager = null;
  }
}
