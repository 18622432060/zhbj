package com.itheima.zhbj74.base.impl.menu;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.google.gson.Gson;
import com.itheima.zhbj.R;
import com.itheima.zhbj74.NewsDetailActivity;
import com.itheima.zhbj74.base.BaseMenuDetailPager;
import com.itheima.zhbj74.domain.NewsMenu.NewsTabData;
import com.itheima.zhbj74.domain.NewsTabBean;
import com.itheima.zhbj74.domain.NewsTabBean.NewsData;
import com.itheima.zhbj74.domain.NewsTabBean.TopNews;
import com.itheima.zhbj74.global.GlobalConstants;
import com.itheima.zhbj74.utils.CacheUtils;
import com.itheima.zhbj74.utils.LogUtils;
import com.itheima.zhbj74.utils.PrefUtils;
import com.itheima.zhbj74.view.PullToRefreshListView;
import com.itheima.zhbj74.view.PullToRefreshListView.onRefreshListener;
import com.itheima.zhbj74.view.TopNewsViewPager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * 单个页签
 * @author liupeng
 */
public class TabDetailPager extends BaseMenuDetailPager {

	private NewsTabData mTabData;

	private View view;

	@InjectView(R.id.vp_top_news)
	TopNewsViewPager mViewPager;

	@InjectView(R.id.tv_title)
	TextView tvTitle;

	@InjectView(R.id.indicator)
	CirclePageIndicator mIndicator;

	@ViewInject(R.id.lv_list)
	PullToRefreshListView lvList;

	private String mUrl;

	private ArrayList<TopNews> mTopnews;
	private ArrayList<NewsData> mNewsList;

	private NewsAdapter mNewsAdapter;

	private String mMoreUrl;// 下一页数据连接
	
	private Handler mHandler;

	public TabDetailPager(Activity activity, NewsTabData nwd) {
		super(activity);
		mTabData = nwd;
		mUrl = GlobalConstants.SERVER_URL + mTabData.url;
	}

	@Override
	public View initView() {
		view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
		ViewUtils.inject(this, view);

		View mHeaderView = View.inflate(mActivity, R.layout.list_item_header, null);
		ButterKnife.inject(this, mHeaderView);// 此处必须将头布局也注入

		lvList.addHeaderView(mHeaderView);
		// 5.设置回调
		lvList.setOnRefreshListener(new onRefreshListener() {
			@Override
			public void onRefresh() {
				getDataFromServer();
			}

			@Override
			public void onLoadMore() {
				// 判断是否有下一页数据
				if (mMoreUrl != null) {
					// 有下一页
					getMoreDataFromServer();
				} else {
					// 没有下一页
					Toast.makeText(mActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
					// 没有数据时也要收起控件
					lvList.onRefreshComplete(true);
				}
			}
		});
		
		lvList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				LogUtils.v("第"+(position-2)+"个被点击了 ");
				int headerViewsCount = lvList.getHeaderViewsCount();//获取头布局数量
				position=position-headerViewsCount;//需要减去头布局的占位
				LogUtils.v("第"+position+"个被点击了 ");
				NewsData news = mNewsList.get(position);
			    String readIds =	PrefUtils.getString(mActivity,"read_ids","");
			    if(!readIds.contains(""+news.id)){//只有不包含当前ID才追加
				    readIds += news.id +",";
			    }
			    PrefUtils.setString(mActivity,"read_ids",readIds);
			    TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
			    tvTitle.setTextColor(Color.GRAY);
			    //跳到新闻详情页面
			    Intent intent = new Intent(mActivity,NewsDetailActivity.class);
			    intent.putExtra("url",news.url.replace("http://10.0.2.2:8080/zhbj", GlobalConstants.SERVER_URL));
			    mActivity.startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public void initData() {
		String cache = CacheUtils.getCache(mUrl, mActivity);
		if (!TextUtils.isEmpty(cache)) {
			processData(cache, false);
		}
		getDataFromServer();
	}

	/**
	 * 
	 * 从服务器获取数据
	 * 
	 * @author liupeng
	 * @date 2017-2-10
	 * @return void
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;// 获取服务器返回结果
				LogUtils.v("服务器返回结果:" + result);
				processData(result, false);
				CacheUtils.setCache(mUrl, result, mActivity);
				lvList.onRefreshComplete(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				error.printStackTrace();
				lvList.onRefreshComplete(false);
			}
		});
	}

	/**
	 * 加载下一页数据
	 */
	protected void getMoreDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				processData(result, true);
				// 收起底部加载更多
				lvList.onRefreshComplete(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				error.printStackTrace();
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				// 收起底部加载更多
				lvList.onRefreshComplete(false);
			}
		});
	}

	/**
	 * 解析数据
	 */
	protected void processData(String json, boolean isMore) {
		Gson gson = new Gson();
		NewsTabBean mNewsTabBean = gson.fromJson(json, NewsTabBean.class);
		String moreUrl = mNewsTabBean.data.more;
		if (!TextUtils.isEmpty(moreUrl)) {
			mMoreUrl = GlobalConstants.SERVER_URL + moreUrl;
		} else {
			mMoreUrl = null;
		}
		if (!isMore) {
			// 头条新闻填充数据
			mTopnews = mNewsTabBean.data.topnews;
			if (mTopnews != null && mTopnews.size() > 0) {
				mViewPager.setAdapter(new TopNewsAdapter());
				mIndicator.setViewPager(mViewPager);
				mIndicator.setSnap(true);// 快照方式展示
				// 时间要设置给mIndicator
				mIndicator.setOnPageChangeListener(new OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						TopNews topNews = mTopnews.get(position);
						tvTitle.setText(topNews.title);

					}

					@Override
					public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

					}

					@Override
					public void onPageScrollStateChanged(int state) {

					}
				});
				tvTitle.setText(mTopnews.get(0).title);
				mIndicator.onPageSelected(0);// 默认让第一个选中（页面销毁后重新初始化时仍然保持上次位置）
			}
			// 列表新闻
			mNewsList = mNewsTabBean.data.news;
			if (mNewsList != null) {
				mNewsAdapter = new NewsAdapter();
				lvList.setAdapter(mNewsAdapter);
			}
			if(mHandler==null){
				mHandler =  new Handler(){
					public void handleMessage(Message msg) {
						int currentItem = mViewPager.getCurrentItem();
						if(currentItem == mTopnews.size()-1){
							currentItem = -1;//如果最后跳到第一个
						}
						currentItem ++;
						mViewPager.setCurrentItem(currentItem);
						mHandler.sendEmptyMessageDelayed(0,3000);//发送延时3秒消息
					}
				};
				//保证轮播只执行一次
				mHandler.sendEmptyMessageDelayed(0,3000);//发送延时3秒消息
				mViewPager.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
							case MotionEvent.ACTION_DOWN:
								// 停止广告自动轮播
								// 删除handler的所有消息
								mHandler.removeCallbacksAndMessages(null);
								// mHandler.post(new Runnable() {
								// @Override
								// public void run() {
								// //在主线程运行
								// }
								// });
								break;
							case MotionEvent.ACTION_CANCEL:// 取消事件,
								// 当按下viewpager后,直接滑动listview,导致抬起事件无法响应,但会走此事件
								LogUtils.v("ACTION_CANCEL");
								// 启动广告
								mHandler.sendEmptyMessageDelayed(0, 3000);
								break;
							case MotionEvent.ACTION_UP:
								LogUtils.v("ACTION_UP");
								// 启动广告
								mHandler.sendEmptyMessageDelayed(0, 3000);
								break;
							default:
								break;
						}
						return false;
					}
				});
			}
		} else {
			// 加载更多数据
			ArrayList<NewsData> moreNews = mNewsTabBean.data.news;
			mNewsList.addAll(moreNews);// 将数据追加在原来的集合中
			// 刷新listview
			mNewsAdapter.notifyDataSetChanged();
		}
	}

	// 头条新闻适配器
	private class TopNewsAdapter extends PagerAdapter {

		private BitmapUtils mBitmapUtils;

		public TopNewsAdapter() {
			mBitmapUtils = new BitmapUtils(mActivity);
			mBitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);// 设置加载中的默认图片（为了更好的效果展示
		}

		@Override
		public int getCount() {
			return mTopnews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView view = new ImageView(mActivity);
			view.setScaleType(ScaleType.FIT_XY);// 设置图片缩放方式, 宽高填充父控件
			TopNews topNews = mTopnews.get(position);
			// 下载图片-将图片设置给imageview-避免内存溢出-缓存(自动帮你缓存)
			// BitmapUtils-XUtils
			mBitmapUtils.display(view, topNews.topimage.replace("http://10.0.2.2:8080/zhbj", GlobalConstants.SERVER_URL));
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	private class NewsAdapter extends BaseAdapter {

		private BitmapUtils mBitmapUtils;

		public NewsAdapter() {
			mBitmapUtils = new BitmapUtils(mActivity);
			mBitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
		}

		@Override
		public int getCount() {
			return mNewsList.size();
		}

		@Override
		public NewsData getItem(int position) {
			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.list_item_news,null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			NewsData news = getItem(position);
			holder.tvTitle.setText(news.title);
			holder.tvDate.setText(news.pubdate.substring(0, 10) + " " + news.pubdate.substring(10, news.pubdate.length()));
			mBitmapUtils.display(holder.ivIcon, news.listimage.replace("http://10.0.2.2:8080/zhbj", GlobalConstants.SERVER_URL));
			//初始化标记颜色
		    String readIds = PrefUtils.getString(mActivity,"read_ids","");
		    if(readIds.contains(""+news.id)){
		    	holder.tvTitle.setTextColor(Color.GRAY);
		    }else{
		    	holder.tvTitle.setTextColor(Color.BLACK);
		    }
			return convertView;
		}

	}

	static class ViewHolder {
		
		@InjectView(R.id.iv_icon)
		ImageView ivIcon;
		@InjectView(R.id.tv_title)
		TextView tvTitle;
		@InjectView(R.id.tv_date)
		TextView tvDate;
		
		public ViewHolder(View view) {
			ButterKnife.inject(this,view);
		}
	}

}