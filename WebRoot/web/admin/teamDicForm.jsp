<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/global/scripts/md5.min.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/select2/select2.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/assets/global/plugins/select2/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/global/plugins/select2/select2.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/select2/i18n/zh-CN.js" type="text/javascript"></script>

<style>
.error{
	color:red;
}
</style>


<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 编辑球队名称字典 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveTeamDic"   method="post">
        	  <input type="hidden" name="id" value="${teamDic.id}" />
			  
		      <div class="form-body">
                 <div class="form-group">
                      <label class="col-md-3 control-label">okid：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="ok_id" name="ok_id"  value="${teamDic.ok_id}" readonly placeholder="okid">
                      	   <label for="chk_std"><input type="checkbox" id="chk_std" name="chk_std" />设为标准</label>
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label"><font color="red">*</font>原始名称：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="team_name" name="team_name" ${teamDic.ok_id>0?'readonly':''}  required onblur="generateMd5(this);" value="${teamDic.team_name}" placeholder="请输入原始名称">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label"><font color="red">*</font>md5：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="md5" name="md5" required value="${teamDic.md5}" placeholder="md5" readonly>
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">标准名称：</label>
                      <div class="col-md-6">
		              	  <select class="bs-select2 form-control"  id="std_name" name="std_name" >
		              	  	 <c:if test="${!empty teamDic}">
		              	  	 	 <option value="${teamDic.std_name}" selected="selected">${teamDic.std_name}:${teamDic.league_name}</option>
		              	  	 </c:if>
		              	  </select>
		              </div>
                      
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">标准md5：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="std_md5" name="std_md5" readonly value="${teamDic.std_md5}" placeholder="请输入标准md5">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">标准拼音：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="std_name_py" name="std_name_py" readonly  value="${teamDic.std_name_py}" placeholder="请输入标准拼音">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">所属联赛：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="league_name" name="league_name"  value="${teamDic.league_name}" placeholder="请输入所属联赛">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">球队英文：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="std_name_en" name="std_name_en" ${teamDic.ok_id>0?'readonly':''}  value="${teamDic.std_name_en}" placeholder="球队英文">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
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
	$(".bs-select2").select2({
	     ajax: {
	         type:'GET',
	         url: "/admin/teamDicSearchData",
	         dataType: 'json',
	         delay: 250,
	         data: function (params) {
	             return {
	            	 q: params.term, // search term 请求参数 ， 请求框中输入的参数
	                 page: params.page
	             };
	         },
	         processResults: function (data, params) {
	             params.page = params.page || 1;
	             return {
	                 results: data.items,
	                 pagination: {
	                     more: (params.page * 30) < data.totalCount
	                 }
	             };
	         },
	         cache: true
	     },
	     placeholder:'请选择',//默认文字提示
	     language: "zh-CN",
	     tags: true,//允许手动添加
	     allowClear: true,//允许清空
	     escapeMarkup: function (markup) { return markup; }, // 自定义格式化防止xss注入
	     minimumInputLength: 1,//最少输入多少个字符后开始查询
	     formatResult: function formatRepo(repo){return repo.text;}, // 函数用来渲染结果
	     formatSelection: function formatRepoSelection(repo){return repo.text;} // 函数用于呈现当前的选择
	 });
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

function generateMd5(obj){
	if(obj.value!=''){
		var hash = md5(obj.value);
		$("#md5").val(hash);
	}
	
}

</script>

