package com.wwqk.utils;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wwqk.model.Player;

public class PlayerUtils {

	public static void updatePlayer(){
		List<Player> lstPlayer = Player.dao.find("SELECT * FROM player where national_flag is null ORDER BY team_id DESC");
		for(Player player : lstPlayer){
			System.err.println("player name："+player.getStr("name")+"  url："+player.getStr("player_url"));
			Connection connection = Jsoup.connect(player.getStr("player_url"));
			try {
				Document document = connection.get();
				Elements elements = document.select(".highlight");
				if(elements.size()>0){
					Element lastTd = elements.get(0).child(3);
					String style = lastTd.child(0).attr("class");
					if(StringUtils.isNotBlank(style)){
						player.set("national_flag", style);
						player.update();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
