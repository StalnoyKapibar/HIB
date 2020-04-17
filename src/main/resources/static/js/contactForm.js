let userData;
let hiddenButton = $('#hidden-submit-btn-feedback');
let senderNameInput = $("#sender-name");
let senderEmailInput = $("#sender-email");
let senderMessageInput = $("#sender-message");
let feedbackModal = $("#feedback-modal");
let senderName = $('#sender-name');
let senderEmail = $('#sender-email');
const messageTemplate = "Hello, I m interested in the book ";

$(document).ready(getUserData());

function getUserData() {
    fetch("/cabinet/getAU")
        .then(status)
        .then(json)
        .then(function (resp) {
            userData = resp;
            senderEmail.val(userData.email);
            senderName.val(userData.firstName + " " + userData.lastName);
        });
}

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

$(document).on('click', '#send-feedback-request', () => {
    hiddenButton.click();
});

$('#feedback-form').submit(async () => {
    let FeedbackRequest = {
        senderName: senderNameInput.val(),
        senderEmail: senderEmailInput.val(),
        content: senderMessageInput.val()
    };
    await fetch("/api/feedback-request", {
        method: 'POST',
        body: JSON.stringify(FeedbackRequest),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    feedbackModal.modal('hide');
});

$(document).on('click', '#ask-question', async () => {
    let location = window.location + '';
    senderMessageInput.val(messageTemplate + '' + location.substr(0, location.indexOf("?")));
    feedbackModal.modal('show');
});