package com.wwqk.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.wwqk.constants.CommonConstants;
import com.wwqk.constants.LeagueEnum;
import com.wwqk.constants.MenuEnum;
import com.wwqk.constants.PlayerEnum;
import com.wwqk.model.Videos;
import com.wwqk.model.VideosRealLinks;
import com.wwqk.utils.CommonUtils;
import com.wwqk.utils.PageUtils;
import com.wwqk.utils.PassWordUtils;
import com.wwqk.utils.StringUtils;

public class VideosController extends Controller {

	public void index(){
		//联赛id
		String leagueId = getPara("league_id");
		if(StringUtils.isBlank(leagueId)){
			leagueId = LeagueEnum.YC.getKey();
		}
		int pageNumber = getParaToInt("pageNumber", 1);
		
		String id = getPara("id");
		if(StringUtils.isNotBlank(id)){
			if(id.contains("-page-")){
				pageNumber = CommonUtils.getPageNo(id);
				//去掉pageNo段
				id = id.replaceAll("-page-\\d+", "");
			}
			leagueId = CommonUtils.getRewriteId(id);
		}
		
		String whereSql = " and league_id = "+leagueId;
		Page<Videos> videosPage = Videos.dao.paginate(pageNumber, 50, whereSql);
		setAttr("videosPage", videosPage);
		setAttr("pageUI", PageUtils.calcStartEnd(videosPage));
		setAttr("leagueId", leagueId);
		
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.VIDEO.getKey());
		render("new/videos.jsp");
	}
	
	public void detail(){
		String id = getPara("id");
		id = CommonUtils.getRewriteId(id);
		Videos videos = Videos.dao.findById(id);
		if(StringUtils.isNotBlank(videos.getStr("summary"))){
			videos.set("summary", videos.getStr("summary").replaceAll("。 ", "。<br>"));
		}
		
		
		setAttr("videos", videos);
		
		List<VideosRealLinks> lstLinks = VideosRealLinks.dao.find("select * from videos_real_links where videos_id = ? ", id);
		VideosRealLinks tempLinks = null;
		for(VideosRealLinks links:lstLinks){
			if(links.getStr("player_type").equals("3") && "1".equals(links.getStr("video_type"))){
				tempLinks = links;
				lstLinks.remove(links);
				break;
			}
		}
		if(tempLinks!=null){
			lstLinks.add(0, tempLinks);
		}
		for(VideosRealLinks links:lstLinks){
			if(PlayerEnum.PPTV.getKey().equals(links.getStr("player_type"))){
				String code = links.getStr("real_url").substring(links.getStr("real_url").lastIndexOf("/")+1);
				code = code.replace(".swf", "");
				links.getAttrs().put("m_real_url", "http://m.pptv.com/show/"+code+".html?rcc_src=vodplayer_qrcode&rcc_starttime=0");
			}
		}
		
		setAttr("lstLinks", lstLinks);
		setAttr(CommonConstants.MENU_INDEX, MenuEnum.VIDEO.getKey());
		render("new/videosDetail.jsp");
	}
	
	public void play() {
		String linkId = getPara("id");
		VideosRealLinks link = VideosRealLinks.dao.findById(linkId);
		setAttr("type", link.getStr("player_type"));
		setAttr("link", link.getStr("real_url"));
		
		if(PlayerEnum.SSPORTS.getKey().equals(link.getStr("player_type"))){
			render("player/ssportsPlayer.jsp");
		}else{
			render("player/player.jsp");
		}
	}
	
	public void mplay() {
		String linkId = getPara("id");
		VideosRealLinks link = VideosRealLinks.dao.findById(linkId);
		String playerPage = "player/mplayer.jsp";
		if(PlayerEnum.PPTV.getKey().equals(link.getStr("player_type"))){
			//http://player.pptv.com/v/4YeAic2fNPXveXMQ.swf
			//http://m.pptv.com/show/ib5Fu7VW7K2nMSrI.html?rcc_src=vodplayer_qrcode&rcc_starttime=0
			String code = link.getStr("real_url").substring(link.getStr("real_url").lastIndexOf("/")+1);
			code = code.replace(".swf", "");
			link.set("real_url", "http://m.pptv.com/show/"+code+".html?rcc_src=vodplayer_qrcode&rcc_starttime=0");
			//playerPage = "player/mpptvPlayer.jsp";
		}else if(PlayerEnum.SSPORTS.getKey().equals(link.getStr("player_type"))){
			String mlink = link.getStr("real_url").substring(0, link.getStr("real_url").indexOf("&"));
			mlink = mlink.replace("bqurl=", "");
			mlink = mlink.replaceAll("%3A", ":").replaceAll("%2F", "/");
			link.set("real_url", mlink);
			playerPage =  "player/mssportsPlayer.jsp";
		}else if(PlayerEnum.QQ.getKey().equals(link.getStr("player_type"))){
			String mlink = "https://m.v.qq.com/play.html?cid="+PassWordUtils.createPassWord(16)+"&vid="+link.getStr("real_url")+"&ptag=v_qq_com%23v.play.adaptor%233";
			link.set("real_url", mlink);
		}
		setAttr("type", link.getStr("player_type"));
		setAttr("link", link.getStr("real_url"));
		render(playerPage);
	}
	
}
