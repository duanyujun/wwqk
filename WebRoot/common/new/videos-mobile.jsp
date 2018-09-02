<%@ page contentType="text/html;charset=UTF-8"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;">
	<div style="width:100%;margin-top:45px;float:left;padding-left:15px;padding-right:15px;">
		
		<div class="team-title" style="font-size:14px;float:left;width:23%;height:30px;line-height:30px;">
			<a href="videos-league-1.html" target="_self"><div class="${leagueId==1?'select-league':'common-league'}">英超</div></a>
		</div>
		<div class="team-title" style="font-size:14px;float:left;width:23%;height:30px;line-height:30px;">
			<a href="videos-league-2.html" target="_self"><div class="${leagueId==2?'select-league':'common-league'}">西甲</div></a>
		</div>
		<div class="team-title" style="font-size:14px;float:left;width:23%;height:30px;line-height:30px;">
			<a href="videos-league-3.html" target="_self"><div class="${leagueId==3?'select-league':'common-league'}">德甲</div></a>
		</div>
		<div class="team-title" style="font-size:14px;float:left;width:23%;height:30px;line-height:30px;">
			<a href="videos-league-4.html" target="_self"><div class="${leagueId==4?'select-league':'common-league'}">意甲</div></a>
		</div>
		<div class="team-title" style="font-size:14px;float:left;width:23%;height:30px;line-height:30px;">
			<a href="videos-league-5.html" target="_self"><div class="${leagueId==5?'select-league':'common-league'}">法甲</div></a>
		</div>
		<div class="team-title" style="font-size:14px;float:left;width:23%;height:30px;line-height:30px;">
			<a href="videos-league-6.html" target="_self"><div class="${leagueId==6?'select-league':'common-league'}">欧冠</div></a>
		</div>
		<div class="team-title" style="font-size:14px;float:left;width:23%;height:30px;line-height:30px;">
			<a href="videos-league-7.html" target="_self"><div class="${leagueId==7?'select-league':'common-league'}">中超</div></a>
		</div>
		<div class="team-title" style="font-size:14px;float:left;width:23%;height:30px;line-height:30px;">
			<a href="videos-league-8.html" target="_self"><div class="${leagueId==8?'select-league':'common-league'}">其他</div></a>
		</div>
		
	</div>
	<div style="width:100%;float:left;padding:0;margin-top:10px;">
		<table class="table small-table" style="text-indent:0;text-align:left;">
		  	<tbody>
			<c:forEach items="${videosPage.list}" var="videos">
				<tr>
			      <td class="a-title" style="line-height:30px;padding-left:5px;">
			      	<div class="text-cut" style="width:350px;max-width:95%;line-height:30px;"><a style="${videos.is_red=='1'?'color:red;':''}" href="/vdetail-<fmt:formatDate value="${videos.match_date}" pattern="yyyy-MM-dd"/>-${videos.match_en_title}-${videos.id}.html" target="_blank">${videos.match_title}</a></div>
			      </td>
			    </tr>
		    </c:forEach>
		   </tbody>
		</table>
   	</div>
   	
   	<div style="width:100%;float:left;padding:0;">
   		<div class="scott center" >
				<a href="/videos-league-${leagueId}-page-1.html" title="首页"> &lt;&lt; </a>
				
				<c:if test="${videosPage.pageNumber == 1}">
					<span class="disabled"> &lt; </span>
				</c:if>
				<c:if test="${videosPage.pageNumber != 1}">
					<a href="/videos-league-${leagueId}-page-${videosPage.pageNumber - 1}.html" > &lt; </a>
				</c:if>
				<c:if test="${videosPage.pageNumber > 8}">
					<a href="/videos-league-${leagueId}-page-1.html">1</a>
					<a href="/videos-league-${leagueId}-page-2.html">2</a>
					...
				</c:if>
				<c:if test="${!empty pageUI.list}">
					<c:forEach items="${pageUI.list}" var="pageNo">
						<c:if test="${videosPage.pageNumber == pageNo }">
							<span class="current">${pageNo}</span>
						</c:if>
						<c:if test="${videosPage.pageNumber != pageNo }">
							<a href="/videos-league-${leagueId}-page-${pageNo}.html">${pageNo}</a>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${(videosPage.totalPage - videosPage.pageNumber) >= 8 }">
					...
					<a href="/videos-league-${leagueId}-page-${videosPage.totalPage - 1}.html">${videosPage.totalPage - 1}</a>
					<a href="/videos-league-${leagueId}-page-${videosPage.totalPage}.html">${videosPage.totalPage}</a>
				</c:if>
				
				<c:if test="${videosPage.pageNumber == videosPage.totalPage}">
					<span class="disabled"> &gt; </span>
				</c:if>
				<c:if test="${videosPage.pageNumber != videosPage.totalPage}">
					<a href="/videos-league-${leagueId}-page-${videosPage.pageNumber + 1}.html"> &gt; </a>
				</c:if>
				
				<a href="/videos-league-${leagueId}-page-${videosPage.totalPage}.html" title="尾页" > &gt;&gt; </a>
			</div>
   	</div>
	

</div>
