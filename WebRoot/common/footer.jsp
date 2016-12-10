<%@ page contentType="text/html;charset=UTF-8"%>

<div class="row clear_row_margin footer">
	<div style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
		<div class="col-lg-7 col-md-7">
			<div class="row">
				<div class="col-lg-12 col-md-12">
					趣点足球网 - 做有意思的足球网站
				</div>
				<div class="col-lg-12 col-md-12" style="margin-top:15px;">
					Copyright © 趣点足球网   粤ICP备16048166号-4 &nbsp;&nbsp;&nbsp;友情链接：<a href="http://www.rich888.cn/app.htm" style="color:white;text-decoration: none;" target="_blank">飞达网</a>
				</div>
				<div class="col-lg-12 col-md-12" style="margin-top:15px;color:#222;">
					免责声明：本网站内容来源于互联网，如有侵权，请联系QQ：920841228
				</div>
			</div>
		</div>
		<div class="col-lg-5 col-md-5">
			<div class="row">
				<div class="col-lg-12 col-md-12">
					<img src="assets/pages/img/rich888.png" class="img-responsive img-rounded" />
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${ctx}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">

$(function(){
	  if($(window).height()==$(document).height()){
        $(".footer").addClass("navbar-fixed-bottom");
      }
      else{
        $(".footer").removeClass("navbar-fixed-bottom");
      }
});

</script>