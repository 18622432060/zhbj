/*
 * 瀹樼綉鍦扮珯:http://www.mob.com
 * 鎶�鏈敮鎸丵Q: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�鏃堕棿閫氳繃寰俊灏嗙増鏈洿鏂板唴瀹规帹閫佺粰鎮ㄣ�傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�氳繃寰俊涓庢垜浠彇寰楄仈绯伙紝鎴戜滑灏嗕細鍦�24灏忔椂鍐呯粰浜堝洖澶嶏級
 *
 * Copyright (c) 2013骞� mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import static com.mob.tools.utils.R.*;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import com.mob.tools.FakeActivity;

/** 鎽囦竴鎽囧惎鍔ㄥ垎浜殑渚嬪瓙 */
public class Shake2Share extends FakeActivity implements SensorEventListener {
	// 妫�娴嬬殑鏃堕棿闂撮殧
	private static final int UPDATE_INTERVAL = 100;
	// 鎽囨檭妫�娴嬮槇鍊硷紝鍐冲畾浜嗗鎽囨檭鐨勬晱鎰熺▼搴︼紝瓒婂皬瓒婃晱鎰�
	private static final int SHAKE_THRESHOLD = 1500;

	private OnShakeListener listener;
	private SensorManager mSensorManager;
	private long mLastUpdateTime;
	private float mLastX;
	private float mLastY;
	private float mLastZ;
	private boolean shaken;

	public void setOnShakeListener(OnShakeListener listener) {
		this.listener = listener;
	}

	public void setActivity(Activity activity) {
		super.setActivity(activity);
		int resId = getBitmapRes(activity, "ssdk_oks_shake_to_share_back");
		if (resId > 0) {
			activity.setTheme(android.R.style.Theme_Dialog);
			activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
			Window win = activity.getWindow();
			win.setBackgroundDrawableResource(resId);
		}
	}

	public void onCreate() {
		startSensor();

		int resId = getBitmapRes(activity, "ssdk_oks_yaoyiyao");
		if (resId > 0) {
			ImageView iv = new ImageView(activity);
			iv.setScaleType(ScaleType.CENTER_INSIDE);
			iv.setImageResource(resId);
			activity.setContentView(iv);
		}

		resId = getStringRes(activity, "shake2share");
		if (resId > 0) {
			Toast.makeText(activity, resId, Toast.LENGTH_SHORT).show();
		}
	}

	private void startSensor() {
		mSensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		if (mSensorManager == null) {
			throw new UnsupportedOperationException();
		}
		Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (sensor == null) {
			throw new UnsupportedOperationException();
		}
		boolean success = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		if (!success) {
			throw new UnsupportedOperationException();
		}
	}

	public void onDestroy() {
		stopSensor();
	}

	private void stopSensor() {
		if (mSensorManager != null) {
			mSensorManager.unregisterListener(this);
			mSensorManager = null;
		}
	}

	public void onSensorChanged(SensorEvent event) {
		long currentTime = System.currentTimeMillis();
		long diffTime = currentTime - mLastUpdateTime;
		if (diffTime > UPDATE_INTERVAL) {
			if(mLastUpdateTime != 0) {
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];
				float deltaX = x - mLastX;
				float deltaY = y - mLastY;
				float deltaZ = z - mLastZ;
				float delta = FloatMath.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / diffTime * 10000;
				if (delta > SHAKE_THRESHOLD) {
					if (!shaken) {
						shaken = true;
						finish();
					}

					if (listener != null) {
						listener.onShake();
					}
				}
				mLastX = x;
				mLastY = y;
				mLastZ = z;
			}
			mLastUpdateTime = currentTime;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public static interface OnShakeListener {
		public void onShake();
	}

}
