// Generated code from Butter Knife. Do not modify!
package com.itheima.zhbj74;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GuideActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.zhbj74.GuideActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968592, "field 'btnStart'");
    target.btnStart = (android.widget.Button) view;
    view = finder.findRequiredView(source, 2130968591, "field 'mViewPager'");
    target.mViewPager = (android.support.v4.view.ViewPager) view;
    view = finder.findRequiredView(source, 2130968593, "field 'llContainer'");
    target.llContainer = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2130968594, "field 'ivRedPoint'");
    target.ivRedPoint = (android.widget.ImageView) view;
  }

  public static void reset(com.itheima.zhbj74.GuideActivity target) {
    target.btnStart = null;
    target.mViewPager = null;
    target.llContainer = null;
    target.ivRedPoint = null;
  }
}
