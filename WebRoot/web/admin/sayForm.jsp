<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/assets/global/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/global/plugins/ztree/js/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/ztree/js/jquery.ztree.excheck.min.js" type="text/javascript"></script>
<style>
.error{
	color:red;
}
</style>


<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 编辑说说 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveSay" enctype="multipart/form-data" method="post">
        	  <input type="hidden" name="id" value="${say.id}" />
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球员Id：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="player_id" name="player_id" required value="${say.player_id}" placeholder="请输入球员Id">
		              </div>
		              <div class="col-md-3"><label for="player_id"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球员名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="player_name" name="player_name" required value="${say.player_name}"  placeholder="请输入球员名称">
		              </div>
		              <div class="col-md-3"><label for="player_name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>发表时间：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="create_time" name="create_time" required value="${say.create_time}" placeholder="请输入发表时间">
		              </div>
		              <div class="col-md-3"><label for="create_time"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>大图片（610x410）：</label>
		              <div class="col-md-6">
		                  <input type="file" class="form-control" id="image_big" name="image_big" >
		              </div>
		              <div class="col-md-3"><label for="image_big"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>小图片（180x135）：</label>
		              <div class="col-md-6">
		                  <input type="file" class="form-control" id="image_small" name="image_small" >
		              </div>
		              <div class="col-md-3"><label for="image_small"></label></div>
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

