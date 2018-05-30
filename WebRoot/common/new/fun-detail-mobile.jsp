<%@ page contentType="text/html;charset=UTF-8"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;">
	<div style="width:100%;float:left;"><h3 style="font-weight:bold;">${fun.title}</h3></div>
	<div style="width:100%;float:left;">
			<div class="mob-author">
                          <div class="author-face">
                          <img src="assets/image/page/logo-small.png">
                          </div>
                          <span class="mob-author-a">
                          	<span class="author-name">趣点足球网</span>
                          </span>
                          <span class="author-name">
                          	<fmt:formatDate value="${fun.create_time}" pattern="yyyy-MM-dd"/>
                          </span>
                  </div>
		</div>
		<div style="width:100%;float:left;margin-top:20px;">
			<blockquote style="background-color:#f3f7f0;font-size:14px;">
				<img src="assets/image/page/quote.png">${fun.summary}
			</blockquote>
		</div>
		<div style="width:100%;float:left;margin-top:10px;font-size:14px;">
			<div class="well well-lg" style="line-height:2;text-indent:20px;padding:10px;background:#fdfdfd;">
				${fun.content}
			</div>
		</div>
		<c:if test="${!empty fun.source_name}">
			<div class="bread" style="width:100%;float:left;margin-top:10px;font-size:14px;">
				来源：<a href="${fun.source_url}" target="_blank">${fun.source_name}</a>
			</div>
		</c:if>
</div>
