package com.felix.o2o.util;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
	
	/**
	 * ��ȡ�ĿͼƬ��·��
	 * @return
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/projectdev/image";
		} else {
			basePath = "/root/projectdev/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	
	/**
	 * ��ȡÿ�����̵�ͼƬ�洢��ַ
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/items/shop/"+shopId+"/";
		return imagePath.replace("/", seperator);
	}

}
