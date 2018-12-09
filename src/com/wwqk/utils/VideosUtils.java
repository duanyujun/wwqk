package com.wwqk.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.wwqk.model.LeagueMatchHistory;
import com.wwqk.model.Videos;


public class VideosUtils {
	private static final Pattern DATE_PATTERN = Pattern.compile("(\\d+)月(\\d+)日");
	private static final String[] leagueArray = {"英超","西甲","德甲","意甲","法甲"};
	
	public static void repairLeagueVideos(int leagueId){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		String wholeDate = null;
		Matcher matcher = null;
		String title=null,month=null,day = null;
		LeagueMatchHistory leagueMatch = null;
		List<Videos> lstVideo = Videos.dao.find("select * from videos where league_id = ? and match_date is null", leagueId);
		StringBuilder sbTitle = new StringBuilder();
		StringBuilder sbKeywords = new StringBuilder();
		for(Videos video : lstVideo){
			sbTitle.setLength(0);
			sbKeywords.setLength(0);
			title = video.getStr("match_title");
			matcher = DATE_PATTERN.matcher(title);
			if(matcher.find()){
			    month = matcher.group(1);
				if(month.length()==1){
					month = "0"+month;
				}
				day = matcher.group(2);
				if(day.length()==1){
					day = "0" + day;
				}
				wholeDate = year+"-"+month+"-"+day;
				if(leagueId>5){
					if(leagueId==6){
						wholeDate = wholeDate+" 02:45:00";
					}else if(leagueId==7){
						wholeDate = wholeDate+" 17:35:00";
					}else if(leagueId==8){
						if(title.contains("中甲")){
							wholeDate = wholeDate+" 15:30:00";
						}else if(title.contains("国家联赛")){
							wholeDate = wholeDate+" 02:00:00";
						}else if(title.contains("友谊赛")){
							wholeDate = wholeDate+" 02:45:00";
						}else{
							wholeDate = wholeDate+" 22:00:00";
						}
					}
					
					try {
						video.set("match_date", DateTimeUtils.parseDate(wholeDate, DateTimeUtils.ISO_DATETIME_FORMAT_ARRAY));
					} catch (ParseException e) {
						
					}
					String wholeTitle = year+"年"+title;
					video.set("match_title", year+"年"+title);
					video.set("keywords", wholeTitle.replace(" ", ","));
					video.update();
					continue;
				}
				
				leagueMatch = LeagueMatchHistory.dao.findFirst("select * from league_match_history where league_id = ? and home_team_name = ? and date_format(match_date,'%Y-%m-%d') = ? ",
						leagueId, video.getStr("home_team"), wholeDate);
				if(leagueMatch==null){
					leagueMatch = LeagueMatchHistory.dao.findFirst("select * from league_match_history where league_id = ? and away_team_name = ? and date_format(match_date,'%Y-%m-%d') = ? ",
							leagueId, video.getStr("away_team"), wholeDate);
				}else{
					video.set("match_date", leagueMatch.getDate("match_date"));
					String titleStr = sbTitle.append(year).append("年").append(month).append("月").append(day).append("日 ")
							.append(leagueArray[leagueId-1]).append("第").append(leagueMatch.get("match_round")).append("轮 ").append(video.getStr("home_team"))
							.append("vs").append(video.getStr("away_team")).append(" 视频录像 集锦").toString();
					video.set("match_title", titleStr);
					video.set("match_history_id", leagueMatch.getStr("id"));
					
			    	String exTitle = titleStr.replace("视频","");
			    	String[] arrayTitle = exTitle.split(" ");
			    	for(int i=0; i<arrayTitle.length; i++){
			    		if(i!=0){
			    			sbKeywords.append(",");
			    		}
			    		if("录像".equals(arrayTitle[i])){
			    			sbKeywords.append(video.getStr("home_team")).append("录像");
			    		}else if("集锦".equals(arrayTitle[i])){
			    			sbKeywords.append(video.getStr("away_team")).append("集锦");
			    		}else{
			    			sbKeywords.append(arrayTitle[i]);
			    		}
			    	}
					video.set("keywords", sbKeywords.toString());
					video.update();
				}
				
			}
		}
	}

}
