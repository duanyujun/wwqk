<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>

<style>
.error{
	color:red;
}
</style>


<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 情报编辑 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveTipsMatch" method="post">
        	  <input type="hidden" name="id" value="${tipsMatch.id}" />
		      <div class="form-body">
		      	  <div class="form-group">
		              <label class="col-md-3 control-label">联赛名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="league_name" name="league_name" value="${tipsMatch.league_name}" >
		              </div>
		              <div class="col-md-3"><label for="league_name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>比赛时间：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="match_time" name="match_time" value="${tipsMatch.match_time}" disabled="disabled">
		              </div>
		              <div class="col-md-3"><label for="match_time"></label></div>
		          </div>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label">主队名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="home_name" name="home_name" value="${tipsMatch.home_name}" disabled="disabled">
		              </div>
		              <div class="col-md-3"><label for="home_name"></label></div>
		          </div>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label">客队名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="away_name" name="away_name" value="${tipsMatch.away_name}" disabled="disabled">
		              </div>
		              <div class="col-md-3"><label for="away_name"></label></div>
		          </div>
		          
		          <div class="form-group">
                      <label class="control-label col-md-3">关联直播：</label>
                      <div class="col-md-4">
                          <select class="bs-select form-control" data-live-search="true" name="live_match_id">
                          	  <option value="">-请选择直播比赛-</option>
                          	  <c:forEach items="${lstLiveMatch}" var="liveMatch">
                          	  		<option value="${liveMatch.id}">${liveMatch.id}-${liveMatch.league_name}:${liveMatch.match_datetime} / ${liveMatch.home_team_name} vs ${liveMatch.away_team_name}</option>
                          	  </c:forEach>
                          </select>
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
	$(".bs-select").selectpicker({
        noneSelectedText:'请选择',
        noneResultsText:"查询不到 {0}"
    });
	
	if('${tipsMatch.live_match_id}'!=''){
		$('.bs-select').selectpicker('val', '${tipsMatch.live_match_id}');
	}
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

