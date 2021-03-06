<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<style>
.error{
	color:red;
}
</style>

<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 权限管理</span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" role="form" id="form">
        	  <input type="hidden" name="id" value="${permission.id}" />
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>权限名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="name" name="name" maxlength="100" required value="${permission.name}" placeholder="请输入权限名">
		              </div>
		              <div class="col-md-3"><label for="name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>权限类型：</label>
		              <div class="col-md-6">
		              	  <select class="form-control" id="ptype" name="ptype" required onchange="changeType(this);">
		              	  		<option value="1" ${permission.ptype==1?'selected':''}>菜单</option>
		              	  		<option value="0" ${permission.ptype==0?'selected':''}>模块</option>
		              	  		<option value="2" ${permission.ptype==2?'selected':''}>其他</option>
		              	  </select>
		              </div>
		              <div class="col-md-3"><label for="ptype"></label></div>
		          </div>
		          <div class="form-group" id="menuGroup">
		              <label class="col-md-3 control-label"><font color="red">*</font>所属模块：</label>
		              <div class="col-md-6">
		              	  <select class="form-control" id="pid" name="pid">
		              	  		<c:forEach items="${parents}" var="parent">
		              	  			<option value="${parent.id}" ${permission.pid==1?'selected':''}>${parent.name}</option>
		              	  		</c:forEach>
		              	  </select>
		              </div>
		              <div class="col-md-3"><label for="ptype"></label></div>
		          </div>
		          <div class="form-group" id="menuValue">
		              <label class="col-md-3 control-label"><font color="red">*</font>权限值：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="pvalue" name="pvalue" maxlength="150" required value="${permission.pvalue}"  placeholder="请输入权限值">
		              </div>
		              <div class="col-md-3"><label for="pvalue"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label">描述：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="description" value="${permission.description}"  placeholder="请输入描述">
		              </div>
		              <div class="col-md-3"></div>
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
			"/permission/save",
			encodeURI(encodeURI(decodeURIComponent($('#form').formSerialize(),true))),
			function(result){
				$('#main-content').load($('#urlHidden').val());
				showToast(1, "保存成功！", "温馨提示");
				
			}
		);
	}
}

function changeType(o){
	if(o.value==1){
		$("#menuValue").show();
		$("#menuGroup").show();
	}else{
		$("#menuGroup").hide();
		if(o.value==0){
			$("#menuValue").hide();
		}else{
			$("#menuValue").show();
		}
	}
}

</script>

