package com.wwqk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Question;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.StringUtils;

public class QuestionService {

	public static Map<Object, Object> questionData(Controller controller){
		String sumSql = "select count(*) from question where 1 = 1  ";
		String sql = "select * from question where 1 = 1 ";
		String orderSql = "";
		String whereSql = "";
		String limitSql = "";
		
		String search = controller.getPara("search[value]");
		if(StringUtils.isNotBlank(search)){
			search =search.replaceAll("'", "").trim();
			whereSql = " and (title like '%"+search+"%')"; 
		}
		
		int sortColumn = controller.getParaToInt("order[0][column]");
		String sortType = controller.getPara("order[0][dir]");
		switch (sortColumn) {
			case 1:
              orderSql = " order by title "+sortType;
              break;
			case 5:
              orderSql = " order by update_time "+sortType;
              break;
			case 6:
              orderSql = " order by status "+sortType;
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
		List<Question> lstQuestion = new ArrayList<Question>();
		Object[] data = null;
		if(recordsTotal!=0){
			lstQuestion = Question.dao.find(sql+whereSql+orderSql+limitSql);
			data = new Object[lstQuestion.size()];
			for(int i=0; i<lstQuestion.size(); i++){
				Object[] obj = new Object[7];
				Question question = lstQuestion.get(i);
				obj[0] = question.get("id");
				obj[1] = question.get("title");
				obj[2] = question.get("status");
				obj[3] = question.get("source_id");
				obj[4] = question.get("a_right");
				obj[5] = question.get("a_show");
				obj[6] = question.get("type");

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
	
	public static void saveQuestion(Controller controller){
	
		String id = controller.getPara("id");
		Question question = null;
		if(StringUtils.isBlank(id)){
			question = new Question();
		}else{
			question = Question.dao.findById(id);
		}
		
		CommonUtils.setNullValue(question, "title", controller.getPara("title"));
		CommonUtils.setNullValue(question, "source_id", controller.getPara("source_id"));
		CommonUtils.setNullValue(question, "a_right", controller.getPara("a_right"));
		CommonUtils.setNullValue(question, "a_show", controller.getPara("a_show"));
		if(StringUtils.isBlank(controller.getPara("update_time"))){
			question.set("update_time", new Date());
		}else{
			question.set("update_time", controller.getPara("update_time"));
		}
		CommonUtils.setNullValue(question, "status", controller.getPara("status"));
		CommonUtils.setNullValue(question, "type", controller.getPara("type"));

		save(question);
	}
	
	public static void save(Question question){
		if(question.get("id")==null){
			question.save();
		}else{
			question.update();
		}
	}
	
}
