<%@ page contentType="text/html;charset=UTF-8"%>

<!-- pc端 -->
<div class="main hidden-sm hidden-xs">
	<div id="main_nav" >	
		<div>
			<div class="logo_div">
				<a href="" title="首页">趣点足球网</a>
			</div>
			<ul style="float:left;">
				<li class="menu_width ${menuIndex==1?'menu_sel':''}"><a href="">首页</a></li>
				<li class="menu_width ${menuIndex==3?'menu_sel':''}"><a href="live.html?bifen=1">直播</a></li>
				<li class="menu_width ${menuIndex==4?'menu_sel':''}"><a href="videos.html">视频</a></li>
				<li class="menu_width ${menuIndex==5?'menu_sel':''}"><a href="data.html">数据</a></li>
				<li class="menu_width ${menuIndex==6?'menu_sel':''}"><a href="goods.html">精选</a></li>
			</ul>
		</div>
	</div>
</div>

<!-- 移动端 -->
<div class="menu_link navbar-fixed-top visible-sm visible-xs" style="float:left;width:100%;background:#00A50D;min-height:35px;color:white;">
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="/" target="_self"><span class="wwqk_menu ${menuIndex==1?'dline':''}">首页</span></a>
	</div>
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="/live.html?bifen=1" target="_self"><span class="wwqk_menu ${menuIndex==3?'dline':''}">直播</span></a>
	</div>
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="videos.html" target="_self"><span class="wwqk_menu ${menuIndex==4?'dline':''}">视频</span></a>
	</div>
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="/data.html" target="_self"><span class="wwqk_menu ${menuIndex==5?'dline':''}">数据</span></a>
	</div>
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="/goods.html" target="_self"><span class="wwqk_menu ${menuIndex==6?'dline':''}">精选</span></a>
	</div>
</div>