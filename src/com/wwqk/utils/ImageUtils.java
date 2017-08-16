package com.wwqk.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.jfinal.upload.UploadFile;
import com.wwqk.constants.CommonConstants;

public class ImageUtils {
	
	private static final String IMG_PATH = "assets/image";
	private static final String ARTICLE_IMAGE = "article-image";
	private static final String WEB_PROFIX = "http://cache.images.core.optasports.com/";
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
		//String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		String foldName = imgUrl.substring(0, imgUrl.lastIndexOf("/"));
		String folderPath = getDiskPath()+getFilePath(foldName)+"/";
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
			Thread.sleep(500);
			String fileNameRel = getDiskPath()+getFilePath(imgUrl);
			File file = new File(fileNameRel);
			if(file.exists()){
				//TODO 通过判读标识字段，确定是否要删除
				file.delete();
			}
			//if(!file.exists()){
				Response response = Jsoup.connect(oldImageUrlStr).ignoreContentType(true).execute();
				file.createNewFile();
				OutputStream os = new FileOutputStream(file);
				os.write(response.bodyAsBytes());
				os.close();
			//}
		} catch (Exception e) {
			
		}

		return IMG_PATH+"/"+imgUrl;
	}
	
	private String getFilePath(String path){
		String[] paths = path.split("/");
		StringBuilder sb = new StringBuilder();
		for(String p : paths){
			sb.append("/").append(p);
		}
		String folderStr = IMG_PATH + sb.toString();
		return folderStr;
	}
	
	public String getDiskPath(){
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win") && path.startsWith("/")){
			path = path.substring(1);
		}
		//截取到WEB-INF上一个目录
		int endIdx = path.indexOf("WEB-INF");
		if(endIdx!=-1){
			path = path.substring(0, endIdx);
		}
		
		return path;
	}
	
	/**
	 * H:\workspace\wwqk\WebRoot\assets\image\soccer\players
	 * 
	 * @param type players / venues
	 * @param size 150x150
	 * @return
	 */
	public String getPicPath(String type, String size, boolean needDateStr) {
		String pathStr = getClass().getClassLoader().getResource("").getPath();

		String dateStr = "";
		if(needDateStr){
			dateStr = File.separator + DateTimeUtils.formatDate(new Date());
		}
		
		if ("\\".equals(File.separator)) {
			pathStr = pathStr.substring(1).replaceAll("/", "\\\\");
		}
		pathStr = pathStr.substring(0, pathStr.indexOf("WEB-INF"));
		pathStr = pathStr + "assets" + File.separator + "image"
				+ File.separator + "soccer" + File.separator + type
				+ dateStr + File.separator + size;
		File path = new File(pathStr);
		if(!path.exists()){
			path.mkdirs();
		}
		return pathStr;
	}
	
	public String saveFiles(UploadFile file, String type, String size, String id, boolean needDateStr){
		String fileName = "";
		if(file!=null){
			String lastPrefix = file.getOriginalFileName().substring(file.getOriginalFileName().lastIndexOf("."));
			String picFilePath = getPicPath(type, size, needDateStr);
			fileName = picFilePath.substring(picFilePath.indexOf("assets"))+File.separator+CommonConstants.UPLOAD_FILE_FLAG+id+lastPrefix;
			File localFile = new File(picFilePath+File.separator+CommonConstants.UPLOAD_FILE_FLAG+id+lastPrefix);
			try {
				if(localFile.exists()){
					localFile.delete();
				}
				localFile.createNewFile();
				FileService fileService = new FileService();
				fileService.fileChannelCopy(file.getFile(), localFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fileName = fileName.replaceAll("\\\\", "/");
		return fileName;
	}
	
	public String saveFile(UploadFile file, String newFileName, boolean needDateStr){
		String fileName = "";
		if(file!=null){
			String lastPrefix = file.getOriginalFileName().substring(file.getOriginalFileName().lastIndexOf("."));
			String picFilePath = getPicPath(needDateStr);
			fileName = picFilePath.substring(picFilePath.indexOf(ARTICLE_IMAGE))+File.separator+newFileName+lastPrefix;
			File localFile = new File(picFilePath+File.separator+newFileName+lastPrefix);
			try {
				if(localFile.exists()){
					localFile.delete();
				}
				localFile.createNewFile();
				FileService fileService = new FileService();
				fileService.fileChannelCopy(file.getFile(), localFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fileName = fileName.replaceAll("\\\\", "/");
		return fileName;
	}
	
	/** 
	 * 改变图片的大小到宽为size，然后高随着宽等比例变化 
	 * @param is 上传的图片的输入流 
	 * @param os 改变了图片的大小后，把图片的流输出到目标OutputStream 
	 * @param size 新图片的宽 
	 * @param format 新图片的格式 
	 * @throws IOException 
	 */  
	public static void resizeImage(InputStream is, OutputStream os, int size, String format){  
	    BufferedImage prevImage;
		try {
			prevImage = ImageIO.read(is);
			double width = prevImage.getWidth();  
		    double height = prevImage.getHeight();  
		    double percent = size/width;  
		    int newWidth = (int)(width * percent);  
		    int newHeight = (int)(height * percent);  
		    BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);  
		    Graphics graphics = image.createGraphics();  
		    graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);  
		    ImageIO.write(image, format, os);  
		    os.flush();  
		    is.close();  
		    os.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	public String getPicPath(boolean needDateStr) {
		String pathStr = getClass().getClassLoader().getResource("").getPath();

		String dateStr = "";
		if(needDateStr){
			dateStr = DateTimeUtils.formatDate(new Date());
		}
		
		if ("\\".equals(File.separator)) {
			pathStr = pathStr.substring(1).replaceAll("/", "\\\\");
		}
		pathStr = pathStr.substring(0, pathStr.indexOf("WEB-INF"));
		pathStr = pathStr + ARTICLE_IMAGE + File.separator + dateStr;
		File path = new File(pathStr);
		if(!path.exists()){
			path.mkdirs();
		}
		return pathStr;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		//System.err.println(getInstance().getImgName("http://cache.images.core.optasports.com/soccer/venues/300x225/81.jpg"));
		File fileBig = new File("D:/big.png");
		File fileSmall = new File("D:/small.png");
		//resizeImage(new FileInputStream(fileBig), new FileOutputStream(fileSmall), 50, "png");
		System.err.println(fileSmall.length());
		
	}
}
