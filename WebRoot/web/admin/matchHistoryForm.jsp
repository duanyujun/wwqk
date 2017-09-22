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
.reveal-modal{
	width:620px;
}
</style>


<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"><fmt:formatDate value="${history.match_date}" pattern="yyyy-MM-dd HH:mm"/>（第${history.match_round}轮） ${history.home_team_name} vs ${history.away_team_name}</span>
        </div>
    </div>
    <div class="portlet-body">
    	<div class="table-toolbar">
            <div class="row">
                <div class="col-md-6">
                    <div class="btn-group">
                        <button id="sample_editable_1_new" class="btn sbold green data-reveal-id" data-reveal-id="myModal" live_id=""> 添加直播地址
                            <i class="fa fa-plus"></i>
                        </button>
                        <button type="button" class="btn default" onclick="cancel();" style="margin-left:10px;">返 回</button>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="btn-group pull-right" style="display:none;">
                        
                    </div>
                </div>
            </div>
        </div>
    
    	<c:if test="${!empty lstMatchLive }">
    		<table class="table small-table" >
	        	  <thead>
	        	  		<th>直播名称</th>
	        	  		<th>直播链接</th>
	        	  		<th>操作</th>
	        	  </thead>
				  <tbody>
				  		<c:forEach items="${lstMatchLive}" var="matchLive">
				  			<tr>
				  				<td>${matchLive.live_name}</td>
				  				<td>${matchLive.live_url}</td>
				  				<td>
				  					<button id="sample_editable_1_new" class="btn sbold green data-reveal-id" data-reveal-id="myModal" live_id="${matchLive.id}"  live_name="${matchLive.live_name}" live_url="${matchLive.live_url}"> 编辑
			                            <i class="fa fa-edit"></i>
			                        </button>
				  					<button id="deleteBtn" onclick="deleteLive('${matchLive.id}');" class="btn sbold red" style="margin-left:10px;"> 删除
			                            <i class="fa fa-trash"></i>
			                        </button>
				  				</td>
				  			</tr>
				  		</c:forEach>
				  </tbody>
			</table>
    	</c:if>
    	<c:if test="${empty lstMatchLive }">
    		<h2>该场比赛暂无直播地址</h2>
    	</c:if>
        
    </div>
</div>

<div id="myModal" class="reveal-modal">
	<form class="form-horizontal" role="form" id="form">
			  <input type="hidden" name="id" id="id" value=""/>
        	  <input type="hidden" name="matchKey" id="matchKey" value="${history.id}"/>
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>直播名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="live_name" name="live_name" required  placeholder="请输入直播名称">
		              </div>
		              <div class="col-md-3"><label for="live_name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>链接地址：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="live_url" name="live_url" required  placeholder="请输入链接地址">
		              </div>
		              <div class="col-md-3"><label for="live_url"></label></div>
		          </div>
		      </div>
		</form>
	<button type="button" class="btn green" onclick="save();" style="margin-left:50%;">保存</button>
	<button type="button" class="btn default" onclick="cancelPop();" style="margin-left:10px;">取 消</button>
	<a class="close-reveal-modal">&#215;</a>
</div>

<script type="text/javascript">
$(document).ready(function() {
	
	$('.data-reveal-id').click(function(e){
		e.preventDefault();
		var modalLocation = $(this).attr('data-reveal-id');
		$('#'+modalLocation).reveal($(this).data());
		if($(this).attr("live_id")==""){
			$("#id").val("");
		}else{
			$("#id").val($(this).attr("live_id"));
			$("#live_name").val($(this).attr("live_name"));
			$("#live_url").val($(this).attr("live_url"));
		}
		
		var clientWidth = document.body.clientWidth;
		if(clientWidth>=420){
			var left = (clientWidth - $(".reveal-modal").width() - 80)/2;
			$(".reveal-modal").css("left",left+"px");
		}
	});
	
	var validator = $("#form").validate({
		errorPlacement: function(error, element) {
			$( element )
				.closest( "form" )
					.find( "label[for='" + element.attr( "id" ) + "']" )
						.append( error );
		},
		errorElement: "span"
	});
});


function cancel(){
	$('#main-content').load($('#urlHidden').val());
}

function save(){
	var r = $("#form").valid(); 
	if(r==true){
		$.post(
			"/admin/saveMatchLive",
			encodeURI(encodeURI(decodeURIComponent($('#form').formSerialize(),true))),
			function(result){
				var timestamp=new Date().getTime();
				$('#main-content').load("/admin/editMatchHistory?id=${history.id}&t="+timestamp);
				showToast(1, "保存成功！", "温馨提示");
			}
		);
	}
}

function cancelPop(){
	$('#myModal').trigger('reveal:close');
}

function deleteLive(id){
	if(id){
		$.ajax({ 
			url: "/admin/deleteMatchLive", 
			data: {id: id},
	    	success: function(data){
	    		var timestamp=new Date().getTime();
				$('#main-content').load("/admin/editMatchHistory?id=${history.id}&t="+timestamp);
				showToast(1, "删除成功！", "温馨提示");
	        }
		});
	}
}


</script>

