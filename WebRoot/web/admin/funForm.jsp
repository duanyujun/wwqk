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
            <span class="caption-subject bold uppercase"> 编辑趣点 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveFun" enctype="multipart/form-data" method="post">
        	  <input type="hidden" name="id" value="${fun.id}" />
        	  <input type="hidden" id="content" name="content" value="" />
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>标题：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="title" name="title" required value="${fun.title}" placeholder="请输入标题">
		              </div>
		              <div class="col-md-3"><label for="title"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>摘要：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="summary" name="summary" required value="${fun.summary}"  placeholder="请输入摘要">
		              </div>
		              <div class="col-md-3"><label for="summary"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>内容：</label>
		              <div class="col-md-6">
			              	<div id="div_content" style="height:400px;max-height:900px;">
							    ${fun.content}
							</div>
		              </div>
		              <div class="col-md-3"><label for="content"></label></div>
		          </div>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>来源名称：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="source_name" name="source_name"  value="${fun.source_name}"  placeholder="请输入来源名称">
		              </div>
		              <div class="col-md-3"><label for="source_name"></label></div>
		          </div>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>来源URL：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="source_url" name="source_url"  value="${fun.source_url}"  placeholder="请输入来源URL">
		              </div>
		              <div class="col-md-3"><label for="source_url"></label></div>
		          </div>
		         
		          <div class="form-group" style="display:none;">
		              <label class="col-md-3 control-label"><font color="red">*</font>大图片：</label>
		              <div class="col-md-6">
		                  <input type="file" class="form-control" id="image_big" name="image_big" >
		              </div>
		              <div class="col-md-3"><label for="image_big"></label></div>
		          </div>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>小图片（220x140）：</label>
		              <div class="col-md-6">
		                  <input type="file" class="form-control" id="image_small" name="image_small" >
		              </div>
		              <div class="col-md-3"><label for="image_small"></label></div>
		          </div>
		          
		          <c:if test="${!empty fun.image_small}">
		          	<div class="form-group">
			              <label class="col-md-3 control-label"></label>
			              <div class="col-md-6">
			                  <img src="${fun.image_small}" />
			              </div>
			              <div class="col-md-3"><label for=""></label></div>
			          </div>
		          </c:if>
		          
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
    	$("#content").val(editor.$txt.html());
        $(this).ajaxSubmit(options);
        return false;
    });
});

function showSuccess(data){
	$('#main-content').load($('#urlHidden').val());
	showToast(1, "保存成功！", "温馨提示");
}

</script>

