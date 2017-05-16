package com.itheima.zhbj74;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.zhbj.R;
import com.itheima.zhbj74.utils.DensityUtils;
import com.itheima.zhbj74.utils.LogUtils;
import com.itheima.zhbj74.utils.PrefUtils;

/**
 * 新手引导页面
 * @author liupeng
 */
@SuppressWarnings("deprecation")
public class GuideActivity extends Activity {
	
	@InjectView(R.id.vp_guide)
	ViewPager mViewPager;
	@InjectView(R.id.ll_container)
	LinearLayout llContainer;
	@InjectView(R.id.iv_red_ponit)
	ImageView ivRedPoint;// 小红点
	@InjectView(R.id.btn_start)
	Button btnStart;
	
	private ArrayList<ImageView> mImageViewList; 
	
	//引导页图片id数组
	private int [] mImageIds = new int []{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

	// 小红点移动距离
	private int mPointDis;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题（必须在setContentView之前）
		setContentView(R.layout.activty_guide);
		ButterKnife.inject(this);
		initDate();
		mViewPager.setAdapter(new GuideAdapter());//设置布局
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// 某个页面被选中
				if (position == mImageViewList.size() - 1) {// 最后一个页面显示开始体验的按钮
					btnStart.setVisibility(View.VISIBLE);
				} else {
					btnStart.setVisibility(View.INVISIBLE);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {
				// 当页面滑动过程中的回调
				LogUtils.v("当前位置:" + position + ";移动偏移百分比:"+ positionOffset);
				// 更新小红点距离
				int leftMargin = (int) (mPointDis * positionOffset) + position * mPointDis;// 计算小红点当前的左边距
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
				params.leftMargin = leftMargin;// 修改左边距
				// 重新设置布局参数
				ivRedPoint.setLayoutParams(params);
				// 透明动画
				if(positionOffset<0.5){
					ivRedPoint.setAlpha(1-positionOffset*2);
				}else{
					ivRedPoint.setAlpha(1-(1-positionOffset)*2);
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// 页面状态发生变化的回调
			}
		});
		// 计算两个圆点的距离
		// 移动距离=第二个圆点left值 - 第一个圆点left值
		// measure->layout(确定位置)->draw(activity的onCreate方法执行结束之后才会走此流程)
		// mPointDis = llContainer.getChildAt(1).getLeft() - llContainer.getChildAt(0).getLeft();
	
		// 监听layout方法结束的事件,位置确定好之后再获取圆点间距
		// 视图树
		ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				// 移除监听,避免重复回调
				ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// ivRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);//高版本可用layout方法执行结束的回调
				mPointDis = llContainer.getChildAt(1).getLeft()- llContainer.getChildAt(0).getLeft();
				LogUtils.v("圆点距离:" + mPointDis);
			}
		});
		
		btnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//更新sp, 已经不是第一次进入了
				PrefUtils.setBoolean(getApplicationContext(), "is_first_enter", false);
				//跳到主页面
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});	
	}
	
	//初始化数据
	private void initDate(){
		mImageViewList = new ArrayList<ImageView>(); 
		for(int i=0;i<mImageIds.length;i++){
			ImageView view = new ImageView(this);
			view.setBackgroundResource(mImageIds[i]);//通过设置背景可以让宽高填充布局
//			view.setImageResource(mImageIds[i]);//根据图片自己的宽高
			mImageViewList.add(view);
			//初始化小圆点
			ImageView point = new ImageView(this);
			point.setImageResource(R.drawable.shape_point_gray);//设置图片(shape形状)
			// 初始化布局参数, 宽高包裹内容,父控件是谁,就是谁声明的布局参数
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			if(i>0){
				params.leftMargin = DensityUtils.dip2px(10,this);
			}
			llContainer.addView(point);
			point.setLayoutParams(params);
		}
	}
	
	private class GuideAdapter extends PagerAdapter{

		//item个数
		@Override
		public int getCount() {
			return mImageViewList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}
		
		//初始化item布局
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView view = mImageViewList.get(position);
			container.addView(view);
			return view;
		}
		
		//销毁item
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
}