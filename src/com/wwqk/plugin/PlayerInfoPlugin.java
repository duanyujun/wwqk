package com.wwqk.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jfinal.plugin.activerecord.Db;
import com.wwqk.model.Player;
import com.wwqk.model.Transfer;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.FetchHtmlUtils;
import com.wwqk.utils.StringUtils;

public class PlayerInfoPlugin {

	public static void updateTransfer(HttpClient httpClient, Player player){
		String htmlTeam = FetchHtmlUtils.getHtmlContent(httpClient, player.getStr("player_url"));
		if(StringUtils.isNotBlank(htmlTeam)){
			Document document = Jsoup.parse(htmlTeam);
			List<Transfer> lstTransfer = new ArrayList<Transfer>();
			Elements contentDiv = document.select(".transfers-container");
			if(contentDiv.size()>0){
				Elements trElements = contentDiv.get(0).select("tr");
				for(int i=1; i<trElements.size(); i++){
					Element trNode = trElements.get(i);
					Transfer transfer = new Transfer();
					transfer.set("date", CommonUtils.getDateByString(trNode.child(0).text()));
					transfer.set("from_team", trNode.child(1).child(0).attr("title"));
					transfer.set("to_team", trNode.child(2).child(0).attr("title"));
					String[] result = CommonUtils.getCNValueFromEuro(trNode.child(3).text());
					transfer.set("value", result[0]);
					transfer.set("extra", result[1]);
					transfer.set("player_id", player.get("id"));
					transfer.set("update_time", new Date());
					lstTransfer.add(transfer);
				}
			}		
			if(lstTransfer.size()>0){
				Db.update("delete from transfer where player_id = ?", player.getStr("id"));
				Db.batchSave(lstTransfer, lstTransfer.size());
			}
		}
	}
	
}
