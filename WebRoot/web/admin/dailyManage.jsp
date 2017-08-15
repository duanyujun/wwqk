<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>

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
                <div class="col-md-6">
                    <div class="btn-group">
                        <button id="deleteBtn" onclick="updateMatches();" class="btn sbold green" style="margin-left:10px;"> 更新比赛
                            <i class="fa fa-cog"></i>
                        </button>
                    </div>
                </div>
                <div class="col-md-6">
                    &nbsp;
                </div>
            </div>
        </div>
        
    </div>
</div>

<script type="text/javascript">


function goInsert(id){
	var url = "/admin/editSay";
	if(id){
		var timestamp=new Date().getTime();
		url = url + "?id="+id+"&t="+timestamp;
	}
	$('#main-content').load(url);
}

function updateMatches(){
	showToast(1, "更新中...", "温馨提示");
	
	$.post("/admin/handUpdateMatches",
				function(result){
					showToast(1, "更新成功！", "温馨提示");
				}
	);
}

</script>

