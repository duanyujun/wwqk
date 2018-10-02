<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
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
            <span class="caption-subject bold uppercase"> 关联射手 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveShooter163"  method="post">
        	  <input type="hidden" name="id" value="${shooter163.id}" />
		      <div class="form-body">
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font><a href="http://goal.sports.163.com${shooter163.player_url_163}" target="_blank">球员名称（163）</a>：</label>
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
		              	  <select class="bs-select2 form-control" required id="player_id" name="player_id">
		              	  	 <c:if test="${!empty player}">
		              	  	 	 <option value="${player.id}" selected="selected">${player.name}:${player.id}</option>
		              	  	 </c:if>
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
	$(".bs-select2").select2({
	     ajax: {
	         type:'GET',
	         url: "/admin/playData",
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

function initSelect(){
	$(".bs-select").selectpicker({
        noneSelectedText:'请选择',
        noneResultsText:"查询不到 {0}"
    });
	
	if('${shooter163.player_id}'!=''){
		$('.bs-select').selectpicker('val', '${shooter163.player_id}');
	}
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

