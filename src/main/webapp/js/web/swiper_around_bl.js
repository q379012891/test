$.fn.swiper_around = function(v) {
	var g, i, n, l, t, m = $(this),
		r, j,tempy,tempY;
	var u = m.find(".swiper-left"),
		p = m.find(".swiper-right");
	var a = u.width();
	var b = p.width();
	var d = m.width();
	v ? r = v : r = "L-R";
	var j = -b / a * 100;
	var k = function() {
		if(r == "L-R") {
			j = -b / a * 100
		} else {
			if(r == "R-L") {
				j = -a / b * 100
			}
		}
	};
	$(window).resize(function() {
		a = u.width();
		b = p.width();
		j = -b / a * 100;
		m.css({
			"transition": "all " + 0 + "s linear ",
			"-webkit-transition": "all " + 0 + "s linear ",
			"transform": "translate3d(0px,0px,0px)",
			"-webkit-transform": "translate3d(0px,0px,0px)"
		});
		l = 0;
		m.attr("now_move", l)
	});
	var q, f, c = false,
		e = true,
		h = -1;
	var s = function(w) {
		w.css({
			"transition": "all " + 0.2 + "s linear ",
			"-webkit-transition": "all " + 0.2 + "s linear ",
			"transform": "translate3d(0px,0px,0px)",
			"-webkit-transform": "translate3d(0px,0px,0px)"
		});
		l = 0;
		w.attr("now_move", l)
	};
	var o = function(w) {
		w.css({
			"transition": "all " + 0.2 + "s linear ",
			"-webkit-transition": "all " + 0.2 + "s linear ",
			"transform": "translate3d(" + j / 100 * a + "px,0px,0px)",
			"-webkit-transform": "translate3d(" + j / 100 * a + "px,0px,0px)"
		});
		l = j;
		w.attr("now_move", l)
	};
	m.each(function(w, x) {
		var y = $(this);
		y[0].addEventListener("touchstart", function(z) {
			g = z.touches[0].clientX;
			tempy=z.touches[0].clientY;
			i = $(this).width();
			n = Number($(this).attr("now_move"));
			!n ? n = 0 : null;
			f = w;
			l = 0, t = 0;
			q = true, c = false
		});
		y[0].addEventListener("touchmove", function(z) {
			t = (z.touches[0].clientX - g) / i * 100;
			tempY= Math.abs(z.touches[0].clientY - tempy);
			if(Math.abs(t) > 10) {
				c = true
			}
			l = t + n;
			console.log(l >= j - 3 && l <= 0 + 3&&Math.abs(z.touches[0].clientX - g)>tempY);
			if(l >= j - 3 && l <= 0 + 3&&Math.abs(z.touches[0].clientX - g)>tempY) {
				console.log("111");
				$(this).css({
					"transition": "all 0s linear ",
					"-webkit-transition": "all 0s linear ",
					"transform": "translate3d(" + l + "%,0px,0px)",
					"-webkit-transform": "translate3d(" + l + "%,0px,0px)"
				}).attr("now_move", l);
				if(q) {
					q = false;
					m.each(function(A, B) {
						if(A != f) {
							$(B).css({
								"transition": "all " + 0.2 + "s linear ",
								"-webkit-transition": "all " + 0.2 + "s linear ",
								"transform": "translate3d(0px,0px,0px)",
								"-webkit-transform": "translate3d(0px,0px,0px)"
							}).attr("now_move", 0)
						}
					})
				}
			} 
			c ? z.preventDefault() : null
		});
		y[0].addEventListener("touchend", function(z) {
			if(l > j / 2) {
				if(Math.abs(t) > 5) {
					h = w;
					s($(this));
					e = true
				} else {
					if(h === w) {
						e ? s($(this)) : o($(this))
					}
				}
			} else {
				if(Math.abs(t) > 5) {
					h = w;
					o($(this));
					e = false
				} else {
					if(h === w) {
						e ? s($(this)) : o($(this))
					}
				}
			}
		})
	})
};
$('.swiper').swiper_around();