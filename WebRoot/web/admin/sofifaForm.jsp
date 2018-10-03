<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
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
            <span class="caption-subject bold uppercase"> 编辑FIFA数据 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveSofifa"   method="post">
        	  <input type="hidden" name="id" value="${sofifa.id}" />
			  
		      <div class="form-body">
                 <div class="form-group">
                      <label class="col-md-3 control-label"><font color="red">*</font>fifa姓名：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="fifa_name" name="fifa_name" required value="${sofifa.fifa_name}" placeholder="请输入fifa姓名">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">fifa球队：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="team" name="team" value="${sofifa.team}" placeholder="请输入fifa姓名">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="control-label col-md-3"><font color="red">*</font>系统球员id：</label>
                      <div class="col-md-4">
                          <select class="bs-select2 form-control" required id="player_id" name="player_id">
		              	  	 <c:if test="${!empty player}">
		              	  	 	 <option value="${player.id}" selected="selected">${player.name}:${player.id}</option>
		              	  	 </c:if>
		              	  </select>
                      </div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">惯用脚：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="foot" name="foot"  value="${sofifa.foot}" placeholder="请输入惯用脚">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">国际声誉：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="inter_rep" name="inter_rep"  value="${sofifa.inter_rep}" placeholder="请输入国际声誉">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">逆足能力：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="unuse_foot" name="unuse_foot"  value="${sofifa.unuse_foot}" placeholder="请输入逆足能力">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">花式技巧：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="trick" name="trick"  value="${sofifa.trick}" placeholder="请输入花式技巧">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">积极性：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="work_rate" name="work_rate"  value="${sofifa.work_rate}" placeholder="请输入积极性">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">身体条件：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="body_type" name="body_type"  value="${sofifa.body_type}" placeholder="请输入身体条件">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">违约金：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="release_clause" name="release_clause"  value="${sofifa.release_clause}" placeholder="请输入违约金">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">位置：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="position" name="position"  value="${sofifa.position}" placeholder="请输入位置">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">号码：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="number" name="number"  value="${sofifa.number}" placeholder="请输入号码">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">合同到期：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="contract" name="contract"  value="${sofifa.contract}" placeholder="请输入合同到期">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">速度：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="pac" name="pac"  value="${sofifa.pac}" placeholder="请输入速度">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">射门：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="sho" name="sho"  value="${sofifa.sho}" placeholder="请输入射门">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">传球：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="pas" name="pas"  value="${sofifa.pas}" placeholder="请输入传球">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">盘带：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="dri" name="dri"  value="${sofifa.dri}" placeholder="请输入盘带">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">防守：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="def" name="def"  value="${sofifa.def}" placeholder="请输入防守">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">力量：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="phy" name="phy"  value="${sofifa.phy}" placeholder="请输入力量">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">综合能力：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="overall_rate" name="overall_rate"  value="${sofifa.overall_rate}" placeholder="请输入综合能力">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">潜力：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="potential" name="potential"  value="${sofifa.potential}" placeholder="请输入潜力">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" style="display:none;">
                      <label class="col-md-3 control-label">身价：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="market_value" name="market_value"  value="${sofifa.market_value}" placeholder="请输入身价">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group" >
                      <label class="col-md-3 control-label">周薪：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="wage" name="wage"  value="${sofifa.wage}" placeholder="请输入周薪">
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

