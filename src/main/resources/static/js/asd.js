function asd() {
let tmp;
    // let response = await fetch('/welcome');


    $.ajax({

        url: '/welcome',
        datatype: 'json',
        type: "post",
        contentType: "application/json",
        success: function (data) {
            temp = data;
        }

    });



}