<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
<style>
.error{
	color:red;
}
</style>


<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <span class="caption-subject bold uppercase"> <i class="icon-settings font-dark"></i> 编辑题目 </span>
        </div>
       <center>
        	<button type="button" class="btn default" onclick="cancel();" style="margin-left:10px;">取 消</button>&nbsp;&nbsp;
            <button type="button" class="btn green" onclick="save();">保 存</button>
        </center>
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveQuestion"   method="post">
        	  <input type="hidden" name="id" value="${question.id}" />
			  
		      <div class="form-body">
                 <div class="form-group">
                      <label class="col-md-3 control-label"><font color="red">*</font><a href='https://www.baidu.com/s?wd=${question.title}' target="_blank">题目名称：</a></label>
                      <div class="col-md-6">
                      		<textarea rows="3" cols="120" id="title" name="title" placeholder="请输入题目名称">${question.title}</textarea>
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">源id：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="source_id" name="source_id"  value="${question.source_id}" placeholder="请输入源id">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">正确答案（ABCD）：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="a_right" name="a_right"  value="${question.a_right}" placeholder="请输入正确答案（ABCD）">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">完整正确答案：</label>
                      <div class="col-md-3">
                      		<textarea rows="2" cols="120" id="a_show" name="a_show" placeholder="请输入完整正确答案">${question.a_show}</textarea>
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">更新时间：</label>
                      <div class="col-md-3">
                           <input type="text" class="form-control" id="update_time" name="update_time"  value="${question.update_time}" placeholder="请输入更新时间">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">状态：</label>
                      <div class="col-md-2">
                      	  <select id="status" name="status">
                      	  		<option value="1" ${question.status==1?'selected':''}>待处理</option>
                      	  		<option value="2" ${question.status==2?'selected':''}>推荐</option>
                      	  		<option value="3" ${question.status==3?'selected':''}>废弃</option>
                      	  </select>
                      </div>
                      <label class="col-md-2 control-label">类型：</label>
                      <div class="col-md-3">
                      	  <select id="status" name="status">
                      	  		<c:forEach items="${lstQtype}" var="qtype">
	                      	  		<option value="${qtype.key}" ${question.type==qtype.key?'selected':''}>${qtype.value}</option>
                      	  		</c:forEach>
                      	  </select>
                      </div>
                 </div>

		      </div>
		</form>
		
		<div class="table-toolbar">
	            <div class="row">
	                <div class="col-md-6">
	                    <div class="btn-group">
	                        <button id="sample_editable_1_new" class="btn sbold green data-reveal-id" data-reveal-id="myModal" link_id=""> 添加答案
	                            <i class="fa fa-plus"></i>
	                        </button>
	                        <button type="button" class="btn default" onclick="cancel();" style="margin-left:10px;">返 回</button>
	                    </div>
	                </div>
	            </div>
	        </div>
		<c:if test="${!empty lstAnswer }">
				    		<table class="table small-table" >
					        	  <thead>
					        	  		<th>正确答案</th>
					        	  		<th>完整答案</th>
					        	  		<th>操作</th>
					        	  </thead>
								  <tbody>
								  		<c:forEach items="${lstAnswer}" var="answer">
								  			<tr>
								  				<td>${answer.choice}</td>
								  				<td>${answer.show}</td>
								  				<td>
								  					<button id="sample_editable_1_new" class="btn sbold green data-reveal-id" data-reveal-id="myModal" 
								  					answer_id="${answer.id}"  choice="${answer.choice}" show='${answer.show}' > 编辑
							                            <i class="fa fa-edit"></i>
							                        </button>
								  					<button id="deleteBtn" onclick="deleteLink('${answer.id}');" class="btn sbold red" style="margin-left:10px;"> 删除
							                            <i class="fa fa-trash"></i>
							                        </button>
							                        <button id="chooseBtn" onclick="choose('${answer.choice}','${answer.show}')" class="btn sbold green" style="margin-left:10px;"> 选择
							                            <i class="fa fa-check"></i>
							                        </button>
								  				</td>
								  			</tr>
								  		</c:forEach>
								  </tbody>
							</table>
				    	</c:if>
				    	<c:if test="${empty lstAnswer }">
				    		<h4>暂无答案</h4>
				    	</c:if>
    </div>
</div>

<div id="myModal" class="reveal-modal" style="width:850px;">
	<form class="form-horizontal" role="answer_form" id="answer_form">
			  <input type="hidden" name="id" id="answer_id" value=""/>
        	  <input type="hidden" name="question_id" value="${question.id}"/>
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>答案选项：</label>
		              <div class="col-md-9">
		                  <input type="text" class="form-control" id="choice" name="choice" required  placeholder="请输入答案选项">
		              </div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>完整答案：</label>
		              <div class="col-md-9">
		              	  <textarea id="show" name="show" class="form-control" rows="6" cols="30" required></textarea>
		              </div>
		          </div>
		      </div>
		</form>
	<button type="button" class="btn green" onclick="saveAnswer();" style="margin-left:50%;">保存</button>
	<button type="button" class="btn default" onclick="cancelPop();" style="margin-left:10px;">取 消</button>
	<a class="close-reveal-modal">&#215;</a>
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
	
	$('.data-reveal-id').click(function(e){
		e.preventDefault();
		var modalLocation = $(this).attr('data-reveal-id');
		$('#'+modalLocation).reveal($(this).data());
		if($(this).attr("answer_id")==""){
			$("#answer_id").val("");
		}else{
			$("#answer_id").val($(this).attr("answer_id"));
			$("#choice").val($(this).attr("choice"));
			$("#show").val($(this).attr("show"));
		}
		
		var clientWidth = document.body.clientWidth;
		if(clientWidth>=420){
			var left = (clientWidth - $(".reveal-modal").width() - 80)/2;
			$(".reveal-modal").css("left",left+"px");
		}
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

function saveAnswer(){
	var r = $("#answer_form").valid(); 
	if(r==true){
		$.post(
			"/admin/saveAnswer",
			encodeURI(encodeURI(decodeURIComponent($('#answer_form').formSerialize(),true))),
			function(result){
				var timestamp=new Date().getTime();
				$('#main-content').load("/admin/editQuestion?id=${question.id}&t="+timestamp);
				showToast(1, "保存成功！", "温馨提示");
			}
		);
	}
}

function deleteLink(id){
	if(id){
		$.ajax({ 
			url: "/admin/deleteAnswer", 
			data: {id: id},
	    	success: function(data){
	    		var timestamp=new Date().getTime();
	    		$('#main-content').load("/admin/editQuestion?id=${question.id}&t="+timestamp);
				showToast(1, "删除成功！", "温馨提示");
	        }
		});
	}
}

function cancelPop(){
	$('#myModal').trigger('reveal:close');
}

function choose(choice, show){
	$("#a_right").val(choice);
	$("#a_show").val(show);
}

</script>

