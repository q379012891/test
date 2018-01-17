require(['comm', 'config'], function() {
    require(['vendor', 'touchslider'], function() {
        $.each($('.scroll-list'), function(index, val) {
             $(this).children('.ovfs').attr('id', 'j-scroll-'+ index);
             $('#j-scroll-'+index+' img.lazy-x').lazyload({
                container: '#j-scroll-'+index
             });
        });
    })
})
