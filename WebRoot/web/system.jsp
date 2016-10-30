<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>系统管理</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <link href="${ctx}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/global/css/components-md.min.css" rel="stylesheet" id="style_components" type="text/css" />
        <link href="${ctx}/assets/global/css/plugins-md.min.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/layouts/layout4/css/layout.min.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/layouts/layout4/css/themes/light.min.css" rel="stylesheet" type="text/css" id="style_color" />
        <link href="${ctx}/assets/layouts/layout4/css/custom.min.css" rel="stylesheet" type="text/css" />
        <link href="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
        <link rel="shortcut icon" href="favicon.ico" /> 
    	<style type="text/css">
    		.page-content-wrapper .page-content{padding:0 0 0 20px;}
    	</style>
    
    </head>
    <!-- END HEAD -->
    

    <body class="page-container-bg-solid page-header-fixed page-sidebar-closed-hide-logo page-md">
        <div class="page-header navbar navbar-fixed-top">
            <div class="page-header-inner ">
                <div class="page-logo">
                    <a href="/home">
                        <img src="${ctx}/assets/layouts/layout4/img/logo-word.jpg" alt="logo" class="logo-default" /> </a>
                    <div class="menu-toggler sidebar-toggler">
                    </div>
                </div>
                <a href="javascript:;" class="menu-toggler responsive-toggler" data-toggle="collapse" data-target=".navbar-collapse"> </a>
                <div class="page-top">
                    <div class="top-menu">
                        <ul class="nav navbar-nav pull-right">
                            <li class="dropdown dropdown-user dropdown-dark">
                                <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                                    <span class="username username-hide-on-mobile">${username}</span>
                                    <img alt="" class="img-circle" src="${ctx}/assets/layouts/layout4/img/head.png" /> </a>
                                <ul class="dropdown-menu dropdown-menu-default">
                                    
                                    <li>
                                        <a href="/login">
                                            <i class="icon-login"></i>重新登录</a>
                                    </li>
                                    <li>
                                        <a href="/logout">
                                            <i class="icon-key"></i>退出系统</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="clearfix"> </div>
        <div class="page-container">
            <div class="page-sidebar-wrapper">
                <div class="page-sidebar navbar-collapse collapse">
                    <ul class="page-sidebar-menu" data-keep-expanded="true" data-auto-scroll="true" data-slide-speed="200">
                        
                        <c:forEach items="${groupMenuList}" var="groupMenu" varStatus="status">
                        	<li class="nav-model nav-item ${status.index==0?'open':''}">
	                            <a href="javascript:;" class="nav-link nav-toggle">
	                                <i class="icon-settings"></i>
	                                <span class="title">${groupMenu.groupName}</span>
	                                <span class="selected"></span>
	                                <span class="arrow ${status.index==0?'open':''}"></span>
	                            </a>
	                            <ul class="sub-menu" >
	                            	<c:set var="lstMenu" value="${groupMenu.lstMenu}" />
	                            	<c:forEach items="${lstMenu}" var="menu" varStatus="s">
	                                <li class="nav-item">
	                                    <a href="${menu.attrs.pvalue}" class="nav-link ">
	                                        <span class="title">${menu.attrs.name}</span>
	                                        <span class="selected"></span>
	                                    </a>
	                                </li>
	                               </c:forEach>
	                            </ul>
	                        </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="page-content-wrapper">
                <div class="page-content">
                    <div class="row">
                        <div id="main-content" class="col-md-12">
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- END CONTAINER -->
        <!-- BEGIN FOOTER -->
        <div class="page-footer">
            <div class="page-footer-inner" style="display:none;"> 2016 &copy; CZF Co.
            </div>
            <div class="scroll-to-top">
                <i class="icon-arrow-up"></i>
            </div>
        </div>
        
        <input type="hidden" id="urlHidden" />
        
        <!-- END FOOTER -->
        <!--[if lt IE 9]>
<script src="${ctx}/assets/global/plugins/respond.min.js"></script>
<script src="${ctx}/assets/global/plugins/excanvas.min.js"></script> 
<![endif]-->
        <!-- BEGIN CORE PLUGINS -->
        <script src="${ctx}/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/global/scripts/app.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/layouts/layout4/scripts/layout.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/layouts/layout4/scripts/demo.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/layouts/global/scripts/quick-sidebar.min.js" type="text/javascript"></script>
        <script src="${ctx}/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
		<script src="${ctx}/assets/global/plugins/jquery-validation/js/localization/messages_zh.min.js" type="text/javascript"></script>
        <script src="http://api.map.baidu.com/api?v=2.0&ak=F389kFdrmsN4UtGQkBM3kpMB4c84mNxe" type="text/javascript"></script>
    </body>

<script type="text/javascript">

$(function(){  
	init();
});

function init(){
	$('.sub-menu li a').click(function(e) {
        e.preventDefault();
        var url = this.href;
        if (url != null && url != 'javascript:;') {
        
        	$(".nav-model").removeClass("active");
        	$(".nav-model").removeClass("open");
        	
        	var subLi = $(".sub-menu li");
        	subLi.removeClass("active");
        	subLi.removeClass("open");
        	
        	var modelItem = $(this).parent().parent().parent();
        	modelItem.addClass("active");
        	modelItem.addClass("open");
        	
        	$(this).parent().addClass("active");
        	$(this).parent().addClass("open");
        
        	
        	$('#main-content').load(url);
        	$('#urlHidden').val(url);
        }
    });
	
	$(".sub-menu").eq(0).show();
	//$('.sub-menu li a').eq(0).click();
	
	$('#main-content').load("/monitor/tableview");
	$('#urlHidden').val("/monitor/tableview");
}

function showToast(type, title, content){
	toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "positionClass": "toast-top-center",
			  "onclick": null,
			  "showDuration": "1000",
			  "hideDuration": "1000",
			  "timeOut": "5000",
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			};
	
	if(type==1){
		toastr.success(title, content);
	}else if(type==2){
		
		toastr.info(title, content);
	}else if(type==3){
		
		toastr.warning(title, content);
	}else if(type==4){
		
		toastr.error(title, content);
	}
	
}

</script>

</html>
