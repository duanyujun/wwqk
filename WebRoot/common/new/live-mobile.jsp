<%@ page contentType="text/html;charset=GB2312"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;">
	<ul id="myTab" class="nav nav-tabs bread" style="margin-top:40px;">
					<li class="${bifen==1?'':'active'}" id="m_live_li"><a href="#m_live" data-toggle="tab" >直播</a></li>
					<li class="${bifen==1?'active':''}" id="m_bifen_li"><a href="#m_bifen" data-toggle="tab" >比分</a></li>
					<li ><a href="#m_chatroom" data-toggle="tab" >侃球室</a></li>
				</ul>
				<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in ${bifen==1?'':'active'}" id="m_live" >
							 <table class="table small-table" style="text-indent:0;">
							  	<tbody>
								<c:forEach items="${lstMatch}" var="match">
									<c:if test="${empty match.home_team_name}">
										<tr>
											<td colspan="3" style="padding:0;"><span style="display:block;background:#5cb85c;height:35px;line-height:35px;font-size:16px;color:white;">&nbsp;${match.match_date_week}</span></td>
										</tr>
									</c:if>
									<c:if test="${!empty match.home_team_name}">
										<tr >
										  <td style="width:60px;height:35px;line-height:35px;padding-left:5px;">
										  		<span class="label label-default"><fmt:formatDate value="${match.match_datetime}" pattern="HH:mm"/></span>
										  </td>
									      <td style="width:60px;height:35px;line-height:35px;text-align:left;"><span class="league_${match.league_id}"><div class="text-cut" style="width:60px;line-height:35px;">${match.league_name}</div></span></td>
									      <td style="height:35px;line-height:35px;text-align:left;" class="a-title">
									      	<c:if test="${empty match.league_id}">
									      		<a title="${match.game_id!='0'?'有分析':''}" href="/live-<fmt:formatDate value="${match.match_datetime}" pattern="yyyy-MM-dd"/>-${match.home_team_enname}-vs-${match.away_team_enname}-${match.id}.html" target="_self">
									      		<c:if test="${match.game_id!='0'}"><span class="label label-success">析</span></c:if> <c:if test="${match.tips!='0'}"><span class="label label-success" style="background-color:#03a4a2;">荐</span></c:if> ${match.home_team_name} VS ${match.away_team_name}
									      		</a>
									      	</c:if>
									      	<c:if test="${!empty match.league_id}">
									      		<b class="a-title" ><a title="${match.game_id!='0'?'有分析':''}" href="match-${match.home_team_enname}-vs-${match.away_team_enname}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_self">
									      			<c:if test="${match.game_id!='0'}"><span class="label label-success">析</span></c:if> <c:if test="${match.tips!='0'}"><span class="label label-success" style="background-color:#03a4a2;">荐</span></c:if> ${match.home_team_name} VS ${match.away_team_name}
									      		</a></b>
									      	</c:if>
									      	
									      </td>
									    </tr>
									    <tr>
									    	<td colspan="3" class="a-title" style="height:35px;line-height:35px;border-top:none;padding-left:5px;text-align:left;">
									      	  	<c:forEach items="${match.liveList}" var="live">
									      	  		<nobr><i class="fa fa-tv"></i> <a href="${live.live_url}" target="_blank" style="color:red;">${live.live_name}</a></nobr>
									      	   </c:forEach>
									      	   &nbsp;<nobr><span class="grey-title"><img src="assets/pages/img/zq.gif" style="width:18px;"/> <a class="live-a" target="_self" >比分直播</a></span></nobr>
									       </td>
									    </tr>
									</c:if>
							    </c:forEach>
							   </tbody>
							</table>
						</div>
						<div class="tab-pane fade in ${bifen==1?'active':''}" id="m_bifen" >
							 <table class="main" cellspacing="0" >
									<tbody id="bifenBody" >
										
								   </tbody>
							 </table>
						</div>
						<div class="tab-pane fade in" id="m_chatroom" >
								<div id="m_chat_div" style="float:left;width:100%;">
								</div>
						</div>
				</div>

</div>
