package com.wwqk.job;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.wwqk.model.Player;
import com.wwqk.plugin.PlayerInfoPlugin;

public class TransferJob implements Job {
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.err.println("TransferJob player end!!!");
		HttpClient httpClient = new DefaultHttpClient();
		List<Player> lstPlayers = Player.dao.find("SELECT * FROM player ORDER BY team_id IS NULL, team_id DESC");
		for(Player player : lstPlayers){
			System.err.println("player name："+player.getStr("name")+" player id："+player.getStr("id")+" team id："+player.getStr("team_id"));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			PlayerInfoPlugin.updateTransfer(httpClient, player);
		}
		httpClient.getConnectionManager().shutdown();
		System.err.println("TransferJob player end!!!");
	}
	
}






