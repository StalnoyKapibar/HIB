
    function myFunction() {

        //function which send ajax request to the server
        var username = $('#username').val();
        var password = $('#password').val();
        var a=$('input:checked'); //выбираем все отмеченные checkbox
        var out=[]; //выходной массив
        var List = new Set();
        for (var x=0; x<a.length;x++){ //перебераем все объекты
            out.push(a[x].value); //добавляем значения в выходной массив
        }
        $.ajax({
            url: '/admin/alcoves',
            datatype: 'json',
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({}),
            success: function (allRoles0) {

                for (let item of allRoles0) {
                    List.add(item);
                    var qqq = 0;
                    for (let item00 of out) {
                        qqq = item00;
                        var dig = item.id;
                        if (dig == qqq) {
                            List.add(item);

                        }

                    }

                }




            }
        });






        alert(out);
        //   $obj = toObject($out);



        $.ajax({

            url: '/admin/add',
            datatype: 'json',
            type: "post",
            contentType: "application/json",
            charset: "utf-8",
            data: JSON.stringify({
                username: username,
                password: password,
                roles: List
            }),
            success: function (data) {


            }
        });

    }
