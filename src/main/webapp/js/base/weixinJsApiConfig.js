function rand(num) {
		len = num;
		var res = '';
		var chars = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
		for (i = 0; i < len; ++i) {
			res += chars.charAt(parseInt(chars.length * Math.random()));
		}
		return res;
	};

	
	
	var timestamp_val = Math.floor(new Date().getTime()/1000);
	var nonceStr_val = rand(16);

	var url_total = window.location.href;
	var url_val = '';
	var patt = new RegExp('#');// 要查找的字符串为'#'

	if (patt.test(url_total)) {// 字符串存在返回true否则返回false
		var s = url_total.indexOf(patt);
		url_val = url_total.substr(0, s - 1);
	} else {
		url_val = url_total;
	}

	
	var string1 = "jsapi_ticket=" + jsApi_ticket_val + "&noncestr=" + nonceStr_val + "&"+"timestamp=" + timestamp_val + "&url=" + url_val;
    var signature_val = $.sha1(string1);
	var jsApiList_val = [ 'checkJsApi','onMenuShareTimeline', 'onMenuShareAppMessage',
			'onMenuShareQQ', 'onMenuShareWeibo', 'startRecord', 'stopRecord',
			'onVoiceRecordEnd', 'playVoice', 'pauseVoice', 'stopVoice',
			'onVoicePlayEnd', 'uploadVoice', 'downloadVoice', 'chooseImage',
			'previewImage', 'uploadImage', 'downloadImage', 'translateVoice',
			'getNetworkType', 'openLocation', 'getLocation', 'hideOptionMenu',
			'showOptionMenu', 'hideMenuItems', 'showMenuItems',
			'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem', 'closeWindow',
			'scanQRCode', 'chooseWXPay', 'openProductSpecificView', 'addCard',
			'chooseCard', 'openCard' ];

	wx.config({
		debug:false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId:appId_val, // 必填，公众号的唯一标识
		timestamp:timestamp_val, // 必填，生成签名的时间戳
		nonceStr:nonceStr_val, // 必填，生成签名的随机串
		signature:signature_val,// 必填，签名，见附录1
		jsApiList:jsApiList_val
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	
	
	wx.error(function(res){
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。

//		alert(res.errMsg);
	});