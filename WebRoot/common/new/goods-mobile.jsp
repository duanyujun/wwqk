<%@ page contentType="text/html;charset=UTF-8"%>

<div class="visible-sm visible-xs" style="width:100%;float:left;text-align:left;">
<c:forEach items="${goodsPage.list}" var="goods" varStatus="status">
			<div class="goods-div" style="width:100%;float:left;">
				<div style="float:left;width:100%;">
					<a href="${goods.tbk_short_url}" target="_blank"><img src="${goods.product_img}" style="width:100%;" /></a>
				</div>
				<div style="float:left;width:90%;margin-left:5%;margin-right:5%;height:36px;line-height:40px;">
					<div style="font-size:18px;color:#F40;">
				       <span>¥</span><strong>${goods.price}</strong>
				    </div>
				</div>
				<div style="float:left;width:90%;margin-left:5%;margin-right:5%;height:40px;">
					<div class="more_text goods-link" style="height:40px;">
				       <a href="${goods.tbk_short_url}" href="_blank" title="${goods.product_name}">${goods.product_name}</a>
				    </div>
				</div>
			</div>
</c:forEach>
</div>
