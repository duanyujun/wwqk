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
            <span class="caption-subject bold uppercase"> 编辑网友推荐  ${matchGuess.country} / ${matchGuess.league} &nbsp;&nbsp;<a href="${matchGuess.source_url}" target="_blank">源地址</a></span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveMatchGuess"   method="post">
        	  <input type="hidden" name="id" value="${matchGuess.id}" />

		      <div class="form-body">
		      	 <div class="form-group">
                      <label class="col-md-3 control-label">比赛时间：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="match_time" name="match_time"  value="${matchGuess.match_time}" placeholder="请输入比赛时间">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">比赛id：</label>
                      <div class="col-md-6">
                          <select class="bs-select form-control" data-live-search="true" name="live_match_id" id="live_match_id">
                          	  <option value="">-请选择直播比赛-</option>
                          	  <c:forEach items="${lstLiveMatch}" var="liveMatch">
                          	  		<option value="${liveMatch.id}">${liveMatch.id}-${liveMatch.league_name}:${liveMatch.match_datetime} / ${liveMatch.home_team_name} vs ${liveMatch.away_team_name}</option>
                          	  </c:forEach>
                          </select>
                      </div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label"><font color="red">*</font>英文标题：</label>
                      <div class="col-md-8">
                      		${matchGuess.bet_title}
                           <input type="text" style="display:none;" class="form-control" id="bet_title" name="bet_title" required value="${matchGuess.bet_title}" placeholder="请输入英文标题">
                      </div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">中文标题：</label>
                      <div class="col-md-8">
                           <input type="text" class="form-control" id="bet_title_cn" name="bet_title_cn"  value="${matchGuess.bet_title_cn}" placeholder="请输入中文标题">
                      </div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">英文内容：</label>
                      <div class="col-md-8">
                               ${matchGuess.content}
                      </div>
                 </div>
                 
                 <div class="form-group">
                      <label class="col-md-3 control-label">中文内容：</label>
                      <div class="col-md-8">
                      		 <textarea rows="4" cols="50"  class="form-control" id="content_cn" name="content_cn">${matchGuess.content_cn}</textarea>
                      </div>
                 </div>
                 
                 <div class="form-group">
                      <label class="col-md-3 control-label">系统比赛id：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="match_key" name="match_key"  value="${matchGuess.match_key}" placeholder="请输入系统比赛id">
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
	$(".bs-select").selectpicker({
        noneSelectedText:'请选择',
        noneResultsText:"查询不到 {0}"
    });
	
	if('${matchGuess.live_match_id}'!='0'){
		$('.bs-select').selectpicker('val', '${matchGuess.live_match_id}');
	}
	
	$('.bs-select').on('changed.bs.select', function (e) {
		if($("#bet_title_cn").val()=='' || $("#bet_title_cn").val().lastIndexOf("：")==($("#bet_title_cn").val().length-1))
		var value = $(".filter-option").text();
		var slashIdx = value.indexOf("/");
		if(slashIdx!=-1){
			value = value.substring(slashIdx+2);
			$("#bet_title_cn").val(value+"预测：")
		}
	});
	
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

