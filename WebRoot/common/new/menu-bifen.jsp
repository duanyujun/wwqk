<%@ page contentType="text/html;charset=GB2312"%>

<!-- pc�� -->
<div class="main hidden-sm hidden-xs">
	<div id="main_nav" >	
		<div>
			<div class="logo_div">
				<a href="" title="��ҳ">Ȥ��������</a>
			</div>
			<ul style="float:left;">
				<li class="menu_width ${menuIndex==1?'menu_sel':''}"><a href="">��ҳ</a></li>
				<li class="menu_width ${menuIndex==3?'menu_sel':''}"><a href="live.html">ֱ��</a></li>
				<li class="menu_width ${menuIndex==4?'menu_sel':''}"><a href="videos.html">��Ƶ</a></li>
				<li class="menu_width ${menuIndex==5?'menu_sel':''}"><a href="data.html">����</a></li>
				<li class="menu_width ${menuIndex==6?'menu_sel':''}"><a href="goods.html">��ѡ</a></li>
			</ul>
		</div>
	</div>
</div>

<!-- �ƶ��� -->
<div class="menu_link navbar-fixed-top visible-sm visible-xs" style="float:left;width:100%;background:#00A50D;min-height:35px;color:white;">
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="/" target="_self"><span class="wwqk_menu ${menuIndex==1?'dline':''}">��ҳ</span></a>
	</div>
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="/live.html" target="_self"><span class="wwqk_menu ${menuIndex==3?'dline':''}">ֱ��</span></a>
	</div>
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="videos.html" target="_self"><span class="wwqk_menu ${menuIndex==4?'dline':''}">��Ƶ</span></a>
	</div>
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="/data.html" target="_self"><span class="wwqk_menu ${menuIndex==5?'dline':''}">����</span></a>
	</div>
	<div class="wwqk_menu_wh" style="width:20%;float:left;">
		<a href="/goods.html" target="_self"><span class="wwqk_menu ${menuIndex==6?'dline':''}">��ѡ</span></a>
	</div>
</div>