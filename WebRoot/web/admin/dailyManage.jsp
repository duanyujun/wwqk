<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/loading/css/showLoading.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/assets/global/plugins/loading/js/jquery.showLoading.min.js" type="text/javascript"></script>
       

<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase">日常管理</span>
        </div>
        
    </div>
    <div class="portlet-body">
        <div class="table-toolbar">
            <div class="row">
                <div class="col-md-4">
                    <div class="btn-group">
                        <button onclick="updateMatches();" class="btn sbold green" style="margin-left:10px;"> 更新比赛
                            <i class="fa fa-cog"></i>
                        </button>
                        <button onclick="syncShooterAssister();" class="btn sbold green" style="margin-left:10px;"> 同步射手助攻
                            <i class="fa fa-refresh"></i>
                        </button>
                    </div>
                </div>
                <div class="col-md-4">
                    <input type="text" id="teamId" maxlength="20" placeholder="球队Id" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
                    <button onclick="updateTeamPlayer();" class="btn sbold green" style="margin-left:10px;"> 更新球队成员
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
                <div class="col-md-4">
                	<select id="leagueId">
                		<option value="">--请选择联赛--</option>
                		<option value="1">英超</option>
                		<option value="2">西甲</option>
                		<option value="3">德甲</option>
                		<option value="4">意甲</option>
                		<option value="5">法甲</option>
                	</select>
                    <button onclick="updateLeaugePlayer();" class="btn sbold green" style="margin-left:10px;"> 更新联赛成员
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
                
            </div>
            <div class="row" style="margin-top:20px;">
             	<div class="col-md-4">
                    <div class="btn-group">
                        <button onclick="updateSiteMap();" class="btn sbold green" style="margin-left:10px;"> 更新网站地图
                            <i class="fa fa-cog"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
</div>

<script type="text/javascript">

function updateSiteMap(){
	showToast(1, "更新中...", "温馨提示");
	$("body").showLoading();
	$.post("/admin/updateSiteMap",
				function(result){
					$("body").hideLoading();
					showToast(1, "更新成功！", "温馨提示");
				}
	);
}


function updateMatches(){
	showToast(1, "更新中...", "温馨提示");
	$("body").showLoading();
	$.post("/admin/handUpdateMatches",
				function(result){
					$("body").hideLoading();
					showToast(1, "更新成功！", "温馨提示");
				}
	);
}

function syncShooterAssister(){
	showToast(1, "同步中...", "温馨提示");
	$("body").showLoading();
	$.post("/admin/syncShooterAssister",
				function(result){
					$("body").hideLoading();
					showToast(1, "同步成功！", "温馨提示");
				}
	);
}

function updateTeamPlayer(){

	if($("#teamId").val()==''){
		showToast(2, "请填写球队ID", "温馨提示");
		return;
	}
	$("body").showLoading();
	showToast(1, "更新中...", "温馨提示");
	$.post("/admin/updateTeamPlayer",
				{teamId: $("#teamId").val()},
				function(result){
					$("body").hideLoading();
					showToast(1, "更新成功！", "温馨提示");
				}
	);
	
}


function updateLeaugePlayer(){

	if($("#leagueId").val()==''){
		showToast(2, "请选择联赛", "温馨提示");
		return;
	}
	$("body").showLoading();
	showToast(1, "更新中...", "温馨提示");
	$.post("/admin/updateLeaguePlayer",
				{leagueId: $("#leagueId").val()},
				function(result){
					$("body").hideLoading();
					showToast(1, "更新成功！", "温馨提示");
				}
	);
	
}


</script>

