package com.wwqk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.TaobaoAlliance;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.StringUtils;

public class TaobaoAllianceService {

	public static Map<Object, Object> taobaoAllianceData(Controller controller){
		String sumSql = "select count(*) from taobao_alliance where 1 = 1  ";
		String sql = "select * from taobao_alliance where 1 = 1 ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
			whereSql = " and (product_name like '%"+search+"%'" +" OR product_img like '%"+search+"%'" +" OR product_url like '%"+search+"%'" +" OR store_name like '%"+search+"%'" +" OR price like '%"+search+"%'" +" OR sale_month_count like '%"+search+"%'" +" OR earn_percent like '%"+search+"%'" +" OR earn_common like '%"+search+"%'" +" OR promotion like '%"+search+"%'" +" OR promotion_percent like '%"+search+"%'" +" OR earn_promotion like '%"+search+"%'" +" OR promotion_start like '%"+search+"%'" +" OR promotion_end like '%"+search+"%'" +" OR store_ww like '%"+search+"%'" +" OR tbk_short_url like '%"+search+"%'" +" OR tbk_url like '%"+search+"%'" +" OR tkl like '%"+search+"%'" +" OR coupon_count like '%"+search+"%'" +" OR coupon_count_last like '%"+search+"%'" +" OR coupon_desc like '%"+search+"%'" +" OR coupon_start like '%"+search+"%'" +" OR coupon_end like '%"+search+"%'" +" OR coupon_url like '%"+search+"%'" +" OR coupon_tkl like '%"+search+"%'" +" OR coupon_short_url like '%"+search+"%'" +" OR recom like '%"+search+"%')"; 
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
			case 1:
              orderSql = " order by product_name "+sortType;
              break;
            case 2:
              orderSql = " order by product_img "+sortType;
              break;
            case 3:
              orderSql = " order by product_url "+sortType;
              break;
            case 4:
              orderSql = " order by store_name "+sortType;
              break;
            case 5:
              orderSql = " order by price "+sortType;
              break;
            case 6:
              orderSql = " order by sale_month_count "+sortType;
              break;
            case 7:
              orderSql = " order by earn_percent "+sortType;
              break;
            case 8:
              orderSql = " order by earn_common "+sortType;
              break;
            case 9:
              orderSql = " order by promotion "+sortType;
              break;
            case 10:
              orderSql = " order by promotion_percent "+sortType;
              break;
            case 11:
              orderSql = " order by earn_promotion "+sortType;
              break;
            case 12:
              orderSql = " order by promotion_start "+sortType;
              break;
            case 13:
              orderSql = " order by promotion_end "+sortType;
              break;
            case 14:
              orderSql = " order by store_ww "+sortType;
              break;
            case 15:
              orderSql = " order by tbk_short_url "+sortType;
              break;
            case 16:
              orderSql = " order by tbk_url "+sortType;
              break;
            case 17:
              orderSql = " order by tkl "+sortType;
              break;
            case 18:
              orderSql = " order by coupon_count "+sortType;
              break;
            case 19:
              orderSql = " order by coupon_count_last "+sortType;
              break;
            case 20:
              orderSql = " order by coupon_desc "+sortType;
              break;
            case 21:
              orderSql = " order by coupon_start "+sortType;
              break;
            case 22:
              orderSql = " order by coupon_end "+sortType;
              break;
            case 23:
              orderSql = " order by coupon_url "+sortType;
              break;
            case 24:
              orderSql = " order by coupon_tkl "+sortType;
              break;
            case 25:
              orderSql = " order by coupon_short_url "+sortType;
              break;
            case 26:
              orderSql = " order by recom "+sortType;
              break;
			default:
				break;
		}
		
		int start = controller.getParaToInt("start");
		int length = controller.getParaToInt("length");
		if(length!=-1){
			limitSql = " limit "+start+","+length;
		}
		Long recordsTotal = Db.queryLong(sumSql+whereSql);
		List<TaobaoAlliance> lstTaobaoAlliance = new ArrayList<TaobaoAlliance>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstTaobaoAlliance = TaobaoAlliance.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstTaobaoAlliance.size()];
			for(int i=0; i<lstTaobaoAlliance.size(); i++){
				Object[] obj = new Object[13];
				TaobaoAlliance taobaoAlliance = lstTaobaoAlliance.get(i);
				obj[0] = taobaoAlliance.get("id");
				
				obj[1] = taobaoAlliance.get("product_name");
				obj[2] = taobaoAlliance.get("price");
				obj[3] = taobaoAlliance.get("earn_percent");
				obj[4] = taobaoAlliance.get("earn_common");
				obj[5] = taobaoAlliance.get("promotion");
				obj[6] = taobaoAlliance.get("promotion_percent");
				obj[7] = taobaoAlliance.get("earn_promotion");
				obj[8] = taobaoAlliance.get("promotion_start");
				obj[9] = taobaoAlliance.get("promotion_end");
				obj[10] = taobaoAlliance.get("tbk_short_url");
				obj[11] = taobaoAlliance.get("tkl");
				obj[12] = taobaoAlliance.get("recom");

				data[i] = obj;
			}
		}
		if(data==null){
			data = new Object[0];
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("draw", controller.getParaToInt("draw"));
		map.put("recordsTotal", recordsTotal);
		map.put("recordsFiltered", recordsTotal);
		map.put("data", data);
		
		return map;
	}
	
	public static void saveTaobaoAlliance(Controller controller){
	
		String id = controller.getPara("id");
		TaobaoAlliance taobaoAlliance = null;
		if(StringUtils.isBlank(id)){
			taobaoAlliance = new TaobaoAlliance();
		}else{
			taobaoAlliance = TaobaoAlliance.dao.findById(id);
		}
		
		String[] fields = {"product_name","product_img", "product_url", "store_name", "price", "sale_month_count","earn_percent","earn_common",
				"promotion","earn_promotion","promotion_start","promotion_end","store_ww","tbk_short_url","tbk_url","tkl","coupon_count",
				"coupon_count_last","coupon_desc","coupon_start","coupon_end","coupon_url","coupon_tkl","coupon_short_url","recom","position",};
		for(String field:fields){
			CommonUtils.setNullValue(taobaoAlliance, field, controller.getPara(field));
		}

		save(taobaoAlliance);
	}
	
	public static void save(TaobaoAlliance taobaoAlliance){
		if(taobaoAlliance.get("id")==null){
			taobaoAlliance.save();
		}else{
			taobaoAlliance.update();
		}
	}
	
}
