package com.wwqk.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

public class ImageUtils {
	
	private static final String IMG_PATH = "assets/img/article/";
	private static final String WEB_PROFIX = "http://cache.images.core.optasports.com/";
	//private static final String DISK_PATH = "D:" + File.separator;
	private static ImageUtils instance = null;
	
	public static ImageUtils getInstance(){
		if(instance==null){
			instance = new ImageUtils();
		}
		return instance;
	}

	public String getImgName(String imgUrl) {
		String oldImageUrlStr = imgUrl;
		imgUrl = imgUrl.replace(WEB_PROFIX, "");
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		String foldName = imgUrl.substring(0, imgUrl.lastIndexOf("/"));
		String folderPath = getDiskPath()+getFilePath(foldName)+File.separator;
		File filePath = new File(folderPath);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		try {
			if(!oldImageUrlStr.contains("http")){
				if(oldImageUrlStr.indexOf("/")==0){
					oldImageUrlStr = oldImageUrlStr.substring(1);
				}
				oldImageUrlStr = WEB_PROFIX + oldImageUrlStr;
			}
			Thread.sleep(1000);
			Response response = Jsoup.connect(oldImageUrlStr).ignoreContentType(true).execute();
			String fileNameRel = getDiskPath()+getFilePath(imgUrl);
			System.err.println("++++++ filename real :"+fileNameRel);
			File file = new File(fileNameRel);
			if(!file.exists()){
				file.createNewFile();
				OutputStream os = new FileOutputStream(file);
				os.write(response.bodyAsBytes());
				os.close();
			}
		} catch (Exception e) {
			
		}

		return fileName;
	}
	
	private String getFilePath(String path){
		String[] paths = path.split("/");
		StringBuilder sb = new StringBuilder();
		for(String p : paths){
			sb.append(File.separator).append(p);
		}
		String folderStr = IMG_PATH + sb.toString();
		return folderStr;
	}
	
	private String getDiskPath(){
		String path = this.getClass().getClassLoader().getResource("").getPath();
		return path;
	}
	
	public static void main(String[] args) {
		System.err.println(getInstance().getDiskPath());
	}
}
