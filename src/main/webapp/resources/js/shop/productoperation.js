/**
 * 
 */
$(function(){
	//获取前台传回的shopId
	var productId = getQueryString("productId");
	//通过productId获取商品信息的URL
	var productInfoUrl = '/o2o/shopadmin/getproductbyid?productId='+productId;
	//获取当前店铺设定的商品类别列表的URL
	var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
	//更新商品信息的URL
	var productUrl = '/o2o/shopadmin/addproduct';
	//用以区分是商品编辑界面还是新增商品界面
	var isEdit = productId?true:false;
	if(isEdit){
		getProductInfo();
	}else{
		getCategory();
		productPostUrl = '/o2o/shopadmin/addproduct'
	}
	function getProductInfo(){
		$.getJSON(productInfoUrl,function(data){
			if(data.success){
				var product = data.product;
				$('#product-name').val(product.productName);
				$('#product-desc').val(product.productDesc);
				$('#priority').val(product.priority);
				$('#normal-price').val(product.normalPrice);
				$('#promotion-price').val(product.promotionPrice);
				//获取原本的商品类别以及该店铺的所有商品类别列表
				var optionHtml = '';
				var optionArr = data.productCategoryList;
				var optionSelected = product.productCategory.productCategoryId;
				//生成前端的HTML商品类别列表，并默认选择编辑前的商品类别
				optionArr.map(function(item,index){
					var isSelect = optionSelected === item.productCategoryId?'selected':'';
					optionHtml += '<option data-value="'
							   +item.productCategoryId
							   +'"'
							   +isSelect
							   +'>'
							   +item.productCategoryName
							   +'</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}
	
	function getCategory(){
		$.getJSON(categoryUrl,function(data){
			if(data.success){
				var productCategoryList = data.productCategoryList;
				var optionHtml = '';
				productCategoryList.map(function(item,index){
					optionHtml += '<option data-value="'+item.productCategoryId + '">'
					+item.productCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
		

		$('#submit').click(function(){
			//创建商品json对象，并从表单中获取对应的属性值
			var product = {};
			product.productName = $('#product-name').val();
			product.productDesc = $('#product-desc').val();
			product.priority = $('#priority').val();
			product.normalPrice = $('#normal-price').val();
			product.promotionPrice = $('#promotion-price').val();
			//获取选定的商品类别值
			product.productCategory = {
					productCategoryId:$('#category').find('option').not(function(){
						return !this.selected;
					}).data('value')
			};
			product.productId = productId;
			//获取缩略图文件流
			var thumbnail = $('#small-img')[0].files[0];
			//生成表单对象 用于接收参数并传递给后台
			var formData = new FormData();
			formData.append('thumbnail',thumbnail);
			//遍历商品详情图控件，获取里面的文件流
//			$('detail-img').map(function(index,item){
//				//判断控件是否已选择了文件
//				if($('.detail-img')[index].files.length > 0){
//					formData.append('productImg'+index,$('.detail-img')[index].files[0]);
//				}
//				
//			});
			
			$('.detail-img').map(
					function(index, item) {
						// 判断该控件是否已选择了文件
						if ($('.detail-img')[index].files.length > 0) {
							// 将第i个文件流赋值给key为productImgi的表单键值对里
							formData.append('productImg' + index,
									$('.detail-img')[index].files[0]);
						}
					});
			formData.append('productStr',JSON.stringify(product));
			//获取表单中输入的验证码
			var verifyCodeActual = $('#j_captcha').val();
			if(!verifyCodeActual){
				$.toast('请输入验证码');
				return;
			}
			formData.append('verifyCodeActual',verifyCodeActual);
			$.ajax({
				url:productUrl,
				type:'POST',
				data:formData,
				contentType:false,
				processData:false,
				cache:false,
				success:function(data){
					if(data.success){
						$.toast('提交成功');
					}else{
						$.toast('提交失败！' + data.errMsg)
					}
					$('#captcha_img').click();
				}
			});
		});
	}
	
	//针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片）
	//且控件总数未达到6个，则生成新的一个文件上传控件
	$('.detail-img-div').on('change','.detail-img:last-child', function(){
		if($('.detail-img').length < 6){
			$('#detail-img').append('<input type = "file" class = "detail-img">');
		}
	});
})