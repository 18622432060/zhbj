package com.itheima.zhbj74.base.impl.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.itheima.zhbj74.MainActivity;
import com.itheima.zhbj.R;
import com.itheima.zhbj74.base.BaseMenuDetailPager;
import com.itheima.zhbj74.domain.NewsMenu.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;
/**
 * 菜单详情页-新闻
 * 
 * ViewPagerIndicator使用流程: 1.引入库 2.解决support-v4冲突(让两个版本一致) 3.从例子程序中拷贝布局文件
 * 4.从例子程序中拷贝相关代码(指示器和viewpager绑定; 重写getPageTitle返回标题) 5.在清单文件中增加样式 6.背景修改为白色
 * 7.修改样式-背景样式&文字样式
 * 
 * @author liupeng
 * @date 2017-10-18
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements OnPageChangeListener{

	@ViewInject(R.id.vp_news_menu_detail)
	private ViewPager mViewPager;
	
	@ViewInject(R.id.indicator)
	private TabPageIndicator mIndicator;

	private ArrayList<NewsTabData> mTabData;// 页签网络数据

	private ArrayList<TabDetailPager> mPagers;

	public NewsMenuDetailPager(Activity activity,
			ArrayList<NewsTabData> children) {
		super(activity);
		mTabData = children;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_news_menu_detail,
				null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		mPagers = new ArrayList<TabDetailPager>();
		for (NewsTabData nwd : mTabData) {
			TabDetailPager pager = new TabDetailPager(mActivity,nwd);
			mPagers.add(pager);
		}
		NewsMeunDetailAdapter adapter = new NewsMeunDetailAdapter();
		mViewPager.setAdapter(adapter);
		mIndicator.setViewPager(mViewPager);//将ViewPager和指示器绑定在一起。注意必须在设置数据后才能绑定
//		mViewPager.setOnPageChangeListener(this);
		mIndicator.setOnPageChangeListener(this);//// 此处必须给指示器设置页面监听,不能设置给viewpager
	}

	class NewsMeunDetailAdapter extends PagerAdapter {
		
		//指示器的标题
		@Override
		public CharSequence getPageTitle(int position) {
			NewsTabData data = mTabData.get(position);
			return data.title;
		}
		
		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager = mPagers.get(position);
			View view = pager.mRootView;
			container.addView(view);
			pager.initData();
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		
	}

	@Override
	public void onPageSelected(int position) {
		if(position==0){
			//开启侧边栏
			setSlidingMenuEnable(true);
		}else{
			//禁用侧边栏
			setSlidingMenuEnable(false);
		}
		if(position<mPagers.size()-1){
			mImagetButton.setVisibility(View.VISIBLE);
		}else{
			mImagetButton.setVisibility(View.INVISIBLE);
			mImagetButton.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int state) {
		
	}
	
	/**
	 * 开启或禁用侧边栏
	 * 
	 * @param enable
	 */
	protected void setSlidingMenuEnable(boolean enable) {
		// 获取侧边栏对象
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@ViewInject(R.id.btn_next)
	private ImageButton mImagetButton;
	
	@OnClick(R.id.btn_next)
	public void nextPage(View view) {
		// 跳到下个页面
		int currentItem = mViewPager.getCurrentItem();
		if(currentItem>=mTabData.size()-1){
			mImagetButton.setVisibility(View.INVISIBLE);
			mImagetButton.setVisibility(View.GONE);
			return;
		}
		mImagetButton.setVisibility(View.VISIBLE);
		currentItem++;
		mViewPager.setCurrentItem(currentItem);
	}
	
}
