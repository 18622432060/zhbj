package com.itheima.zhbj74.base;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.zhbj.R;
import com.itheima.zhbj74.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 五个标签页基类
 * @author liupeng
 */
public abstract class BasePager {

	public Activity mActivity;
	@InjectView(R.id.tv_title)
	public TextView tvTitle;
	@InjectView(R.id.btn_menu)
	public ImageButton btnMenu;
	@InjectView(R.id.fl_content)
	public FrameLayout flContent;// 空的帧布局对象, 要动态添加布局
	@InjectView(R.id.btn_photo)
	public ImageButton btnPhoto;
	public View mRootView;// 当前页面的布局对象

	public BasePager(Activity activity) {
		mActivity = activity;
		mRootView = initView();
	}

	public View initView() {
		View view = View.inflate(mActivity, R.layout.base_pager, null);
		ButterKnife.inject(this, view);
		btnMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		return view;
	}
	
	protected void toggle() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();
	}

	public abstract void initData() ;

}