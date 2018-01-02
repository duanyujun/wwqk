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
            <span class="caption-subject bold uppercase">网友推荐管理</span>
        </div>
        
    </div>
    <div class="portlet-body">
        <div class="table-toolbar">
            <div class="row">
                <div class="col-md-6">
                    <div class="btn-group">
                        <button id="sample_editable_1_new" onclick="goInsert();" class="btn sbold green"> 添加网友推荐
                            <i class="fa fa-plus"></i>
                        </button>
                        <button id="deleteBtn" onclick="goDelete();" class="btn sbold red" style="margin-left:10px;display:none;"> 删除网友推荐
                            <i class="fa fa-trash"></i>
                        </button>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="btn-group pull-right" style="display:none;">
                        <button class="btn green  btn-outline dropdown-toggle" data-toggle="dropdown">Tools
                            <i class="fa fa-angle-down"></i>
                        </button>
                        <ul class="dropdown-menu pull-right">
                            <li>
                                <a href="javascript:;">
                                    <i class="fa fa-print"></i> Print </a>
                            </li>
                            <li>
                                <a href="javascript:;">
                                    <i class="fa fa-file-pdf-o"></i> Save as PDF </a>
                            </li>
                            <li>
                                <a href="javascript:;">
                                    <i class="fa fa-file-excel-o"></i> Export to Excel </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <table class="table table-striped table-bordered table-hover table-checkable order-column" id="main_table">
            <thead>
                <tr>
                    <th>
                        <input type="checkbox" class="group-checkable" data-set="#main_table .checkboxes" /> 
                    </th>
                    <th>比赛id</th>
                    <th>英文标题</th>
                    <th>中文标题</th>
                    <th>比赛时间</th>
                    <th>范围</th>
                    <th>联赛名称</th>

                </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">
$(document).ready(function() {
    $('#main_table').dataTable( {
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
              ,{"targets":2,"orderable": false,"searchable": true},{"targets":3,"orderable": false,"searchable": true},{"targets":4,"orderable": true,"searchable": false},{"targets":5,"orderable": false,"searchable": false},{"targets":6,"orderable": false,"searchable": false}     
        
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
        "ajax": "/admin/matchGuessData"
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

function goInsert(id){
	var url = "/admin/editMatchGuess";
	if(id){
		var timestamp=new Date().getTime();
		url = url + "?id="+id+"&t="+timestamp;
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
           			$.post( "/admin/deleteMatchGuess",
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

