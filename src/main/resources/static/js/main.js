function status(response) {
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}

$('#ddMenu').on('click', 'a', async function (eventOnInnerTag) {
    await fetch("/lang/" + eventOnInnerTag.target.text)
        .then(status)
        .then(json)
        .then(function (data) {
            $('#dropdownMenuLink').text(eventOnInnerTag.target.text);
    });
});
