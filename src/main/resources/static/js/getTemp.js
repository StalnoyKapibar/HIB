function getTemp() {
    var ans;
     $.ajax({
        url: '/admin/alcoves',
        datatype: 'json',
        type: "post",
         async: false,
        contentType: "application/json",
        data: JSON.stringify({}),
        success: function (data) {
            ans = data;

        }
    });


return ans;

}