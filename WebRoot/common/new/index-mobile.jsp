<%@ page contentType="text/html;charset=UTF-8"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;">
	<div style="width:100%;float:left;margin-top:45px;">
		<div style="width:50%;float:left;min-height:140px;">
			<!-- 图片 -->
			<img class="img-responsive image" big="${say.image_big}" class="image" style="min-height:140px;" src="${say.image_small}" />
		</div>
		<div style="width:50%;height:140px;float:left;">
			<div class="multi-line-cut" style="-webkit-line-clamp: 5;text-align:left;width:100%;height:110px;float:left;background:#f5f5f5;padding:4px;padding-top:10px;font-size:14px;">
				<span style="color:#666;"><i>${say.content} （<fmt:formatDate value="${say.create_time}" pattern="M月d日"/>）</i></span>
			</div>
			<div style="width:100%;height:30px;float:left;background:#f9f9f9;">
				<div style="width:100%;height:30px;line-height:30px;float:left;margin-left:4px;text-align:left;"><img src="${say.player_img_local}" style="width:30px;height:30px;border-radius:50%;"/>&nbsp;<span class="grey-title" style="color:grey;"><a href="" target="_self" title="${say.player_name}的详细信息">${say.player_name}</a></span></div>
			</div>
		</div>
	</div>
	<!-- 赛事预告 -->
	<div style="width:100%;float:left;padding:3px;padding-top:5px;">
			<c:forEach items="${liveMatchList}" var="match" end="4">
				<div style="width:100%;height:60px;float:left;">
					<div style="width:100%;height:30px;line-height:30px;float:left;text-align:left;padding-left:5px;">
						<span class="label label-primary">${match.league_name}</span> <span style="color:#aaa;font-size:12px;">${match.weekday}</span>
					</div>
					<div class="a-title" style="width:100%;height:30px;line-height:30px;text-align:left;padding-left:5px;">
						<span style="color:#888;"><fmt:formatDate value="${match.match_datetime}" pattern="M月d日 HH:mm"/></span> 
							<c:if test="${empty match.league_id}">
								<a href="/live-<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/>-${match.home_team_enname}-vs-${match.away_team_enname}-${match.id}.html" target="_self" title="查看详情">${match.home_team_name} <img src="assets/image/page/team_default.png" style="width:20px;height:20px;" > <span style="font-size:12px;color:#777;">VS</span> <img src="assets/image/page/team_default.png" style="width:20px;height:20px;" > ${match.away_team_name }<c:if test="${match.game_id!=0}"> <span class="label label-success" title="分析">析</span></c:if></a>
							</c:if>
							<c:if test="${!empty match.league_id}">
								<a href="/match-${match.home_team_enname}-vs-${match.away_team_enname}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_self" title="查看详情">${match.home_team_name} <img src="assets/image/page/team_default.png" style="width:20px;height:20px;" > <span style="font-size:12px;color:#777;">VS</span> <img src="assets/image/page/team_default.png" style="width:20px;height:20px;" > ${match.away_team_name }<c:if test="${match.game_id!=0}"> <span class="label label-success" title="分析">析</span></c:if></a>
							</c:if>
					</div>
				</div>
			</c:forEach>
	</div>
	<!-- 资讯 -->
	<div style="width:100%;float:left;text-align:left;padding-left:3px;padding-top:3px;margin-bottom:10px;">
		<c:forEach items="${funList}" var="fun" end="4">
			<div class="text-cut a-title" title="${fun.title}" style="width:100%;height:34px;line-height:34px;font-size:14px;"><span style="font-size:16px;color:#ddd;">&bull;</span> <a href="/fdetail-<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>-${fun.title_en}-${fun.id}.html" target="_self"><span >${fun.title}（<fmt:formatDate value="${fun.create_time}" pattern="yyyy/MM/dd"/>）</span></a></div>
		</c:forEach>
	</div>
	<!-- 视频集锦 -->
	<c:forEach items="${videoList}" var="video" varStatus="status" end="3">
		<div style="width:170px;float:left;margin-left:10px;">
			<div style="width:170px;height:155px;float:left;margin-right:10px;">
				<div style="width:170px;height:100px;float:left;">
					<a href="/vdetail-<fmt:formatDate value="${video.match_date}" pattern="yyyy-MM-dd"/>-${video.match_en_title}-${video.id}.html" target="_self" style="font-size:12px;"><img src="${(empty video.video_img)?'assets/image/page/v-default.jpg':video.video_img}" class="img-responsive"/></a>
				</div>
				<div  style="width:170px;height:55px;text-align:left;float:left;font-size:12px;padding:4px;margin-top:6px;">
					<div class="multi-line-cut a-title" title="${video.match_title}" style="width:100%;height:40px;float:left;">
						<a href="/vdetail-<fmt:formatDate value="${video.match_date}" pattern="yyyy-MM-dd"/>-${video.match_en_title}-${video.id}.html" target="_self" style="font-size:12px;">${video.match_title}</a>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>

</div>
