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
            <span class="caption-subject bold uppercase">情报管理</span>
        </div>
        
    </div>
    <div class="portlet-body">
        <div class="table-toolbar">
            
        </div>
        <table class="table table-striped table-bordered table-hover table-checkable order-column" id="main_table">
            <thead>
                <tr>
                    <th>
                        <input type="checkbox" class="group-checkable" data-set="#main_table .checkboxes" /> 
                    </th>
                    <th> 比赛时间 </th>
                    <th> 主队名称 </th>
                    <th> 客队名称 </th>
                    <th> 所在联赛 </th>
                    <th> 源ID </th>
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
        "columnDefs": [{
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
        
        ],
        "order": [[1, 'desc']],
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
        "ajax": "/admin/tipsMatchData"
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
	var url = "/admin/editTipsMatch";
	if(id){
		var timestamp=new Date().getTime();
		url = url + "?id="+id+"&t="+timestamp;
	}
	$('#main-content').load(url);
}

</script>

