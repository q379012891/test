	require(['vendor/vendor.min'], function(FastClick){
    FastClick.attach(document.body);
    
    require(['swiper_around_bl','touchslider'], function() {
        $('.swiper-slide').swiper_around();
          TouchSlide({
              slideCell: "#touchSlide",
              titCell: ".swiper-pagination ul",
              mainCell: ".swiper-wrapper",
              effect: "left",
              autoPlay: false,
              delayTime: 300,
              interTime: 2000,
              autoPage: true,
              switchLoad: "_src"
          });
      });
    //按钮 下一步定位
    var bodyHeight=$(window).height();
    var btnHeight=$(".scan-btn").outerHeight();
    var contentHeight=bodyHeight - btnHeight;
    $('.scan-height').css({
      height: contentHeight + 'px',
      overflowY: 'auto'
    });
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: $("#appId").val(), // 必填，公众号的唯一标识
        timestamp: $("#timestamp").val(), // 必填，生成签名的时间戳
        nonceStr: $("#noncestr").val(), // 必填，生成签名的随机串
        signature: $("#signature").val(),// 必填，签名，见附录1
        jsApiList: [ 'checkJsApi',
                     'onMenuShareTimeline',
                     'onMenuShareAppMessage',
                     'onMenuShareQQ',
                     'onMenuShareWeibo',
                     'onMenuShareQZone',
                     'hideMenuItems',
                     'showMenuItems',
                     'hideAllNonBaseMenuItem',
                     'showAllNonBaseMenuItem',
                     'translateVoice',
                     'startRecord',
                     'stopRecord',
                     'onVoiceRecordEnd',
                     'playVoice',
                     'onVoicePlayEnd',
                     'pauseVoice',
                     'stopVoice',
                     'uploadVoice',
                     'downloadVoice',
                     'chooseImage',
                     'previewImage',
                     'uploadImage',
                     'downloadImage',
                     'getNetworkType',
                     'openLocation',
                     'getLocation',
                     'hideOptionMenu',
                     'showOptionMenu',
                     'closeWindow',
                     'scanQRCode',
                     'chooseWXPay',
                     'openProductSpecificView',
                     'addCard',
                     'chooseCard',
                     'openCard'
//                     'swiper_around_bl',
//                     'touchslider'
                     ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
    
    

    //扫一扫按钮事件
    $(".scan-ico").click(function(){
    	$(".pop-sm").remove();
    	wx.scanQRCode({
            needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
            desc: 'scanQRCode desc',
            //scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
            success: function (res) {
                var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
                console.debug("result1:"+result);
                result = result.split(",");
                console.debug("result2:"+result[1]);
                var  barCode= result[1];
                if((barCode==undefined)||(barCode==null)||(barCode=="undefined")){
                	callCusPop("请扫描正确的条形码");
                	return  false;
                }
                //校验此页面是否有该条形码
                var num1  = codeNum(barCode);
                if(num1!=0){callCusPop("您已添加过此商品"); return  false;  }
                //校验此页面是否有该条形码
               var  num2 = noCodeNum(barCode);
               if(num2!=0){ callCusPop("您已添加过此商品"); return  false;  }
               showPage(barCode);
       		   $("#selectValue").val("");//清空参数
            }
    	});
    });
    
//    wx.error(function (res) {
//    	alert(res.errMsg);
//    	});
    $(".num-info").on('blur', function(event) {
    	setTimeout(function(){
        	var value=$("#selectValue").val();
        	if(value==''){
        		var  barCode= $(".num-info").val();
        		if(barCode==""){return  false;}
	          	var reg = /^\d+$/;
	          	if((barCode.match(reg))&&(parseInt(7)<parseInt(barCode.length))&&(parseInt(barCode.length)<parseInt(14))){
        		var  num1= codeNum(barCode);
        		if(num1!=0){callCusPop("页面已经存在此条形码....");$(".num-info").val("");$(".code-history p").remove() ;;return  false;}//说明页面已经存在
        		var  num2= noCodeNum(barCode);
        		if(num2!=0){callCusPop("页面已经存在此条形码....");$(".num-info").val("");$(".code-history p").remove() ;;return  false;}//说明页面已经存在
        		showPage(barCode);
        		$("#selectValue").val("");//清空参数
	          	}else{
	          		callCusPop("请输入正确的条形码....");return  false;
	          	}
        	}else{
//        		alert("执行select事件");
        		//取select的值  .也就是select的值
        		var barCode=$("#selectValue").val();
        		var  num1= codeNum(barCode);
        		if(num1!=0){callCusPop("页面已经存在此条形码....");$("#selectValue").val("onblurFail");return  false;}//说明页面已经存在
        		var  num2= noCodeNum(barCode);
        		if(num2!=0){callCusPop("页面已经存在此条形码....");$("#selectValue").val("onblurFail");return  false;}//说明页面已经存在
        		showPage(barCode);
        		$("#selectValue").val("");//清空参数
        	}
    	},200);
    	
    	
    	
    });
    //手动输入条形码事件num-info
    $(".num-info").on('keyup', function(event) {

    	$(".close-ico").show();
    	var num = $(".num-info").val();
    	if(num.length>5){
	    	if( num ){ 
	              $.ajax({
	  	    		type : "post",
	  	    		url : $("#webUrl").val()+"/shouCodeList.html",
	  	    		data : {
	  	    			  "code":num,
	  	    			  "isShow10":1
	  	    		},
	  	    		async : false,
	  	    		success : function(data){
	  	    			console.debug(data);
	  	    			 var strs= new Array(); //定义一数组 
	  	    			  strs= data.split(",");
	  	    				$(".code-history p").remove() ;
	  	    			  for(var i=0;i<strs.length;i++){
	  	    				 var  tt= strs[i];
	  	    				 if(tt!=''){
	  	    					 $(".code-history").append("<p  class='slectBarCode' >"+tt+"</p>")
	  	    				 }
	  	    				}
	  	    			  if(strs.length!=1){
	  	    		    	document.getElementById('here').scrollIntoView();
	  	    		    	$(".pop-sm").remove();
	  	    			  }
	  	    		},
	  	    		error : function(){	callCusPop("系统繁忙，请刷新重试...."); 		}
	  	    	});
	            //符合8-13位数字显示
//	          	var reg = /^\d+$/;
//	          	if((num.match(reg))&&(parseInt(7)<parseInt(num.length))&&(parseInt(num.length)<parseInt(14))){
//	          	}else{
//	          	}
	    	    $(".code-history").show();
    	}
    	}else{
    		$(".code-history p").remove() ;
    	}
    });
    //选择选中的select  slectBarCode
    $(document).on("click", ".slectBarCode", function() {
    	$(".pop-sm").remove();
    	var barCode  = $(this).text();
    	var  selectValue =$("#selectValue").val();
    	if(selectValue=="onblurFail"){
    		var  num1= codeNum(barCode);
    		if(num1!=0){callCusPop("页面已经存在此条形码....");$("#selectValue").val("onblurFail");return  false;}//说明页面已经存在
    		var  num2= noCodeNum(barCode);
    		if(num2!=0){callCusPop("页面已经存在此条形码....");$("#selectValue").val("onblurFail");return  false;}//说明页面已经存在
    		showPage(barCode);
    		$("#selectValue").val("");//清空参数
    	}else{
    		$("#selectValue").val(barCode);
    	}
    });
    
    
    //选购数量输入框校验事件
    $(document).on("keyup", ".num-infos", function() {
    	var  value= $(this).val();
    	if((0>value)||(value>999999)){callCusPop("采购数量只能在1--999999");  	}
    });
    
    //删除当前商品
    $(document).on("click", ".swiper-delete", function() {
    	$(this).parent().parent().parent().parent().remove();
    });
    //图片一删除
    $(document).on("click", ".spandiv1", function() {
    	$(this).parent().parent().parent().find("[class='file-upload']").css("display","block");
//    	//1.判断第二张图路径是否为空
    	var  img2=$(this).parent().parent().parent().find("[name='imgHead2']").attr("src");
    	if(img2==''){
    		//第一张照片路径删除
    		$(this).parent().parent().parent().find("[name='imgHead1']").attr("src","");
    		//第一个div隐藏
    		$(this).parent().parent().parent().find("[class='div1']").css("display","none");
    		$(this).parent().parent().parent().find("[class='li1']").css("display","none");
    	}else{
    		//取第二个图片路径给第一个图片
    		$(this).parent().parent().parent().find("[name='imgHead1']").attr("src",img2);
    		//判断第三个图片
    		var  img3=$(this).parent().parent().parent().find("[name='imgHead3']").attr("src");
    		 if(img3==''){
    			 //第二个图片路径删除
    			 $(this).parent().parent().parent().find("[name='imgHead2']").attr("src","");
    			 //第二个div隐藏
    			 $(this).parent().parent().parent().find("[class='div2']").css("display","none");
    			 $(this).parent().parent().parent().find("[class='li2']").css("display","none");
    		 }else{
    			 //第三张图片路径给第二张
    			 $(this).parent().parent().parent().find("[name='imgHead2']").attr("src",img3);
    			 //判断第四个图片
    	          var  img4=$(this).parent().parent().parent().find("[name='imgHead4']").attr("src");
    	          if(img4==''){
    	        	  //第三个图片路径删除
    	        	  $(this).parent().parent().parent().find("[name='imgHead3']").attr("src","");
    	        	  //第三个div隐藏
    	        	  $(this).parent().parent().parent().find("[class='div3']").css("display","none");
    	        	  $(this).parent().parent().parent().find("[class='li3']").css("display","none");
    	          }else{
    	        	//第四张图片路径给第三张
    	        	  $(this).parent().parent().parent().find("[name='imgHead3']").attr("src",img4);
    	        	  //判断第五个图片
    	        	  var  img5=$(this).parent().parent().parent().find("[name='imgHead5']").attr("src");
    	        	  if(img5==''){
    	        		  //第四个图片路径删除
    	        		  $(this).parent().parent().parent().find("[name='imgHead4']").attr("src","");
    	        		  //第四个div隐藏
    	        		  $(this).parent().parent().parent().find("[class='div4']").css("display","none");
    	        		  $(this).parent().parent().parent().find("[class='li4']").css("display","none");
    	        	  }else{
    	    	        	//第五张图片路径给第四张
        	        	  $(this).parent().parent().parent().find("[name='imgHead4']").attr("src",img5);
    	        		  //第五个图片路径删除
    	        		  $(this).parent().parent().parent().find("[name='imgHead5']").attr("src","");
    	        		  //第五个div隐藏
    	        		  $(this).parent().parent().parent().find("[class='div5']").css("display","none");
    	        		  $(this).parent().parent().parent().find("[class='li5']").css("display","none");
    	        	  }
    	          }
    		 }
    	}
    });
    
  //图片二删除
    $(document).on("click", ".spandiv2", function() {
    	$(this).parent().parent().parent().find("[class='file-upload']").css("display","block");
////    	//1.判断第三张图路径是否为空
    	var  img3=$(this).parent().parent().parent().find("[name='imgHead3']").attr("src");
    	if(img3==''){
			 //第二个图片路径删除
			 $(this).parent().parent().parent().find("[name='imgHead2']").attr("src","");
			 //第二个div隐藏
			 $(this).parent().parent().parent().find("[class='div2']").css("display","none");
			 $(this).parent().parent().parent().find("[class='li2']").css("display","none");
    	}else{
    		//第三张图片路径给第二张
    		 $(this).parent().parent().parent().find("[name='imgHead2']").attr("src",img3);
    		//判断第四张图片
    		var  img4=$(this).parent().parent().parent().find("[name='imgHead4']").attr("src");
    		if(img4==''){
	        	  //第三个图片路径删除
	        	  $(this).parent().parent().parent().find("[name='imgHead3']").attr("src","");
	        	  //第三个div隐藏
	        	  $(this).parent().parent().parent().find("[class='div3']").css("display","none");
	        	  $(this).parent().parent().parent().find("[class='li3']").css("display","none");
    		}else{
    			//第四张图片路径给第三张
	        	  $(this).parent().parent().parent().find("[name='imgHead3']").attr("src",img4);
	      		//判断第五张图片
	      		var  img5=$(this).parent().parent().parent().find("[name='imgHead5']").attr("src");
	      		if(img5==''){
	      			//第四个图片路径删除
	      			$(this).parent().parent().parent().find("[name='imgHead4']").attr("src","");
	      			//第四个div隐藏
	      			$(this).parent().parent().parent().find("[class='div4']").css("display","none");
	      			$(this).parent().parent().parent().find("[class='li4']").css("display","none");
	      		}else{
	      		     //第5张图片路径给第4张
		        	  $(this).parent().parent().parent().find("[name='imgHead4']").attr("src",img5);
		      			//第5个图片路径删除
		      			$(this).parent().parent().parent().find("[name='imgHead5']").attr("src","");
		      			//第5个div隐藏
		      			$(this).parent().parent().parent().find("[class='div5']").css("display","none");
		      			$(this).parent().parent().parent().find("[class='li5']").css("display","none");
	      		}
    		     }
    		
    	 }
    	
    	 
    });
    //图片三删除
    $(document).on("click", ".spandiv3", function() {
    	$(this).parent().parent().parent().find("[class='file-upload']").css("display","block");
    	var  img4=$(this).parent().parent().parent().find("[name='imgHead4']").attr("src");
        if(img4==''){
      	  //第三个图片路径删除
      	  $(this).parent().parent().parent().find("[name='imgHead3']").attr("src","");
      	  //第三个div隐藏
      	  $(this).parent().parent().parent().find("[class='div3']").css("display","none");
          $(this).parent().parent().parent().find("[class='li3']").css("display","none");
        }else{
      	//第四张图片路径给第三张
      	  $(this).parent().parent().parent().find("[name='imgHead3']").attr("src",img4);
          var  img5=$(this).parent().parent().parent().find("[name='imgHead5']").attr("src");
          if(img5==''){
        	  //第四个图片路径删除
        	  $(this).parent().parent().parent().find("[name='imgHead4']").attr("src","");
        	  //第四个div隐藏
        	  $(this).parent().parent().parent().find("[class='div4']").css("display","none");
        	  $(this).parent().parent().parent().find("[class='li4']").css("display","none");
          }else{
            	//第5张图片路径给第4张
          	  $(this).parent().parent().parent().find("[name='imgHead4']").attr("src",img5);
        	  //第5个图片路径删除
        	  $(this).parent().parent().parent().find("[name='imgHead5']").attr("src","");
        	  //第5个div隐藏
        	  $(this).parent().parent().parent().find("[class='div5']").css("display","none");
        	  $(this).parent().parent().parent().find("[class='li5']").css("display","none");
        	  
          }
        }
    });
    //图片四删除
    $(document).on("click", ".spandiv4", function() {
    	$(this).parent().parent().parent().find("[class='file-upload']").css("display","block");
    	var  img5=$(this).parent().parent().parent().find("[name='imgHead5']").attr("src");
    	if(img5==''){
    		//第四个图片路径删除
    		$(this).parent().parent().parent().find("[name='imgHead4']").attr("src","");
    		//第四个div隐藏
    		$(this).parent().parent().parent().find("[class='div4']").css("display","none");
    		$(this).parent().parent().parent().find("[class='li4']").css("display","none");
    	}else{
    		 $(this).parent().parent().parent().find("[name='imgHead4']").attr("src",img5);
    		//第5个图片路径删除
    		$(this).parent().parent().parent().find("[name='imgHead5']").attr("src","");
    		//第5个div隐藏
    		$(this).parent().parent().parent().find("[class='div5']").css("display","none");
    		$(this).parent().parent().parent().find("[class='li5']").css("display","none");
    	}
    });
    //图片5删除
    $(document).on("click", ".spandiv5", function() {
    	$(this).parent().parent().parent().find("[class='file-upload']").css("display","block");
    		//第5个图片路径删除
    		$(this).parent().parent().parent().find("[name='imgHead5']").attr("src","");
    		//第5个div隐藏
    		$(this).parent().parent().parent().find("[class='div5']").css("display","none");
    		$(this).parent().parent().parent().find("[class='li5']").css("display","none");
    });
    
    
    $(".close-ico").on("click",function(){
    	$(".num-info").val("");
    	$(".close-ico").css("display","none");
    	$(".code-history p").remove() ;
    	$("#selectValue").val("");
    });
    
    
    $(".num-info").on("keyup",function(){
  
    	    $("#selectValue").val("");
    	    
        
    });
    

    
    // 下一步操作
    $(".next-btn").on("click",function(){
    	callCusPop("正在检查数据....");
    	var reg = /^\d+$/;
    	 var  status    =  true  ;
    	//调转到下一步
    	//获取   已选和未扫描到的 barcode  数据传到下一页
    	 //1.获取扫码扫到的商品进行校验
    	var ary = new Array();
   	     $(".code1").each(function(){
   	    	var  barcode = $(this).find("input").val();
   	    	ary.push(barcode);
   	    	var   num= $("#"+barcode).val();
   	    	if (!num.match(reg)){
   	    		callCusPop("请输入正确的采购数量");
   	    		status= false;
   	    		return  false;
   	     	} 
   	    	if(num==''){
   	    		callCusPop("采购数量不能为空");
   	    		status= false;
   	    		return  false;
   	    				}
   	    	if((num<1)||(num>999999)){  
   	    	callCusPop("采购数量只能在1--999999");
	    		status= false;
   	    		return  false;
   	    	}
   	    	ary.push(num);
   	    	ary.push("-");
    	  });
   	    var  date  = ary.toString();
   	    
   	    //遍历未扫描的item  nocode
   	    var ary1 = new Array();
	   	 var a = [];// 创建数组//a.unshift(); // 添加到第一个位置
	     $(".nocode").each(function(){
	    	 var  barcode = $(this).find("input").val();
	    	 var  barCodeLength=barcode.length;
	    	 if((!barcode.match(reg))||(parseInt(8)>parseInt(barCodeLength))||(parseInt(barCodeLength)>parseInt(13))){
  				callCusPop("请输入正确的8-13位数字条形码");
   	    		status= false;
   	    		return  false;
	    	 }
	    	    //自定义条形码校验
	    	 //1.后台纯在性校验ajax
	         $.ajax({
	     		type : "post",
	     		url : $("#webUrl").val()+"/shouCodeList.html",
	     		data : {"code":barcode},
	     		async : false,
	     		success : function(data){
	     			if(data!=""){
	     				callCusPop("条形码:"+barcode+"已在系统维护,不可使用");
	       	    		status= false;
	       	    		return  false;
	     			}
	     		},
	     		error : function(){
	     			callCusPop("系统繁忙....");
       	    		status= false;
       	    		return  false;
	     		}
	     	});
	    	 //2.页面存在性校验
	         if(a.toString().indexOf(barcode)>-1){   //存在
	        	 callCusPop("提交存在相同的条形码"+barcode);
	        	 status= false;
    	    	 return  false;
	            }
	        	 a.push(barcode);
		    	 ary1.push(barcode);
		    	var  num = $(this).find(".num-infos").val();
	   	    	if (!num.match(reg)){
	   	    		callCusPop("请输入正确的采购数量");
	   	    		status= false;
	   	    		return  false;
	   	     	} 
		    	if((num<1)||(num>999999)){  
	   	    		callCusPop("采购数量在1--999999");
	   	    		status= false;
	   	    		return  false;
	   	    	           }
		    		ary1.push(num);
		    	var   imageSrc1=$(this).find("li").eq(0).find("img").attr("src");
		    	if(imageSrc1!=''){	ary1.push(imageSrc1);}
		    	
		    	var   imageSrc2=$(this).find("li").eq(1).find("img").attr("src");
		    	if(imageSrc2!=''){	ary1.push(imageSrc2);}
		    	
		    	var   imageSrc3=$(this).find("li").eq(2).find("img").attr("src");
		    	if(imageSrc3!=''){	ary1.push(imageSrc3);}
		    	
			    var   imageSrc4=$(this).find("li").eq(3).find("img").attr("src");
			    if(imageSrc4!=''){	ary1.push(imageSrc4);}
			    
			    var   imageSrc5=$(this).find("li").eq(4).find("img").attr("src");
			    if(imageSrc5!=''){	ary1.push(imageSrc5);}
			    if(imageSrc1==''){
		    		callCusPop("条形码:"+barcode+"至少上传一张照片");
		    		status= false;
		    		return  false;
			    }
		    	ary1.push("!-!");//图3地址	
//	     alert(JSON.stringify(ary1));
	     });
	     //已经选
	     if(status){
	    	 if((date=="")&&(ary1=="")){
	    		 callCusPop("您未选中任何商品");
	    		 return  false;
	    	 }
	    	 window.location.href=$("#webUrl").val()+"/toConfirmPurchasing.html?date="+date+"&date1="+ary1.toString();
	     }
    	
    });
    
    //readey事件
    $(function(){
    	 //图片上传点击事件
        $('#uploadBasicInfoHead').on('click', function() {
        	$("input[name='basicInfoHead']").click();
		});
        
        //点击图片放大
        $(document).on("click", ".lazy", function() {
        	var  n= $(this).attr("name");
        	var  liList="" ;
        	var  i=0;
        	var  j=0;
        	 $(this).parent().parent().parent().find("[class='lazy']").each(function(){
        		 var  src = $(this).attr("src");
        		 if(src!=""){
        			 i=i+1;
        			 if($(this).attr("name")==n){
        				 j=i;
        			 }
        				 //拼装成一个li集合数据
//        			 src=src+"?x-oss-process=image/resize,m_fixed,w_200,h_200";
              	 liList =  liList+ '<li class="swiper-slide half-two"><a href="javascript:;"><img src="'+src+'" alt=""></a></li>';
        		           }
        		 
        	 })
//        	 console.log(liList);
        	 //加载整个轮播元素div
        	 var  allHtml='<div class="mask"   style="z-index:15"></div> <div class="swiper-container" id="touchSlide"><ul class="swiper-wrapper">'+liList+'</ul><div class="swiper-pagination"><ul></ul></div></div>';
        	 $(".lunbo").append(allHtml),
        	 setTimeout(function(){
        		 var index=  0;
        		 if(n=="imgHead1"){
        			 index=  0;
     			}else if(n=="imgHead2"){
     				 index=  1;
     			}else if(n=="imgHead3"){
     				 index= 2;
     			}else if(n=="imgHead4"){
     				 index=  3;
	        	 }else if(n=="imgHead5"){
	        		 index=  4;
	        	 };
        		 $(".lunbo").css("display","block");
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
        			
        			
        	 }
        			 ,1000);
        });
        //隐藏图片
        $(document).on("click", ".swiper-container", function() {
        	$(".lunbo").css("display","none");
        	$(".mask").remove();
        	$(".swiper-container").remove();
        });
        
        
        //未查到商品条形码手动输入校验
        $(document).on("keyup", ".no-code", function() {
        	var va =$(this).find("input").val();
        	$(this).parent().parent().find("font").text(va);
        	
        	//去除下面的图片地址
        	$(this).parent().parent().find("ul li div img").attr("src","");
        	$(this).parent().parent().find("ul li div ").css("display","none");
        	$(this).parent().parent().find("[class='file-upload']").css("display","block");
        });
        $(document).on("blur", ".no-code input", function() {
        	  var va =$(this).val();
        		var reg = /^\d+$/;//正则匹配
	          	if(!va.match(reg)){  callCusPop("请输入正确的8-13位数字条形码"); return false;	 	}
        		if(va.length<8){callCusPop("条形码必须大于八位数"); return false; }
        		//本页面中校验不可以存在  相同的自定义code
                var num = 0;
                $(".no-code").find("input").each(function(){ if($(this).val()==va){ num  =num+1;} });
                if(num>1){ callCusPop("存在相同的条形码"+va);$(this).parent().parent().parent().parent().parent().remove();return  false;} 
        		
                var n= codeNum(va);
                if(n!=0){callCusPop("存在相同的条形码"+va);$(this).parent().parent().parent().parent().parent().remove();return  false;} 
                showPage(va);
                $(this).parent().parent().parent().parent().parent().remove();
        
        
        });
        
      
        
    });//ready事件加载结束
    
    
    
    // 错误提示
    function callCusPop(text){
    	$("#errorMsg").html(text);
    	$(".toast").show();
    	setTimeout(function(){
    		$(".toast").hide();
    	}, 2000);
    };
    
      //获取页面里的扫出来的bacode数量
      var   codeNum=    function(barCode){
             var array = new Array();
             $(".code1").find("input").each(function(){
             	array.push($(this).val());
              });
             var  num =0;
             for (var int = 0; int < array.length; int++) {
 				if(array[int]==barCode){
 					num=num+1;
 				}
 			}
             return   num ;
         }
    
	    //获取页面里的胃扫描出来的bacode数量
	    function noCodeNum(barCode){
            var array = new Array();
            $(".no-code").find("input").each(function(){
            	array.push($(this).val());
             });
            var  num =0;
            for (var int = 0; int < array.length; int++) {
				if(array[int]==barCode){
					num=num+1;
				}
			}
            return   num ;
	    }
    //barCode传递后台, 生成有商品页面或者新建商品页面
    function showPage(date){
    	 $.ajax({
	    		type : "post",
	    		url : $("#webUrl").val()+"/goods.html",
	    		data : {"id":date},
	    		async : false,
	    		success : function(data){
	    			console.debug(data);
	    				$("#addCode").append(data);
	    				$(".next-btn").show();
	    				$(".code-history p").remove() ;
	    				$(".num-info").val("") ;
	    				
	    		},
	    		error : function(){
	    			callCusPop("系统繁忙，请刷新重试....");
	    		}
	    	});
	        $('.swiper-slide').swiper_around();
    }
    
});
