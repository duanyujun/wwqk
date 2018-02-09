<%@ page contentType="text/html;charset=UTF-8"%>

<table class="table">
  <tbody>
  	<c:if test="${!empty lstGuesses}">
  		<tr>
    		<td id="guess_show" colspan="3" class="team-title font-14" >
    		网友<i>${lstGuesses[0].tipster_name}</i> 推荐 <img src="assets/image/page/voice.png" class="guess-voice" ><br>
    		${lstGuesses[0].bet_title_cn}&nbsp;<a href="guess-${lstGuesses[0].id}.html" target="_blank"><u>详情</u></a>
    		</td>
    	</tr>
  		<c:forEach items="${lstGuesses}" var="guess" varStatus="status">
  			<tr style="display:none;">
	    		<td id="guess_${status.index}" colspan="3" class="team-title font-14" >
	    		网友<i>${guess.tipster_name}</i> 推荐 <img src="assets/image/page/voice.png" class="guess-voice"><br>
	    		${guess.bet_title_cn}&nbsp;<a href="guess-${guess.id}.html" target="_blank"><u>详情</u></a>
	    		</td>
	    	</tr>
  		</c:forEach>
  	</c:if>
  	<c:forEach items="${lstRecomMatches}" var="match">
	    <tr style="font-size:12px;">
	      <td class="team-title">
	      		<div class="row">
	      			<div class="col-lg-12 col-md-12"><center><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/150x150/${match.home_team_id}.png" class="w40-h40" /></a></center></div>
	      			<div class="col-lg-12 col-md-12"><center><a href="team-${match.home_team_en_name}-${match.home_team_id}.html" target="_blank">${match.home_team_name}</a></center></div>
	      		</div>
	      </td>
	      <td class="team-title">
		      	<fmt:formatDate value="${match.match_date}" pattern="yy/MM/dd"/><br>&nbsp;
		      		<b>${match.result}</b><br>&nbsp;
		      		<c:if test="${match.status=='完场'}">
			      		<span class="grey-title font-12"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">集锦</a></span>
			      	</c:if>
			      	<c:if test="${match.status!='完场'}">
			      		<b class="a-title font-12"><a href="match-${match.home_team_en_name}-vs-${match.away_team_en_name}_${match.year_show}-${match.home_team_id}vs${match.away_team_id}.html" target="_blank">直播</a></b>
			      	</c:if>
	      </td>
	      <td class="team-title">
	      		<div class="col-lg-12 col-md-12"><center><a href="team-${match.away_team_en_name}-${match.away_team_id}.html" target="_blank"><img src="assets/image/soccer/teams/150x150/${match.away_team_id}.png" class="w40-h40" /></a></center></div>
	      		<div class="col-lg-12 col-md-12"><center><a href="team-${match.away_team_en_name}-${match.away_team_id}.html" target="_blank">${match.away_team_name}</a></center></div>
	      </td>
	    </tr>
    </c:forEach>
    	
  </tbody>
</table>