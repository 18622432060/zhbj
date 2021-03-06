package com.itheima.zhbj74.base.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.zhbj74.MainActivity;
import com.itheima.zhbj74.base.BaseMenuDetailPager;
import com.itheima.zhbj74.base.BasePager;
import com.itheima.zhbj74.base.impl.menu.InteractMenuDetailPager;
import com.itheima.zhbj74.base.impl.menu.NewsMenuDetailPager;
import com.itheima.zhbj74.base.impl.menu.PhotosMenuDetailPager;
import com.itheima.zhbj74.base.impl.menu.TopicMenuDetailPager;
import com.itheima.zhbj74.domain.NewsMenu;
import com.itheima.zhbj74.fragment.LeftMenuFragment;
import com.itheima.zhbj74.global.GlobalConstants;
import com.itheima.zhbj74.utils.CacheUtils;
import com.itheima.zhbj74.utils.LogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 新闻中心
 * @author liupeng
 * @date 2017-10-18
 */
public class NewsCenterPager extends BasePager {

	private ArrayList<BaseMenuDetailPager> mMenuDetailPagers;
	
	private NewsMenu mNewsData;// 分类信息网络数据

	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		LogUtils.v("首页初始化啦...");
		// 修改页面标题
		tvTitle.setText("智慧北京");
		// 隐藏菜单按钮
		btnMenu.setVisibility(View.GONE);
		// 显示菜单按钮
		btnMenu.setVisibility(View.VISIBLE);
		// 先判断有没有缓存,如果有的话,就加载缓存
		String cache = CacheUtils.getCache(GlobalConstants.CATEGORY_URL,mActivity);
		if (!TextUtils.isEmpty(cache)) {
			LogUtils.v("发现缓存啦...");
			processData(cache);
		}else{
			getDataFromServer();
		}
	}

	/**
	 * 从服务器获取数据
	 * 
	 * @author liupeng
	 * @date 2017-2-10
	 * @return void
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalConstants.CATEGORY_URL,new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;// 获取服务器返回结果
				LogUtils.v("服务器返回结果:" + result);
				// JsonObject, Gson
				processData(result);
				// 写缓存
				CacheUtils.setCache(GlobalConstants.CATEGORY_URL,result, mActivity);
			}
	
			@Override
			public void onFailure(HttpException error, String msg) {
				error.printStackTrace();
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	/**
	 * 解析数据
	 */
	protected void processData(String json) {
		Gson gson = new Gson();
		mNewsData = gson.fromJson(json, NewsMenu.class);
		LogUtils.v("解析结果:" + mNewsData);
		// 获取侧边栏对象
		MainActivity mainUI = (MainActivity) mActivity;
		LeftMenuFragment fragment = mainUI.getLeftMenuFragment();
		// 给侧边栏设置数据
		fragment.setMenuData(mNewsData.data);
		// 初始化4个菜单详情页
		mMenuDetailPagers = new ArrayList<BaseMenuDetailPager>();
		mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity, mNewsData.data.get(0).children));
		mMenuDetailPagers.add(new TopicMenuDetailPager(mActivity));
		mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity,btnPhoto));
		mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));
		// 将新闻菜单详情页设置为默认页面
		setCurrentDetailPager(0);
	}

	// 设置菜单详情页
	public void setCurrentDetailPager(int position) {
		// 重新给frameLayout添加内容
		BaseMenuDetailPager pager = mMenuDetailPagers.get(position);// 获取当前应该显示的页面
		View view = pager.mRootView;// 当前页面的布局
		// 清除之前旧的布局
		flContent.removeAllViews();
		flContent.addView(view);// 给帧布局添加布局
		// 初始化页面数据
		pager.initData();
		// 更新标题
		tvTitle.setText(mNewsData.data.get(position).title);
		if(pager instanceof PhotosMenuDetailPager){
			btnPhoto.setVisibility(View.VISIBLE);
		}else{
			//隐藏切换按钮
			btnPhoto.setVisibility(View.GONE);
		}
	}

}