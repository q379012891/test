require(['comm', 'config'], function() {
    require(['vendor', 'touchslider'], function() {
        var u;
        $(".sicon-1").click(function() {
            if ($(this).parent().hasClass("disabled")) {
                return false;
            } else {
                u = parseInt($(this).siblings(".fl-input").find("input").val());
                u--;
                if (u <= 1) {
                    u = 1;
                    $(this).parents("li").removeClass("on");
                }
            }

            $(this).siblings(".fl-input").find("input").val(u);
        });

        $(".sicon-2").click(function() {
            if ($(this).parent().hasClass("disabled")) {
                return false;
            } else {
                u = parseInt($(this).siblings(".fl-input").find("input").val());
                u++;
                $(this).parents("li").addClass("on");
                if (u >= 9999) {
                    u = 9999;
                }
                $(this).siblings(".fl-input").find("input").val(u);
            }
        });
        $('.list-item input').on('input', function() {
            $(this).parent().find('.form-close').show();
            if ($(this).val().length === 0) {
                $(this).parent().find('.form-close').hide();
            }
        });
        $('.form-close').on('click', function(event) {
            $(this).hide().siblings('input').val('').focus();
        });
        
        //校验按钮输入框是否填写
        function checkSubmint(){
        	var cpurName=$("input[name='purName']").val();
        	var cpurPhone=$("input[name='purPhone']").val();
        	var cpurCompany=$("input[name='purCompany']").val();
        	var ccheckedsCheck = true;
        	$(".check-blhome").each(function(){
        		if( $(this).prop("checked")){
        			ccheckedsCheck = false;
        		}
        	});
        	if(!ccheckedsCheck && cpurName != '' && cpurPhone != '' && cpurCompany != ''){
        		$(".dis-confirmbtn").addClass('C01235E');
        		$(".C01235E").css("color","#FFFFFF");
        	} else {
        		$(".dis-confirmbtn").removeClass('C01235E');
        		$(".dis-confirmbtn").css("color","#666666");
        	}
        } 
        
        $(".check-blhome").on('click', function() {
            if ($(this).prop("checked")) {
            	$(this).attr("checked","checked");
            } else {
            	$(this).attr("checked","");
            }
            checkSubmint();
        });
        $(".form-control input").on('keyup',function(){
        	checkSubmint();
        });
        $(".form-control input").on('click',function(){
        	$('.form-close').hide();
       		$(this).siblings('.form-close').show();
       });
        
        //调用自定义弹窗
		function callCusPop(text){
			$(".show-bg-msg").show();
			$(".show-bg-msg").text(text);
			setTimeout(function(){
				$(".show-bg-msg").hide();
			}, 2000);
		};
		
        //确认采购
        $('.dis-confirmbtn').on('click', function(event) {
        	//点击事件开关C01235E是否存在
        	if(!$('.dis-confirmbtn').hasClass('C01235E')){
        		return;
        	}
        	var purName=$("input[name='purName']").val();
        	var purPhone=$("input[name='purPhone']").val();
        	var purCompany=$("input[name='purCompany']").val();
        	//校验是否选中至少一个商品
        	var checkedsCheck = true;
        	$(".check-blhome").each(function(){
        		if( $(this).prop("checked")){
        			checkedsCheck = false;
        		}
        	});
        	if(checkedsCheck){
        		callCusPop("请至少选择一个商品");
        		return;
        	}
        	
        	if(purName == null || purName == ""){
        		callCusPop("请填写采购人");
        		return;
        	}
			if(purCompany == null || purCompany == ""){
				callCusPop("请填写所属企业");	
				return;
			}
			if(purPhone == null || purPhone == ""){
				callCusPop("请填写联系电话");
				return;
			}
			if(/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/.test(purPhone) ||  /^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})+(\-[0-9]{1,6})?$/.test(purPhone)){
				
			} else {
				callCusPop("请输入正确的联系方式");
				return;
			}
        	
        	var purchaseOrderDetailList = new Array();
        	var purchaseApplyGoodsList = new Array();
        	var purchaseOrder = {};
        	var insertPurchaseReqDto = {};
        	
        	$(".s1").each(function(){
        		var checked = $(this).find("[class='check-blhome']").prop("checked");
        		if(checked){
        			var barcode = $(this).find("[class='code']").val();
           	    	var num = $("#"+barcode).val();
           	    	var sku = $(this).find("[class='itemId']").val();
           	    	var purchaseOrderDetail = {};
           	    	purchaseOrderDetail['goodsId'] = sku;
           	    	if(num <= 0){
           	    		checkedsCheck = true;
           	    	}
           	    	purchaseOrderDetail['saleSum'] = num;
           	    	purchaseOrderDetail['barCode'] = barcode;
           	    	purchaseOrderDetailList.push(purchaseOrderDetail);
        		}
        	});
        	$(".s2").each(function(){
        		var checked = $(this).find("[class='check-blhome']").prop("checked");
        		if(checked){
        			var purchaseApplyGoods = {};
        			var purchaseApplyGoodsImageList = new Array();
	        		var barcode = $(this).find("[class='code']").text();
	        		var num = $("#"+barcode).val();
	        		$(this).find("[class='imageList']").each(function(){
	        			var purchaseApplyGoodsImage = {};
	        			purchaseApplyGoodsImage['imgType'] = 'jpg';
	        			purchaseApplyGoodsImage['imgUrl'] = $(this).attr('imgUrl');
	        			purchaseApplyGoodsImageList.push(purchaseApplyGoodsImage);
	        		});
	        		
	        		purchaseApplyGoods['barCode'] = barcode;
	        		if(num <= 0){
	        			checkedsCheck = true;
           	    	}
	    			purchaseApplyGoods['saleSum'] = num;
	    			purchaseApplyGoods['purchaseApplyGoodsImageList'] = purchaseApplyGoodsImageList;
	    			purchaseApplyGoodsList.push(purchaseApplyGoods);
        		}
        	});
        	
        	if(checkedsCheck){
        		callCusPop("购买数量不能为0!");
				return;
        	}
        	purchaseOrder['purId'] = 1;
        	purchaseOrder['purName'] = purName;
        	purchaseOrder['purPhone'] = purPhone;
        	purchaseOrder['purCompany'] = purCompany;
        	purchaseOrder['appointmentTime'] = new Date();
        	purchaseOrder['state'] = 1;
        	purchaseOrder['sourceCode'] = 1;
        	purchaseOrder['trandMode'] = '跨境贸易';
        	purchaseOrder['submitUser'] = 1;
        	purchaseOrder['purchaseOrderDetailList'] = purchaseOrderDetailList;
        	purchaseOrder['purchaseApplyGoodsList'] = purchaseApplyGoodsList;
        	insertPurchaseReqDto['purchaseOrder'] = purchaseOrder;
        	var json = JSON.stringify(insertPurchaseReqDto);
        	$.ajax({
 	    		type : "post",
 	    		contentType:'application/x-www-form-urlencoded; charset=UTF-8',
 	    		url : $("#webUrl").val()+"/toConfirm.html",
 	    		data : {"json":json},
 	    		async : false,
 	    		success : function(data){
 	    			if(data.resultCode=='00100000'){
 	    				
 	    				window.location.href = $("#webUrl").val()+"/toPurchaseSubmittedSuccessfully.html?purPhone="+data.resultInfo;
 	    				callCusPop("新增完成");
 	    				setTimeout(function(){
 	    					$(".check-blhome").each(function(){
 	 	    					$(this).click();
 	 	    				});
 	    					//第一个没有触发点击事件:原因不知
 	    					$(".check-blhome").eq(0).click();
 	 	    				$(".sum").val("1");
 	 	    				$("input[name='purName']").val("");
 	 	    	        	$("input[name='purPhone']").val("");
 	 	    	        	$("input[name='purCompany']").val("");
 	    				}, 1);
 	    			}else{
 	    				callCusPop("新增失败");
 	    			}
 	    		},
 	    		error : function(){
 	    			callCusPop("系统繁忙，请刷新重试....");
 	    		}
 	    	});
        });
        
        $(".msg-btn-1").on("click",function(){
        	window.location.href = $("#webUrl").val()+"/recommendCommodities.html";
        });
        $(".return").on("click",function(){
        	if(confirm("返回页面会清空采购车,您确定要返回吗?")){
        		window.location.href = $("#webUrl").val()+"/sweepCodePurchasing.html";
        	} else {
        		return;
        	}
        });
        /*$(".returnSubmit").on("click",function(){
        	window.location.href = document.referrer;
        });*/
        
        //点击图片放大预览
        $(".info-item-pic").on("click", function() {
        	var liList = "";
        	var allHtml = "";
        	var cuturl = $("#cuturl").val();
        	$(this).siblings(".imageList").each(function(){
        		var imgUrl = $(this).attr('imgUrl');
        		if('' != imgUrl){
        			liList =  liList+ '<li class="swiper-slide half-two"><a href="javascript:;"><img src="'+cuturl+imgUrl+'" alt=""></a></li>';
        		}
        	});
        	allHtml='<div class="mask"   style="z-index:15"></div> <div class="swiper-container" id="touchSlide"><ul class="swiper-wrapper">'+liList+'</ul><div class="swiper-pagination"><ul></ul></div></div>';
        	$(".lunbo").append(allHtml),setTimeout(function(){
        		$(".lunbo").css("display","block");
        		var index = 0;
				TouchSlide({
					slideCell: "#touchSlide",
					titCell: ".swiper-pagination ul",
					mainCell: ".swiper-wrapper",
					effect: "left",
					autoPlay: false,
					delayTime: 300,
					interTime: 2000,
					autoPage: true,
					switchLoad: "_src",
					defaultIndex:index
				});
        	},1000);
        });
        
        //隐藏图片
        $(document).on("click", ".swiper-container", function() {
        	$(".lunbo").css("display","none");
        	$(".mask").remove();
        	$(".swiper-container").remove();
        });
    })
})