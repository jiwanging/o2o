$(function(){
	var shopId = getQueryString('shopId');
	var shopInfoUrl = "/o2o/shopadmin/shopmanagement?shopId="+ shopId;
	$.getJSON(shopInfoUrl,function(data){
		if(data.redirect){
			window.location.gref = data.url;
		}else{
			if(data.shopId != undefined && data.shopId != null){}
			shopId = data.shopId;
		}
		$('#shopInfo')
		.attr('href','/o2o/shopadmin/shopoperation?shopId='+shopId);
	});
});