// Generated code from Butter Knife. Do not modify!
package com.itheima.zhbj74.base.impl.menu;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class PhotosMenuDetailPager$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.zhbj74.base.impl.menu.PhotosMenuDetailPager target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968617, "field 'gvPhoto'");
    target.gvPhoto = (android.widget.GridView) view;
    view = finder.findRequiredView(source, 2130968616, "field 'lvPhoto'");
    target.lvPhoto = (android.widget.ListView) view;
  }

  public static void reset(com.itheima.zhbj74.base.impl.menu.PhotosMenuDetailPager target) {
    target.gvPhoto = null;
    target.lvPhoto = null;
  }
}
