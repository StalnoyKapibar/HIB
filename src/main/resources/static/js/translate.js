function sendTextToTranslate() {
    $.ajax({
        type: "POST",
        url: "/translate",
        data: $("#transText").val(),
        success: function (data) {
            $("#translatedText").val(data);
        }
    });
}