// Generated code from Butter Knife. Do not modify!
package com.itheima.zhbj74.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ContentFragment$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.zhbj74.fragment.ContentFragment target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968597, "field 'mViewPager'");
    target.mViewPager = (com.itheima.zhbj74.view.NoScrollViewPager) view;
    view = finder.findRequiredView(source, 2130968598, "field 'rgGroup'");
    target.rgGroup = (android.widget.RadioGroup) view;
  }

  public static void reset(com.itheima.zhbj74.fragment.ContentFragment target) {
    target.mViewPager = null;
    target.rgGroup = null;
  }
}
