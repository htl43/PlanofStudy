$(document).ready(function() {

	var $header = $("header");
	var $sticky = $header.before($header.clone().addClass("sticky"))

	$(window).on("scroll", function() {
		var ScrollFromTop = $(window).scrollTop();
		$("body").toggleClass("scroll", (ScrollFromTop > 350));
	});


// FILLING TEXT 
$(function() {
  $('.intro').addClass('go');

  $('.reload').click(function() {
    $('.intro').removeClass('go').delay(200).queue(function(next) {
      $('.intro').addClass('go');
      next();
    });

  });
})
// FILLING TEXT ENDS 
});