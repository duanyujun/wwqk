<%@ page contentType="text/html;charset=UTF-8"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;margin-top:45px;padding-left:5px;padding-right:5px;">
	<div class="bread" style="text-align:left;margin-bottom:10px;">
		当前位置：<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;<a href="/data-${leagueENName}-${team.league_id}.html" title="${leagueName}" target="_self">数据</a>&nbsp;&gt;&nbsp;${team.name}
	</div>
	<div style="width:100%;float:left;">
		<div style="width:45%;float:left;">						
			<img src="assets/image/soccer/teams/150x150/${team.id}.png" class="img-responsive image" style="width:150px;height:150px;" alt="${team.name}" />
		</div>
		<div style="width:55%;float:left;text-align:left;">		
			<div style="width:100%;float:left;height:30px;line-height:30px;color:grey;margin-top:10px;">	
				${team.country}&nbsp;·&nbsp;${team.setup_time}年&nbsp;·&nbsp;<a href="${team.offical_site}" target="_blank"  title="查看${team.name}官网" style="color:grey;">官网</a>
			</div>
			<div style="width:100%;float:left;height:30px;margin-top:10px;color:grey;">	
				地址：${team.address}
			</div>
			<div style="width:100%;float:left;height:30px;line-height:30px;margin-top:10px;color:grey;">	
				${team.telphone}
			</div>
			<c:if test="${!empty postion}">
				<div style="width:100%;float:left;height:30px;line-height:30px;color:grey;">	
					联赛排名：第<span style="color:black;"> ${postion} </span>名
				</div>
			</c:if>
		</div>
	</div>
	<div style="width:100%;float:left;text-align:left;">
		<div style="width:100%;float:left;margin-top:10px;color:grey;">
			${team.venue_address}&nbsp;·&nbsp;${team.venue_name}&nbsp;·&nbsp;容量：${team.venue_capacity}人
		</div>
		<div style="width:100%;float:left;">
			<img src="${team.venue_small_img_local}" class="img-responsive img-rounded" alt="${team.name}球场名称：${team.venue_name}" title="${team.name}球场名称：${team.venue_name}"/>
		</div>
	</div>
	
	<div style="width:100%;float:left;text-align:left;margin-top:10px;">
		<c:forEach items="${lstGroup}" var="group">
			<div style="width:100%;float:left;text-align:left;">
				<table class="table small-table" >
				  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">${group[0].position}</b></caption>
				  <tbody >
				  	<c:set var="i" value="1"/>
				  	<tr style="border-top:1px solid #dddddd; ${2==group.size()?'border-bottom:1px solid #dddddd;':''}">
					<c:forEach items="${group}" var="player">
						<td style="width:50px;border:none;"><a href="player-${player.en_url}-${player.id}.html" target="_self"><img src="${player.img_small_local}" alt="${player.name}" title="${player.name}" style="width:50px;"/></a></td>
				      	<td colspan="${i==group.size()?(group.size()==2?1:3):1}" class="team-title" style="border:none;width:250px;font-size:12px;color:grey;">
				      		<div>
										<div style="height:17px;width:17px;margin-top:2px;float:left;" class="${player.national_flag}" title="${player.nationality}" >&nbsp;</div> 
										<div style="height:17px;float:left;">
											&nbsp;<div class="text_cut" style="width:55px;line-height:17px;float:left;"><a href="player-${player.en_url}-${player.id}.html" target="_blank" title="${player.name}更多信息">${player.name}</a></div>
												<div style="height:17px;float:left;">
														<c:if test="${!empty player.number}">
															<span title="球衣：${player.number}号">[${player.number}号]</span>
														</c:if>
												</div>
										</div>
							</div>
							<div style="line-height:20px;height:20px;clear:both;">
								${player.age}岁 &nbsp;
								<c:if test="${player.goal_count!=0}">
									<nobr><span title="进球数：${player.goal_count}" style="color:black;"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span></nobr>
								</c:if>
								<c:if test="${player.goal_count!=0 && player.assists_count!=0}">
								&nbsp;
								</c:if>
								<c:if test="${player.assists_count!=0}">
									<nobr><span title="助攻数：${player.assists_count}" style="color:black;"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span></nobr>
								</c:if>
							</div>
				      	</td>
				      	<c:if test="${i%2==0}">
						</tr>
						<tr style="${i+2 ge group.size()?'border-bottom:1px solid #dddddd;':''};width:250px;">
						</c:if>
						<c:set var="i" value="${i+1}"></c:set>
					</c:forEach>
					</tr>
					
				  </tbody>
				</table>
			</div>
		</c:forEach>
	</div>
	
	<div style="width:100%;float:left;text-align:left;">
		<table class="table small-table" >
			  <caption style="min-height:30px;text-align:left;"><b style="margin-left:15px;">最近五场比赛</b></caption>
			  <tbody>
			  		<c:forEach items="${lstMatchHistory}" var="history">
			  			<tr >
			  				<td class="a-title" style="font-size:13px;"><a href="team-${history.home_team_en_name}-${history.home_team_id}.html" target="_self"><img src="assets/image/soccer/teams/25x25/${history.home_team_id}.png" style="width:25px;height:25px;" alt="${history.home_team_name}" title="${history.home_team_name}"/>&nbsp;${history.home_team_name}</a></td>
			  				<td class="a-title" style="text-align:center;font-size:13px;">
			  					<c:if test="${fn:contains(history.result, '-')}">
						      		<b>${history.result}</b>
						      	</c:if>
						      	<c:if test="${!fn:contains(history.result, '-')}">
						      		<fmt:formatDate value="${history.match_date}" pattern="yy/MM/dd"/><br><fmt:formatDate value="${history.match_date}" pattern="HH:mm"/>
						      	</c:if>
			  				</td>
			  				<td class="a-title" style="font-size:13px;"><a href="team-${history.away_team_en_name}-${history.away_team_id}.html" target="_self"><img src="assets/image/soccer/teams/25x25/${history.away_team_id}.png" style="width:25px;height:25px;" alt="${history.away_team_name}" title="${history.away_team_name}"/>&nbsp;${history.away_team_name}</a></td>
			  				<td class="a-title" style="text-align:center;font-size:13px;">
			  					<c:if test="${fn:contains(history.result, '-')}">
						      		<a title="观看集锦" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_${history.year_show}-${history.home_team_id}vs${history.away_team_id}.html" target="_self" style="color:grey;">集锦</a>
						      	</c:if>
						      	<c:if test="${!fn:contains(history.result, '-')}">
						      		<b><a title="直播地址" href="match-${history.home_team_en_name}-vs-${history.away_team_en_name}_${history.year_show}-${history.home_team_id}vs${history.away_team_id}.html" target="_self">直播</a></b>
						      	</c:if>
			  				</td>
			  			</tr>
			  		</c:forEach>
			  </tbody>
		</table>
	
	</div>

</div>
