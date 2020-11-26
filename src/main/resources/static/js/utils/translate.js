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
function findRadioButton(name) {
    let inp = document.getElementsByName(name);
    for (let i = 0; i < inp.length; i++) {
        if (inp[i].type == "radio" && inp[i].checked) {
            return  inp[i].value;
        }
    }
}

function findCheckBox(name, unnecessaryLanguage) {
    let inp = document.getElementsByName(name);
    let checkboxesChecked = [];
    for (let i = 0; i < inp.length; i++) {
        if(inp[i].type == "checkbox" && inp[i].checked && inp[i].value != unnecessaryLanguage){
            checkboxesChecked.push(inp[i].value);
        }
    }
    return checkboxesChecked;
}

function translateText(label) {
    let checkedRadioButton = findRadioButton('rb'+label);
    let checkedCheckBox = findCheckBox('cb'+label, checkedRadioButton);
    let text = $("#inp"+label+checkedRadioButton).val();
    if (checkedRadioButton==null){
        alert("Check Lang from");
        return;
    }
    if(text == ''){
        alert("Enter text")
        return;
    }
    if (checkedCheckBox.length == 0){
        alert("Check Lang to");
        return;
    }
    let j = {
        langFrom: checkedRadioButton,
        arrLangTo: checkedCheckBox,
        text: text
    };
    let prop = JSON.stringify(j);
    $.ajax({
        type: "POST",
        url: "/translate/list",
        data: prop,
        contentType: 'application/json',
        success: function (data) {
            for (let key in data) {
                let inp = $("#inp"+label+key);
                inp.val(data[key]);
            }
        }
    })
}

// for Transliterate
function transliterationText(label) {
    let text = $("#inp" + label).val();
    fetch('/api/transliteration', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(text),
    }).then(function (response) {
        response.text().then(function (data) {
            $("#in" + label).val(data)
        })
    })
}