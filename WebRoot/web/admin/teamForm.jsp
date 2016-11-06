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
            <span class="caption-subject bold uppercase"> 编辑球队 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" role="form" id="form" enctype="multipart/form-data" method="post">
        	  <input type="hidden" name="id" value="${team.id}" />
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球队名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="name" name="name" required value="${team.name}" placeholder="请输入球队名称">
		              </div>
		              <div class="col-md-3"><label for="name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>成立时间：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="setup_time" name="setup_time" required value="${team.setup_time}"  placeholder="请输入成立时间">
		              </div>
		              <div class="col-md-3"><label for="setup_time"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球场名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="venue_name" name="venue_name" required value="${team.venue_name}" placeholder="请输入球场名称">
		              </div>
		              <div class="col-md-3"><label for="venue_name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球场图片：</label>
		              <div class="col-md-6">
		                  <input type="file" class="form-control" id="venue_img_local" name="venue_img_local" required value="${team.venue_img_local}" >
		              </div>
		              <div class="col-md-3"><label for="venue_img_local"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label">球队链接：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="team_url" value="${team.team_url}"  placeholder="请输入球队链接">
		              </div>
		              <div class="col-md-3"><label for="team_url"></label></div>
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
		$.post(
			"/admin/teamSave",
			encodeURI(encodeURI(decodeURIComponent($('#form').formSerialize(),true))),
			function(result){
				$('#main-content').load($('#urlHidden').val());
				showToast(1, "保存成功！", "温馨提示");
			}
		);
	}
}


</script>

