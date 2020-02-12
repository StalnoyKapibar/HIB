function getrolesbyid() {

    var id = $('#exampleInputId').val();



    $.ajax({

        url: '/admin/getrolebyid/'+id,
        datatype: 'json',
        type: "get",
        contentType: "application/json",
        success: function (data) {

            for (let item of data) {
                $("#border").append("<div class='form-check-inline'>" +
                    "<label class='font-weight-bold form-check-label' >" +
                    "<input type='checkbox' class='form-check-input' id = '" + item.id+"' value = '" + item.role+"'>" +

                    "                                " + item.role+"        </label>" +
                    "                                    </div>");

            }
        }
    });

}