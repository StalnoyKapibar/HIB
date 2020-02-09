
    function startProgress() {

        $.ajax({

            url: '/admin/alcoves',
            datatype: 'json',
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({}),
            success: function (data) {





                /*
                  let b = document.getElementById("border");
                  data.forEach(e => b.innerHTML += "<span>" + e.role + "</span>");

                  let bc = document.getElementById("id");
                  data.forEach(ec => bc.innerHTML += "<span>" + ec.role + "</span>"); */


                // $(data).each(function (i, user));

                for (let item of data) {
                    $("#border").append("<div class=\"form-check-inline\">\n" +
                        "                                        <label class=\"font-weight-bold form-check-label\" >\n" +
                        "                                            <input type=\"checkbox\"\n" +
                        "\n" +
                        "                                                   class=\"form-check-input\"\n" +


                        "                                                   value=\"" + item.id + "\">\n" +
                        "                                            <span class=\"text-uppercase\"\n" +
                        "                                                  id=\"exampleInput\">" + item.role + "</span>\n" +
                        "                                        </label>\n" +
                        "                                    </div>");
                    // $("#id").text(item.id);
                    //   $("#exampleInput").text(item.role);
                }
            }
        });

    }
