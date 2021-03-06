<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/assets/global/plugins/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/global/plugins/ztree/js/jquery.ztree.core.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/ztree/js/jquery.ztree.excheck.min.js" type="text/javascript"></script>
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
            <span class="caption-subject bold uppercase"> 编辑球队 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveTeam" enctype="multipart/form-data" method="post">
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
		              <label class="col-md-3 control-label"><font color="red">*</font>球场城市：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" id="venue_address" name="venue_address" required value="${team.venue_address}" placeholder="请输入球场城市">
		              </div>
		              <div class="col-md-3"><label for="venue_address"></label></div>
		          </div>
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球场小图片（300x225）：</label>
		              <div class="col-md-6">
		                  <input type="file" class="form-control" id="file_small" name="file_small" >
		              </div>
		              <div class="col-md-3"><label for="file_small"></label></div>
		          </div>
		          <c:if test="${!empty team.venue_small_img_local}">
		          	<div class="form-group">
			              <label class="col-md-3 control-label"></label>
			              <div class="col-md-6">
			                  <img src="${team.venue_small_img_local}" class="img-responsive"/>
			              </div>
			              <div class="col-md-3"><label for="venue_small_img_local"></label></div>
			          </div>
		          </c:if>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label"><font color="red">*</font>球场大图片：</label>
		              <div class="col-md-6">
		                  <input type="file" class="form-control" id="file_big" name="file_big" >
		              </div>
		              <div class="col-md-3"><label for="file_big"></label></div>
		          </div>
		          <c:if test="${!empty team.venue_img_local}">
		          	<div class="form-group">
			              <label class="col-md-3 control-label"></label>
			              <div class="col-md-6">
			                  <img src="${team.venue_img_local}" class="img-responsive"/>
			              </div>
			              <div class="col-md-3"><label for="venue_img_local"></label></div>
			          </div>
		          </c:if>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label">球队链接：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="team_url" value="${team.team_url}"  placeholder="请输入球队链接">
		              </div>
		              <div class="col-md-3"><label for="team_url"></label></div>
		          </div>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label">官网链接：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="offical_site" value="${team.offical_site}"  placeholder="请输入官网链接">
		              </div>
		              <div class="col-md-3"><label for="team_url"></label></div>
		          </div>
		          <div class="form-group" style="disply:none;">
		              <label class="col-md-3 control-label">球衣：</label>
		              <div class="col-md-6">
		                  <input type="text" class="form-control" name="cloth" value="${team.cloth}"  placeholder="请输入球衣">
		              </div>
		              <div class="col-md-3"><label for="cloth"></label></div>
		          </div>
		          
		          <div class="form-group">
		              <label class="col-md-3 control-label">标准md5：</label>
		              <div class="col-md-6">
		              	  <select class="bs-select2 form-control"  id="std_md5" name="std_md5" >
		              	  	 <c:if test="${!empty teamDic}">
		              	  	 	 <option value="${teamDic.std_md5}" selected="selected">${teamDic.std_name}:${teamDic.league_name}</option>
		              	  	 </c:if>
		              	  </select>
		              </div>
		              <div class="col-md-3"><label for="std_md5"></label></div>
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
	         url: "/admin/teamDicStdMd5",
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

</script>

