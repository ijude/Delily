package com.sh.dilily.data;

/**
 * 广告, 一个展示图片资源, 另一个可以是外部链接, 也可以是html数据(raw)
 * */
public class Banner {
	public static final int TYPE_EXTERNAL_URL = 1;		//外部浏览器打开URL
	public static final int TYPE_INTERNAL_URL = 2;			//内部浏览器打开URL
	public static final int TYPE_ASSETS_HTML = 3;		//打开assets下的html文件
	public static final int TYPE_ASSETS_VIDEO = 4;		//播放assets下的视频文件

	private int imageId;	//drawable
	private String url;
	private int type;
	
	public Banner(int imageId, int type, String url) {
		this.imageId = imageId;
		this.type = type;
		this.url = url;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}
	
	public String getAssetsUrl() {
		return "file:///android_asset/" + url;
	}
}
