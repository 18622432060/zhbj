// Generated code from Butter Knife. Do not modify!
package com.itheima.zhbj74;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class NewsDetailActivity$$ViewInjector {
  public static void inject(Finder finder, final com.itheima.zhbj74.NewsDetailActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2130968586, "field 'mWebView'");
    target.mWebView = (android.webkit.WebView) view;
    view = finder.findRequiredView(source, 2130968645, "field 'btnTextSize'");
    target.btnTextSize = (android.widget.ImageButton) view;
    view = finder.findRequiredView(source, 2130968587, "field 'pbLoading'");
    target.pbLoading = (android.widget.ProgressBar) view;
    view = finder.findRequiredView(source, 2130968646, "field 'btnShare'");
    target.btnShare = (android.widget.ImageButton) view;
    view = finder.findRequiredView(source, 2130968644, "field 'llControl'");
    target.llControl = (android.widget.LinearLayout) view;
    view = finder.findRequiredView(source, 2130968643, "field 'btnBack'");
    target.btnBack = (android.widget.ImageButton) view;
    view = finder.findRequiredView(source, 2130968642, "field 'btnMenu'");
    target.btnMenu = (android.widget.ImageButton) view;
  }

  public static void reset(com.itheima.zhbj74.NewsDetailActivity target) {
    target.mWebView = null;
    target.btnTextSize = null;
    target.pbLoading = null;
    target.btnShare = null;
    target.llControl = null;
    target.btnBack = null;
    target.btnMenu = null;
  }
}
