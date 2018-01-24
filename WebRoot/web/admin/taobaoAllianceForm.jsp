<%@ include file="/common/include.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="${ctx}/assets/global/plugins/jquery.form.min.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/bootstrap-toastr/toastr.js" type="text/javascript"></script>
<script src="${ctx}/assets/global/plugins/reveal/jquery.reveal.js" type="text/javascript"></script>
<link href="${ctx}/assets/global/plugins/reveal/reveal.css" rel="stylesheet" type="text/css" />
<style>
.error{
	color:red;
}
</style>


<div class="portlet light bordered">
    <div class="portlet-title">
        <div class="caption font-dark">
            <i class="icon-settings font-dark"></i>
            <span class="caption-subject bold uppercase"> 编辑淘宝联盟 </span>
        </div>
        
    </div>
    <div class="portlet-body">
        <form class="form-horizontal" id="form" action="/admin/saveTaobaoAlliance"   method="post">
        	  <input type="hidden" name="id" value="${taobaoAlliance.id}" />
			  
		      <div class="form-body">
                 <div class="form-group">
                      <label class="col-md-3 control-label">商品名称：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="product_name" name="product_name"  value="${taobaoAlliance.product_name}" placeholder="请输入商品名称">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">商品主图：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="product_img" name="product_img"  value="${taobaoAlliance.product_img}" placeholder="请输入商品主图">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">商品详情：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="product_url" name="product_url"  value="${taobaoAlliance.product_url}" placeholder="请输入商品详情">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">店铺名称：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="store_name" name="store_name"  value="${taobaoAlliance.store_name}" placeholder="请输入店铺名称">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">商品价格：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="price" name="price"  value="${taobaoAlliance.price}" placeholder="请输入商品价格">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">商品月销量：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="sale_month_count" name="sale_month_count"  value="${taobaoAlliance.sale_month_count}" placeholder="请输入商品月销量">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">通用收入比率（%）：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="earn_percent" name="earn_percent"  value="${taobaoAlliance.earn_percent}" placeholder="请输入通用收入比率（%）">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">通用佣金：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="earn_common" name="earn_common"  value="${taobaoAlliance.earn_common}" placeholder="请输入通用佣金">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">活动状态：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="promotion" name="promotion"  value="${taobaoAlliance.promotion}" placeholder="请输入活动状态">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">活动收入比率（%）：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="promotion_percent" name="promotion_percent"  value="${taobaoAlliance.promotion_percent}" placeholder="请输入活动收入比率（%）">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">活动佣金：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="earn_promotion" name="earn_promotion"  value="${taobaoAlliance.earn_promotion}" placeholder="请输入活动佣金">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">活动开始时间：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="promotion_start" name="promotion_start"  value="${taobaoAlliance.promotion_start}" placeholder="请输入活动开始时间">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">活动结束时间：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="promotion_end" name="promotion_end"  value="${taobaoAlliance.promotion_end}" placeholder="请输入活动结束时间">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">卖家旺旺：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="store_ww" name="store_ww"  value="${taobaoAlliance.store_ww}" placeholder="请输入卖家旺旺">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">淘宝客短链接（300天内有效）：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="tbk_short_url" name="tbk_short_url"  value="${taobaoAlliance.tbk_short_url}" placeholder="请输入淘宝客短链接（300天内有效）">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">淘宝客链接：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="tbk_url" name="tbk_url"  value="${taobaoAlliance.tbk_url}" placeholder="请输入淘宝客链接">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">淘口令：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="tkl" name="tkl"  value="${taobaoAlliance.tkl}" placeholder="请输入淘口令">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">优惠券总量：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="coupon_count" name="coupon_count"  value="${taobaoAlliance.coupon_count}" placeholder="请输入优惠券总量">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">优惠券剩余量：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="coupon_count_last" name="coupon_count_last"  value="${taobaoAlliance.coupon_count_last}" placeholder="请输入优惠券剩余量">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">优惠券面额：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="coupon_desc" name="coupon_desc"  value="${taobaoAlliance.coupon_desc}" placeholder="请输入优惠券面额">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">优惠券开始时间：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="coupon_start" name="coupon_start"  value="${taobaoAlliance.coupon_start}" placeholder="请输入优惠券开始时间">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">优惠券结束时间：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="coupon_end" name="coupon_end"  value="${taobaoAlliance.coupon_end}" placeholder="请输入优惠券结束时间">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">优惠券链接：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="coupon_url" name="coupon_url"  value="${taobaoAlliance.coupon_url}" placeholder="请输入优惠券链接">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">优惠券淘口令(30天内有效)：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="coupon_tkl" name="coupon_tkl"  value="${taobaoAlliance.coupon_tkl}" placeholder="请输入优惠券淘口令(30天内有效)">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">优惠券短链接(300天内有效)：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="coupon_short_url" name="coupon_short_url"  value="${taobaoAlliance.coupon_short_url}" placeholder="请输入优惠券短链接(300天内有效)">
                      </div>
                      <div class="col-md-3"><label for="name"></label></div>
                 </div>
                 <div class="form-group">
                      <label class="col-md-3 control-label">首页推荐：</label>
                      <div class="col-md-6">
                           <input type="text" class="form-control" id="recom" name="recom"  value="${taobaoAlliance.recom}" placeholder="请输入首页推荐">
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

