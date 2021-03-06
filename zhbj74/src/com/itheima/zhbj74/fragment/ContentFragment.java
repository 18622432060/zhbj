package com.itheima.zhbj74.fragment;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import butterknife.InjectView;

import com.itheima.zhbj.R;
import com.itheima.zhbj74.MainActivity;
import com.itheima.zhbj74.base.BasePager;
import com.itheima.zhbj74.base.impl.GovAffairsPager;
import com.itheima.zhbj74.base.impl.HomePager;
import com.itheima.zhbj74.base.impl.NewsCenterPager;
import com.itheima.zhbj74.base.impl.SettingPager;
import com.itheima.zhbj74.base.impl.SmartServicePager;
import com.itheima.zhbj74.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 主页面fragment
 * 
 * @author liupeng
 * @date 2017-10-18
 */
public class ContentFragment extends BaseFragment {
	
	@InjectView(R.id.vp_content)
	NoScrollViewPager mViewPager;
	@InjectView(R.id.rg_group)
	RadioGroup rgGroup;

	private ArrayList<BasePager> mPagers;// 五个标签页集合

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		return view;
	}

	@Override
	public void initData() {
		mPagers = new ArrayList<BasePager>();
		// 添加五个标签页
		mPagers.add(new HomePager(mActivity));
		mPagers.add(new NewsCenterPager(mActivity));
		mPagers.add(new SmartServicePager(mActivity));
		mPagers.add(new GovAffairsPager(mActivity));
		mPagers.add(new SettingPager(mActivity));

		mViewPager.setAdapter(new ContentAdapter());

		// 底栏标签切换监听
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.rb_home:
						// 首页
						// mViewPager.setCurrentItem(0);
						mViewPager.setCurrentItem(0, false);// 参2:表示是否具有滑动动画
						break;
					case R.id.rb_news:
						// 新闻中心
						mViewPager.setCurrentItem(1, false);
						break;
					case R.id.rb_smart:
						// 智慧服务
						mViewPager.setCurrentItem(2, false);
						break;
					case R.id.rb_gov:
						// 政务
						mViewPager.setCurrentItem(3, false);
						break;
					case R.id.rb_setting:
						// 设置
						mViewPager.setCurrentItem(4, false);
						break;
					default:
						break;
				}
			}
		});

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				BasePager pager = mPagers.get(position);
				pager.initData();
				if (position == 0 || position == mPagers.size() - 1) {
					// 首页和设置页要禁用侧边栏
					setSlidingMenuEnable(false);
				} else {
					// 其他页面开启侧边栏
					setSlidingMenuEnable(true);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		// 手动加载第一页数据
		mPagers.get(0).initData();
		// 首页禁用侧边栏
		setSlidingMenuEnable(false);
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

	private class ContentAdapter extends PagerAdapter {

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
			BasePager pager = mPagers.get(position);
			View view = pager.mRootView;// 获取当前页面对象的布局
			// pager.initData();// 初始化数据,viewPage默认加载下一页为了流量和性能不在此处调用
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	public NewsCenterPager getNewsCenterPager() {
		NewsCenterPager pager = (NewsCenterPager) mPagers.get(1);
		return pager;
	}

}