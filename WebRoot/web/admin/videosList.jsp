<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/datatables-1.10.15/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/datatables-1.10.15/js/dataTables.bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>

<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase">集锦管理</span>
        </div>
        
    </div>
    <div class="portlet-body">
        <div class="table-toolbar">
            <div class="row">
                <div class="col-md-6">
                    <div class="btn-group">
                        <button id="sample_editable_1_new" onclick="goInsert();" class="btn sbold green"> 添加集锦
                            <i class="fa fa-plus"></i>
                        </button>
                        <button id="deleteBtn" onclick="goDelete();" class="btn sbold red" style="margin-left:10px;display:none;"> 删除集锦
                            <i class="fa fa-trash"></i>
                        </button>
                    </div>
                </div>
                <div class="col-md-6">
                    <select class="form-control pull-right" id="leagueIdSel" onchange="reloadTable();" style="width:155px;">
                            <option value="" >-过滤赛事-</option>
                        	<option value="1" ${leagueId==1?'selected':''}>英超</option>
                        	<option value="2" ${leagueId==2?'selected':''}>西甲</option>
                        	<option value="3" ${leagueId==3?'selected':''}>德甲</option>
                        	<option value="4" ${leagueId==4?'selected':''}>意甲</option>
                        	<option value="5" ${leagueId==5?'selected':''}>法甲</option>
                        	<option value="6" ${leagueId==6?'selected':''}>欧冠</option>
                        	<option value="7" ${leagueId==7?'selected':''}>中超</option>
                        	<option value="8" ${leagueId==8?'selected':''}>其他</option>
                     </select>
                </div>
            </div>
        </div>
        <table class="table table-striped table-bordered table-hover table-checkable order-column" id="main_table">
            <thead>
                <tr>
                    <th>
                        <input type="checkbox" class="group-checkable" data-set="#main_table .checkboxes" /> 
                    </th>
                    <th>联赛id</th>
                    <th>比赛时间</th>
                    <th>主队</th>
                    <th>客队</th>
                    <th>标题</th>
                    <th>比赛id</th>
                    <th>关键字</th>

                </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">
var mainTable;
$(document).ready(function() {
   mainTable = $('#main_table').dataTable( {
        "processing": true,
        "serverSide": true,
        "orderClasses": false,
        "pagingType": "full_numbers",
        "columnDefs": [
        	 {
            "targets": 0,
            "orderable": false,
            "searchable": false,
            "render": function ( data, type, full, meta ) {
                return '<input type="checkbox" class="checkboxes" data-id='+data+' />';
              }},
              {
                  "targets": 1,
                  "orderable": true,
                  "searchable": true,
                  "render": function ( data, type, full, meta ) {
                      return '<a href="" class="editClass" >'+data+'</a>';
              }}
              ,{"targets":2,"orderable": true,"searchable": false},{"targets":3,"orderable": true,"searchable": true},{"targets":4,"orderable": true,"searchable": true},{"targets":5,"orderable": false,"searchable": true},{"targets":6,"orderable": true,"searchable": false},{"targets":7,"orderable": true,"searchable": true}     
        
        ],
        "order": [[1, 'asc']],
        "language": {
            "emptyTable": "暂 无 数  据",
            "info": "显示从_START_到_END_条，共_TOTAL_条记录",
            "infoEmpty": "暂 无 数  据",
            "infoFiltered": "(filtered1 from _MAX_ total records)",
            "lengthMenu": "每页显示_MENU_条记录",
            "search": "搜索:",
            "zeroRecords": "暂 无 数  据",
            "paginate": {
                "previous": "上一页",
                "next": "下一页",
                "last": "最后一页",
                "first": "第一页"
            }
        },
        "bStateSave": !0,
       	"ajax" : {
       	        "url" : "/admin/videosData",
       	        "data" : function(d){
    	        	var leagueId = $("#leagueIdSel").val();
    	        	if(leagueId==''){
    	        		leagueId = '${leagueId}';﻿
    	        	}
    	        	d.leagueId=leagueId;
    	       	﻿}
       	}
    } );
    
    $('#main_table').find(".group-checkable").change(function() {
        var e = jQuery(this).attr("data-set"),
        a = jQuery(this).is(":checked");
        jQuery(e).each(function() {
            a ? ($(this).prop("checked", !0), $(this).parents("tr").addClass("active")) : ($(this).prop("checked", !1), $(this).parents("tr").removeClass("active"))
        }),
        jQuery.uniform.update(e)
    });
    $('#main_table').on("change", "tbody tr .checkboxes",
    function() {
        $(this).parents("tr").toggleClass("active")
    });
    
    $('#main_table').on('draw.dt', function () {
    	 $(this).find(".editClass").click(function(event){
    	    	event.preventDefault();
    	    	var id = $(this).parent().prev().children(0).attr("data-id");
    	    	goInsert(id);
    	 });
    } );
    
    
    
} );

function reloadTable(){
	mainTable.fnDraw();
}

function goInsert(id){
	var url = "/admin/editVideos";
	if(id){
		var timestamp=new Date().getTime();
		url = url + "?id="+id+"&t="+timestamp+"&leagueId="+$("#leagueIdSel").val();
	}
	$('#main-content').load(url);
}

function goDelete(){
	if($("input[class='checkboxes']:checked").length==0){
		showToast(2, "请选择记录！", "温馨提示");
	}else{
		var ids = '';
		$("input[class='checkboxes']:checked").each(function(){
			ids += $(this).attr("data-id")+",";
		});
		if(ids!=''){
			 bootbox.setLocale("zh_CN");
			 bootbox.confirm("确定要删除?",
           	 function(o) {
               	if(o==true){
               		ids = ids.substring(0, ids.length-1);
           			$.post( "/admin/deleteVideos",
           					{ids:ids},
           					function(result){
           						$('#main-content').load($('#urlHidden').val());
           						showToast(1, "删除成功！", "温馨提示");
           					}
           			);
               	}
          	 });
		}
	}
}

</script>

