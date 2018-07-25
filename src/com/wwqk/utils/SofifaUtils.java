package com.wwqk.utils;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wwqk.model.Sofifa;

public class SofifaUtils {
	
	//英超 https://sofifa.com/league/13
	//西甲 https://sofifa.com/league/53?hl=zh-CN
	//德甲 https://sofifa.com/league/19
	//意甲 https://sofifa.com/league/31
	//法甲 https://sofifa.com/league/16
	//阿森纳 https://sofifa.com/team/1?hl=zh-CN
	//球员 https://sofifa.com/player/193301?hl=zh-CN

	public static void collectLeague(String leagueId){
		Connection connection = Jsoup.connect("https://sofifa.com/league/"+leagueId);
		for(Entry<String, String> entry : MatchUtils.getSofifaHeader().entrySet()){
			connection.header(entry.getKey(), entry.getValue());
		}
		try {
			Document doc = connection.get();
			Elements elements = doc.select(".table-hover");
			if(elements.size()>0){
				Elements as = elements.get(0).select("a");
				for(Element element : as){
					String href = element.attr("href");
					href = href.substring(href.lastIndexOf("/")+1);
					collectTeam(href);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int countAll = 1;
	public static void collectTeam(String teamId){
		//System.err.println((countAll++)+" https://sofifa.com/team/"+teamId+"?hl=zh-CN");
		Connection connection = Jsoup.connect("https://sofifa.com/team/"+teamId);
		for(Entry<String, String> entry : MatchUtils.getSofifaHeader().entrySet()){
			connection.header(entry.getKey(), entry.getValue());
		}
		try {
			Document doc = connection.get();
			Elements elements = doc.select(".persist-area");
			Elements aTags = elements.get(0).select("a");
			for(Element a : aTags){
				if(a.attr("href").contains("/player/")){
					collectPlayer(a.attr("href").replace("/player/", ""));
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private static final Pattern FOOT_PATTERN = Pattern.compile("惯用脚</label>(.*?)</li>");
	private static final Pattern INTER_REP_PATTERN = Pattern.compile("国际声誉</label>(.*?)<i");
	private static final Pattern UNUSE_FOOT_PATTERN = Pattern.compile("逆足能力</label>(.*?)<i");
	private static final Pattern TRICK_PATTERN = Pattern.compile("花式技巧</label>(.*?)<i");
	private static final Pattern WORK_RATE_PATTERN = Pattern.compile("Work Rate</label>(.*?)</li>");
	private static final Pattern BODY_TYPE_PATTERN = Pattern.compile("身体模型</label>(.*?)</li>");
	private static final Pattern REALEASE_PATTERN = Pattern.compile("Release Clause</label>(.*?)</li>");
	private static final Pattern NUMBER_PATTERN = Pattern.compile("球衣号码</label>(.*?)</li>");
	private static final Pattern CONTRACT_PATTERN = Pattern.compile("合同到期</label>(.*?)</li>");
	private static final Pattern TEAM_PATTERN = Pattern.compile("/team/.*?>(.*?)</a>");
	private static final Pattern PAC_PATTERN = Pattern.compile("pointPAC =(.*?);");
	private static final Pattern SHO_PATTERN = Pattern.compile("pointSHO =(.*?);");
	private static final Pattern PAS_PATTERN = Pattern.compile("pointPAS =(.*?);");
	private static final Pattern DRI_PATTERN = Pattern.compile("pointDRI =(.*?);");
	private static final Pattern DEF_PATTERN = Pattern.compile("pointDEF =(.*?);");
	private static final Pattern PHY_PATTERN = Pattern.compile("pointPHY =(.*?);");

	
	public static void collectPlayer(String playerId){
		Connection connection = Jsoup.connect("https://sofifa.com/player/"+playerId);
		for(Entry<String, String> entry : MatchUtils.getSofifaHeader().entrySet()){
			connection.header(entry.getKey(), entry.getValue());
		}
		try {
			Document doc = connection.get();
			Sofifa fifaDb = Sofifa.dao.findById(playerId);
			if(fifaDb==null){
				fifaDb = new Sofifa();
			}
			String name = doc.select("h1").eq(0).text();
			name = StringUtils.trim(name.substring(0,name.indexOf("(")));
			
			setFifaValue(fifaDb, "fifa_name", name);
			setFifaValue(fifaDb, "team", clearData(CommonUtils.matcherString(TEAM_PATTERN, doc.html())));
			setFifaValue(fifaDb, "foot", clearData(CommonUtils.matcherString(FOOT_PATTERN, doc.html())));
			setFifaValue(fifaDb, "inter_rep", clearData(CommonUtils.matcherString(INTER_REP_PATTERN, doc.html())));
			setFifaValue(fifaDb, "unuse_foot", clearData(CommonUtils.matcherString(UNUSE_FOOT_PATTERN, doc.html())));
			setFifaValue(fifaDb, "trick", clearData(CommonUtils.matcherString(TRICK_PATTERN, doc.html())));
			setFifaValue(fifaDb, "work_rate", clearData(CommonUtils.matcherString(WORK_RATE_PATTERN, doc.html())));
			setFifaValue(fifaDb, "body_type", clearData(CommonUtils.matcherString(BODY_TYPE_PATTERN, doc.html())));
			setFifaValue(fifaDb, "release_clause", clearData(CommonUtils.matcherString(REALEASE_PATTERN, doc.html())));
			
			Element teamInfo = doc.select(".pl").get(1);
			setFifaValue(fifaDb, "position", clearData(teamInfo.select(".pos").get(0).text()));
			setFifaValue(fifaDb, "number",clearData(CommonUtils.matcherString(NUMBER_PATTERN, doc.html())));
			setFifaValue(fifaDb, "contract",clearData(CommonUtils.matcherString(CONTRACT_PATTERN, doc.html())));
			
			
			Element statsInfo = doc.select(".stats").get(0);
			Elements allSpanElements = statsInfo.select("span");
			
			setFifaValue(fifaDb, "overall_rate",clearData(allSpanElements.get(0).text()));
			setFifaValue(fifaDb, "potential",clearData(allSpanElements.get(1).text()));
			setFifaValue(fifaDb, "market_value",clearData(allSpanElements.get(2).text()));
			setFifaValue(fifaDb, "wage",clearData(allSpanElements.get(3).text()));
			setFifaValue(fifaDb, "pac", CommonUtils.matcherString(PAC_PATTERN, doc.html()));
			setFifaValue(fifaDb, "sho", CommonUtils.matcherString(SHO_PATTERN, doc.html()));
			setFifaValue(fifaDb, "pas", CommonUtils.matcherString(PAS_PATTERN, doc.html()));
			setFifaValue(fifaDb, "dri", CommonUtils.matcherString(DRI_PATTERN, doc.html()));
			setFifaValue(fifaDb, "def", CommonUtils.matcherString(DEF_PATTERN, doc.html()));
			setFifaValue(fifaDb, "phy", CommonUtils.matcherString(PHY_PATTERN, doc.html()));
			
			if(fifaDb.get("id")==null){
				fifaDb.set("id", playerId);
				fifaDb.save();
			}else{
				fifaDb.update();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void setFifaValue(Sofifa fifaDb, String attribute, String value){
		if(StringUtils.isNotEmpty(value)){
			fifaDb.set(attribute, value);
		}
	}
	
	public static String clearData(String source){
		if(StringUtils.isBlank(source)){
			return source;
		}
		source = source.replaceAll("<.*?>", "").replaceAll("\\s+", "");
		return source;
	}
	
	public static void main(String[] args) {
		collectLeague("13");
	}
	
}
