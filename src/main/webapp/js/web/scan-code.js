require(['vendor/vendor.min'], function(FastClick) {

  FastClick.attach(document.body);

  require(['swiper_around_bl','touchslider'], function() {
      //滑动删除
      $('.swiper-slide').swiper_around();
      //轮播图
        TouchSlide({
            slideCell: "#touchSlide",
            titCell: ".swiper-pagination ul",
            mainCell: ".swiper-wrapper",
            effect: "leftLoop",
            autoPlay: true,
            delayTime: 300,
            interTime: 2000,
            autoPage: true,
            switchLoad: "_src"
        });
        
        //按钮 下一步定位
        var bodyHeight=$(window).height();
        var btnHeight=$(".scan-btn").outerHeight();
        var contentHeight=bodyHeight - btnHeight;
        $('.scan-height').css({
          height: contentHeight + 'px',
          overflowY: 'auto'
        });
    });
});

