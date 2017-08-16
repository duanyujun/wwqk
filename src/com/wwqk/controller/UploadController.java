package com.wwqk.controller;

import java.util.UUID;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.wwqk.utils.ImageUtils;

/**
 * 上传图片服务类，上传目录：WebRoot/upload/
 * @author Administrator
 *
 */
public class UploadController extends Controller {

	public void index(){
		UploadFile file = getFile();
		String fileName = ImageUtils.getInstance().saveFile(file, UUID.randomUUID().toString(), true);
		renderText(getRequest().getContextPath()+fileName);
	}
	
}
