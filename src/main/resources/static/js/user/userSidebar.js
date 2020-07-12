let categoryRow;
let valueInput = '';

$(document).ready(function () {
    $(document).keypress(function(event){
        let keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13'
            && $("#searchInput").val().trim() !== ''
            && !document.location.href.includes('search')) {
            $('#searchIcon').click();
        }
    });

    if (currentLang === '') {
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }
    getLanguage();
    setLocaleFields();
    getCategoriesLocal(currentLang);

});

$('#sidebar').mouseenter(() => {
    $('#mainNameInPage').text('History in books');
    $('#page-wrapper').removeClass('pinned');
});

$('#sidebar').mouseleave(() => {
    $('#mainNameInPage').text('HIB');
    $('#page-wrapper').addClass('pinned');
    $('.ul-books').hide();
});

function getCategoriesLocal(name) {
    fetch('/categories/getpanelcategories/' + name, {})
        .then(function (response) {
            return response.json()
        })
        .then(function (primaryCategories) {
            let count = 1;
            for (let i in primaryCategories) {
                categoryRow =
                    `<li>
                        <a href="/search/${[count]}">${primaryCategories[i]}</a>
                    </li>`;
                count++;
                $('#primaryCategories').append(categoryRow);
            }
        });
}


$(document).on('click', '#searchIcon', async () => {
    document.location = `/search?request=${$("#searchInput").val()}`
});

jQuery(function ($) {
    // Dropdown menu
    $(".sidebar-dropdown > a").click(function () {
        $(".sidebar-submenu").slideUp(200);
        if ($(this).parent().hasClass("active")) {
            $(".sidebar-dropdown").removeClass("active");
            $(this).parent().removeClass("active");
        } else {
            $(".sidebar-dropdown").removeClass("active");
            $(this).next(".sidebar-submenu").slideDown(200);
            $(this).parent().addClass("active");
        }
    });

    // Toggle sidebar
    $("#toggle-sidebar").click(function () {
        $(".page-wrapper").toggleClass("toggled");
    });
    // Pin sidebar
    $("#pin-sidebar").click(function () {
        if ($(".page-wrapper").hasClass("pinned")) {
            // Unpin sidebar when hovered
            $(".page-wrapper").removeClass("pinned");
            $("#sidebar").unbind("hover");
        } else {
            $(".page-wrapper").addClass("pinned");
            $("#sidebar").hover(
                function () {
                    console.log("mouseenter");
                    $(".page-wrapper").addClass("sidebar-hovered");
                },
                function () {
                    console.log("mouseout");
                    $(".page-wrapper").removeClass("sidebar-hovered");
                }
            )
        }
    });

    // Toggle sidebar overlay
    $("#overlay").click(function () {
        $(".page-wrapper").toggleClass("toggled");
    });

    // Switch between themes 
    var themes = "default-theme legacy-theme chiller-theme ice-theme cool-theme light-theme";
    $('[data-theme]').click(function () {
        $('[data-theme]').removeClass("selected");
        $(this).addClass("selected");
        $('.page-wrapper').removeClass(themes);
        $('.page-wrapper').addClass($(this).attr('data-theme'));
    });

    // Switch between background images
    var bgs = "bg1 bg2 bg3 bg4";
    $('[data-bg]').click(function () {
        $('[data-bg]').removeClass("selected");
        $(this).addClass("selected");
        $('.page-wrapper').removeClass(bgs);
        $('.page-wrapper').addClass($(this).attr('data-bg'));
    });

    // Toggle background image
    $("#toggle-bg").change(function (e) {
        e.preventDefault();
        $('.page-wrapper').toggleClass("sidebar-bg");
    });

    // Toggle border radius
    $("#toggle-border-radius").change(function (e) {
        e.preventDefault();
        $('.page-wrapper').toggleClass("border-radius-on");
    });

    // Custom scroll bar is only used on desktop
    if (!/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
        $(".sidebar-content").mCustomScrollbar({
            axis: "y",
            autoHideScrollbar: true,
            scrollInertia: 300
        });
        $(".sidebar-content").addClass("desktop");
    }
});
