 require(['vendor/vendor.min'], function() {
		
		$(".brand-class").eq(0).css("padding-top","0.3rem");
		/*$(".tab-li").click(function(){
			var index=$(".tab-li").index($(this));
			$(".tab-modal").hide();
			$(".tab-modal").eq(index).show();
			$(this).addClass("active").siblings().removeClass("active");
		});*/
		
	    //readey事件
	   /* $().ready(function(){
	    	var tab= $(".tab").val();
	    	if(tab==2){
	    		$(".tab2").addClass("active");
	    		$(".tab-modal1").hide();
	    		$(".tab-modal2").show();
	    	}else{
	    		$(".tab1").addClass("active");
	    		$(".tab-modal2").hide();
	    		$(".tab-modal1").show();
	    	}
	       
	    });*/
	    
	    //点击品牌进入商品页面
		  $(".logo-img").on("click",function(){
			    var  brandId =$(this).attr("brandId");
			    var  tagName =encodeURI(encodeURI($(this).attr("brandCnName")));
		    	window.location.href=$("#webUrl").val()+"/toItem.html?brandId="+brandId+"&tagName="+tagName;
		  
		  });
 });