<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/assets/global/plugins/loading/css/showLoading.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/global/plugins/loading/js/jquery.showLoading.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/scripts/md5.min.js" type="text/javascript"></script>
<style>
.error{
	color:red;
}
</style>

<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 用户管理</span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" role="form" id="form">
        	  <input type="hidden" id="id" name="id" value="${user.id}" />
        	  <input type="hidden" id="roleIds" name="roleIds" value="${existRoleIds}" />
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>用户名：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="username" name="username" required value="${user.username}" placeholder="请输入用户名">
		              </div>
		              <div class="col-md-3"><label for="username"></label></div>
		          </div>
		          <c:if test="${empty user.id}">
		          	   <div class="form-group">
			              <label class="col-md-3 control-label"><font color="red">*</font>密码：</label>
			              <div class="col-md-6">
			                  <input type="text" class="form-control" id="password" name="password" required value="${user.password}"  placeholder="请输入密码">
			              </div>
			              <div class="col-md-3"> <label for="password"></label></div>
			          </div>
		          </c:if>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>姓名：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="name" name="name" required value="${user.name}"  placeholder="请输入姓名">
		              </div>
		              <div class="col-md-3"><label for="name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>手机号：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="mobile_no" name="mobile_no" required value="${user.mobile_no}"  placeholder="请输入手机号">
		              </div>
		              <div class="col-md-3"><label for="mobile_no"></label></div>
		          </div>
		          <div class="form-group">
                      <label class="control-label col-md-3"><font color="red">*</font>角色</label>
                      <div class="col-md-4">
                          <select class="bs-select form-control" id="rid" required multiple >
                          	  <c:forEach items="${lstRoles}" var="role">
                          	  		<option value="${role.id}">${role.role_name_cn}</option>
                          	  </c:forEach>
                          </select>
                      </div>
                      <div class="col-md-3"><label for="rid"></label></div>
                  </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label">QQ：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="qq" value="${user.qq}"  placeholder="请输入QQ">
		              </div>
		              <div class="col-md-3"></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label">Email：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="email" value="${user.email}"  placeholder="请输入Email">
		              </div>
		              <div class="col-md-3"></div>
		          </div>
		          
		          <div class="form-group" style="display:none;">
		              <label class="col-md-3 control-label">用户状态：</label>
		              <div class="col-md-6">
		                   <label class="checkbox-inline">
						      <input type="radio" name="ustatus" id="s1" 
						         value="0" > 未激活
						   </label>
						   <label class="checkbox-inline">
						      <input type="radio" name="ustatus" id="s2" 
						         value="1" checked> 已激活
						   </label>
						   <label class="checkbox-inline">
						      <input type="radio" name="ustatus" id="s3" 
						         value="2" > 已注销
						   </label>
		              </div>
		              <div class="col-md-3"></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label">备注：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="remark" value="${user.remark}"  placeholder="请输入备注">
		              </div>
		              <div class="col-md-3"></div>
		          </div>
		          <div class="form-group">
			          <div class="col-md-offset-3 col-md-9">
	                      <button type="button" class="btn green" onclick="save();">保 存</button>
	                      <button type="button" class="btn default" onclick="cancel();" style="margin-left:10px;">取 消</button>
	                  	  <c:if test="${!empty user.id}">
	                  	  		<button type="button" class="btn default" data-reveal-id="myModal" style="margin-left:10px;">修改密码<i class="fa fa-cog"></i></button>
	                  	  </c:if>
	                  </div>
                  </div>
		      </div>
		</form>
    </div>
</div>

<div id="myModal" class="reveal-modal" style="width:600px;">
	<div class="form-body">
		<div class="form-group">
             <label class="col-md-3 control-label"><font color="red">*</font>新密码：</label>
             <div class="col-md-9">
                 <input type="password" class="form-control" id="password" name="password" placeholder="请输入新密码" maxlength="100">
             </div>
             <label class="col-md-3 control-label" style="margin-top:20px;"><font color="red">*</font>重复新密码：</label>
             <div class="col-md-9" style="margin-top:20px;">
                 <input type="password" class="form-control" id="re-password" name="re-password" placeholder="请再次输入新密码" maxlength="100">
             </div>
         </div>
	</div>
	<button type="button" class="btn default" onclick="cancelChange();" style="margin-left:40%;margin-top:20px;">取 消</button>
	<button type="button" class="btn green" onclick="savePass();" style="margin-left:10px;margin-top:20px;">确 定</button>
	
	<a class="close-reveal-modal">&#215;</a>
</div>

<script type="text/javascript">
$(document).ready(function() {
	
	 $(".bs-select").selectpicker({
         iconBase: "fa",
         tickIcon: "fa-check",
         noneSelectedText:'请选择'
     });
	 if('${existRoleIds}'!=''){
		 var array = '${existRoleIds}'.split(",");
		 $('.bs-select').selectpicker('val', array);
	 }
	
	 $('.bs-select').on('changed.bs.select', function (e) {
		 $("#roleIds").val($(".bs-select").val());
	 });
	 
	 $('button[data-reveal-id]').click(function(e){
			e.preventDefault();
			columnOuter = $(this).attr('column');
			var modalLocation = $(this).attr('data-reveal-id');
			$('#'+modalLocation).reveal($(this).data());
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

function savePass(){
	if($("#password").val()=='' || $("#re-password").val()==''){
		showToast(4, "请填写完整", "温馨提示");
		return;	
	}else if($("#password").val()!=$("#re-password").val()){
		showToast(4, "两次输入的密码不一致", "温馨提示");
		return;	
	}
	var password = md5($("#password").val());
	$("body").showLoading();
	$.ajax({ 
		url: "/admin/changePassword", 
		data: {id: $("#id").val(), password:password},
    	success: function(data){
    		$("body").hideLoading();
    		$('#myModal').trigger('reveal:close');
			showToast(1, "修改成功！", "温馨提示");
        }
	});
	
}

function cancelChange(){
	$('#myModal').trigger('reveal:close');
}


function cancel(){
	$('#main-content').load($('#urlHidden').val());
}

function save(){
	var r = $("#form").valid(); 
	if(r==true){
		$.post(
			"/user/save",
			encodeURI(encodeURI(decodeURIComponent($('#form').formSerialize(),true))),
			function(result){
				$('#main-content').load($('#urlHidden').val());
				showToast(1, "保存成功！", "温馨提示");
				
			}
		);
	}
}

</script>

