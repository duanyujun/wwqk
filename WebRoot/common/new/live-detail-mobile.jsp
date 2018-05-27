<%@ page contentType="text/html;charset=UTF-8"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;margin-top:45px;padding-left:5px;padding-right:5px;">
	<div class="bread" style="margin-bottom:10px;text-align:left;">
		当前位置：<a href="/" target="_self">首页</a>&nbsp;&gt;&nbsp;<a href="/live" target="_self">直播</a>&nbsp;&gt;&nbsp;${match.home_team_name}vs${match.away_team_name}
	</div>

	<table style="text-indent:0;text-align:left;">
					<tr>
						<td style="font-size:14px;font-weight:bold;color:black;">${match.league_name} / ${match.home_team_name} vs ${match.away_team_name}</td>
					</tr>
					<tr>
						<td style="padding-top:5px;"><fmt:formatDate value="${match.match_datetime}" pattern="yyyy年MM月dd日"/> ${match.weekday} <span class="label label-success"><img src="assets/pages/img/time.png" style="width:18px;margin-top:-5px;"/><fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/></span></td>
					</tr>
					<tr>
						<td style="padding-top:5px;">
							<c:if test="${empty lstMatchLive}">
								<span class="a-title" ><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_blank" style="color:red;">比分直播</a></span>
							</c:if>
							<c:if test="${!empty lstMatchLive}">
									<span class="a-title" >
										<c:forEach items="${lstMatchLive}" var="live">
											<nobr><i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a>&nbsp;&nbsp;</nobr>
										</c:forEach>
										<nobr><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a href="/bifen.html" target="_blank" style="color:red;">比分直播</a></nobr>
									</span>
							</c:if>
						</td>
					</tr>
					<c:if test="${!empty lstTips}">
						<tr>
							<td style="padding-top:10px;">
								<ul id="minfoTab" class="nav nav-tabs bread" >
									<li class="active"><a href="#m_home_tab" data-toggle="tab">主队分析</a></li>
									<li ><a href="#m_away_tab" data-toggle="tab">客队分析</a></li>
								</ul>
								<div id="minfoTabContent" class="tab-content">
									<div class="tab-pane fade in active" id="m_home_tab" style="border:1px solid #ddd;border-top:none;padding:8px;">
											<c:forEach items="${lstTips}" var="tips">
												<c:if test="${tips.is_home_away==0}">
													<c:if test="${tips.is_good_bad=='0'}">
														<div class="alert alert-success" title="有利情报"><span class="label label-danger">主</span> ${tips.news}</div>
													</c:if>
													<c:if test="${tips.is_good_bad=='1'}">
														<div class="alert alert-danger" title="不利情报"><span class="label label-danger">主</span> ${tips.news}</div>
													</c:if>
													<c:if test="${tips.is_good_bad!='0' && tips.is_good_bad!='1'}">
														<div class="alert alert-warning" title="中立情报"><span class="label label-danger">主</span> ${tips.news}</div>
													</c:if>
												</c:if>
											</c:forEach>
									</div>
									<div class="tab-pane fade in" id="m_away_tab" style="border:1px solid #ddd;border-top:none;padding:8px;">
											<c:forEach items="${lstTips}" var="tips">
												<c:if test="${tips.is_home_away==1}">
													<c:if test="${tips.is_good_bad=='0'}">
														<div class="alert alert-success" title="有利情报"><span class="label label-primary">客</span> ${tips.news}</div>
													</c:if>
													<c:if test="${tips.is_good_bad=='1'}">
														<div class="alert alert-danger" title="不利情报"><span class="label label-primary">客</span> ${tips.news}</div>
													</c:if>
													<c:if test="${tips.is_good_bad!='0' && tips.is_good_bad!='1'}">
														<div class="alert alert-warning" title="中立情报"><span class="label label-primary">客</span> ${tips.news}</div>
													</c:if>
												</c:if>
											</c:forEach>
									</div>
								</div>
							</td>
						</tr>
					</c:if>
					
					<c:if test="${!empty lstGuess}">
						<tr>
							<td style="padding-top:10px;">
								<ul id="mguessTab" class="nav nav-tabs bread" >
									<c:forEach items="${lstGuess}" var="guess" varStatus="status">
										<li class="${guessId==guess.id?'active':''}"><a href="#mguess_tab_${status.index}" id="mgtab_${guess.id}" data-toggle="tab">${guess.tipster_name}</a></li>
									</c:forEach>
								</ul>
								<div id="mguessTabContent" class="tab-content">
									<c:forEach items="${lstGuess}" var="guess" varStatus="status">
										<div class="tab-pane fade in ${guessId==guess.id?'active':''}" id="mguess_tab_${status.index}" style="border:1px solid #ddd;border-top:none;padding:8px;">
												<div class="alert alert-success" style="background-color:#f5f5f5;color:#333;">
													<span class="label label-danger">荐</span> <b>${guess.bet_title_cn}</b>
												</div>
												<div class="alert alert-success" style="background-color:#f5f5f5;color:#333;">
													${guess.content_cn}
												</div>
										</div>
									</c:forEach>
								</div>
							</td>
						</tr>
					</c:if>
					
				</table>
</div>
