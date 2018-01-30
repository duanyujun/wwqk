<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/datatables-1.10.15/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/datatables-1.10.15/js/dataTables.bootstrap.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<style>
.red{color:red;}
.green{color:green;}
</style>

<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase">题目管理</span>
        </div>
        
    </div>
    <div class="portlet-body">
        <div class="table-toolbar">
            <div class="row">
                <div class="col-lg-6 col-md-6">
                    <div class="btn-group">
                        <button id="sample_editable_1_new" onclick="goInsert();" class="btn sbold green"> 添加题目
                            <i class="fa fa-plus"></i>
                        </button>
                        <button id="deleteBtn" onclick="goDelete();" class="btn sbold red" style="margin-left:10px;display:none;"> 删除题目
                            <i class="fa fa-trash"></i>
                        </button>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6">
                	<select class="form-control pull-right" id="typeSel" onchange="reloadTable();" style="width:155px;">
                            <option value="" >-类型-</option>
                            <c:forEach items="${lstQtype}" var="qtype">
                            	<option value="${qtype.key}" ${qtype.key==typeSel?'selected':''}>${qtype.value}</option>
                            </c:forEach>
                     </select>
                    <select class="form-control pull-right" id="statusSel" onchange="reloadTable();" style="width:155px;">
                            <option value="" >-状态-</option>
                        	<option value="1" ${statusSel==1?'selected':''}>待处理</option>
                        	<option value="2" ${statusSel==2?'selected':''}>推荐</option>
                        	<option value="3" ${statusSel==3?'selected':''}>废弃</option>
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
                    <th style="width:700px;">题目名称</th>
                    <th>状态</th>
                    <th>正确答案（ABCD）</th>
                    <th>完整答案</th>
                    <th>类型</th>

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
              ,{"targets":2,"orderable": true,"searchable": false,
            	  "render": function ( data, type, full, meta ) {
            		  if(data=='1'){
            			  data = '待处理';
            		  }else if(data=='2'){
            			  data = '<span class="green">推荐</span>';
            		  }else if(data=='3'){
            			  data = '<span class="red">废弃</span>';
            		  }
                      return data;
            	  }
              }
              ,{"targets":3,"orderable": false,"searchable": false}
              ,{"targets":4,"orderable": false,"searchable": false}
              ,{"targets":5,"orderable": false,"searchable": false,
            	  "render": function ( data, type, full, meta ) {
            		  if(data=='1'){
            			  data = '文史';
            		  }else if(data=='2'){
            			  data = '自然';
            		  }else if(data=='3'){
            			  data = '娱乐';
            		  }else if(data=='4'){
            			  data = '生活常识';
            		  }else if(data=='5'){
            			  data = '理科';
            		  }else if(data=='6'){
            			  data = '趣味';
            		  }else if(data=='7'){
            			  data = '冷知识';
            		  }
                      return data;
            	  }
              }     
        
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
       	        "url" : "/admin/questionData",
       	        "data" : function(d){
       	        	var statusSel = $("#statusSel").val();
    	        	if(statusSel==''){
    	        		statusSel = '${statusSel}';﻿
    	        	}
    	        	d.statusSel=statusSel;
    	        	
    	        	var typeSel = $("#typeSel").val();
    	        	if(typeSel==''){
    	        		typeSel = '${typeSel}';﻿
    	        	}
    	        	d.typeSel=typeSel;
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

function goInsert(id){
	var url = "/admin/editQuestion";
	if(id){
		var timestamp=new Date().getTime();
		url = url + "?id="+id+"&t="+timestamp+"&statusSel="+$("#statusSel").val()+"&typeSel="+$("#typeSel").val();
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
           			$.post( "/admin/deleteQuestion",
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

function reloadTable(){
	mainTable.fnDraw();
}

</script>

