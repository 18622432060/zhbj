package com.itheima.zhbj74.domain;

import java.util.ArrayList;
/**
 * 组图对象
 * @author liupeng
 * @date 2017-10-22
 */
public class PhotosBean {

	public PhotosData data;

	public class PhotosData {
		public ArrayList<PhotoNews> news;
	}

	public class PhotoNews {
		public int id;
		public String listimage;
		public String title;
	}
}