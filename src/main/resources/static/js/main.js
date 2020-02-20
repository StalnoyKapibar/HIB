$('#dd_menu').on('click', 'a', async function (event_on_inner_tag) {
    var response = await fetch("/lng/" + event_on_inner_tag.target.text);
    if(response.ok){
        $('#dropdownMenuLink').text(event_on_inner_tag.target.text);

        //reload страницы с
    }else {
        console.log('ERROR' + response);
    }
});