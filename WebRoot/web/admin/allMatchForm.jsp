<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/assets/global/plugins/My97DatePicker/WdatePicker.js"></script> 

<style>
.error{
	color:red;
}
</style>


<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 编辑直播比赛 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveAllMatch" method="post">
        	  <input type="hidden" id="info" name="info" value="" />
        
		      <div class="form-body">
		      	  <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>比赛id：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="id" name="id" required value="${match.id}" readonly>
		              </div>
		              <div class="col-md-3"><label for="id"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label">比赛时间：</label>
		              <div class="col-md-9" >
				           <input type="text" id="match_datetime" class="form-control" name="match_datetime" maxlength="19" onFocus="WdatePicker({el:'match_datetime',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${match.match_datetime}" placeholder="请输入比赛时间" style="width:180px;display:inline-block;"><img onclick="WdatePicker({el:'match_datetime',dateFmt:'yyyy-MM-dd HH:mm:ss'})" src="assets/image/page/cal_pick.png"  style="cursor:pointer; ">
		              </div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>主队：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="home_team_name" name="home_team_name" required value="${match.home_team_name}" placeholder="请输入主队">
		              </div>
		              <div class="col-md-3"><label for="name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>客队：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="away_team_name" name="away_team_name" required value="${match.away_team_name}" placeholder="请输入客队">
		              </div>
		              <div class="col-md-3"><label for="name"></label></div>
		          </div>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>情报：</label>
		              <div class="col-md-6">
			              	<div id="div_content" style="height:260px;max-height:900px;">
							    ${match.info}
							</div>
		              </div>
		              <div class="col-md-3"><label for="info"></label></div>
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

var editor = new wangEditor('div_content');
editor.create();


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
    	$("#info").val(editor.$txt.html());
        $(this).ajaxSubmit(options);
        return false;
    });
});

function showSuccess(data){
	$('#main-content').load($('#urlHidden').val());
	showToast(1, "保存成功！", "温馨提示");
}

function setMatchEnd(){
	$("#status").val("完场");
}
</script>

