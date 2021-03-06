package com.itheima.zhbj74.base;

import android.app.Activity;
import android.view.View;

/**
 * 菜单详情页基类
 * @author liupeng
 */
public abstract class BaseMenuDetailPager {

	public Activity mActivity;
	public View mRootView;// 当前页面的布局对象

	public BaseMenuDetailPager(Activity activity) {
		mActivity = activity;
		mRootView = initView();
	}
	
	//初始化布局，必须子类实现
	public abstract View initView();

	public void initData() {

	}

}