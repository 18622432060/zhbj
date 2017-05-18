// Generated code from Butter Knife. Do not modify!
package com.itheima.zhbj74.base.impl.menu;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class NewsMenuDetailPager$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.zhbj74.base.impl.menu.NewsMenuDetailPager target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968615, "field 'mViewPager'");
    target.mViewPager = (android.support.v4.view.ViewPager) view;
    view = finder.findRequiredView(source, 2130968609, "field 'mIndicator'");
    target.mIndicator = (com.viewpagerindicator.TabPageIndicator) view;
    view = finder.findRequiredView(source, 2130968614, "field 'mImagetButton' and method 'nextPage'");
    target.mImagetButton = (android.widget.ImageButton) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.nextPage(p0);
        }
      });
  }

  public static void reset(com.itheima.zhbj74.base.impl.menu.NewsMenuDetailPager target) {
    target.mViewPager = null;
    target.mIndicator = null;
    target.mImagetButton = null;
  }
}
