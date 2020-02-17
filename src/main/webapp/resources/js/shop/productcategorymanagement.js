/**
 * 
 */
$(function(){
	var listUrl = "/o2o/shopadmin/getproductcategorylist";
	var addUrl = "/o2o/shopadmin/addproductcategorys"
	var deleteUrl = "/o2o/shopadmin/removeproductcategory"
	getList();
	function getList(e){
		$.ajax({
				url:listUrl,
				type:"get",
				dataType:"json",
				success:function(data){
					if(data.success){
						handleList(data.productCategoryList);
					}
				}
			});
	}
	function handleList(data){
		var html = '';
		data.map(function(item,index){
			html += '<div class = "row row-product-category now"><div class = "col-33">'
			+ item.productCategoryName + '</div><div class = "col-33">'
			+ item.priority
			+ '</div><div class = "col-33">'
			+removeProductCategory(item.productCategoryId,item.shopId) + '</div></div>'
		});
		$('.productcategory-wrap').html(html);
	}
	
	/**
	 * 主要用于删除商品列表信息
	 */
	function removeProductCategory(productCategoryId,id){
			return '<a hef = "#" class="button delete" data-id="'+
			productCategoryId+'">删除</a>';
	}
	
	/**
	 * 点击一次 新增一行的功能 row-product-category temp
	 */
	$('#new').click(function(){
				var tempHtml = '<div class = "row row-product-category temp">'
					+ '<div class "col-33"><input class="category-input category" type="text" placeholder = "商品类别"></div>'
					+ '<div class "col-33"><input class="category-input priority" type="number" placeholder = "优先级"></div>'
					+ '<div class "col-33"><a hef = "#" class="button delete">删除</a></div>'
					+'</div>';
					$('.productcategory-wrap').append(tempHtml);
	});
	
	/**
	 * 提交按钮的功能
	 */
	$('#submit').click(function(){
		var tempArr = $('.temp');
		var productCategoryList = [];
		tempArr.map(function(index,item){
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			if(tempObj.productCategoryName && tempObj.priority){
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url:addUrl,
			type:'POST',
			data:JSON.stringify(productCategoryList),
			contentType:'application/json',
			success:function(data){
				if(data.success){
					$.toast('提交成功！');
					getList();//调用该方法重新显示
				}else{
					$.toast('提交失败！');
				}
			}
		})
	});
	
	
	/**
	 * 还未添加进数据库的数据删除按钮的功能
	 */
	$('.productcategory-wrap').on('click','.row-product-category.temp .delete',//注意这里的空格
			function(e){
		console.log($(this).parent().parent());
		$(this).parent().parent().remove();
	});
	
	/**
	 * 已添加进数据库的数据删除按钮的功能
	 */
	$('.productcategory-wrap').on('click','.row-product-category.now .delete',
			function(e){
		var target = e.currentTarget;

		$.ajax({
			url:deleteUrl,
			type:'POST',
			data:{
				productCategoryId:target.dataset.id
			},
			dataType:'json',
			success:function(data){
				if(data.success){
					$.toast('删除成功');
					getList();
				}else{
					$.toast('删除失败');
				}
			}
		});
		//以下代码执行不了了 为什么
//		$.confirm('is sure?',function(){
//			$.toast('不删除');
//		});
	});
});