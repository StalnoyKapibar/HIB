// $('#dd_menu').on('click','a',function (event) {
//     $.ajax({
//             type: "GET",
//             contentType: "application/json",
//             url: "/lng/" + event.target.text,
//             success: function (data) {
//                 $('#dropdownMenuLink').text(event.target.text);
//                 // запустим метод на перерисовку страницы
//             },
//             error: function (data) {
//                 console.log('ERROR: ' + data)
//             }
//         }
//     );
//
// });
$('#dd_menu').on('click', 'a', async function (event_on_inner_tag) {
    var response = await fetch("/lng/" + event_on_inner_tag.target.text);
    if(response.ok){
        console.log(response.headers.get("Content-Type"));
        $('#dropdownMenuLink').text(event_on_inner_tag.target.text);
    }else {
        console.log('ERROR' + response);
    }
});