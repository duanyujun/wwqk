package com.wwqk.utils;

import java.util.ArrayList;
import java.util.List;

import com.wwqk.model.ColumnVO;

/**
 * 
 * 
SELECT 
  a.COLUMN_NAME,
  a.COLUMN_COMMENT,
  a.`COLUMN_TYPE`
FROM
  information_schema.`COLUMNS` a 
WHERE a.TABLE_SCHEMA = 'gtsh' 
  AND a.TABLE_NAME = 'article';
  
SELECT DISTINCT(a.`TABLE_NAME`) FROM information_schema.`COLUMNS` a WHERE a.`TABLE_SCHEMA` = 'gtsh';

表备注
select `TABLE_COMMENT` from information_schema.`TABLES`  where `TABLE_NAME` = 'article' and `TABLE_SCHEMA` = 'gtsh'

 * 
 * 代码生成
 */
public class CodeGenerator {
	
	private static final String DATABASE_NAME = "wwqk";
	//是否有上传图片（文件）
	private static boolean isNeedGetFiles = false;

	public static void build(String tableName, String tableNameCN, String packageName, List<ColumnVO> lstColomnVO){
		//生成java代码
		//1、生成AdminXXXController
		String adminTemplate = FileUtils.readByBufferedReaderWithReturn(CommonUtils.getTemplatePath("admin.template")); 
		String clazzLowercase = CommonUtils.field2CamelString(tableName);
		String clazzUppercase = CommonUtils.firstLetter2UpperCase(clazzLowercase);
		adminTemplate = adminTemplate.replaceAll("#path", packageName);
		adminTemplate = adminTemplate.replaceAll("#Clazz", clazzUppercase);
		adminTemplate = adminTemplate.replaceAll("#clazz", clazzLowercase);
		adminTemplate = adminTemplate.replaceAll("#table_name", tableName);
		String adminFilepath = CommonUtils.getTargetPath(packageName, "controller", "Admin"+clazzUppercase+"Controller.java");
		FileUtils.deleteFile(adminFilepath);
		FileUtils.writeFile(adminTemplate, adminFilepath);
		
		//2、生成model
		String modelTemplate = FileUtils.readByBufferedReaderWithReturn(CommonUtils.getTemplatePath("model.template")); 
		modelTemplate = modelTemplate.replaceAll("#path", packageName);
		modelTemplate = modelTemplate.replaceAll("#Clazz", clazzUppercase);
		String modelFilepath = CommonUtils.getTargetPath(packageName, "model", clazzUppercase+".java");
		FileUtils.deleteFile(modelFilepath);
		FileUtils.writeFile(modelTemplate, modelFilepath);
		
		//3、生成service
		String serviceTemplate = FileUtils.readByBufferedReaderWithReturn(CommonUtils.getTemplatePath("service.template")); 
		serviceTemplate = serviceTemplate.replace("#whereSql", buildWhereSql(lstColomnVO));
		serviceTemplate = serviceTemplate.replace("#orderSql", buildOrderSql(lstColomnVO));
		List<ColumnVO> lstShow = getShowColumnList(lstColomnVO);
		serviceTemplate = serviceTemplate.replace("#showSize", String.valueOf(lstShow.size()+1));
		serviceTemplate = serviceTemplate.replaceAll("#dataObjectCode", buildDataObjectCode(lstShow));
		serviceTemplate = serviceTemplate.replaceAll("#saveCode", buildSaveCode(lstColomnVO));
		serviceTemplate = serviceTemplate.replaceAll("#path", packageName);
		serviceTemplate = serviceTemplate.replaceAll("#Clazz", clazzUppercase);
		serviceTemplate = serviceTemplate.replaceAll("#clazz", clazzLowercase);
		serviceTemplate = serviceTemplate.replaceAll("#table_name", tableName);
		String serviceFilepath = CommonUtils.getTargetPath(packageName, "service", clazzUppercase+"Service.java");
		FileUtils.deleteFile(serviceFilepath);
		FileUtils.writeFile(serviceTemplate, serviceFilepath);
		
		//生成list代码
		String listTemplate = FileUtils.readByBufferedReaderWithReturn(CommonUtils.getTemplatePath("list.template")); 
		//TODO 
		//Record tableCommentRecord = Db.findFirst("select `TABLE_COMMENT` from information_schema.`TABLES`  where `TABLE_NAME` = ? and `TABLE_SCHEMA` = ?", tableName, DATABASE_NAME);
		String tableComment = tableNameCN;//tableCommentRecord.getStr("TABLE_COMMENT");
		listTemplate = listTemplate.replaceAll("#tableName", tableComment);
		listTemplate = listTemplate.replaceAll("#ThHeader", buildThHeaderString(lstShow));
		listTemplate = listTemplate.replaceAll("#ColDetail", buildColDetailString(lstShow));
		listTemplate = listTemplate.replaceAll("#clazz", clazzLowercase);
		listTemplate = listTemplate.replaceAll("#Clazz", clazzUppercase);
		String listFilepath = CommonUtils.getAdminPath()+clazzLowercase+"List.jsp";
		FileUtils.deleteFile(listFilepath);
		FileUtils.writeFile(listTemplate, listFilepath);
		
		//生成form代码
		String formTemplate = FileUtils.readByBufferedReaderWithReturn(CommonUtils.getTemplatePath("form.template")); 
		String multipartStr = "";
		if(isNeedGetFiles){
			multipartStr = "enctype=\"multipart/form-data\"";
		}
		formTemplate = formTemplate.replaceAll("#multipart", multipartStr);
		formTemplate = setFields(lstColomnVO, formTemplate);
		formTemplate = formTemplate.replaceAll("#tableName", tableComment);
		formTemplate = formTemplate.replaceAll("#clazz", clazzLowercase);
		formTemplate = formTemplate.replaceAll("#Clazz", clazzUppercase);
		String formFilepath = CommonUtils.getAdminPath()+clazzLowercase+"Form.jsp";
		FileUtils.deleteFile(formFilepath);
		FileUtils.writeFile(formTemplate, formFilepath);
	}
	
	private static String setFields(List<ColumnVO> lstColumn, String formTemplate){
		StringBuilder richEditor = new StringBuilder();
		StringBuilder fields = new StringBuilder();
		StringBuilder fieldsSetter = new StringBuilder();
		StringBuilder sb = new StringBuilder();
		int i=0;
		for(ColumnVO columnVO : lstColumn){
			if(columnVO.getIsInForm()==1){
				if(columnVO.getFinalType()==0){
					sb.append("                 <div class=\"form-group\">\n");
					sb.append("                      <label class=\"col-md-3 control-label\">").append(columnVO.getIsRequired()==1?"<font color=\"red\">*</font>":"").append(columnVO.getColomnComment()).append("：</label>\n");
					sb.append("                      <div class=\"col-md-6\">\n");
					sb.append("                           <input type=\"text\" class=\"form-control\" id=\"").append(columnVO.getColomnName()).append("\" name=\"").append(columnVO.getColomnName()).append("\" ").append(columnVO.getIsRequired()==1?"required":"").append(" value=\"${#clazz.").append(columnVO.getColomnName()).append("}\" placeholder=\"请输入").append(columnVO.getColomnComment()).append("\">\n");
					sb.append("                      </div>\n");
					sb.append("                      <div class=\"col-md-3\"><label for=\"name\"></label></div>\n");
					sb.append("                 </div>\n");
				}else if(columnVO.getFinalType()==1){
					fields.append("<input type=\"hidden\" id=\"").append(columnVO.getColomnName()).append("\" name=\"").append(columnVO.getColomnName()).append("\" value=\"\" />\n");
					sb.append("                 <div class=\"form-group\">\n");
					sb.append("                      <label class=\"col-md-3 control-label\">").append(columnVO.getColomnComment()).append("：</label>\n");
					sb.append("                               <div class=\"col-md-6\">\n");
					sb.append("                                        <div id=\"div_content_").append(i).append("\" style=\"height:400px;max-height:900px;\">\n");
					sb.append("                                                  ${#clazz.").append(columnVO.getColomnName()).append("}\n");
					sb.append("                                        </div>\n");
					sb.append("                               </div>\n");
					sb.append("                      <div class=\"col-md-3\"><label for=\"content\"></label></div>\n");
					sb.append("                 </div>\n");
					richEditor.append("var editor").append(i).append(" = new wangEditor('div_content_").append(i).append("');\neditor")
					.append(i).append(".config.uploadImgUrl = '/upload';\neditor").append(i).append(".create();\n");
					fieldsSetter.append("$(\"#").append(columnVO.getColomnName()).append("\").val(editor").append(i).append(".$txt.html());\n");
				}else if(columnVO.getFinalType()==2){
					sb.append("                 <div class=\"form-group\">\n");
					sb.append("                       <label class=\"col-md-3 control-label\">").append(columnVO.getIsRequired()==1?"<font color=\"red\">*</font>":"").append(columnVO.getColomnComment()).append("：</label>\n");
					sb.append("                       <div class=\"col-md-6\">\n");
					sb.append("                            <input type=\"file\" class=\"form-control\" id=\"").append(columnVO.getColomnName()).append("\" name=\"").append(columnVO.getColomnName()).append("\" >\n");
					sb.append("                       </div>\n");
					sb.append("                       <div class=\"col-md-3\"><label for=\"").append(columnVO.getColomnName()).append("\"></label></div>\n");
					sb.append("                 </div>\n");
					sb.append("                 <c:if test=\"${!empty #clazz.").append(columnVO.getColomnName()).append("}\">\n");
					sb.append("                       <div class=\"form-group\">\n");
					sb.append("                            <label class=\"col-md-3 control-label\"></label>\n");
				    sb.append("                            <div class=\"col-md-6\">\n");
					sb.append("                                 <img src=\"${#clazz.").append(columnVO.getColomnName()).append("}\" class=\"img-responsive\"/>\n");
					sb.append("                            </div>\n");
					sb.append("                            <div class=\"col-md-3\"><label for=\"").append(columnVO.getColomnName()).append("\"></label></div>\n");
					sb.append("                       </div>\n");
					sb.append("                 </c:if>\n");
				}
			}
			i++;
		}
		
		formTemplate = formTemplate.replace("#allFields", sb.toString());
		formTemplate = formTemplate.replace("#fields", fields.toString());
		formTemplate = formTemplate.replace("#richEditor", richEditor.toString());
		formTemplate = formTemplate.replace("#Setter", fieldsSetter.toString());
		
		return formTemplate;
	}
	
	private static String buildColDetailString(List<ColumnVO> lstShow){
		StringBuilder sb = new StringBuilder();
		for(int i=1; i<lstShow.size(); i++){
			sb.append(",{\"targets\":").append(i+1).append(",\"orderable\": ").append(lstShow.get(i).getIsOrderable()==1?true:false)
			.append(",\"searchable\": ").append(lstShow.get(i).getIsSearchable()==1?true:false).append("}");
		}
		
		return sb.toString();
	}
	
	private static String buildThHeaderString(List<ColumnVO> lstColumnVO){
		StringBuilder sb = new StringBuilder();
		for(ColumnVO columnVO : lstColumnVO){
			if(columnVO.getIsInList()==1){
				if(columnVO.getColomnComment().contains(",")){
					columnVO.setColomnComment(columnVO.getColomnComment().substring(0,columnVO.getColomnComment().indexOf(",")));
				}
				sb.append("                    <th>").append(columnVO.getColomnComment()).append("</th>").append("\n");
			}
		}
		return sb.toString();
	}
	
	//构造where sql
	private static String buildWhereSql(List<ColumnVO> lstColumnVO){
		List<ColumnVO> lstTarget = new ArrayList<ColumnVO>();
		for(ColumnVO columnVO : lstColumnVO){
			if(columnVO.getIsSearchable()==1){
				lstTarget.add(columnVO);
			}
		}
		StringBuilder sb = new StringBuilder();
		if(lstTarget.size()!=0){
			sb.append("\" and (");
			for(int i=0; i<lstTarget.size(); i++){
				if(i!=0){
					sb.append("+\" OR ");
				}
				if(i!=lstTarget.size()-1){
					sb.append(lstTarget.get(i).getColomnName()).append(" like '%\"+search+\"%'\" ");
				}else{
					sb.append(lstTarget.get(i).getColomnName()).append(" like '%\"+search+\"%')\"; ");
				}
			}
		}
		return sb.toString();
	}
	
	private static String buildOrderSql(List<ColumnVO> lstColumnVO){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<lstColumnVO.size(); i++){
			if(lstColumnVO.get(i).getIsOrderable()==1){
				sb.append("              case ").append(i+1).append(":\n")
				.append("              orderSql = \" order by ").append(lstColumnVO.get(i).getColomnName())
				.append(" \"+sortType;").append("\n              break;\n");
			}
		}
		return sb.toString();
	}
	
	private static List<ColumnVO> getShowColumnList(List<ColumnVO> lstColumnVO){
		List<ColumnVO> lstResult = new ArrayList<ColumnVO>();
		List<ColumnVO> lstOrder = new ArrayList<ColumnVO>();
		List<ColumnVO> lstNoOrder = new ArrayList<ColumnVO>();
		for(ColumnVO colomnVO : lstColumnVO){
			if(colomnVO.getIsInList()==1){
				if(colomnVO.getIsOrderable()==1){
					lstOrder.add(colomnVO);
				}else{
					lstNoOrder.add(colomnVO);
				}
			}
		}
		
		for(ColumnVO orderVO:lstOrder){
			lstResult.add(orderVO);
		}
		for(ColumnVO orderVO:lstNoOrder){
			lstResult.add(orderVO);
		}
		return lstResult;
	}
	
	private static String buildDataObjectCode(List<ColumnVO> lstShow){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<lstShow.size(); i++){
			if(i!=0){
				sb.append("				");
			}
			sb.append("obj[").append(i+1).append("] = #clazz.get(\"").append(lstShow.get(i).getColomnName()).append("\");\n");
		}
	
		return sb.toString();
	}
	
	private static String buildSaveCode(List<ColumnVO> lstShow){
		StringBuilder sb = new StringBuilder();
		for(ColumnVO columnVO:lstShow){
			if(columnVO.getFinalType()==2){
				isNeedGetFiles = true;
				break;
			}
		}
		if(isNeedGetFiles){
			sb.append("List<UploadFile> files = new ArrayList<UploadFile>();\n")
			.append("        try {files = controller.getFiles();}catch (Exception e){e.printStackTrace();}\n")
			.append("        for(UploadFile file : files){\n").append("            String fieldName = file.getParameterName();\n")
			.append("            #clazz.set(fieldName, ImageUtils.getInstance().saveFile(file, UUID.randomUUID().toString(), true));\n")
			.append("        }\n");
		}
		for(int i=0; i<lstShow.size(); i++){
			if(lstShow.get(i).getFinalType()==2){
				continue;
			}
			
			sb.append("        #clazz.set(\"").append(lstShow.get(i).getColomnName()).append("\", controller.getPara(\"").append(lstShow.get(i).getColomnName()).append("\"));\n");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		//注：不在列表中展示的字段放在末尾，不影响排序字段sql的生成
		//getColumnVO(colomnName, colomnType, colomnComment, 
		//isInForm, isInList, isOrderable, isSearchable, finalType[0、text；1、wangEditor；2、file], isRequired)
		
		List<ColumnVO> lstColumnVO = new ArrayList<ColumnVO>();
		
		ColumnVO c1 = getColumnVO("team_name", null, "原始名称", 1, 1, 1, 1, 0, 0);
		ColumnVO c2 = getColumnVO("std_name", null, "标准名称", 1, 1, 1, 1, 0, 0);
		ColumnVO c3 = getColumnVO("std_name_py", null, "标准拼音", 1, 1, 1, 1, 0, 0);
		ColumnVO c4 = getColumnVO("ok_id", null, "okid", 1, 1, 0, 0, 0, 0);
		ColumnVO c5 = getColumnVO("md5", null, "md5", 1, 1, 0, 0, 0, 1);
		ColumnVO c6 = getColumnVO("std_md5", null, "标准md5", 1, 1, 0, 0, 0, 0);
		ColumnVO c7 = getColumnVO("league_name", null, "所属联赛", 1, 1, 0, 1, 0, 0);
		ColumnVO c8 = getColumnVO("std_name_en", null, "球队英文", 1, 0, 0, 0, 0, 0);
	
		ColumnVO[] allColumns = {c1,c2,c3,c4,c5,c6,c7,c8};
		for(ColumnVO columnVO : allColumns){
			lstColumnVO.add(columnVO);
		}
		
		build("team_dic", "球队名称字典", "com.wwqk", lstColumnVO);
		System.err.println("done!!!");
	}
	
	//getColumnVO(colomnName, colomnType, colomnComment, isInForm, isInList, isOrderable, isSearchable, finalType, isRequired)
	public static ColumnVO getColumnVO(String colomnName, String colomnType, String colomnComment, int isInForm, 
			int isInList, int isOrderable, int isSearchable, int finalType, int isRequired){
		ColumnVO c = new ColumnVO();
		c.setColomnName(colomnName);
		c.setColomnType(colomnType);
		c.setColomnComment(colomnComment);
		c.setIsInForm(isInForm);
		c.setIsInList(isInList);
		c.setIsOrderable(isOrderable);
		c.setIsSearchable(isSearchable);
		c.setFinalType(finalType);
		c.setIsRequired(isRequired);
		return c;
	}
}
