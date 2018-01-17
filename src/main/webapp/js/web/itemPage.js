 require(['vendor/vendor.min'], function() {
		
	    
		  $(document).ready(function(){
			  //商品切换特效
			  $(".lazy-box").click(function(){
//			  $(this).children().get(0).toggle();
//			  var  datqe=$(this).find("class").eq(0).css("display");
			  var  date=	 $(this).children().eq(0).css("display");
//				 console.debug(date);
			  if(date=="block"){
				  $(this).children().eq(0).css("display","none");
			  }else{
				  $(this).children().eq(0).css("display","block");
			  }
			  
			  });
			});
		  
		  	//点击加入采购车操作
		    $(".but").on("click",function(){
		    	$("#isDate").val("1");
		    	//点击后按钮置灰,文案修改,采购车数量增加
		    	if($(this).hasClass("but02")){
		    		return;
		    	} else {
		    		$(this).text("已加入采购车");
		    		$(this).addClass("but02");
		    		var shoppingCartSum = Number($("#shoppingCartSum").text());
		    		//数量最多显示到999
		    		if(shoppingCartSum < 999){
		    			$("#shoppingCartSum").text(shoppingCartSum+=1);
		    		} else {
		    			$("#shoppingCartSum").text("999+");
		    		}
		    	}
		    	//拼接请求参数
		    	var barCode=$(this).attr("barCode");
		    	var shoppingCartData = $("#shoppingCartData").val();
		    	if(shoppingCartData.indexOf(barCode)<0){
		    		if(shoppingCartData != "" && shoppingCartData != null){
		    			$("#shoppingCartData").val(shoppingCartData+",-,"+barCode+",1");
		    		} else {
		    			$("#shoppingCartData").val(barCode+",1");
		    		}
		    	}
		    });
		    
		    //调用自定义弹窗
			function callCusPop(text){
				$(".show-bg-msg").show();
				$(".show-bg-msg").text(text);
				setTimeout(function(){
					$(".show-bg-msg").hide();
				}, 2000);
			};
		    
		    //跳转至采购车
		    $("#goConfirmPurchasing").on("click",function(){
		    	if($("#isDate").val()==0){
		    		callCusPop("您还没挑选商品哦~");
		    		return false ;
		    	}
		    	if($("#shoppingCartData").val()!=''){
			    	 var date = $("#shoppingCartData").val();
					 var url= $("#webUrl").val()+"/toConfirmPurchasing.html?date="+date;
					 window.location = url;
					 $("#shoppingCartData").val("");
					 $("#isDate").val("0");
		    	} else {
		    		callCusPop("您还没挑选商品哦~");
		    	}
		    });
 });