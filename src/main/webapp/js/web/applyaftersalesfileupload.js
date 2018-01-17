		//上传头像，触发click方法
		var i = 1;
		function uploadHead(b) {
		var  fileid=	$(b).attr("id");
			var filemaxsize = 1024*1024 * 5;//5M 
			var f = document.getElementById(fileid).value;
			 
			if (f == "") {
				callCusPop("请上传图片");
				return false;
			} else {
				if (!/\.(jpg|png|JPG|PNG|BMP|bmp)$/.test(f)) {
					callCusPop("图片类型只能是jpg,png,bmp"+f);
					return false;
				}
			};
			var f = document.getElementById(fileid).files;
			if(parseInt(f[0].size)>parseInt(filemaxsize)){
				callCusPop("图片不能大于5M");
				return false;
			};
			$.ajaxFileUpload({
				url :  $("#webUrl").val()+"/file/uploadImage.html",//需要链接到服务器地址 
				secureuri : false,
				fileElementId : fileid,//文件选择框的id属性
				dataType : 'json', //json
				success : function(data) {
					callCusPop("上传成功");
					var  a=$(b).parent().parent().find(".div1").css("display");
					if($("."+fileid).find(".div1").css("display")=='none'){
						$("."+fileid).find(".li1").css("display","block")
						$("."+fileid).find(".div1").css("display","block")
						$("."+fileid).find(".div1").find("[name='imgHead1']").attr("src", $("#baseurl").val()+data);
					}else{
						if($("."+fileid).find(".div2").css("display")=='none'){
							$("."+fileid).find(".li2").css("display","block")
							$("."+fileid).find(".div2").css("display","block")
							$("."+fileid).find(".div2").find("[name='imgHead2']").attr("src", $("#baseurl").val()+data);
						}else{
							if($("."+fileid).find(".div3").css("display")=='none'){
								$("."+fileid).find(".li3").css("display","block")
								$("."+fileid).find(".div3").css("display","block")
								$("."+fileid).find(".div3").find("[name='imgHead3']").attr("src",$("#baseurl").val()+data);
							}
							else{
								if($("."+fileid).find(".div4").css("display")=='none'){
									$("."+fileid).find(".li4").css("display","block")
									$("."+fileid).find(".div4").css("display","block")
									$("."+fileid).find(".div4").find("[name='imgHead4']").attr("src",$("#baseurl").val()+data);
									//影藏上传控件
								
								}else{
									if($("."+fileid).find(".div5").css("display")=='none'){
										$("."+fileid).find(".li5").css("display","block")
										$("."+fileid).find(".div5").css("display","block")
										$("."+fileid).find(".div5").find("[name='imgHead5']").attr("src",$("#baseurl").val()+data);
										//影藏上传控件
										$("."+fileid).find(".file-upload").css("display","none");
									}
								}
							}
							
						}
					}
					//封装放大图数据参数
//					var mi  =0;
//					$("."+fileid).find("img").each(function(){
//						var  src= $(this).attr("src");
//						if(src!=""){
//							mi=mi+1;
//						}
//					});
//					if(mi==4){
//						$("."+fileid).find(".file-upload").css("display","none");
//					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					callCusPop("上传失败");
				}
			});
		};
		
	    
	    // 错误提示
	    function callCusPop(text){
	    	$("#errorMsg").html(text);
	    	$(".toast").show();
	    	setTimeout(function(){
	    		$(".toast").hide();
	    	}, 2500);
	    };