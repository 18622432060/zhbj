/*
 * 瀹樼綉鍦扮珯:http://www.mob.com
 * 鎶�鏈敮鎸丵Q: 4006852216
 * 瀹樻柟寰俊:ShareSDK   锛堝鏋滃彂甯冩柊鐗堟湰鐨勮瘽锛屾垜浠皢浼氱涓�鏃堕棿閫氳繃寰俊灏嗙増鏈洿鏂板唴瀹规帹閫佺粰鎮ㄣ�傚鏋滀娇鐢ㄨ繃绋嬩腑鏈変换浣曢棶棰橈紝涔熷彲浠ラ�氳繃寰俊涓庢垜浠彇寰楄仈绯伙紝鎴戜滑灏嗕細鍦�24灏忔椂鍐呯粰浜堝洖澶嶏級
 *
 * Copyright (c) 2013骞� mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import com.mob.tools.utils.R;

/**
 * ShareCore鏄揩鎹峰垎浜殑瀹為檯鍑哄彛锛屾绫讳娇鐢ㄤ簡鍙嶅皠鐨勬柟寮忥紝閰嶅悎浼犻�掕繘鏉ョ殑HashMap锛�
 *鏋勯�爗@link ShareParams}瀵硅薄锛屽苟鎵ц鍒嗕韩锛屼娇蹇嵎鍒嗕韩涓嶅啀闇�瑕佽�冭檻鐩爣骞冲彴
 */
public class ShareCore {
	private ShareContentCustomizeCallback customizeCallback;

	/** 璁剧疆鐢ㄤ簬鍒嗕韩杩囩▼涓紝鏍规嵁涓嶅悓骞冲彴鑷畾涔夊垎浜唴瀹圭殑鍥炶皟 */
	public void setShareContentCustomizeCallback(ShareContentCustomizeCallback callback) {
		customizeCallback = callback;
	}

	/**
	 * 鍚戞寚瀹氬钩鍙板垎浜唴瀹�
	 * <p>
	 * <b>娉ㄦ剰锛�</b><br>
	 * 鍙傛暟data鐨勯敭鍊奸渶瑕佷弗鏍兼寜鐓@link ShareParams}涓嶅悓瀛愮被鍏蜂綋瀛楁鏉ュ懡鍚嶏紝
	 *鍚﹀垯鏃犳硶鍙嶅皠姝ゅ瓧娈碉紝涔熸棤娉曡缃叾鍊笺��
	 */
	public boolean share(Platform plat, HashMap<String, Object> data) {
		if (plat == null || data == null) {
			return false;
		}

		try {
			String imagePath = (String) data.get("imagePath");
			Bitmap viewToShare = (Bitmap) data.get("viewToShare");
			if (TextUtils.isEmpty(imagePath) && viewToShare != null && !viewToShare.isRecycled()) {
				String path = R.getCachePath(plat.getContext(), "screenshot");
				File ss = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
				FileOutputStream fos = new FileOutputStream(ss);
				viewToShare.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
				data.put("imagePath", ss.getAbsolutePath());
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}

		ShareParams sp = new ShareParams(data);
		if (customizeCallback != null) {
			customizeCallback.onShare(plat, sp);
		}

		plat.share(sp);
		return true;
	}

	/** 鍒ゆ柇鎸囧畾骞冲彴鏄惁浣跨敤瀹㈡埛绔垎浜� */
	public static boolean isUseClientToShare(String platform) {
		if ("Wechat".equals(platform) || "WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "Email".equals(platform) || "GooglePlus".equals(platform)
				|| "QQ".equals(platform) || "Pinterest".equals(platform)
				|| "Instagram".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform) || "QZone".equals(platform)
				|| "Mingdao".equals(platform) || "Line".equals(platform)
				|| "KakaoStory".equals(platform) || "KakaoTalk".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "BaiduTieba".equals(platform) || "Laiwang".equals(platform)
				|| "LaiwangMoments".equals(platform) || "Alipay".equals(platform)
				) {
			return true;
		} else if ("Evernote".equals(platform)) {
			Platform plat = ShareSDK.getPlatform(platform);
			if ("true".equals(plat.getDevinfo("ShareByAppClient"))) {
				return true;
			}
		} else if ("SinaWeibo".equals(platform)) {
			Platform plat = ShareSDK.getPlatform(platform);
			if ("true".equals(plat.getDevinfo("ShareByAppClient"))) {
				Intent test = new Intent(Intent.ACTION_SEND);
				test.setPackage("com.sina.weibo");
				test.setType("image/*");
				ResolveInfo ri = plat.getContext().getPackageManager().resolveActivity(test, 0);
				return (ri != null);
			}
		}

		return false;
	}

	/** 鍒ゆ柇鎸囧畾骞冲彴鏄惁鍙互鐢ㄦ潵鎺堟潈 */
	public static boolean canAuthorize(Context context, String platform) {
		return !("WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "Email".equals(platform)
				|| "Pinterest".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform) || "Line".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "BaiduTieba".equals(platform) || "Laiwang".equals(platform)
				|| "LaiwangMoments".equals(platform) || "Alipay".equals(platform));
	}


	/** 鍒ゆ柇鎸囧畾骞冲彴鏄惁鍙互鐢ㄦ潵鑾峰彇鐢ㄦ埛璧勬枡 */
	public static boolean canGetUserInfo(Context context, String platform) {
		return !("WechatMoments".equals(platform)
				|| "WechatFavorite".equals(platform) || "ShortMessage".equals(platform)
				|| "Email".equals(platform)
				|| "Pinterest".equals(platform) || "Yixin".equals(platform)
				|| "YixinMoments".equals(platform) || "Line".equals(platform)
				|| "Bluetooth".equals(platform) || "WhatsApp".equals(platform)
				|| "Pocket".equals(platform) || "BaiduTieba".equals(platform)
				|| "Laiwang".equals(platform) || "LaiwangMoments".equals(platform)
				|| "Alipay".equals(platform));
	}

	/** 鍒ゆ柇鏄惁鐩存帴鍒嗕韩 */
	public static boolean isDirectShare(Platform platform) {
		return platform instanceof CustomPlatform || isUseClientToShare(platform.getName());
	}
}
