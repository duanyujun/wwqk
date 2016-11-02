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
            <span class="caption-subject bold uppercase"> Managed Table</span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" role="form" id="form">
        	  <input type="hidden" name="id" value="${roles.attrs.id}" />
        	  <input type="hidden" id="permissionids" name="permissionids" value="" />
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>角色名：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="role_name" name="role_name" required value="${roles.attrs.role_name}" placeholder="请输入角色名">
		              </div>
		              <div class="col-md-3"><label for="role_name"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>角色中文名：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="role_name_cn" name="role_name_cn" required value="${roles.attrs.role_name_cn}"  placeholder="请输入角色中文名">
		              </div>
		              <div class="col-md-3"><label for="role_name_cn"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>分配权限：</label>
		              <div class="col-md-6">
		                  <a href="javascript:;" class="btn btn-sm default" data-reveal-id="myModal"> 编辑 
                               <i class="fa fa-edit"></i>
                           </a>
		              </div>
		              <div class="col-md-3"></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label">描述：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="description" value="${roles.attrs.description}"  placeholder="请输入描述">
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

<div id="myModal" class="reveal-modal">
	<ul id="ztree" class="ztree"></ul>
	<button type="button" class="btn green" onclick="saveTree();" style="margin-left:50%;">确 定</button>
	<button type="button" class="btn default" onclick="cancelTree();" style="margin-left:10px;">取 消</button>
	<a class="close-reveal-modal">&#215;</a>
</div>

<script type="text/javascript">
$(document).ready(function() {
	initztree();
	$('a[data-reveal-id]').click(function(e){
		e.preventDefault();
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

function initztree(){
	var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
	};
	$.ajax({ 
		url: "/role/getPemissions", 
		data: {roleId: '${roles.attrs.id}'},
    	success: function(data){
         $.fn.zTree.init($("#ztree"), setting, data);
    }});
	
}

function cancel(){
	$('#main-content').load($('#urlHidden').val());
}

function save(){
	var r = $("#form").valid(); 
	if(r==true){
		$.post(
			"/role/save",
			encodeURI(encodeURI(decodeURIComponent($('#form').formSerialize(),true))),
			function(result){
				$('#main-content').load($('#urlHidden').val());
				showToast(1, "保存成功！", "温馨提示");
			}
		);
	}
}

function saveTree(){
	//get tree checked ids
	var treeObj = $.fn.zTree.getZTreeObj("ztree");
    var nodes = treeObj.getCheckedNodes(true);
    var ids = "";
	for(var i=0; i<nodes.length; i++){
		ids = ids + nodes[i].id+",";
	}
	if(ids!=""){
		ids = ids.substring(0, ids.length-1);
	}
    $("#permissionids").val(ids);
    $('#myModal').trigger('reveal:close');
    
    $.ajax({ 
		url: "/role/saveRolePermissions", 
		data: {roleId: '${roles.attrs.id}', permissionids:ids},
    	success: function(data){
    		showToast(1, "分配权限成功！", "温馨提示");
        }
	});
}

function cancelTree(){
	$('#myModal').trigger('reveal:close');
}

</script>

