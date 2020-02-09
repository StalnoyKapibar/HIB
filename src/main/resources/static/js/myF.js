
    function myFunction() {

        //function which send ajax request to the server
        var username = $('#username').val();
        var password = $('#password').val();
        var a=$('input:checked'); //выбираем все отмеченные checkbox
        var out=[]; //выходной массив
        var List = new Array();
        for (var x=0; x<a.length;x++){ //перебераем все объекты
            out.push(a[x].value); //добавляем значения в выходной массив
        }
        var array = getTemp();



        for (let item of array) {

            var qqq = 0;
            for (let item00 of out) {
                qqq = item00;
                var dig = item.id;
                if (dig == qqq) {
                    List.push(item);

                }

            }

        }
        List.filter(x => array.includes(x))

        var roles = new Set(List);

        alert(out);

        $.ajax({

            url: '/admin/add',
            datatype: 'json',
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({
                username: username,
                password: password,
                roles: roles,
            }),
            success: function (data) {


            }
        });

    }
