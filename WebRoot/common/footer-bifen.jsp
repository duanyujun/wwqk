<%@ page language="java" contentType="text/html;charset=GB2312"
    pageEncoding="GB2312"%>

<div class="row clear_row_margin footer">
		<div style="min-height:20px;" class="col-lg-8 col-lg-offset-2 col-md-8 col-md-offset-2 col-sm-12 col-xs-12">		
			<div class="col-lg-7 col-md-7">
				<div class="row">
					<div class="col-lg-12 col-md-12" style="margin-top:15px;">
						趣点足球网 - 做有意思的足球网站&nbsp;<a href="http://www.yutet.com/sitemap.html" target="_blank" style="color:white;">网站地图</a>
						<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1261131271'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/z_stat.php%3Fid%3D1261131271%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
					</div>
					<div class="col-lg-12 col-md-12" style="margin-top:10px;">
						Copyright &copy; 趣点足球网   粤ICP备16048166号-4
					</div>
					<div class="col-lg-12 col-md-12 hidden-sm hidden-xs" style="margin-top:10px;color:#222;">
						免责声明：本网站内容来源于互联网，如有侵权，请联系QQ：920841228
					</div>
				</div>
			</div>
			<div class="col-lg-5 col-md-5 hidden-sm hidden-xs" style="margin-top:15px;">
				<div class="row">
					<div class="col-lg-12 col-md-12" >
						<img src="assets/pages/img/rich888.png" class="img-responsive img-rounded" />
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="scroll-to-top">
	    <i class="icon-arrow-up"></i>
	</div>
	
	<script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<script type="text/javascript">
		//Handles the go to top button at the footer
		var handleGoTop = function() {
		    var offset = 300;
		    var duration = 300;
		    if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) { // ios supported
		        $(window).bind("touchend touchcancel touchleave", function(e) {
		            if ($(this).scrollTop() > offset) {
		                $('.scroll-to-top').fadeIn(duration);
		            } else {
		                $('.scroll-to-top').fadeOut(duration);
		            }
		        });
		    } else { // general 
		        $(window).scroll(function() {
		            if ($(this).scrollTop() > offset) {
		                $('.scroll-to-top').fadeIn(duration);
		            } else {
		                $('.scroll-to-top').fadeOut(duration);
		            }
		        });
		    }
		
		    $('.scroll-to-top').click(function(e) {
		        e.preventDefault();
		        $('html, body').animate({
		            scrollTop: 0
		        }, duration);
		        return false;
		    });
		};
		
		$(function(){
			  handleGoTop();
		});
	</script>