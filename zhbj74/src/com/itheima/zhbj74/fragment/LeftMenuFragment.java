package com.itheima.zhbj74.fragment;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.zhbj74.MainActivity;
import com.itheima.zhbj74.R;
import com.itheima.zhbj74.base.impl.NewsCenterPager;
import com.itheima.zhbj74.domain.NewsMenu.NewsMenuData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 侧边栏fragment
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class LeftMenuFragment extends BaseFragment {

	@ViewInject(R.id.lv_list)
	private ListView lvList;

	private ArrayList<NewsMenuData> mNewsMenuData;// 侧边栏网络数据
	
	private int mCurrentPos;//当前被选中的item的位置
	
	private LeftMenuAdpater mAdpater;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		return view;
	}

	@Override
	public void initData() {


	}

	// 给侧边栏设置数据
	public void setMenuData(ArrayList<NewsMenuData> data) {
		// 更新页面
		mNewsMenuData = data;
		mAdpater = new LeftMenuAdpater();
		lvList.setAdapter(mAdpater);
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				mCurrentPos = position  ;
				mAdpater.notifyDataSetChanged();//刷新当前所选的位置
				toggle();
				
				setCurrentDetailPager(position);
			}
		
		});
	}
	
	/**
	 * 设置当前详情页
	 * @param position
	 * @author Administrator
	 * @date 2017-2-10 
	 * @return void
	 */
	protected void setCurrentDetailPager(int position) {
		// 获取新闻中心的对象
		MainActivity mainUI = (MainActivity) mActivity;
		// 获取ContentFragment
		ContentFragment fragment = mainUI.getContentFragment();
		// 获取NewsCenterPager
		NewsCenterPager newsCenterPager = fragment.getNewsCenterPager();
		// 修改新闻中心的FrameLayout的布局
		newsCenterPager.setCurrentDetailPager(position);
	}

	protected void toggle() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();
	}
	
	class LeftMenuAdpater extends BaseAdapter {

		@Override
		public int getCount() {
			return mNewsMenuData.size();
		}

		@Override
		public NewsMenuData getItem(int position) {
			return mNewsMenuData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {// 多的话需要重用
			View view = View.inflate(mActivity, R.layout.list_item_left_menu,null);
			TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu);
			
			NewsMenuData item = getItem(position); 
			tvMenu.setText(item.title);
			
			if(position == mCurrentPos){
				tvMenu.setEnabled(true);
			}else{
				tvMenu.setEnabled(false);
			}
			
			return view;
		}
	}

}
