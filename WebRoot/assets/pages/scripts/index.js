function resizeBackground(){
  var winWidth = $(window).width();
  if (winWidth<992) {
	  $(".mobile-div-img").each(function(){
		  var percent = (parseFloat($(this).attr("height"))/parseFloat($(this).attr("width"))).toFixed(2);
		  var width = winWidth-70;
		  var height = width * percent;
		  $(this).css("width",width+"px").css("height",height+"px");
		  var imageHtml = "<img class='image' src='"+$(this).attr("data-bg-sm")+"' style='width:"+width+"px;height:"+height+"px;' />";
		  $(this).html(imageHtml);
	  });
	  $('.image').viewer(
				{toolbar:false, 
				zIndex:20000,
				shown: function() {
					$(".viewer-canvas").attr("data-action","mix");
				 }
			 }
		);
  } 
}

var startCount = 1;
var guessCount = parseInt('${guessCount}');
var jsonObj;
$(document).ready(function(){
	$(window).resize(function() {
		resizeBackground();
	});
	jsonObj = JSON.parse('${jsonStr}');
	if(jsonObj.length!=0){
		//消息提示
		setTimeout("showMsg()",5000);
		setInterval("showMsg()",40000); 
	}
	
	if(guessCount>0){
		setInterval("changeGuess()",18000); 
	}
});

function changeGuess(){
	if(startCount<guessCount){
		$("#guess_show").html($("#guess_"+startCount).html());
		startCount++;
	}else{
		$("#guess_show").html($("#guess_0").html());
		startCount = 1;
	}
}

var i = 0;
function showMsg(){
	if($(".lobibox-notify").size()>0){
		$(".lobibox-notify").remove();
	}
	
	var leagueName = jsonObj[i].attrs.league_name; 
	var homeName = jsonObj[i].attrs.home_name; 
	var awayName = jsonObj[i].attrs.away_name; 
	var predictionDesc = jsonObj[i].attrs.prediction_desc; 
	var predictionAll = jsonObj[i].attrs.prediction_all;
	var live_match_id = jsonObj[i].attrs.live_match_id;
	var match_key = jsonObj[i].attrs.match_key;
	var match_time = jsonObj[i].attrs.match_time;
	match_time = match_time.substring(0,10);
	var home_team_enname = jsonObj[i].attrs.home_team_enname;
	var away_team_enname = jsonObj[i].attrs.away_team_enname;
	var home_team_id = jsonObj[i].attrs.home_team_id;
	var away_team_id = jsonObj[i].attrs.away_team_id;
	var year_show = jsonObj[i].attrs.year_show;
	
	var urlLink = '';
	if(live_match_id!=0){
		if(home_team_id==undefined || home_team_id==''){
			urlLink = '&nbsp;<a style="color:#ddd;" target="_blank" href="live-'+match_time+'-'+home_team_enname+'-vs-'+away_team_enname+'-'+live_match_id+'.html">详情...</a>';
		}else{
			urlLink = '&nbsp;<a style="color:#ddd;" target="_blank" href="match-'+home_team_enname+'-vs-'+away_team_enname+'_'+match_key+'.html">详情...</a>';
		}
	}
	
	Lobibox.notify(
			  'success',
			  {
				  title: false,   
				  msg: leagueName+' '+homeName+' vs '+awayName+urlLink+'<br><span title="'+predictionAll+'">'+predictionDesc+'</span>',  
				  icon: false, 
				  delay: 25000, 
				  position: "bottom right"
			  }
	); 
	i++;
	if(i==jsonObj.length){
		i=0;
	}
}

$(function(){
	resizeBackground();
	$('#slideBox').slideBox({
		duration : 0.3,//滚动持续时间，单位：秒
		easing : 'linear',//swing,linear//滚动特效
		hideClickBar : false,//不自动隐藏点选按键
	});
	
	if (!(navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
		return;
	}
    var noDataStr = '<div class="dropload-noData">暂无数据</div>';
    if(parseInt('${initCount}')<5){
    	if(parseInt('${initCount}')!=0){
	    	noDataStr = '<div class="dropload-noData">&nbsp;</div>';
    	}
    }
    
    var pageNo = 1;
    // dropload
    $('.container').dropload({
        scrollArea : window,
        domUp : {
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh">↓下拉刷新</div>',
            domUpdate  : '<div class="dropload-update">↑释放更新</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
        },
        domDown : {
            domClass   : 'dropload-down',
            domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
            domNoData  : noDataStr
        },
        loadUpFn : function(me){
            $.ajax({
                type: 'GET',
                data: {pageNo:1},
                url: 'listMore',
                dataType: 'json',
                success: function(data){
                    var strhtml = '';
                    for(var i = 0; i < data.length; i++){
                    	if(i!=0){
                    		strhtml += "<div class=\"col-sm-12 col-xs-12 m-idx-line\"></div>";
                    	}
                    	strhtml += addRecode(data[i]);
                    }
                    $('#list_content').html(strhtml);
                    // 每次数据加载完，必须重置
                    me.resetload();
                    // 重置页数，重新获取loadDownFn的数据
                    pageNo = 1;
                    // 解锁loadDownFn里锁定的情况
                    me.unlock();
                    me.noData(false);
                    $('.image').viewer(
        					{toolbar:false, 
        					zIndex:20000,
        					shown: function() {
        						$(".viewer-canvas").attr("data-action","mix");
        					 }
        				 }
        			);
                },
                error: function(xhr, type){
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });
        },
        loadDownFn : function(me){
        	pageNo++;
            // 拼接HTML
            var strhtml = '';
            $.ajax({
            	type: 'GET',
                data: {pageNo:pageNo},
                url: 'listMore',
                dataType: 'json',
                success: function(data){
                    var arrLen = data.length;
                    if(arrLen > 0){
                    	strhtml += "<div class=\"col-sm-12 col-xs-12 m-idx-line\" ></div>";
                        for(var i=0; i<arrLen; i++){
                        	if(i!=0){
	                    		strhtml += "<div class=\"col-sm-12 col-xs-12 m-idx-line\" ></div>";
	                    	}
                        	strhtml += addRecode(data[i]);
                        }
                    // 如果没有数据
                    }else{
                        // 锁定
                        me.lock();
                        // 无数据
                        me.noData();
                    }
                 	// 插入数据到页面，放到最后面
                    $('#list_content').append(strhtml);
                    // 每次数据插入，必须重置
                    me.resetload();
                    $('.image').viewer(
        					{toolbar:false, 
        					zIndex:20000,
        					shown: function() {
        						$(".viewer-canvas").attr("data-action","mix");
        					 }
        				 }
        			);
                },
                error: function(xhr, type){
                    // 即使加载出错，也得重置
                    me.resetload();
                }
            });
        },
        threshold : 50
    });
});

function addRecode(fun){
	var winWidth = $(window).width();
	var width;
	var height;
	if (winWidth<992) {
		  var percent = (parseFloat($(this).attr("height"))/parseFloat($(this).attr("width"))).toFixed(2);
		  width = winWidth-70;
		  height = width * percent;
	} 
	
	var create_time = '';
	var date_str = '';
	if(fun.create_time){
		create_time = fun.create_time.substring(0,10);
		date_str = create_time.substring(0,10);
	}
	var strhtml = "<div class=\"col-sm-12 col-xs-12\">";
	strhtml+="		   					<div class=\"mob-author\">";
	strhtml+="		                               <div class=\"author-face\">";
	if(fun.type==1){
		strhtml+="											<img src=\"assets/image/page/logo-small.png\">";
	}else{
		strhtml+="											<a href=\"player-"+fun.player_name_en+"-"+fun.player_id+".html\" target=\"_self\"><img src=\""+fun.player_image+"\"></a>";
	}
	strhtml+="		                            </div>";
	if(fun.type==1){
		strhtml+="										<a href=\"fun.html\" target=\"_blank\" class=\"mob-author-a\">";
		strhtml+="			                                <span class=\"author-name\">趣点足球网</span>";
		strhtml+="			                            </a>";
	}else{
		strhtml+="										<a href=\"player-"+fun.player_name_en+"-"+fun.player_id+".html\" target=\"_self\" class=\"mob-author-a\">";
		strhtml+="			                                <span class=\"author-name\">"+fun.player_name+"</span>";
		strhtml+="			                            </a>";
	}
	strhtml+="		                            <span class=\"author-name\">";
	strhtml+="										&nbsp;"+create_time;
	strhtml+="									</span>";
	strhtml+="		                    </div>";
	strhtml+="			    	</div>";
	strhtml+="			    	<div class=\"col-sm-12 col-xs-12 content-title m-idx-title\" >";
	if(fun.type==1){
		strhtml+="							<a href=\"fdetail-"+date_str+"-"+fun.title_en+"-"+fun.id+".html\" target=\"_self\" ><span class=\"summary-mobile\">"+fun.summary+"</span></a>";
	}else{
		strhtml+="							<span class=\"summary-mobile\">"+fun.summary+"</span>";
	}
	strhtml+="					</div>";
	strhtml+="			    	<div class=\"col-sm-12 col-xs-12 content-title m-idx-title\" >";
	if(fun.type==1){
		strhtml+="							<a href=\"fdetail-"+date_str+"-"+fun.title_en+"-"+fun.id+".html\" target=\"_self\" ><img src=\""+fun.image_small+"\" class=\"img-responsive\" alt=\""+fun.title+"\" /></a>";
	}else{
		if(fun.image_big && fun.image_big!=''){
			strhtml+="<div class='mobile-div-img' data-bg-sm='"+fun.image_big+"' width='"+fun.image_big_width+"' height='"+fun.image_big_height+"' style='width:"+width+"px;height:"+height+"px;'><img class='image' src='"+fun.image_big+"' style='width:"+width+"px;height:"+height+"px;' /></div>";
		}
	}
	strhtml+="					</div>";
	strhtml+="					<div class=\"col-sm-12 col-xs-12 padding-zero\" >";
	strhtml+="						<div class=\"index-line\"></div>";
	strhtml+="					</div>";
		
	return strhtml;
}