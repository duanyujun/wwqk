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
</style>


<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 编辑集锦 </span>
        </div>
        
    </div>
    <div class="portlet-body">
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
                           <input type="text" class="form-control" id="match_date" name="match_date"  value="${videos.match_date}" placeholder="请输入比赛时间">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label"><font color="red">*</font>主队：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="home_team" name="home_team" required value="${videos.home_team}" placeholder="请输入主队">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label"><font color="red">*</font>客队：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="away_team" name="away_team" required value="${videos.away_team}" placeholder="请输入客队">
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
                 <div class="form-group">
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
			          <div class="col-md-offset-3 col-md-9">
	                      <button type="button" class="btn green" onclick="save();">保 存</button>
	                      <button type="button" class="btn default" onclick="cancel();" style="margin-left:10px;">取 消</button>
	                  </div>
                   </div>
		      </div>
		</form>
    </div>
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
});



function cancel(){
	$('#main-content').load($('#urlHidden').val());
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
	$('#main-content').load($('#urlHidden').val());
	showToast(1, "保存成功！", "温馨提示");
}

</script>

