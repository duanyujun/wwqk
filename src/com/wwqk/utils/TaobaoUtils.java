package com.wwqk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.wwqk.model.TaobaoAlliance;

public class TaobaoUtils {

	public static void updateProduct(Controller controller){
		List<TaobaoAlliance> list = new ArrayList<TaobaoAlliance>();
		try {
			UploadFile file = controller.getFile();
			File excelFile = file.getFile();
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(excelFile));
			TaobaoAlliance alliance = null;
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null) {
						alliance = new TaobaoAlliance();
						
						HSSFCell id = hssfRow.getCell(0);
						HSSFCell product_name = hssfRow.getCell(1);
						HSSFCell product_img = hssfRow.getCell(2);
						HSSFCell product_url = hssfRow.getCell(3);
						HSSFCell store_name = hssfRow.getCell(4);
						HSSFCell price = hssfRow.getCell(5);
						HSSFCell sale_month_count = hssfRow.getCell(6);
						HSSFCell earn_percent = hssfRow.getCell(7);
						HSSFCell earn_common = hssfRow.getCell(8);
						HSSFCell promotion = hssfRow.getCell(9);
						HSSFCell promotion_percent = hssfRow.getCell(10);
						HSSFCell earn_promotion = hssfRow.getCell(11);
						HSSFCell promotion_start = hssfRow.getCell(12);
						HSSFCell promotion_end = hssfRow.getCell(13);
						HSSFCell store_ww = hssfRow.getCell(14);
						HSSFCell tbk_short_url = hssfRow.getCell(15);
						HSSFCell tbk_url = hssfRow.getCell(16);
						HSSFCell tkl = hssfRow.getCell(17);
						HSSFCell coupon_count = hssfRow.getCell(18);
						HSSFCell coupon_count_last = hssfRow.getCell(19);
						HSSFCell coupon_desc = hssfRow.getCell(20);
						HSSFCell coupon_start = hssfRow.getCell(21);
						HSSFCell coupon_end = hssfRow.getCell(22);
						HSSFCell coupon_url = hssfRow.getCell(23);
						HSSFCell coupon_tkl = hssfRow.getCell(24);
						HSSFCell coupon_short_url = hssfRow.getCell(25);
						
						alliance.set("id", getValue(id));
						alliance.set("product_name", getValue(product_name));
						alliance.set("product_img", getValue(product_img));
						alliance.set("product_url", getValue(product_url));
						alliance.set("store_name", getValue(store_name));
						alliance.set("price", getValue(price));
						alliance.set("sale_month_count", getValue(sale_month_count));
						alliance.set("earn_percent", getValue(earn_percent));
						alliance.set("earn_common", getValue(earn_common));
						alliance.set("promotion", getValue(promotion));
						alliance.set("promotion_percent", getValue(promotion_percent));
						alliance.set("earn_promotion", getValue(earn_promotion));
						alliance.set("promotion_start", getValue(promotion_start));
						alliance.set("promotion_end", getValue(promotion_end));
						alliance.set("store_ww", getValue(store_ww));
						alliance.set("tbk_short_url", getValue(tbk_short_url));
						alliance.set("tbk_url", getValue(tbk_url));
						alliance.set("tkl", getValue(tkl));
						alliance.set("coupon_count", getValue(coupon_count));
						alliance.set("coupon_count_last", getValue(coupon_count_last));
						alliance.set("coupon_desc", getValue(coupon_desc));
						if(getValue(coupon_start)!=null){
							alliance.set("coupon_start", getValue(coupon_start));
						}
						if(getValue(coupon_end)!=null){
							alliance.set("coupon_end", getValue(coupon_end));
						}
						alliance.set("coupon_url", getValue(coupon_url));
						alliance.set("coupon_tkl", getValue(coupon_tkl));
						alliance.set("coupon_short_url", getValue(coupon_short_url));
						
						list.add(alliance);
					}
				}
			}
        }catch (Exception e){
            e.printStackTrace();
        }
		
		if(list.size()>0){
			List<TaobaoAlliance> lstInsert = new ArrayList<TaobaoAlliance>();
			List<TaobaoAlliance> lstUpdate = new ArrayList<TaobaoAlliance>();
			for(TaobaoAlliance one : list){
				TaobaoAlliance db = TaobaoAlliance.dao.findById(one.getStr("id"));
				if(db==null){
					lstInsert.add(one);
				}else{
					lstUpdate.add(one);
				}
			}
			if(lstInsert.size()>0){
				Db.batchSave(lstInsert, lstInsert.size());
			}
			if(lstUpdate.size()>0){
				Db.batchUpdate(lstUpdate, lstUpdate.size());
			}
		}
		//删除已经过期的
		Db.update("delete from taobao_alliance where promotion_end < ? ", new Date());
	}
	
	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
		if(hssfCell==null){
			return null;
		}
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return StringUtils.trim(String.valueOf(hssfCell.getStringCellValue()));
        }
    }
	
}
