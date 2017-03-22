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

import com.itheima.zhbj74.utils.PrefUtils;

public class SplashActivity extends Activity {

	
	Context myContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myContext = this;
		setContentView(R.layout.activity_splash);
		RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_root);// ��ȡ��Բ���
		// ��ת����
		RotateAnimation animRotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
				0.5F);
		animRotate.setDuration(1500);
		animRotate.setFillAfter(true);
		// ���Ŷ���
		ScaleAnimation animaScale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
				0.5F);
		animaScale.setDuration(1500);
		animaScale.setFillAfter(true);
		// ���䶯����͸���ȶ�����
		AlphaAnimation animaAlpha = new AlphaAnimation(0, 1);
		animaAlpha.setDuration(2500);
		animaAlpha.setFillAfter(true);

		// ��������
		AnimationSet set = new AnimationSet(true);// ע������Ƿ��� ��ʦ˵true��false������
		set.addAnimation(animRotate);
		set.addAnimation(animaScale);
		set.addAnimation(animaAlpha);

		// ��������
		rlRoot.startAnimation(set);

		set.setAnimationListener(new AnimationListener() {


			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
 
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// ��������
				// ��һ�� ��������
				// ���� ��ҳ��
				boolean isFirstEnter = PrefUtils.getBoolean(myContext,"is_first_enter", true);
				Intent intent;
				if(isFirstEnter){
					//����
					intent = new Intent(getApplicationContext(),GuideActivity.class);
				}else{
					//��ҳ��
					intent = new Intent(getApplicationContext(),MainActivity.class);
				}
				startActivity(intent);
				finish();  
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
