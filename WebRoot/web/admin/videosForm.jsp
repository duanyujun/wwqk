<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
<style>
.error{
	color:red;
}
.text_cut{text-overflow:ellipsis;white-space:nowrap;overflow:hidden;}
</style>


<div class="portlet light bordered">
    <div class="portlet-title" style="height:24px;">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 编辑 ${videos.match_title} ID：${videos.id} </span>
        </div>
        
    </div>
    <div class="portlet-body">
    	<ul id="myTab" class="nav nav-tabs bread" >
					<li class="active"><a href="#v_detail" data-toggle="tab" >详情</a></li>
					<li ><a href="#v_links" data-toggle="tab" >链接</a></li>
				</ul>
		 <div id="myTabContent" class="tab-content">
				<div class="tab-pane fade ${(empty isSecond)?'in active':''}" id="v_detail" >
					 	<form class="form-horizontal" id="form" action="/admin/saveVideos"   method="post">
				        	  <input type="hidden" name="id" value="${videos.id}" />
						      <div class="form-body">
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">联赛id：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="league_id" name="league_id"  value="${videos.league_id}" placeholder="请输入联赛id">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">比赛时间：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="match_date" name="match_date" maxlength="19"  value="${videos.match_date}" placeholder="请输入比赛时间">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">主队：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="home_team" name="home_team"  value="${videos.home_team}" placeholder="请输入主队">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">客队：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="away_team" name="away_team"  value="${videos.away_team}" placeholder="请输入客队">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">标题：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="match_title" name="match_title"  value="${videos.match_title}" placeholder="请输入标题">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">源地址：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="source_url" name="source_url"  value="${videos.source_url}" placeholder="请输入源地址">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group" style="display:none;">
				                      <label class="col-md-3 control-label">来源：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="from_site" name="from_site"  value="${videos.from_site}" placeholder="请输入来源">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">比赛id：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="match_history_id" name="match_history_id"  value="${videos.match_history_id}" placeholder="请输入比赛id">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">关键字：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="keywords" name="keywords"  value="${videos.keywords}" placeholder="请输入关键字">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">简述：</label>
				                      <div class="col-md-6">
				                           <input type="text" class="form-control" id="description" name="description"  value="${videos.description}" placeholder="请输入简述">
				                      </div>
				                      <div class="col-md-3"><label for="name"></label></div>
				                 </div>
				                 <div class="form-group">
				                      <label class="col-md-3 control-label">summary：</label>
				                      <div class="col-md-8">
				                      		 <textarea rows="7" cols="50"  class="form-control" id="summary" name="summary">${videos.summary}</textarea>
				                      </div>
				                 </div>
				
							  		<div class="form-group">
							          <div class="col-md-offset-3 col-md-9">
					                      <button type="button" class="btn green" onclick="save();">保 存</button>
					                      <button type="button" class="btn default" onclick="cancel();" style="margin-left:10px;">取 消</button>
					                  </div>
				                   </div>
						      </div>
						</form>
					 
					 
				</div>
				<div class="tab-pane fade ${(!empty isSecond)?'in active':''}" id="v_links" >
						
						<div class="table-toolbar">
				            <div class="row">
				                <div class="col-md-6">
				                    <div class="btn-group">
				                        <button id="sample_editable_1_new" class="btn sbold green data-reveal-id" data-reveal-id="myModal" link_id=""> 添加视频地址
				                            <i class="fa fa-plus"></i>
				                        </button>
				                        <button type="button" class="btn default" onclick="cancel();" style="margin-left:10px;">返 回</button>
				                    </div>
				                </div>
				            </div>
				        </div>
				        
				        <c:if test="${!empty lstLinks }">
				    		<table class="table small-table" >
					        	  <thead>
					        	  		<th>视频名称</th>
					        	  		<th>链接地址</th>
					        	  		<th>操作</th>
					        	  </thead>
								  <tbody>
								  		<c:forEach items="${lstLinks}" var="links">
								  			<tr>
								  				<td>${links.title}</td>
								  				<td><div class="text_cut" style="width:410px;line-height:32px;" title="${links.real_url}">${links.real_url}</div></td>
								  				<td>
								  					<button id="sample_editable_1_new" class="btn sbold green data-reveal-id" data-reveal-id="myModal" link_id="${links.id}"  
								  					title="${links.title}" real_url="${links.real_url}" player_type="${links.player_type}" video_type="${links.video_type}" 
								  					source_url="${links.source_url}" > 编辑
							                            <i class="fa fa-edit"></i>
							                        </button>
								  					<button id="deleteBtn" onclick="deleteLink('${links.id}');" class="btn sbold red" style="margin-left:10px;"> 删除
							                            <i class="fa fa-trash"></i>
							                        </button>
								  				</td>
								  			</tr>
								  		</c:forEach>
								  </tbody>
							</table>
				    	</c:if>
				    	<c:if test="${empty lstLinks }">
				    		<h4>暂无录像集锦地址</h4>
				    	</c:if>
						
				</div>
		</div>
    
    
        
		
    </div>
</div>

<div id="myModal" class="reveal-modal" style="width:850px;">
	<form class="form-horizontal" role="link_form" id="link_form">
			  <input type="hidden" name="id" id="r_id" value=""/>
        	  <input type="hidden" name="videos_id" value="${videos.id}"/>
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>视频名称：</label>
		              <div class="col-md-9">
		                  <input type="text" class="form-control" id="r_title" name="title" required  placeholder="请输入视频名称">
		              </div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>链接地址：</label>
		              <div class="col-md-9">
		              	  <textarea id="r_real_url" name="real_url" class="form-control" rows="6" cols="30" required></textarea>
		              </div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>播放器类型：</label>
		              <div class="col-md-6">
		                  <select id="r_player_type" name="player_type">
		                  		<option value="1">QQ</option>
		                  		<option value="2">PPTV</option>
		                  		<option value="3">新英</option>
		                  		<option value="4">乐视</option>
		                  		<option value="5">CNTV</option>
		                  		<option value="6">优酷</option>
		                  		<option value="7">新浪</option>
		                  		<option value="8">新浪2</option>
		                  		<option value="9">土豆</option>
		                  		<option value="10">QQNBA</option>
		                  		<option value="11">56</option>
		                  		<option value="12">搜狐</option>
		                  		<option value="13">看点</option>
		                  </select>
		              </div>
		              <div class="col-md-3"><label for="player_type"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>视频类型：</label>
		              <div class="col-md-6">
		                  <select id="r_video_type" name="video_type">
		                  		<option value="1">录像</option>
		                  		<option value="2">集锦</option>
		                  </select>
		              </div>
		              <div class="col-md-3"><label for="video_type"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>源地址：</label>
		              <div class="col-md-9">
		                  <input type="text" class="form-control" id="r_source_url" name="source_url"  placeholder="请输入源地址">
		              </div>
		          </div>
		          
		      </div>
		</form>
	<button type="button" class="btn green" onclick="saveLink();" style="margin-left:50%;">保存</button>
	<button type="button" class="btn default" onclick="cancelPop();" style="margin-left:10px;">取 消</button>
	<a class="close-reveal-modal">&#215;</a>
</div>

<script type="text/javascript">
$(document).ready(function() {
	var validator = $("#form").validate({
		errorPlacement: function(error, element) {
			$( element )
				.closest( "form" )
					.find( "label[for='" + element.attr( "id" ) + "']" )
						.append( error );
		},
		errorElement: "span"
	});
	
	var is_second = '${is_second}';
	if(is_second!=''){
		$('#myTab a:last').tab('show');
	}
	
	$('.data-reveal-id').click(function(e){
		e.preventDefault();
		var modalLocation = $(this).attr('data-reveal-id');
		$('#'+modalLocation).reveal($(this).data());
		if($(this).attr("link_id")==""){
			$("#id").val("");
		}else{
			$("#r_id").val($(this).attr("link_id"));
			$("#r_title").val($(this).attr("title"));
			$("#r_real_url").val($(this).attr("real_url"));
			$("#r_player_type").val($(this).attr("player_type"));
			$("#r_video_type").val($(this).attr("video_type"));
			$("#r_source_url").val($(this).attr("source_url"));
		}
		
		var clientWidth = document.body.clientWidth;
		if(clientWidth>=420){
			var left = (clientWidth - $(".reveal-modal").width() - 80)/2;
			$(".reveal-modal").css("left",left+"px");
		}
	});
});



function cancel(){
	$('#main-content').load($('#urlHidden').val()+"?leagueId=${leagueId}");
}

function save(){
	var r = $("#form").valid(); 
	if(r==true){
		$('#form').submit();
	}
}

$(function(){
    var options = {
        target: '#form',
   		success:showSuccess
    };
    $('#form').submit(function(){
    	
        $(this).ajaxSubmit(options);
        return false;
    });
});

function showSuccess(data){
	$('#main-content').load($('#urlHidden').val()+"?leagueId=${leagueId}");
	showToast(1, "保存成功！", "温馨提示");
}

String.prototype.replaceAll = function(s1,s2){ 
	return this.replace(new RegExp(s1,"gm"),s2); 
}
function saveLink(){
	var r = $("#link_form").valid(); 
	if(r==true){
		if($("#r_real_url").val()!=''){
			var real_url = $("#r_real_url").val();
			real_url = real_url.replaceAll("&","___");
			$("#r_real_url").val(real_url);
		}
		$.post(
			"/admin/saveVideosLink",
			encodeURI(encodeURI(decodeURIComponent($('#link_form').formSerialize(),true))),
			function(result){
				var timestamp=new Date().getTime();
				$('#main-content').load("/admin/editVideos?id=${videos.id}&leagueId=${leagueId}&is_second=true&t="+timestamp);
				showToast(1, "保存成功！", "温馨提示");
			}
		);
	}
}

function cancelPop(){
	$('#myModal').trigger('reveal:close');
}

function deleteLink(id){
	if(id){
		$.ajax({ 
			url: "/admin/deleteVideosLink", 
			data: {id: id},
	    	success: function(data){
	    		var timestamp=new Date().getTime();
				$('#main-content').load("/admin/editVideos?id=${videos.id}&leagueId=${leagueId}&is_second=true&t="+timestamp);
				showToast(1, "删除成功！", "温馨提示");
	        }
		});
	}
}

</script>

