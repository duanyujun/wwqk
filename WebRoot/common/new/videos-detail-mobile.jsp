<%@ page contentType="text/html;charset=UTF-8"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;">
	<div style="width:100%;float:left;padding:0;padding-bottom:10px;">
		 <iframe name="mytplayer" id="mytplayer" frameborder="0" width="100%" height="" marginheight="0" marginwidth="0" scrolling="no" src=""></iframe>
   	</div>
   	
   	<c:forEach items="${lstLinks}" var="link" varStatus="status">
    	<div style="width:100%;float:left;text-align:left;padding:3px;" >
    		<c:if test="${link.player_type=='2'}">
    			<a href="${link.m_real_url}" title="${link.title}" target="mytplayer">${link.title}</a>
    		</c:if>
    		<c:if test="${link.player_type!='2'}">
    			<a href="/videos/mplay?id=${link.id}" title="${link.title}" target="mytplayer">${link.title}</a>
    		</c:if>
		</div>
	</c:forEach>
	
	<div style="width:100%;float:left; text-align:left;padding:3px;">
		${videos.description}
		<c:if test="${(!empty videos.summary) && videos.summary!='0'}">
			<br>
			${videos.summary}
		</c:if>
	</div>

</div>
