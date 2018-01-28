package com.wwqk.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wwqk.model.Answer;
import com.wwqk.model.Question;

public class QuestionUtils {
	
	private final static Pattern MAX_PAGE_SIZE_PATTERN = Pattern.compile("page>(\\d+)");
	private static String MAIN_URL = null;
	
	/**
	 * 
	 */
	public static void collect(String url, String refererUrl, String type){
		MAIN_URL = url;
		Connection connection = Jsoup.connect(url);
		for(Entry<String, String> entry :get33IQHeader(refererUrl).entrySet()){
			connection.header(entry.getKey(), entry.getValue());
		}
		try {
			Document doc = connection.get();
			handleOnePage(doc, type);
			String nextUrl = null;
			String nextRefererUrl = null;
			String maxPageCountStr = CommonUtils.matcherString(MAX_PAGE_SIZE_PATTERN, doc.html());
			int maxPage = Integer.valueOf(maxPageCountStr);
			for(int i=2; i<= maxPage; i++){
				if(i==2){
					nextUrl = MAIN_URL.replace(".html", "/2.html");
					nextRefererUrl = url;
				}else{
					nextUrl = MAIN_URL.replace(".html", "/"+i+".html");
					nextRefererUrl = MAIN_URL.replace(".html", "/"+(i-1)+".html");
				}
				Thread.sleep(150);
				System.err.println("handle page："+i+" url-"+nextUrl+" ====refererUrl："+nextRefererUrl);
				for(Entry<String, String> entry :get33IQHeader(nextRefererUrl).entrySet()){
					connection.header(entry.getKey(), entry.getValue());
				}
				connection = Jsoup.connect(nextUrl);
				doc = connection.get();
				handleOnePage(doc, type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("done!!!!");
	}
	
	@Before(Tx.class)
	private static void handleOnePage(Document doc, String type){
		Elements quesElements = doc.select(".question_text_content");
		for(Element quesEle:quesElements){
			String sourceId = quesEle.attr("id").replace("question_text_content_", "");
			Element quesTitle = quesEle.select(".timu").first();
			String title = StringUtils.trim(quesTitle.text());
			Question question = Question.dao.findFirst("select * from question where source_id = ? ", sourceId);
			if(question!=null){
				continue;
			}else{
				question = new Question();
			}
			question.set("title", title);
			question.set("source_id", sourceId);
			question.set("r_answer", "0");
			question.set("update_time", new Date());
			question.set("type", type);
			question.save();
			List<Answer> lstAnswer = new ArrayList<Answer>();
			System.err.println(sourceId+"："+title);
			Elements answerElements = quesEle.select(".btn-chooseans");
			for(Element ansEle:answerElements){
				Answer answer = new Answer();
				answer.set("r_answer", ansEle.attr("chooseid"));
				answer.set("answer_show", StringUtils.trim(ansEle.text()));
				answer.set("question_id", question.get("id"));
				lstAnswer.add(answer);
				System.err.println(ansEle.attr("chooseid")+"："+ansEle.text());
			}
			if(lstAnswer.size()>0){
				Db.batchSave(lstAnswer, lstAnswer.size());
			}
		}
	}

	
	private static Map<String, String> get33IQHeader(String refererUrl){
		Map<String, String> header = new HashMap<String, String>();

		header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		header.put("Accept-Encoding", "gzip, deflate, br");
		header.put("Accept-Language", "zh-CN,zh;q=0.9");
		header.put("Connection", "keep-alive");
		header.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36");
		header.put("Host", "www.33iq.com");
		header.put("Referer", refererUrl);

		return header;
	}
	
	public static void main(String[] args) {
		String refererUrl = "https://www.33iq.com/tag/%D6%AA%CA%B6%B0%D9%BF%C6.html";
		//collect(MAIN_URL, refererUrl, );
	}
	
}
