package com.itheima.zhbj74;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.itheima.zhbj.R;
import com.itheima.zhbj74.utils.PrefUtils;

/**
 * @author liupeng
 */
public class SplashActivity extends Activity {

	Context myContext;
	@InjectView(R.id.rl_root)
	RelativeLayout rlRoot;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myContext = this;
		setContentView(R.layout.activity_splash);
		ButterKnife.inject(this);

		// 旋转动画
		RotateAnimation animRotate = new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,0.5F);
		animRotate.setDuration(1500);//动画时间
		animRotate.setFillAfter(true);//保持动画结束状态
		// 缩放动画
		ScaleAnimation animaScale = new ScaleAnimation(0, 1, 0, 1,Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,0.5F);
		animaScale.setDuration(1500);
		animaScale.setFillAfter(true);
		// 透明动画
		AlphaAnimation animaAlpha = new AlphaAnimation(0, 1);
		animaAlpha.setDuration(2500);
		animaAlpha.setFillAfter(true);

		// 动画集合
		AnimationSet set = new AnimationSet(true);// 初始化动画集合
		set.addAnimation(animRotate);
		set.addAnimation(animaScale);
		set.addAnimation(animaAlpha);

		// 启动动画
		rlRoot.startAnimation(set);

		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
 
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				//第一次进入
				Intent intent;
				if(PrefUtils.getBoolean(myContext,"is_first_enter", true)){
					//新手引导
					intent = new Intent(getApplicationContext(),GuideActivity.class);
				}else{
					//主页面
					intent = new Intent(getApplicationContext(),MainActivity.class);
				}
				startActivity(intent);
				finish();  
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}