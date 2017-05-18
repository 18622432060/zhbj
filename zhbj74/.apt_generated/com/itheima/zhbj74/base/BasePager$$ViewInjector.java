// Generated code from Butter Knife. Do not modify!
package com.itheima.zhbj74.base;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BasePager$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.zhbj74.base.BasePager target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968608, "field 'tvTitle'");
    target.tvTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2130968596, "field 'flContent'");
    target.flContent = (android.widget.FrameLayout) view;
    view = finder.findRequiredView(source, 2130968642, "field 'btnMenu'");
    target.btnMenu = (android.widget.ImageButton) view;
    view = finder.findRequiredView(source, 2130968647, "field 'btnPhoto'");
    target.btnPhoto = (android.widget.ImageButton) view;
  }

  public static void reset(com.itheima.zhbj74.base.BasePager target) {
    target.tvTitle = null;
    target.flContent = null;
    target.btnMenu = null;
    target.btnPhoto = null;
  }
}
