<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
 <link href="${ctx}/assets/global/plugins/select2/select2.min.css" rel="stylesheet" type="text/css" />
 
<style>
.error{
	color:red;
}
</style>


<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 编辑射手榜 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveSay" enctype="multipart/form-data" method="post">
        	  <input type="hidden" name="id" value="${shooter163.id}" />
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球员名称（163）：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="player_name_163" name="player_name_163" required value="${shooter163.player_name_163}" >
		              </div>
		              <div class="col-md-3"><label for="player_name_163"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球队名称（163）：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="team_name_163" name="team_name_163" required value="${shooter163.team_name_163}"  >
		              </div>
		              <div class="col-md-3"><label for="team_name_163"></label></div>
		          </div>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>关联球员：</label>
		              <div class="col-md-6">
		              	  <select class="form-control" id="player_id" name="player_id" required>
		              	  	<option>xxx</option>
		              	  </select>
		              </div>
		              <div class="col-md-3"><label for="team_name_163"></label></div>
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
	initSelect2();
	
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

function initSelect2(){
	$("#player_id").select2({placeholder:'请选择球员'});
	
}

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

