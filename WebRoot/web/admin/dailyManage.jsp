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
                <div class="col-md-6 col-sm-6 col-xs-6">
                    <div class="btn-group">
                        <button onclick="updateMatches();" class="btn sbold green" style="margin-left:10px;"> 更新比赛
                            <i class="fa fa-cog"></i>
                        </button>
                        
                        <button onclick="syncShooterAssister();" class="btn sbold green" style="margin-left:10px;"> 同步射手助攻
                            <i class="fa fa-refresh"></i>
                        </button>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6 col-xs-6">
                    <input type="text" id="teamId" maxlength="20" placeholder="球队Id" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
                    <button onclick="updateTeamPlayer();" class="btn sbold green" style="margin-left:10px;"> 更新球队成员
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
            </div>
            
            <div class="row" style="margin-top:20px;">
            	<div class="col-md-6 col-sm-6 col-xs-6">
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
                <div class="col-md-6 col-sm-6 col-xs-6">
                    <div class="btn-group">
                        <button onclick="updateSiteMap();" class="btn sbold green" style="margin-left:10px;"> 更新网站地图
                            <i class="fa fa-refresh"></i>
                        </button>
                        <button onclick="updateLives();" class="btn sbold green" style="margin-left:10px;"> 更新直播源
                            <i class="fa fa-refresh"></i>
                        </button>
                    </div>
                </div>
            </div>
            
            
            <div class="row" style="margin-top:20px;">
                <div class="col-md-6 col-sm-6 col-xs-6">
                    <div class="btn-group">
                        <button onclick="analyzeAll();" class="btn sbold green" style="margin-left:10px;"> 更新统计
                            <i class="fa fa-refresh"></i>
                        </button>
                        <button onclick="updateSameOdds();" class="btn sbold green" style="margin-left:10px;"> 更新相同赔率
                            <i class="fa fa-refresh"></i>
                        </button>
                    </div>
                </div>
                <div class="col-md-6 col-sm-6 col-xs-6">
                    <div class="btn-group">
                        <button onclick="updateTeamPosition();" class="btn sbold green" style="margin-left:10px;"> 更新球队排名
                            <i class="fa fa-refresh"></i>
                        </button>
                        <button onclick="updateOddsMatches();" class="btn sbold green" style="margin-left:10px;"> 更新主客队ID
                            <i class="fa fa-refresh"></i>
                        </button>
                    </div>
                </div>
            </div>
            
            <div class="row" style="margin-top:20px;">
                <div class="col-md-6 col-sm-12 col-xs-12">
                    <input type="text" id="playerId" maxlength="20" placeholder="球员Id" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
                    <button onclick="updatePlayerTransfer();" class="btn sbold green" style="margin-left:10px;"> 更新球员转会
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
                <div class="col-md-6 col-sm-12 col-xs-12">
                    <button onclick="updateMatchNews();" class="btn sbold green" style="margin-left:10px;"> 更新情报
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
            </div>
            
            <div class="row" style="margin-top:20px;">
                <div class="col-md-6 col-sm-12 col-xs-12">
                    <button onclick="updateVideos();" class="btn sbold green" style="margin-left:10px;"> 更新视频
                        <i class="fa fa-refresh"></i>
                    </button>
                    <button onclick="updateDesc();" class="btn sbold green" style="margin-left:10px;"> 更新简述
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
                <div class="col-md-6 col-sm-12 col-xs-12">
                    <input type="text" id="videosId" maxlength="20" placeholder="视频Id" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" />
                    <button onclick="updateMatchVideos();" class="btn sbold green" style="margin-left:10px;"> 完善视频
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
                
            </div>
            
            <div class="row" style="margin-top:20px;">
            	<div class="col-md-6 col-sm-6 col-xs-6">
                	<select id="fifaLeagueId">
                		<option value="">--请选择联赛--</option>
                		<option value="13">英超</option>
                		<option value="53">西甲</option>
                		<option value="19">德甲</option>
                		<option value="31">意甲</option>
                		<option value="16">法甲</option>
                	</select>
                    <button onclick="updateLeagueFifa();" class="btn sbold green" style="margin-left:10px;"> 更新联赛FIFA数据
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
                <div class="col-md-6 col-sm-6 col-xs-6">
                    <div class="btn-group">
                        <button onclick="updateNumberFoot();" class="btn sbold green" style="margin-left:10px;"> 同步球衣号码及惯用脚
                            <i class="fa fa-refresh"></i>
                        </button>
                    </div>
                </div>
            </div>
            
            <div class="row" style="margin-top:20px;">
                <div class="col-md-12 col-sm-12 col-xs-12">
                    <select id="matches">
                		<option value="">--请选择比赛--</option>
                		<c:forEach items="${lstMatch}" var="match" >
                			<option value="${match.home_team_id},${match.away_team_id}">${match.league_name} ${match.home_team_name}vs${match.away_team_name}</option>
                		</c:forEach>
                	</select>
                    <button onclick="generateMatchStatic();" class="btn sbold green" style="margin-left:10px;"> 获取比赛历史统计
                        <i class="fa fa-refresh"></i>
                    </button>
                </div>
                <div class="col-md-12 col-sm-12 col-xs-12" style="margin-top:10px;">
                	<div style="width:100%;min-height:100px;" id="result_div"></div>
                </div>
            </div>
            
             
            
            
        </div>
        
    </div>
</div>

<script type="text/javascript">

function updateSiteMap(){
	commonPost("/admin/updateSiteMap");
}

function updateMatches(){
	commonPost("/admin/handUpdateMatches");
}

function updateLives(){
	commonPost("/admin/updateLives");
}

function syncShooterAssister(){
	commonPost("/admin/syncShooterAssister");
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
					$("#teamId").val("")
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


function analyzeAll(){
	commonPost("/admin/analyzeAll");
}

function updateSameOdds(){
	commonPost("/admin/updateSameOdds");
}

function updateTeamPosition(){
	commonPost("/admin/updateTeamPosition");
}

function updateOddsMatches(){
	commonPost("/admin/updateOddsMatches");
}

function generateMatchStatic(){
	var match = $("#matches").val();
	if(match==''){
		showToast(3, "请选择比赛！", "温馨提示");
		return;
	}
	var home_id_str = match.split(",")[0];
	var away_id_str = match.split(",")[1];
	$("body").showLoading();
	showToast(1, "更新中...", "温馨提示");
	$.post("/admin/generateMatchStatic",{home_id:home_id_str,away_id:away_id_str},
			function(result){
				$("body").hideLoading();
				showToast(1, "更新成功！", "温馨提示");
				$("#result_div").html(result.data);
			}
	);
}


function updatePlayerTransfer(){

	if($("#playerId").val()==''){
		showToast(2, "请填写球员ID", "温馨提示");
		return;
	}
	$("body").showLoading();
	showToast(1, "更新中...", "温馨提示");
	$.post("/admin/updatePlayerTransfer",
				{playerId: $("#playerId").val()},
				function(result){
					$("#playerId").val("");
					$("body").hideLoading();
					showToast(1, "更新成功！", "温馨提示");
				}
	);
	
}

function updateMatchNews(){
	commonPost("/admin/updateMatchNews");
}

function updateVideos(){
	commonPost("/admin/updateVideos");
}

function updateDesc(){
	commonPost("/admin/updateDesc");
}

function commonPost(url){
	$("body").showLoading();
	showToast(1, "更新中...", "温馨提示");
	$.post(url,function(result){
					$("body").hideLoading();
					showToast(1, "更新成功！", "温馨提示");
				}
	);
}


function updateMatchVideos(){
	
	if($("#videosId").val()==''){
		showToast(2, "请填写视频ID", "温馨提示");
		return;
	}
	$("body").showLoading();
	showToast(1, "更新中...", "温馨提示");
	$.post("/admin/updateMatchVideos",
				{videosId: $("#videosId").val()},
				function(result){
					$("#videosId").val("");
					$("body").hideLoading();
					showToast(1, "更新成功！", "温馨提示");
				}
	);
}

function updateNumberFoot(){
	commonPost("/admin/updateNumberFoot");
}

function updateLeagueFifa(){
	
	if($("#fifaLeagueId").val()==''){
		showToast(2, "请选择联赛", "温馨提示");
		return;
	}
	$("body").showLoading();
	showToast(1, "更新中...", "温馨提示");
	$.post("/admin/updateLeagueFifa",
				{leagueId: $("#fifaLeagueId").val()},
				function(result){
					$("body").hideLoading();
					showToast(1, "更新成功！", "温馨提示");
				}
	);
}

</script>

