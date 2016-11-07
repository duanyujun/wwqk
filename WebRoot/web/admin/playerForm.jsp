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
            <span class="caption-subject bold uppercase"> 编辑球员 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/savePlayer" enctype="multipart/form-data" method="post">
        	  <input type="hidden" name="id" value="${player.id}" />
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球员名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="name" name="name" required value="${player.name}" placeholder="请输入球员名称">
		              </div>
		              <div class="col-md-3"><label for="name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>身高：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="height" name="height" required value="${player.height}"  placeholder="请输入身高">
		              </div>
		              <div class="col-md-3"><label for="height"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>体重：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="weight" name="weight" required value="${player.weight}" placeholder="请输入体重">
		              </div>
		              <div class="col-md-3"><label for="weight"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>惯用脚（如：右）：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="foot" name="foot" required value="${player.foot}" placeholder="请输入惯用脚">
		              </div>
		              <div class="col-md-3"><label for="foot"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球衣号码：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="number" name="number" required value="${player.number}" placeholder="请输入球衣号码">
		              </div>
		              <div class="col-md-3"><label for="number"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球员小图片（50x50）：</label>
		              <div class="col-md-6">
		                  <input type="file" class="form-control" id="file_small" name="file_small" >
		              </div>
		              <div class="col-md-3"><label for="file_small"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球员大图片（150x150）：</label>
		              <div class="col-md-6">
		                  <input type="file" class="form-control" id="file_big" name="file_big" >
		              </div>
		              <div class="col-md-3"><label for="file_big"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label">球员链接：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="player_url" value="${player.player_url}"  placeholder="请输入球员链接">
		              </div>
		              <div class="col-md-3"><label for="player_url"></label></div>
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

