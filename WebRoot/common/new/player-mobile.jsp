<%@ page contentType="text/html;charset=UTF-8"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;text-align:left;padding:5px;padding-top:45px;color:grey;">
	<div class="bread" style="text-align:left;margin-bottom:10px;">
		<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;<a href="/data.html" target="_self">数据</a>&nbsp;&gt;&nbsp;${player.name}
	</div>

	<div  style="width:41%;float:left;">
		<img src="${player.img_big_local}" class="img-responsive" alt="${player.name}" title="${player.name}" />
	</div>
	<div  style="width:59%;float:left;font-size:12px;">
		<div  style="width:100%;float:left;margin-top:10px;">
			${player.nationality} ${player.birthday}（${player.age}岁）
		</div>
		<div  style="width:100%;float:left;margin-top:10px;">
			${player.height}&nbsp;·&nbsp;${player.weight}&nbsp;<c:if test="${!empty player.foot}">·&nbsp;惯用${player.foot}</c:if>
		</div>
		<c:if test="${!empty player.team_id}">
			<div  style="width:100%;float:left;margin-top:10px;">
				<span class="grey-title font-12"><a href="team-${player.team_name_en}-${player.team_id}.html" target="_self" title="${player.team_name}" >${player.team_name}</a></span>&nbsp;·&nbsp;${player.position}&nbsp;·&nbsp;${player.number}号
			</div>
			<div  style="width:100%;float:left;margin-top:10px;">
				赛季数据：
				<c:if test="${player.goal_count!=0}">
		     			<span title="进球数：${player.goal_count}"><img src="assets/pages/img/goal-small.png" style="margin-top:-5px;" /> <b>${player.goal_count}</b></span>
		     			&nbsp;
		     		</c:if>
		     		<c:if test="${player.assists_count!=0}">
		     			<span title="助攻数：${player.assists_count}"><img src="assets/pages/img/goal-assists.png" style="margin-top:-5px;" /> <b>${player.assists_count}</b></span>
		     		</c:if>
			</div>
		</c:if>
		<c:if test="${!empty fifa}">
			<div style="width:100%;float:left;margin-top:10px;">
				周薪： ${fifa.wage}&nbsp;&nbsp;身价： ${fifa.market_value}
			</div>
		</c:if>
	</div>
	<c:if test="${!empty fifa}">
		<div  style="width:100%;float:left;margin-top:10px;">
					<div  style="width:42%;float:left;">
						<div style="width:100%;float:left;height:30px;">
							国际声誉：<c:forEach var="i" begin="1" end="${fifa.inter_rep}"><i class="fa fa-star gold"></i></c:forEach>
						</div>
						<div style="width:100%;float:left;height:30px;">
							逆足能力：<c:forEach var="i" begin="1" end="${fifa.unuse_foot}"><i class="fa fa-star gold"></i></c:forEach>
						</div>
						<div style="width:100%;float:left;height:30px;">
							花式技巧：<c:forEach var="i" begin="1" end="${fifa.trick}"><i class="fa fa-star gold"></i></c:forEach>
						</div>
						<div style="width:100%;float:left;height:30px;">
							<nobr>合同到期：${fifa.contract}</nobr>
						</div>
						<div style="width:100%;float:left;height:30px;">
							违约金：${fifa.release_clause}
						</div>
						<div style="width:100%;float:left;height:30px;">
							综合/潜力：<span class="label label-success">${fifa.overall_rate}/${fifa.potential}</span>
						</div>
					</div>
					<div  style="width:58%;float:left;">
						<div id="mRadardiv" style="width: 200px; height: 150px; -webkit-tap-highlight-color: transparent; user-select: none; background: transparent;"></div>
					</div>
		</div>
	</c:if>
	
	<c:if test="${!empty NO_SAY}">
		<div style="width:100%;float:left;margin-top:15px;">
			<nobr><span style="font-weight:bold;">${player.name}</span>目前还没说说，去瞅瞅<b>${leagueName}</b>其他人 <img src="assets/image/page/smile.png"  style="width:32px;height:32px;"/></nobr>
		</div>
	</c:if>
	<div style="width:100%;float:left;padding-left:0px;padding-right:0px;">
		<div class="index-line"></div>
	</div>
	<div id="list_content"style="width:100%;float:left;margin-top:15px;" >
	    <c:forEach items="${sayPage.list}" var="say" varStatus="status">
		    	<div style="margin-top:10px;width:100%;float:left;">
	   					<div class="mob-author">
	                            <div class="author-face">
			                        <a href="player-${say.player_name_en}-${say.player_id}.html" target="_self"><img src="${say.player_img_local}"></a>
	                            </div>
	                            
	                            <a href="player-${say.player_name_en}-${say.player_id}.html" target="_self" class="mob-author-a">
	                                <span class="author-name">${say.player_name}</span>
	                            </a>
	                            
	                            <span class="author-name say-info">
									&nbsp;<fmt:formatDate value="${say.create_time}" pattern="yyyy-MM-dd HH:mm"/>
								</span>
	                    </div>
		    	</div>
		    	<!-- 内容 -->
		    	<div class="content-title" style="padding-left:45px;width:100%;float:left;">
					<span class="summary" style="color:#292f33;">${say.content}</span>
				</div>
		    	<!-- 图片 -->
		    	<c:if test="${!empty say.image_big}">
			    	<div class="content-title" style="width:100%;float:left;margin-top:8px;padding-left:45px;">
						<img src="${say.image_big}" class="img-responsive image" style="height:220px;" alt="${say.content}" title="${say.content}"/>
					</div>
				</c:if>
				<div style="width:100%;float:left;padding-left:0px;padding-right:0px;">
					<div class="index-line"></div>
				</div>
	    </c:forEach>
    </div>

</div>
