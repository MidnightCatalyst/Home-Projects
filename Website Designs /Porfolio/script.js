//using script to program button to open the navbar on click

$(document).ready(function(){

    $('#menu').click(function(){
        $(this).toggleClass('fa fa-times'); //when the menu is clicked, it will change the icon to an x
        $('header').toggleClass('toggle'); //and then, the header will toggle given header.toggle which we then do in css
    });
 //at this point, when stretching the window back to normal, the menu will not disappear. so we need to add a media query in css

    $(window).on('scroll load', function(){
        $('#menu').removeclass('fa fa-times'); //when the window is stretched, the menu/X button will disappear
        $('header').removeclass('toggle'); //and the header will also disappear
    });


    //smooth scrolling
    
    $('a[href*="#"]').on('click', function(e){
        e.preventDefault();

        $('html, body').animate({
            scrollTop : $($(this).attr('href')).offset().top,
        },
        500,
        'linear'
        );
    });
});